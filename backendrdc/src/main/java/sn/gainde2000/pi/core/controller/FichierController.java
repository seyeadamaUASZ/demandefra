/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Fichier;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.entity.Signature;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IActionService;
import sn.gainde2000.pi.core.service.IDemandeAutFraService;
import sn.gainde2000.pi.core.service.IFichierService;
import sn.gainde2000.pi.core.service.IProduitsService;
import sn.gainde2000.pi.core.service.IRapportService;
import sn.gainde2000.pi.core.service.ISignatureService;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.service.MailingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.RequestParam;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.core.tools.Email;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.StatusDocument;
import sn.gainde2000.pi.ged.service.IDocumentService;
import sn.gainde2000.pi.ged.service.ITypeDocumentService;
import sn.gainde2000.pi.integration.signature.cf.client.test.SOAPClient_3;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.SignatureResult;

/**
 *
 * @author Sagueye
 */
@RestController
public class FichierController {    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("dataSourceJasper")
    private DataSource dataSourceJasper;
    @Autowired
    private AppProperties app;
    @Autowired
    private IDemandeAutFraService demandeService;
    @Autowired
    ITypeDocumentService typeDoc;
    @Autowired
    IDocumentService documentService;
    @Autowired
    IFichierService fichierService;
    @Autowired
    IActionService actionService;
    @Autowired
    IRapportService rapportService;    
    @Autowired
    ApplicationContext context;
    @Autowired
    IUtilisateur userService;
    @Autowired
    IProduitsService produitsService;
    @Autowired
    ISignatureService signatureService;
    @Autowired
    Email email;

    /**
     * Suppresion d'un fichier
     *
     * @param fichier
     * @return
     */
    @PostMapping("fichier/delete")
    public ServeurResponse deletefichier(@RequestBody Fichier fichier) {
        ServeurResponse response = new ServeurResponse();
        fichierService.supprimer(fichier);
        response.setData(fichier);
        response.setStatut(true);
        return response;
    }

    /**
     * Recuperer listes de toutes les fichiers.
     *
     * @return
     */
    @GetMapping("fichier")
    public ServeurResponse getFichier() {
        ServeurResponse response = new ServeurResponse();
        List<Fichier> f = fichierService.getAllFichier();
        if (f == null) {
            response.setData(f);
            response.setStatut(false);
            response.setDescription("Aucune données recuperées");

        } else {
            response.setData(f);
            response.setStatut(true);
            response.setDescription("Données recuperées");

        }

        return response;
    }

    /**
     * Recuperer un fichier d'une application créée.
     *
     * @return
     */
    @GetMapping("fichierByApp")
    public ServeurResponse getFichierByApp() {
        ServeurResponse response = new ServeurResponse();
        List<Fichier> f = fichierService.getAllFichierLibre();
        if (f == null) {
            response.setData(f);
            response.setStatut(false);
            response.setDescription("Aucune données recuperées");

        } else {
            response.setData(f);
            response.setStatut(true);
            response.setDescription("Données recuperées");

        }

        return response;
    }

