/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.ChampsRapport;
import sn.gainde2000.pi.core.service.IChampsRapportService;

/**
 *
 * @author asow, sagueye
 */
@RestController
@CrossOrigin("*")
public class ChampsRapportController {
    @Autowired
    IChampsRapportService champsRapportService;
    
        //Ajouter un rapport
    @PostMapping("champsrapport")
    public ServeurResponse create(@RequestBody ChampsRapport champsRapport) {
        ServeurResponse response = new ServeurResponse();
        champsRapportService.save(champsRapport);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(champsRapport);

        return response;
    }
    
     @GetMapping("champsrapport/{id}")
    public List<ChampsRapport> findById(@PathVariable Long id) {
        return champsRapportService.listBycrtId(id);
    }
     
     //Liste des Champs pour un formulaire
     
     @GetMapping("champsbyrapport/{id}")
     public ServeurResponse getAllChampsIdRapport(@PathVariable Long id) {
         List<ChampsRapport> champs = champsRapportService.listByRapportId(id);
         ServeurResponse response = new ServeurResponse();
         if (champs == null) {
             response.setStatut(false);
             response.setData(null);
             response.setDescription("Aucun élèment trouvé.");

         } else {

             response.setStatut(true);
             response.setData(champs);
             response.setDescription("Données récupérées.");
         }
         return response;
     }
     
     @PostMapping("champsrapport/delete")
     public ServeurResponse deletePays(@RequestBody Long champsRapportId) {
         ServeurResponse response = new ServeurResponse();
         champsRapportService.delete(champsRapportId);
         response.setStatut(true);
         return response;
     }
     
     @GetMapping("deletebyrapport/{id}")
     public ServeurResponse supprimerChampsIdRapport(@PathVariable Long id) {
    	 champsRapportService.supprimerByRapport(id);
         ServeurResponse response = new ServeurResponse();
         response.setStatut(true);
         response.setData(null);
         response.setDescription("suppression des champs");
         return response;
     }
}
