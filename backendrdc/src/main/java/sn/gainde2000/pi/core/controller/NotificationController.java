
package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.web.bind.annotation.PathVariable;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationDestinataire;
import sn.gainde2000.pi.core.entity.NotificationProfil;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.INotificationDestinataireService;
import sn.gainde2000.pi.core.service.INotificationProfilService;
import sn.gainde2000.pi.core.service.INotificationService;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.service.impl.UtilisateurImpl;
import sn.gainde2000.pi.core.tools.Email;

/**
 *
 * @author sagueye
 */
@RestController
@CrossOrigin("*")
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    INotificationService iNotificationService;
    @Autowired
    IUtilisateur userService;
    @Autowired
    INotificationDestinataireService notDesService;
    @Autowired
    INotificationProfilService notProService;
    @Autowired
    IProfile profileService;

    @Autowired
    Email email;

    @GetMapping
    public ServeurResponse getNotifications() {

        Iterable<Notification> notifications = iNotificationService.getListNotification();
        ServeurResponse response = new ServeurResponse();
        if (notifications == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(notifications);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("/listnotificationprofil")
    public ServeurResponse getNotificationProfils() {

        Iterable<NotificationProfil> notificationProfils = notProService.getListNotificationProfil();
        ServeurResponse response = new ServeurResponse();
        if (notificationProfils == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(notificationProfils);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @PostMapping("addaudioparprofil")
    public ServeurResponse createNotification(HttpServletRequest request, @RequestParam("audio") MultipartFile file) {
        ServeurResponse response = new ServeurResponse();
        try {
            Notification notification = new ObjectMapper().readValue(request.getParameter("notification"), new TypeReference<Notification>() {
            });
            Profil p = new ObjectMapper().readValue(request.getParameter("profil"), new TypeReference<Profil>() {
            });
            List<Utilisateur> utilisateurs = userService.findUsersByUtiProfil(p);
            if (file != null) {
                notification.setNtfAudio(file.getBytes());
            }
            Notification n = iNotificationService.save(notification);
            utilisateurs.forEach(u -> {
                NotificationDestinataire nd = new NotificationDestinataire(n, u);
                notDesService.save(nd);
            });
            response.setStatut(true);
            response.setData(n);
            response.setDescription("Enregistrement reussi");
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

//	@PostMapping("/addparprofil")
//    public ServeurResponse createNotificationParProfil(HttpServletRequest request) {
//	 	
//        ServeurResponse response = new ServeurResponse();
//		try {
//			Notification notification = new ObjectMapper().readValue(request.getParameter("notification"), new TypeReference<Notification>() {});
//	        Profil p = new ObjectMapper().readValue(request.getParameter("profil"), new TypeReference<Profil>() { });
//	        List<Utilisateur> utilisateurs = userService.findUsersByUtiProfil(p);
//	        Notification n = iNotificationService.save(notification);
//	        utilisateurs.forEach(u -> {
//	        	NotificationDestinataire nd = new NotificationDestinataire(n, u);
//	        	notDesService.save(nd);
//	        });
//	        iNotificationService.save(n);
//	        response.setStatut(true);
//	        response.setData(n);
//	        response.setDescription("Enregistrement reussi");
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		
//        return response;
//    }
    @PostMapping("/addparprofil")
    public ServeurResponse createNotificationParProfil(HttpServletRequest request) {

        ServeurResponse response = new ServeurResponse();
        try {
            Notification notification = new ObjectMapper().readValue(request.getParameter("notification"), new TypeReference<Notification>() {
            });
            Profil p = new ObjectMapper().readValue(request.getParameter("profil"), new TypeReference<Profil>() {
            });
            Notification n = iNotificationService.save(notification);
            NotificationProfil np = new NotificationProfil(n, p);
            notProService.save(np);
            iNotificationService.save(n);
            response.setStatut(true);
            response.setData(n);
            response.setDescription("Enregistrement reussi");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("/create")
    public ServeurResponse createNotification(HttpServletRequest request, @RequestParam("removed") String removed, @RequestParam("added") String added) {
        ServeurResponse response = new ServeurResponse();
        try {
            Notification notification = new ObjectMapper().readValue(request.getParameter("notification"), new TypeReference<Notification>() {
            });
            Profil p = new ObjectMapper().readValue(request.getParameter("profil"), new TypeReference<Profil>() {
            });
//	        List<Utilisateur> utilisateurs = userService.findUsersByUtiProfil(p);
            Notification n = iNotificationService.save(notification);
            if (p != null) {
                NotificationProfil np = new NotificationProfil(n, p);
                notProService.save(np);
            }
            List<Utilisateur> removedDestionataires = new ObjectMapper().readValue(removed, new TypeReference<List<Utilisateur>>() {
            });
            removedDestionataires.forEach(utilisateur -> {
                notDesService.removedDestinataire(n, utilisateur);
            });

            List<Utilisateur> addedDestionataires = new ObjectMapper().readValue(added, new TypeReference<List<Utilisateur>>() {
            });
            addedDestionataires.forEach(utilisateur -> {
                notDesService.save(new NotificationDestinataire(n, utilisateur));
            });
            response.setStatut(true);
            response.setData(n);
            response.setDescription("Enregistrement reussi");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/update")
    public ServeurResponse updateNotification(@RequestBody NotificationProfil notificationProfil) {
        ServeurResponse response = new ServeurResponse();
        iNotificationService.save(notificationProfil.getNotification());
        notProService.save(notificationProfil);
        response.setStatut(true);
        response.setData(notificationProfil);
        response.setDescription("Enregistrement reussi");
        return response;
    }

    @PostMapping("/listprofilparnotification")
    public ServeurResponse getProfilByNotification(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        try {
            Notification n = new ObjectMapper().readValue(request.getParameter("notification"), new TypeReference<Notification>() {
            });
            Iterable<Profil> profils = notProService.getListProfilParNotifications(n);
            response.setStatut(true);
            response.setData(profils);
            response.setDescription("Données récupérées.");
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("notification/{id}/{contenu}")
    public ServeurResponse SendEmailToClient(@PathVariable("id") long id, @PathVariable("contenu") String contenu) {
        ServeurResponse response = new ServeurResponse();
        try {
            Utilisateur uti = userService.findByUtiId(id);
            String from = "no-reply@gainde2000.sn";
            String subject = "confirmation";
            String to = uti.getUtiEmail();
            String message = contenu;
            email.sendMail(from, to, message, subject);
            response.setStatut(true);
            response.setDescription("Un email de confirmation vous a été envoyé ");
        } catch (MessagingException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @GetMapping("notification/{profilId}/{contenu}")
    public ServeurResponse SendEmailToClientByPorfil(@PathVariable("profilId") long id, @PathVariable("contenu") String contenu) {
        ServeurResponse response = new ServeurResponse();
        try {
            Utilisateur uti = new Utilisateur();
            Profil pro = profileService.findByProId(id);
            String from = "no-reply@gainde2000.sn";
            String subject = "confirmation";
            String to = uti.getUtiEmail();
            String message = "Votre processus de confirmation du paiement est  en cours.\nVeuillez finaliser le processus en saisissant ces identifiants de vérification:\t" + contenu;
            email.sendMail(from, to, message, subject);
            response.setStatut(true);
            response.setDescription("Un email de confirmation vous a été envoyé ");

        } catch (MessagingException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @PostMapping("/delete")
    public ServeurResponse deleteNotification(@RequestBody NotificationProfil notif) {
        ServeurResponse response = new ServeurResponse();
        notProService.supprimerNotification(notif);
        response.setStatut(true);
        response.setDescription("suppression reussi!!!");
        return response;
    }

    @GetMapping("notifprofil/{search}")
    public ServeurResponse search(@PathVariable("search") String keyword) {
        ServeurResponse response = new ServeurResponse();
        List<NotificationProfil> notif = notProService.search(keyword);
        if (notif != null) {
            response.setData(notif);
            response.setStatut(true);
            response.setDescription("menu trouvé!");
        } else {
            response.setData(notif);
            response.setStatut(false);
            response.setDescription("Aucun menu trouvé!");
        }

        return response;

    }
}