    /**
     * Export en pdf et envoie de message.
     *
     * @param request
     * @param response
     * @param logo
     * @param image
     */
    @PostMapping(path = "fichier/pdf")
    public ServeurResponse report(HttpServletRequest request) {
        ServeurResponse response1 = new ServeurResponse();
        try {        	
            Long fichierId = Long.parseLong(request.getParameter("fichier"));
            Long iddemande = Long.parseLong(request.getParameter("iddemande"));
            Long idsignature = Long.parseLong(request.getParameter("signature"));
            Signature signature = signatureService.getOneSignature(idsignature);    

            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);

            final Map<String, Object> parameters = new HashMap<>();

            try {
                Rapport rapport = rapportService.findById(fichierId);
                Optional<DemandeAutFra> demande = demandeService.findById(iddemande);
               
                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);

                JasperReport report = JasperCompileManager.compileReport(inputStream);

                JasperPrint jasperPrint = new JasperPrint();
                if ((report.getQuery() != null) && (report.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                }
                byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
                Document doc = new Document();
                Utilisateur utilisateur = signature.getUtilisateur();
                
                SOAPClient_3 tc = new SOAPClient_3();
                
                SignatureResult sr = tc.callMethd_Signer_fra(signature.getIdSignataire(), signature.getCleSignataire(), signature.getCodePin(), output, 5412794);
                
                System.out.println("le statut:" + sr.getStatus());
                System.out.println(sr.getErroInfo());
                System.out.println("---------------------"+sr.getContent());
                if (sr.getStatus().equals("MSIGN_SRV_STATUS_SUCCESS")) {
                    System.out.println("Document signé longueur:: +++++++++++++++++++++++++++++");                ;
                    byte[] doc_signe = sr.getContent(); 
                    System.out.println("---------------------"+doc_signe);
                    doc.setDctBlob(doc_signe);
                    doc.setDemande(demande.get());
                    doc.setUtilisateur(utilisateur);
                    doc.setDctAuteur(utilisateur.getUtiPrenom());
                    doc.setDctTitre("Demande aut fra");
                    doc.setStatusDocument(StatusDocument.SIGNER);
                    doc.setStatut(true);
                    doc.setTypeDocuments(typeDoc.findByTydId(1L));
                    doc.setDctDate(new Date());
                    doc.setDctType("application/pdf");
                    documentService.saveDocument(doc);
                    DemandeAutFra demandeFra = demandeService.getOneDemandeAutFra(iddemande);
                    demandeFra.setStatus(4L);
                    demandeService.updateDemandeAutFra(iddemande, demandeFra);
                    response1.setStatut(true);  
                    email.sendMail("no-reply@gainde2000.sn", demande.get().getEmailResponsable(), "Bonjour "+demande.get().getPrenomResponsable()+" "+demande.get().getNomResponsable()+",\nAprés examination de votre dossier "+demande.get().getNumdemande()+",\nnous vous délivrons l'Autorisation FRA provisoire.\nVeuillez utiliser votre adresse email et le numéro du dossier pour télécharger votre Autorisation FRA provisoire.", "Demande Autorisation FRA");   
                    System.out.println("Document signé longueur:: " + doc_signe.length);
                } else {
                	response1.setStatut(false);
                }
                
                return response1;
            } catch (JRException | SQLException e) {
                response1.setStatut(false);
                return response1;
            } catch (Exception e) {
                response1.setStatut(false);
                return response1;
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
            response1.setStatut(false);
            return response1;
        }
    }
    
    @PostMapping(path = "fichierSigner/pdf")
    public ServeurResponse fichierSigner(HttpServletRequest request) {
        ServeurResponse response1 = new ServeurResponse();
        try {        	
            Long fichierId = Long.parseLong(request.getParameter("fichier"));
            Long idproduit = Long.parseLong(request.getParameter("id"));
            Long idsignature = Long.parseLong(request.getParameter("signature"));
            Signature signature = signatureService.getOneSignature(idsignature); 

            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);

            final Map<String, Object> parameters = new HashMap<>();

            try {
                Rapport rapport = rapportService.findById(fichierId);
                Optional<Produits> produits = produitsService.findById(idproduit);
               
                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);

                JasperReport report = JasperCompileManager.compileReport(inputStream);

                JasperPrint jasperPrint = new JasperPrint();
                if ((report.getQuery() != null) && (report.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                }
                byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
                Document doc = new Document();
                Utilisateur utilisateur = signature.getUtilisateur();               

                SOAPClient_3 tc = new SOAPClient_3();
                //SignatureResult sr = tc.callMethd_Signer(14874178, output);
                SignatureResult sr = tc.callMethd_Signer_fra(signature.getIdSignataire(), signature.getCleSignataire(), signature.getCodePin(), output, 5412794);
                System.out.println("le statut:" + sr.getStatus());
                System.out.println(sr.getErroInfo());
                if (sr.getStatus().equals("MSIGN_SRV_STATUS_SUCCESS")) {
                    System.out.println("Document signé longueur:: +++++++++++++++++++++++++++++");
                    byte[] doc_signe = null;
                    doc_signe = sr.getContent();
                    doc.setDctBlob(doc_signe);
                    doc.setProduits(produits.get());
                    doc.setUtilisateur(utilisateur);
                    doc.setDctAuteur(utilisateur.getUtiPrenom());
                    doc.setDctTitre("Demande aut fra");
                    doc.setStatusDocument(StatusDocument.SIGNER);
                    doc.setStatut(true);
                    doc.setTypeDocuments(typeDoc.findByTydId(1L));
                    doc.setDctDate(new Date());
                    doc.setDctType("application/pdf");
                    documentService.saveDocument(doc);
                    Produits produitsApte = produitsService.getOneProduits(idproduit);
                    produitsApte.setStatus(6L);
                    produitsService.updateProduits(idproduit, produitsApte);
                    response1.setStatut(true);
                    email.sendMail("no-reply@gainde2000.sn", produits.get().getDemandeautfra().getEmailResponsable(), "Bonjour "+produits.get().getDemandeautfra().getPrenomResponsable()+" "+produits.get().getDemandeautfra().getNomResponsable()+",\nAprés analyse de votre produit "+produits.get().getDemandeautfra().getNumdemande()+",\nnous vous délivrons l'Autorisation FRA défitive.\nVeuillez utiliser votre adresse email et le numéro du dossier pour télécharger votre Autorisation FRA définitive.", "Demande Autorisation FRA");
                    System.out.println("Document signé longueur:: " + doc_signe.length);
                } else {
                	response1.setStatut(false);
                }
                
                return response1;
            } catch (JRException | SQLException e) {
                response1.setStatut(false);
                return response1;
            } catch (Exception e) {
                response1.setStatut(false);
                return response1;
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
            response1.setStatut(false);
            return response1;
        }
    }

