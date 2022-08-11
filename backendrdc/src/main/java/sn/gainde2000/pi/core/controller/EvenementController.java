/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Evenement;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.service.IEvenementService;

/**
 *
 * @author indiaye
 */
@RestController
public class EvenementController {
    
    @Autowired
    IEvenementService evService;
    
    @GetMapping("evenement")
    public ServeurResponse getEvenements() {
    	
        Iterable<Evenement> evenements = evService.getListEvenements();
        ServeurResponse response = new ServeurResponse();
        if (evenements == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(evenements);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @PostMapping("evenement/evenementparnotification")
    @ResponseBody
    public ServeurResponse getEvenementByNotification(@RequestBody Notification n){       
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(evService.getListActionParNotification(n));
        response.setDescription("Evénement récupérés avec succès");
        return response;          
    }
    
    @PostMapping("evenement/evenementparaction")
    @ResponseBody
    public ServeurResponse getEvenementByAction(@RequestBody Action a){       
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(evService.getListEvenementParActions(a));
        response.setDescription("Evénement récupérés avec succès");
        return response;          
    }
    
    @PostMapping("evenement/create")
    public ServeurResponse create(@RequestBody Evenement ev) {
          ServeurResponse response = new ServeurResponse();         
          if(evService.save(ev)!=null){
               response.setStatut(true);
               response.setDescription("Evenement crée avec succès");
          } else {
               response.setStatut(false);
               response.setDescription("Erreur de création d'évènement!");
          }
          return response;
     }

     @PostMapping("evenement/update")
     public ServeurResponse update(@RequestBody Evenement ev) {
          ServeurResponse response = new ServeurResponse();
            if(evService.save(ev)!=null){
               response.setStatut(true);
               response.setDescription("Evenement crée avec succès");
            } else {
               response.setStatut(false);
               response.setDescription("Erreur lors de la modification de l'évènement!");
            }
            return response;
    }

     @PostMapping("evenement/delete")
     public ServeurResponse delete(@RequestBody Evenement ev) {
          ServeurResponse response = new ServeurResponse();
          evService.delete(ev);
          response.setStatut(true);
          response.setDescription("menu supprimé avec succès");
          return response;
     }
     
     @PostMapping("evenement/allocateevenement")
     @ResponseBody
     public ServeurResponse allocateActionNotification(@RequestParam("notification") String notification, @RequestParam("removed") String removed, @RequestParam("added") String added){ 
         ObjectMapper mapper = new ObjectMapper();       
         try {
             Notification n = mapper.readValue(notification, Notification.class);                       
             
             List<Action> removedEvenements =  new ObjectMapper().readValue(removed, new TypeReference<List<Action>>() { });           
             removedEvenements.forEach(action -> {
            	 evService.removedEvenement(n, action);
             });
             
             List<Action> addedEvenements =  new ObjectMapper().readValue(added, new TypeReference<List<Action>>() { });           
             addedEvenements.forEach(action -> {
                	 evService.save(new Evenement(n,action));
             });            
                                     
         } catch (IOException ex) {
             Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
         }                       
        
            
         ServeurResponse response = new ServeurResponse();
         response.setStatut(true);       
         response.setDescription("Evenements mis à jour avec succès");       
         return response;          
     } 
}
