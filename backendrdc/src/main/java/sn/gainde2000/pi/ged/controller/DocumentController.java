/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.ged.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.SearchFilter;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.tools.Sms;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.entity.PrivilegeValidationOtp;
import sn.gainde2000.pi.ged.entity.SignatureDocument;
import sn.gainde2000.pi.ged.entity.StatusDocument;
import sn.gainde2000.pi.ged.service.IDocumentService;
import sn.gainde2000.pi.ged.service.IPrivilegeSignerServce;
import sn.gainde2000.pi.ged.service.IPrivilegeValidationOtp;
import sn.gainde2000.pi.ged.service.ISignatureDocument;
import sn.gainde2000.pi.integration.signature.cf.client.test.SOAPClient_3;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.SignatureResult;
import sn.gainde2000.pi.opt.controller.ParametreOtpController;
import sn.gainde2000.pi.opt.service.IParametreOtp;

/**
 *
 * @author asow
 */
@RestController
public class DocumentController {

    @Autowired
    IDocumentService documentService;
    
    @Autowired
    EntityManager entityManager;
   @Autowired IParametreOtp iParametreOtp;

    @Autowired
    IUtilisateur iUtilisateur;
    @Autowired
    ISignatureDocument isignature;

    @Autowired
    IPrivilegeSignerServce iPrivilegeSignerServce;
    @Autowired IPrivilegeValidationOtp iPrivilegeValidationOtp;
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    
    
    @PostMapping("document/search/{poOwner}")
    public ServeurResponse advancedSearch(@RequestBody SearchFilter search, @PathVariable Long poOwner) {
        ServeurResponse response = new ServeurResponse();
        String querystring = "select r from Document r where r.utilisateur.utiId="+poOwner  ;
        
        
        int i =0;
        if(search!=null) {
			for (Map.Entry<String, String> map : search.getFilter().entrySet()) {
				if (map.getValue()!=null && !map.getValue().trim().isEmpty()) {
						if(i>=1)
							querystring += " and " + "" + (map.getKey().equals("typeDocuments")?"r."+map.getKey()+".tydLibelle":map.getKey().equals("dctDate")?("cast(r."+map.getKey()+" as date)"):"r."+map.getKey())  +( map.getKey().equals("dctDate")?" = cast('"+map.getValue()+"' as date) ":" like '%" + map.getValue() + "%'");
						else {
							querystring +=  " and " + (map.getKey().equals("typeDocuments")?"r."+map.getKey()+".tydLibelle":map.getKey().equals("dctDate")?("cast(r."+map.getKey()+" as date)"):"r."+map.getKey())  + ( map.getKey().equals("dctDate")?" = cast('"+map.getValue()+"' as date) ":" like '%" + map.getValue() + "%'");
						}
						i++;
				}
			}
        }
		
		TypedQuery<Document> query = entityManager.createQuery(querystring, Document.class);
		List<Document> list = query.getResultList();
		response.setStatut(true);
		response.setData(list);
        return response;
    }

    
    @PostMapping("document/search/recu/{poOwner}/{statusDocument}/{status}")
    public ServeurResponse advancedSearchRecus(@RequestBody SearchFilter search, @PathVariable Long poOwner,@PathVariable Long statusDocument,@PathVariable Long status) {
        ServeurResponse response = new ServeurResponse();
        String querystring = "select r from Document r INNER JOIN PrivilegeSigner pri ON (r.dctId=pri.document.dctId) WHERE pri.statusDocument="+statusDocument+" AND pri.status="+status+" AND pri.utilisateur.utiId ="+poOwner+" "  ;
        
        
        int i =0;
        if(search!=null) {
			for (Map.Entry<String, String> map : search.getFilter().entrySet()) {
				if (map.getValue()!=null && !map.getValue().trim().isEmpty()) {
						if(i>=1)
							querystring += " and " + "" + (map.getKey().equals("typeDocuments")?"r."+map.getKey()+".tydLibelle":map.getKey().equals("dctDate")?("cast(r."+map.getKey()+" as date)"):"r."+map.getKey())  +( map.getKey().equals("dctDate")?" = cast('"+map.getValue()+"' as date) ":" like '%" + map.getValue() + "%'");
						else {
							querystring +=  " and " + (map.getKey().equals("typeDocuments")?"r."+map.getKey()+".tydLibelle":map.getKey().equals("dctDate")?("cast(r."+map.getKey()+" as date)"):"r."+map.getKey())  + ( map.getKey().equals("dctDate")?" = cast('"+map.getValue()+"' as date) ":" like '%" + map.getValue() + "%'");
						}
						i++;
				}
			}
        }
		
		TypedQuery<Document> query = entityManager.createQuery(querystring, Document.class);
		List<Document> list = query.getResultList();
		response.setStatut(true);
		response.setData(list);
        return response;
    }
    
