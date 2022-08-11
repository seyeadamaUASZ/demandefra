package sn.gainde2000.pi.core.controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.service.IFormulaireService;

@RestController
@CrossOrigin("*")
public class FormulaireController {

     @Autowired
     IFormulaireService formulaireServiceImpl;
     
     /**
      * endpoint permettant de recuperer la liste des formulaires crees
      * 
      * @return ServeurResponse
      */
     @GetMapping("formulaire/list")
     public ServeurResponse getAllFormulaire() {
          Iterable<Formulaire> formulaire = formulaireServiceImpl.getAllFormulaire();
          ServeurResponse response = new ServeurResponse();
          if (formulaire == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(formulaire);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     
     /**
      * Endpoint permettant de recuperer la liste des formulaires  non generés
      * @return ServeurResponse
      */
     
     @GetMapping("formulaire/notgenerateform")
     public ServeurResponse getAllNotGenerate() {
          Iterable<Formulaire> formulaire = formulaireServiceImpl.listFormNotGenerate();
          ServeurResponse response = new ServeurResponse();
          if (formulaire == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(formulaire);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     
     /**
      * Endpoint permettant de recuperer la liste des formulaires generés
      * @return ServeurResponse
      */
     @GetMapping("formulaire/generateform")
     public ServeurResponse getAllGenerate() {
          Iterable<Formulaire> formulaire = formulaireServiceImpl.listFormGenerate();
          ServeurResponse response = new ServeurResponse();
          if (formulaire == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(formulaire);
               response.setDescription("Données récupérées.");
          }
          return response;
     }

     /**
      * Endpoint permettant de creer un formulaire
      * @param formulaire
      * @return ServeurResponse
      */


     @PostMapping("formulaire/create")
     public ServeurResponse create(@RequestBody Formulaire formulaire) {

          ServeurResponse response = new ServeurResponse();
          
          if(formulaireServiceImpl.findByFrmNom(formulaire.getFrmNom())==null) {
          formulaire.setFrmStatus(false);
          formulaire.setFrmValider("Modeliser");
          formulaire.setFrmModeliser(false);
          formulaireServiceImpl.save(formulaire);
          response.setStatut(true);
          response.setDescription("Enregistrement réussi");
          response.setData(formulaire);
          }else {
        	  response.setStatut(false);
              response.setDescription("le nom du formulaire existe deja");
          }
          
          return response;
     }
    
     /**
      * Endpoint permettant de valider un formulaire modelisé
      * @param formulaire
      * @return
      */

     @PostMapping("formulaire/validerfrm")
     public ServeurResponse valider(@RequestBody Formulaire formulaire) {

          ServeurResponse response = new ServeurResponse();
          formulaire.setFrmStatus(false);
          formulaire.setFrmValider("Valider");
          formulaireServiceImpl.save(formulaire);
          response.setStatut(true);
          response.setDescription("Formulaire validé");
          response.setData(formulaire);

          return response;
     }
     
     /**
      * Endpoint permettant de modeliser un formulaire
      * @param formulaire
      * @return
      */

     @PostMapping("formulaire/modeliserfrm")
     public ServeurResponse modeliser(@RequestBody Formulaire formulaire) {

          ServeurResponse response = new ServeurResponse();
          formulaire.setFrmStatus(false);
          formulaire.setFrmValider("Modeliser");
          formulaire.setFrmModeliser(true);
          formulaireServiceImpl.save(formulaire);
          response.setStatut(true);
          response.setDescription("Formulaire Etat initial");
          response.setData(formulaire);

          return response;
     }

     /**
      * Endpoint permettant de recuperer un formulaire via son id
      * @param id
      * @return
      */

     @GetMapping("formulaire/{id}")
     public Optional<Formulaire> findById(@PathVariable Long id) {
          return formulaireServiceImpl.findById(id);
     }

     /**
      * Endpoint permettant de supprimer un formulaire 
      * @param formulaire
      * @return ServeurResponse
      */

     @PostMapping("formulaire/delete")
     public ServeurResponse deleteFormulaire(@RequestBody Formulaire formulaire) {
          ServeurResponse response = new ServeurResponse();
          formulaireServiceImpl.delete(formulaire);
          response.setStatut(true);
          return response;
     }
     
     /**
      * Liste des formulaires pour une application via l'id de l'application
      * @param id
      * @return
      */

     @GetMapping("formulaire/formulaireApp/{id}")
     public List<Formulaire> findApp(@PathVariable Long id) {
          return formulaireServiceImpl.findByFrmAppId(id);
     }

     @GetMapping("formulaire/formulaireByApp")
     public ServeurResponse getFormulaireByApp() {
          ServeurResponse response = new ServeurResponse();
          List<Formulaire> f = formulaireServiceImpl.getAllFormulaireLibreValider();
          response.setData(f);
          response.setStatut(true);
          return response;
     }

     @RequestMapping(value = "formulaire/formulaireLierApp", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
     public ServeurResponse LierFormulaireEtApp(HttpServletRequest request) {
          ServeurResponse response = new ServeurResponse();
          String idApp = (String) request.getParameter("idApp");
          String idFormulaire = (String) request.getParameter("idFormulaire");
          formulaireServiceImpl.lierFormulaire(idApp, idFormulaire);
          response.setStatut(true);
          response.setDescription("liaison effectué avec succès");

          return response;
     }

     @GetMapping("formulaire/formulaireLierApp/enlever/{idFormulaire}")
     public ServeurResponse EnleverLiaisonFormulaireEtApp(@PathVariable String idFormulaire) {
          ServeurResponse response = new ServeurResponse();
          System.out.println("formulaire id:" + idFormulaire);
          formulaireServiceImpl.enleverLiasonFormulaire(idFormulaire);
          response.setStatut(true);
          response.setDescription("liaison enlevé avec succès");

          return response;
     }

     @GetMapping("formulaire/formulaireByAppId/{id}")
     public ServeurResponse FormulaireByAppId(@PathVariable Long id) {
          ServeurResponse response = new ServeurResponse();
          System.out.println("formulaire id:" + id);
          List<Formulaire> f = formulaireServiceImpl.listFormByAppId(id);
          response.setStatut(true);
          response.setData(f);
          response.setDescription("Formulaire recupéré par id");
          return response;
     }
     
       @GetMapping("formulaire/formulaireByAppOuLibre/{id}")
     public ServeurResponse getFormulaireLibreOuSpecifique(@PathVariable Long id){
          ServeurResponse response = new ServeurResponse();
          List<Formulaire> f = formulaireServiceImpl.listFormByAppIdOulibre(id);
          response.setData(f);
          response.setStatut(true);
          return response;
     } 
     
     @GetMapping("formulaire/nombreFormulaire")
     public ServeurResponse getNombreFormulaire(){
          ServeurResponse response = new ServeurResponse();
          response.setData(formulaireServiceImpl.NombreFormulaire());
          response.setStatut(true);
          return response;
     }

}
