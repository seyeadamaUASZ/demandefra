package sn.gainde2000.pi.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Langue;
import sn.gainde2000.pi.core.repository.ApplicationRepository;
import sn.gainde2000.pi.core.repository.UtilisateurRepository;
import sn.gainde2000.pi.core.service.IApplicationService;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.service.IlangueService;

@RestController
@CrossOrigin("*")
@RequestMapping("langue")
public class LangueController {

     @Autowired
     IlangueService ilangueService;
     //Liste des Langue
     @Autowired
     IUtilisateur userRepo;
     @Autowired
     IApplicationService appRepo;

     /*
      * Liste des langues
      */
     @GetMapping
     public ServeurResponse getAllLangue() {
          Iterable<Langue> langue = ilangueService.getListLang();
          ServeurResponse response = new ServeurResponse();
          if (langue == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(langue);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     
     //Ajouter langue
     @PostMapping()
     public ServeurResponse create(@RequestBody Langue langue) {

          ServeurResponse response = new ServeurResponse();
          ilangueService.save(langue);
          response.setStatut(true);
          response.setDescription("Enregistrement réussi");
          response.setData(langue);

          return response;
     }
     
     //recuperation par id
     @GetMapping("{id}")
     public Optional<Langue> findById(@PathVariable Long id) {
          return ilangueService.findByLangId(id);
     }
     
     //Supprimer un module
     @PostMapping("delete")
     public ServeurResponse deleteLangue(@RequestBody Langue langue) {
          ServeurResponse response = new ServeurResponse();
          ilangueService.delete(langue);
          response.setStatut(true);
          return response;
     }
     
     /*
      * mettre a jour la langue
      */
     @PostMapping("update")
     public ServeurResponse updateLangue(@RequestBody Langue langue) {
         
          ServeurResponse response = new ServeurResponse();
          ilangueService.save(langue);
          response.setDescription("");
          response.setStatut(true);
          return response;

     }


    

}