    /***
     * fonction pour lister les documents par statut!
     * @return 
     */
    @GetMapping("document/list")
    public ServeurResponse getAllDocument() {
        Iterable<Document> document = documentService.findByStatut();
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /***
     * lister les documents d'un utilisateurs
     * @param iduser
     * @return 
     */
    @GetMapping("document/listByUser/{iduser}")
    public ServeurResponse getAllDocumentByUser(@PathVariable Long iduser) {
        Iterable<Document> document = documentService.getListDocumentUtilisateur(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * lister les documents à signer par un utilisateur
     * @param iduser
     * @return 
     */
    @GetMapping("document/listByUserAsigner/{iduser}")
    public ServeurResponse getAllDocumentByUserAsigner(@PathVariable Long iduser) {
        Iterable<Document> document = documentService.getListDocumentUtilisateurSigner(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    /**
     * lister les documents à certifier par  utilisateur
     * @param iduser
     * @return 
     */

    @GetMapping("document/listByUserAcertifier/{iduser}")
    public ServeurResponse getAllDocumentByUserACertifier(@PathVariable Long iduser) {
        Iterable<Document> document = documentService.getListDocumentUtilisateurCertifier(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    /**
     * lister les documents à consulter par  utilisateur
     * @param iduser
     * @return 
     */

    @GetMapping("document/listByUserAconsulter/{iduser}")
    public ServeurResponse getAllDocumentByUserAConsulter(@PathVariable Long iduser) {
        Iterable<Document> document = documentService.getListDocumentUtilisateurConsulter(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    
    /**
     * lister les documents à approuver par  utilisateur
     * @param iduser
     * @return 
     */

    @GetMapping("document/listByUserAapprouver/{iduser}")
    public ServeurResponse getAllDocumentByUserAApprouver(@PathVariable Long iduser) {
        Iterable<Document> document = documentService.getListDocumentUtilisateurAApprouver(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("document/listByUserDejaApprouves/{iduser}")
    public ServeurResponse getAllDocumentByUserDejaApprouver(@PathVariable Long iduser) {
        List<Document> document = iPrivilegeSignerServce.listDocumentDejaApprouver(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("document/listByUserDejaCertifies/{iduser}")
    public ServeurResponse getAllDocumentByUserDejaCertifies(@PathVariable Long iduser) {
        List<Document> document = iPrivilegeSignerServce.listDocumentDejaCertifier(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
/**
 * pour ajouter un document
 * @param request
 * @param file
 * @param username
 * @param statusDocument
 * @return 
 */
    @PostMapping("document/create")
    public ServeurResponse create(HttpServletRequest request, @RequestParam("document") MultipartFile file, @RequestParam("username") String username, @RequestParam("statusDocument") Long statusDocument) {

        ServeurResponse response = new ServeurResponse();
        Utilisateur utilisateur = iUtilisateur.findByUtiUsername(username);
        Document document;
    
      
        try {
            document = new ObjectMapper().readValue(
                    request.getParameter("document"), new TypeReference<Document>() {
            }
            );

            document.setDctBlob(file.getBytes());
            document.setDctType(file.getContentType());
            document.setDctDate(new Date());
            document.setUtilisateur(utilisateur);

            if (statusDocument == 1L) {
                document.setStatusDocument(StatusDocument.SIGNER);
            } else if (statusDocument == 2L) {
                document.setStatusDocument(StatusDocument.CERTIFIER);
            } else if (statusDocument == 3L) {
                document.setStatusDocument(StatusDocument.CONSULTER);
            }else{
            	document.setStatusDocument(StatusDocument.APPROUVER);
            }
            if(documentService.findBydctTitre(document.getDctTitre())==null) {
            document.setStatut(true);
            documentService.saveDocument(document);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(document);
            }
            else {
            	response.setStatut(false);
                response.setDescription("le titre du document existe déja");
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

       

        return response;
    }
    
    /**
     * supprimer un document
     * @param document
     * @return 
     */

    @PostMapping("document/delete")
    public ServeurResponse deleteDocument(@RequestBody Document document) {
        ServeurResponse response = new ServeurResponse();
        documentService.supprimer(document);
        response.setStatut(true);
        return response;
    }
/**
 * supprimer un document par statut
 * @param id
 * @return 
 */
    @GetMapping("documentDelete/{id}")
    public ServeurResponse deleteDocumentParStatut(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        documentService.supprimerDocument(id);
        response.setStatut(true);
        response.setDescription("Document supprimer");
        return response;

    }

    /**
     * modifier un document
     * @param request
     * @param file
     * @return 
     */
    @PostMapping("document/update")
    public ServeurResponse updateDocument(HttpServletRequest request, @RequestParam(value = "document", required = false) MultipartFile file) {
        ServeurResponse response = new ServeurResponse();

        Document document;
        try {
            document = new ObjectMapper().readValue(
                    request.getParameter("document"), new TypeReference<Document>() {
            }
            );

            Document doc = documentService.findBydctId(document.getDctId());
            doc.setDctTitre(document.getDctTitre());
            doc.setDctAuteur(document.getDctAuteur());
            doc.setTypeDocuments(document.getTypeDocuments());
            doc.setDctBlob(document.getDctBlob());
            doc.setDctBlob(file.getBytes());
            documentService.saveDocument(doc);
            response.setStatut(true);
            response.setDescription("Modifier avec success!!!");
            response.setData(document);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * consulter un document
     * @param id
     * @param response
     * @return 
     */
    @GetMapping("document/{id}")
    public ResponseEntity<ByteArrayResource> consultDocument(@PathVariable Long id, HttpServletResponse response) {
        Document doc = documentService.findBydctId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDctAuteur() + "\"")
                .body(new ByteArrayResource(doc.getDctBlob()));

    }

    
    /**
     * signer un document
     * @param username
     * @param document
     * @return
     * @throws IOException 
     */
    @PostMapping("document/signer/{username}")
    public ServeurResponse signerDocument(@PathVariable String username, @RequestBody Document document) throws IOException {
        byte[] doc_signe = null;
        ServeurResponse response = new ServeurResponse();
        Utilisateur utilisateur = iUtilisateur.findByUtiUsername(username);
        SOAPClient_3 tc = new SOAPClient_3();
        SignatureResult sr = tc.callMethd_Signer(14874178, document.getDctBlob());
        System.out.println("le statut:" + sr.getStatus());
        System.out.println(sr.getErroInfo());
        if (sr.getStatus().equals("MSIGN_SRV_STATUS_SUCCESS")) {
            PrivilegeSigner pri = iPrivilegeSignerServce.findByPrivByDoc(document.getDctId(), utilisateur.getUtiId());
            pri.setStatus(true);
            iPrivilegeSignerServce.save(pri);
            doc_signe = sr.getContent();
            document.setDctBlob(doc_signe);
            documentService.saveDocument(document);
            isignature.save(new SignatureDocument(utilisateur, document));
            System.out.println("Document sign� longueur:: " + doc_signe.length);
            response.setStatut(true);
            response.setData(document);
            response.setDescription("Document signé .");
        } else {
            response.setStatut(false);
            response.setDescription("Document non signé");
        }

        return response;
    }
    
    
    /**
     * approuver un document
     * @param username
     * @param document
     * @return ServeurResponse
     * 
     */
    @PostMapping("document/approuver/{username}/{code}")
    public ServeurResponse approuverDocument(@PathVariable String username, @PathVariable String code,@RequestBody Document document) throws IOException {
        ServeurResponse response = new ServeurResponse();
        Utilisateur utilisateur = iUtilisateur.findByUtiUsername(username);
        
        PrivilegeSigner pri = iPrivilegeSignerServce.findByPrivByDoc(document.getDctId(), utilisateur.getUtiId());
        PrivilegeValidationOtp priOtp = iPrivilegeValidationOtp.findByPriviligeSignerAndPrisValCode(pri, code);
        
        if(priOtp !=null) {
        	
        
        // checking timeout otp duration
        	Date d = priOtp.getPrisValDateCreation();
        	d.setTime(d.getTime() + priOtp.getPrisValDuree()*60000);

        if( code.equals(priOtp.getPrisValCode()) && ((new Date()).getTime() - d.getTime()) < 0L) {
        
                	
        	pri.setStatus(true);
        	iPrivilegeSignerServce.save(pri);
        	response.setStatut(true);
        }else {
        	response.setStatut(false);
        	response.setDescription("code incorrect");
        }
        }else {
        	response.setStatut(false);
        	response.setDescription("access denied");
        }
            
       
        

        return response;
    }
    
    
    /**
     * approuver un document
     * @param username
     * @param document
     * @return ServeurResponse
     * 
     */
    @PostMapping("document/envoyercode/{username}")
    public ServeurResponse envoyerCode(@PathVariable String username,@RequestBody Document document) throws IOException {
        ServeurResponse response = new ServeurResponse();
        Utilisateur utilisateur = iUtilisateur.findByUtiUsername(username);
      
        PrivilegeSigner pri = iPrivilegeSignerServce.findByPrivByDoc(document.getDctId(), utilisateur.getUtiId());
        String code = null;
        
        code =new String(iParametreOtp.generateCodeOtp("SMS"));
        PrivilegeValidationOtp pvotp = new PrivilegeValidationOtp();
        pvotp.setPrisValCode(code);
        pvotp.setPrisValDateCreation(new Date());
        pvotp.setPrisValDuree(10L);
        pvotp.setPrivilegeSigner(pri);
       
        iPrivilegeValidationOtp.add(pvotp);
        
        Sms sms = new Sms();
        sms.sendSms(utilisateur.getUtiTelephone(), "code otp: "+ pvotp.getPrisValCode());
        response.setStatut(true);
        response.setData(document);
        response.setDescription("code otp envoye.");
        

        return response;
    }
    
    
}