    /**
     * convertir en pdf le fichier jrxml
     *
     * @param request
     * @param response
     */
    //@PostMapping(path = "fichier/pdfqrcode")
    public void getPdf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Resource resource = context.getResource("classpath:reports/car_list.jrxml");
            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );
            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();

            InputStream inputStream = resource.getInputStream();

            JasperReport report = JasperCompileManager.compileReport(inputStream);

            JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
            params.put("datasource", dataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * exporter en Excel et envoyer le message
     *
     * @param request
     * @param response
     */
    @PostMapping(path = "fichier/excel")
    @ResponseBody
    public void getExcel1(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long fichierId = Long.parseLong(request.getParameter("fichier"));
            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );
            Map<String, Object> params = new HashMap<>();
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            try {
                Rapport rapport = rapportService.findById(fichierId);
                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);
                JasperReport jasperReport = JasperCompileManager.compileReport(JRXmlLoader.load(inputStream)); // compile report
                params.put(JRParameter.REPORT_LOCALE, Locale.US);
                params.put(JRParameter.IS_IGNORE_PAGINATION, true);
                JasperPrint jasperPrint = new JasperPrint();
                if ((jasperReport.getQuery() != null) && (jasperReport.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();
                    jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
                }

                response.setHeader("Content-Disposition", "attachment;filename" + "test" + ".xlsx");
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                final OutputStream ops = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ops));
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setOnePagePerSheet(true);
                configuration.setDetectCellType(true);

                //sendNotification.checkedNotification("gen_fichier_excel");
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } catch (IOException | JRException e) {
            } catch (Exception e) {
            }

        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Export en pdf et envoie de message.
     *
     * @param request
     * @param response
     * @param image
     */
    @PostMapping(path = "fichierQrcode/pdf")
    public void reportQrcodeFichier(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "data1") String image) {

        try {
            Long fichierId = Long.parseLong(request.getParameter("fichier"));

            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);

            final Map<String, Object> parameters = new HashMap<>();

            try {

                Rapport rapport = rapportService.findById(fichierId);

                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);

                JasperReport report = JasperCompileManager.compileReport(inputStream);

                JasperPrint jasperPrint = new JasperPrint();
                if ((report.getQuery() != null) && (report.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();

                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("Data", image);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                }

                //sendNotification.checkedNotification("gen_fichier_pdf");
                response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            } catch (IOException | JRException | SQLException e) {
            } catch (Exception e) {
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostMapping(path = "fichierVisualiser/pdf")
    public void reportVisualiser(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long fichierId = Long.parseLong(request.getParameter("fichier"));

            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);

            final Map<String, Object> parameters = new HashMap<>();

            try {
                Rapport rapport = rapportService.findById(fichierId);

                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);

                JasperReport report = JasperCompileManager.compileReport(inputStream);
                JasperPrint jasperPrint = new JasperPrint();
                if ((report.getQuery() != null) && (report.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                }
                
                response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            } catch (IOException | JRException | SQLException e) {
            	e.printStackTrace();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostMapping(path = "fichierAutFraDefinitif/pdf")
    public void reportAutFraDefinitif(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long fichierId = Long.parseLong(request.getParameter("fichier"));

            Map<String, Object> data = new ObjectMapper().readValue(
                    request.getParameter("data"), new TypeReference<Map<String, Object>>() {
            }
            );

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
            List<Object> var1 = new ArrayList<Object>();
            var1.add(data);

            final Map<String, Object> parameters = new HashMap<>();

            try {
                Rapport rapport = rapportService.findById(fichierId);

                byte[] file = rapport.getRptJrxmlFile();
                InputStream inputStream = new ByteArrayInputStream(file);

                JasperReport report = JasperCompileManager.compileReport(inputStream);
                JasperPrint jasperPrint = new JasperPrint();
                if ((report.getQuery() != null) && (report.getQuery().getText().length() != 0)) {
                    Connection dataSource = this.dataSourceJasper.getConnection();
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                } else {
                    JRDataSource dataSource = new JRBeanCollectionDataSource(var1);
                    params.put("datasource", dataSource);
                    jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

                }
                
                response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            } catch (IOException | JRException | SQLException e) {
            	e.printStackTrace();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FichierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
