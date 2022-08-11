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
import sn.gainde2000.pi.core.entity.NotificationDestinataire;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.service.INotificationDestinataireService;

/**
*
* @author sagueye
*/
@RestController
public class NotificationDestinataireController {
	@Autowired
	INotificationDestinataireService notDesService;
    
    @GetMapping("notificationdestionataire")
    public ServeurResponse getNotificationDestinataires() {
    	
        Iterable<NotificationDestinataire> notificationDestinataires = notDesService.getListNotificationDestinataire();
        ServeurResponse response = new ServeurResponse();
        if (notificationDestinataires == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(notificationDestinataires);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @PostMapping("notificationdestionataire/destinatairesparnotification")
    @ResponseBody
    public ServeurResponse getDestinataireByNotification(@RequestBody Notification n){       
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(notDesService.getListDestinataireParNotification(n));
        response.setDescription("Destinataires récupérés avec succès");
        return response;          
    }
    
    @PostMapping("notificationdestionataire/create")
    public ServeurResponse create(@RequestBody NotificationDestinataire notificationDestinataire) {
          ServeurResponse response = new ServeurResponse();         
          if(notDesService.save(notificationDestinataire)!=null){
               response.setStatut(true);
               response.setDescription("notification-destinataire crée avec succès");
          } else {
               response.setStatut(false);
               response.setDescription("Erreur de création!");
          }
          return response;
     }

     @PostMapping("notificationdestionataire/update")
     public ServeurResponse update(@RequestBody NotificationDestinataire notificationDestinataire) {
          ServeurResponse response = new ServeurResponse();
            if(notDesService.save(notificationDestinataire)!=null){
               response.setStatut(true);
               response.setDescription("notification-destinataire crée avec succès");
            } else {
               response.setStatut(false);
               response.setDescription("Erreur lors de la modification du notification-destinataire!");
            }
            return response;
    }

     
     @PostMapping("notificationdestionataire/allocateDestinataire")
     @ResponseBody
     public ServeurResponse allocateDestinaNotification(@RequestParam("notification") String notification, @RequestParam("removed") String removed, @RequestParam("added") String added){ 
         ObjectMapper mapper = new ObjectMapper();       
         try {
             Notification n = mapper.readValue(notification, Notification.class);                       
             
             List<Utilisateur> removedDestionataires =  new ObjectMapper().readValue(removed, new TypeReference<List<Utilisateur>>() { });           
             removedDestionataires.forEach(utilisateur -> {
            	 notDesService.removedDestinataire(n, utilisateur);
             });
             
             List<Utilisateur> addedDestionataires =  new ObjectMapper().readValue(added, new TypeReference<List<Utilisateur>>() { });           
             addedDestionataires.forEach(utilisateur -> {
            	 notDesService.save(new NotificationDestinataire(n,utilisateur));
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
