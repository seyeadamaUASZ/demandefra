/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Secteur;
import sn.gainde2000.pi.core.service.ISecteurService;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
public class SecteurController {

     @Autowired
     ISecteurService SecteurService;

     @PostMapping("create/secteur")
     public ServeurResponse createSecteur(@RequestBody Secteur secteur) {
          ServeurResponse response = new ServeurResponse();
          response.setData(SecteurService.create(secteur));
          response.setDescription("secteur enregistré avec succès");
          response.setStatut(true);

          return response;
     }

     @PostMapping("update/secteur")
     public ServeurResponse updateSecteur(@RequestBody Secteur secteur) {
          ServeurResponse response = new ServeurResponse();
           System.out.println("-----------" + secteur.getSecId());

          Secteur s = SecteurService.getSecteurById(secteur.getSecId());
          if (s != null) {
               s.setSecNom(secteur.getSecNom());
               s.setSecDescription(secteur.getSecDescription());
               response.setData(SecteurService.create(s));
               response.setDescription("secteur mis à jour avec succès");
               response.setStatut(true);

          } else {
               response.setDescription("mis à jour non enregistré");
               response.setStatut(false);
          }

          return response;
     }

     @GetMapping("get/secteur")
     public ServeurResponse getAllSecteur() {
          ServeurResponse response = new ServeurResponse();
          response.setData(SecteurService.getAllSecteur());
          response.setDescription("secteur récupéré avec succès");
          response.setStatut(true);
          return response;
     }

     @PostMapping("delete/secteur")
     public ServeurResponse deleteAllSecteur(@RequestBody Secteur secteur) {
          ServeurResponse response = new ServeurResponse();
          SecteurService.deleteSecteur(secteur);
          response.setDescription("secteur supprimé avec succès");
          response.setStatut(true);
          return response;
     }

}
