/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.ChampsQrcode;
import sn.gainde2000.pi.core.service.IChampsQrcodeService;

/**
 *
 * @author asow
 */
@RestController
public class ChampsQrcodeController {

    @Autowired
    IChampsQrcodeService champsQrcodeService;

          //Ajouter un rapport
    @PostMapping("champsqrcode/create")
    public ServeurResponse create(@RequestBody ChampsQrcode champsQrcode) {
        ServeurResponse response = new ServeurResponse();
        champsQrcodeService.save(champsQrcode);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(champsQrcode);

        return response;
    }
    
     @GetMapping("champsrqrcode/{id}")
    public List<ChampsQrcode> findById(@PathVariable Long id) {
        return champsQrcodeService.listByQrcodeId(id);
    }
     
     //Liste des Champs pour un formulaire
     
     @GetMapping("champsbyqrcode/{id}")
     public ServeurResponse getAllChampsIdQrcode(@PathVariable Long id) {
         List<ChampsQrcode> champs = champsQrcodeService.listByQrcodeId(id);
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
     
     @PostMapping("champsqrcode/delete")
     public ServeurResponse deletePays(@RequestBody Long champsQrcodeId) {
         ServeurResponse response = new ServeurResponse();
         champsQrcodeService.delete(champsQrcodeId);
         response.setStatut(true);
         return response;
     }
     
     @GetMapping("deletebyqrcode/{id}")
     public ServeurResponse supprimerChampsIdQrcode(@PathVariable Long id) {
    	 champsQrcodeService.supprimerByQrcode(id);
         ServeurResponse response = new ServeurResponse();
         response.setStatut(true);
         response.setData(null);
         response.setDescription("suppression des champs");
         return response;
     }

}
