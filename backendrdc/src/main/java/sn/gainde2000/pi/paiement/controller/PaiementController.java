/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.paiement.entity.Paiement;
import sn.gainde2000.pi.paiement.serviceimpl.PaiementServiceImpl;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("paiement")
public class PaiementController {
    @Autowired
    PaiementServiceImpl paiementService;
    
    //Récupère la liste des transactions effectuées
     @GetMapping("list")
    public ServeurResponse getAllTransactions() {     
        Iterable<Paiement> paiement = paiementService.listOperation();
        ServeurResponse response = new ServeurResponse();
        if (paiement== null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(paiement);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    //Récupère la liste des transactions effectuées par un user
      @GetMapping("list/{username}")
     public ServeurResponse getTransactionByReferenceClient(@PathVariable String username) {     
        Iterable<Paiement> paiement = paiementService.listOperationByReferenceClient(username);
        ServeurResponse response = new ServeurResponse();
        if (paiement== null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(paiement);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
   //Affiche les détails d'une transaction effectuée
     @GetMapping("referencePaiement/{idFacture}")
    public ServeurResponse getPaiementByReference(@PathVariable Long idFacture) {     
      Paiement paiement = paiementService.findByIdFacture(idFacture);
        ServeurResponse response = new ServeurResponse();
        if (paiement== null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {
            response.setStatut(true);
            response.setData(paiement);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
}
