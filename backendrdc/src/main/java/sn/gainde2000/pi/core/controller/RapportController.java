/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.QrCode;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.service.IChampsRapportService;
import sn.gainde2000.pi.core.service.IRapportService;
import sn.gainde2000.pi.core.service.impl.RapportServiceImpl;

/**
 *
 * @author Sagueye
 */
@RestController
@CrossOrigin("*")
public class RapportController {

    @Autowired
    IRapportService rapportService;

    @Autowired
    IChampsRapportService champsRapportService;

    /**
     * création d'un rapport
     *
     * @param request
     * @param file
     * @return
     */
    @PostMapping("rapport")
    public ServeurResponse create(HttpServletRequest request, @RequestParam("jrxmlfile") MultipartFile file) {

        ServeurResponse response = new ServeurResponse();

        Rapport rapport;

        try {
            rapport = new ObjectMapper().readValue(
                    request.getParameter("rapport"), new TypeReference<Rapport>() {
            }
            );

            if (rapportService.findByrptNom(rapport.getRptNom()) == null) {
                rapport.setRptJrxmlFile(file.getBytes());

                rapport.setRptStatus(false);
                rapport.setRptValider("Modeliser");
                rapportService.save(rapport);
                response.setStatut(true);
                response.setDescription("Enregistrement réussi");
                response.setData(rapport);
            } else {
                response.setStatut(false);
                response.setDescription("Le nom du fichier exite déja");
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
     * permettre de mettre en jours le rapport
     *
     * @param request
     * @return
     */
    @PostMapping("updaterapport")
    public ServeurResponse update(HttpServletRequest request) {

        ServeurResponse response = new ServeurResponse();

        Rapport rapport;
        try {
            rapport = new ObjectMapper().readValue(
                    request.getParameter("rapport"), new TypeReference<Rapport>() {
            }
            );

            Rapport r = rapportService.findById(rapport.getRptId());

            r.setRptDescription(rapport.getRptDescription());
            r.setRptNom(rapport.getRptNom());
            rapportService.save(r);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(rapport);
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
     * Permettre de mettre en jours le fichier jrxml
     *
     * @param request
     * @param file
     * @return
     */
    @PostMapping("updaterapportfile")
    public ServeurResponse updateFile(HttpServletRequest request, @RequestParam("jrxmlfile") MultipartFile file) {

        ServeurResponse response = new ServeurResponse();

        Rapport rapport;
        try {
            rapport = new ObjectMapper().readValue(
                    request.getParameter("rapport"), new TypeReference<Rapport>() {
            }
            );

            Rapport r = rapportService.findById(rapport.getRptId());

            r.setRptJrxmlFile(file.getBytes());

            rapportService.save(r);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(rapport);
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
     * Valider un rapport deja créer
     *
     * @param rapport
     * @return
     */
    @PostMapping("validerrapport")
    public ServeurResponse valider(@RequestBody Rapport rapport) {

        ServeurResponse response = new ServeurResponse();
        rapport.setRptStatus(false);
        rapport.setRptValider("Valider");
        rapportService.save(rapport);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(rapport);

        return response;
    }

    /**
     * modeliser un rapport!!
     *
     * @param rapport
     * @return
     */
    @PostMapping("modeliserrapport")
    public ServeurResponse modeliser(@RequestBody Rapport rapport) {
        ServeurResponse response = new ServeurResponse();
        Rapport r = rapportService.findById(rapport.getRptId());
        r.setRptValider("Modeliser");
        rapportService.save(r);
        response.setStatut(true);
        response.setDescription("Rapport valider");
        response.setData(r);

        return response;
    }

    /**
     * recuperer un rapport par son id
     *
     * @param id
     * @return
     */
    @GetMapping("rapport/{id}")
    public Rapport findById(@PathVariable Long id) {
        return rapportService.findById(id);
    }

    /**
     * Listes des rapports non generées!!
     *
     * @return
     */
    @GetMapping("notgeneraterapport")
    public ServeurResponse getAllnotgenerate() {
        Iterable<Rapport> rapport = rapportService.listRapportNotGenerate();
        ServeurResponse response = new ServeurResponse();
        if (rapport == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(rapport);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Recuperation listes des rapports générées!!
     *
     * @return
     */
    @GetMapping("generaterapport")
    public ServeurResponse getAllgenerate() {
        Iterable<Rapport> rapport = rapportService.getListRapportGenerate();
        ServeurResponse response = new ServeurResponse();
        if (rapport == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(rapport);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * recuperation listes des rapport par menu.
     *
     * @param menu
     * @return
     */
    @PostMapping("rapportparmenu")
    public ServeurResponse getAllRapportByMenu(@RequestBody Menu menu) {
        Iterable<Rapport> rapports = rapportService.getAllRapportByMenu(menu);
        ServeurResponse response = new ServeurResponse();
        if (rapports == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(rapports);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Recuperation liste des rapports libres.
     *
     * @return
     */

    @GetMapping("generateRapportLibre")
    public ServeurResponse AllgenerateRapportLibre() {
        Iterable<Rapport> rapport = rapportService.getListRapportGenerateEtLibre();
        ServeurResponse response = new ServeurResponse();
        if (rapport == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(rapport);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Lier un fichier avec une application.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fichierLierApp", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServeurResponse LierFichierEtApp(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String idApp = (String) request.getParameter("idApp");
        String idFichier = (String) request.getParameter("idFichier");
        rapportService.lierFichier(idApp, idFichier);
        response.setStatut(true);
        response.setDescription("liaison effectué avec succès");

        return response;
    }

    /**
     * Enlever la liaison entre le fichier et le rapport.
     *
     * @param idFichier
     * @return
     */
    @GetMapping("fichierLierApp/enlever/{idFichier}")
    public ServeurResponse EnleverLiaisonRapportEtApp(@PathVariable String idFichier) {
        ServeurResponse response = new ServeurResponse();
        rapportService.enleverLiasonFichier(idFichier);
        response.setStatut(true);
        response.setDescription("liaison enlevé avec succès");

        return response;
    }

    /**
     * Listes des fichier lier à une application.
     *
     * @param id
     * @return
     */
    @GetMapping("fichierByAppId/{id}")
    public ServeurResponse getFichierByAppId(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        List<Rapport> f = rapportService.getAllFichierByIdApp(id);
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    /**
     * Supprimer un rapport.
     *
     * @param rapport
     * @return
     */
    @PostMapping("supprimerrapport")
    public ServeurResponse deleteRapport(@RequestBody Rapport rapport) {
        ServeurResponse response = new ServeurResponse();
        champsRapportService.supprimerByRapport(rapport.getRptId());
        rapportService.delete(rapport);
        response.setStatut(true);
        return response;
    }

    /**
     * Listes des rapports libres ou specifique.
     *
     * @param id
     * @return
     */
    @GetMapping("fichierByAppOuLibre/{id}")
    public ServeurResponse getRapportLibreOuSpecifique(@PathVariable Application id) {
        ServeurResponse response = new ServeurResponse();
        List<Rapport> f = rapportService.getListRapportGenerateEtLibreOuSpecifique(id);
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    /**
     * Permettre d'afficher le nombre de fichier.
     *
     * @return
     */
    @GetMapping("fichier/nombreFichier")
    public ServeurResponse getNombreFichier() {
        ServeurResponse response = new ServeurResponse();
        response.setData(rapportService.nombreFichier());
        response.setStatut(true);
        return response;
    }

    @ResponseBody
    @PostMapping("rapport/qrcode")
    public ServeurResponse postFile(@RequestParam(value = "data") byte[] data) throws Exception {
        ServeurResponse response = new ServeurResponse();
         Rapport rapport = new Rapport();
        response.setData(data);
        response.setStatut(true);
        return response;
    }
}
