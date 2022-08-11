/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Inscription;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.TypeDeNotification;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IInscriptionService;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.ITypeDeNotificationService;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.tools.Email;
import sn.gainde2000.pi.core.tools.PasswordGenerator;
import sn.gainde2000.pi.core.tools.Sms;

/**
 *
 * @author asow
 */
@RestController
@CrossOrigin("*")
public class InscriptionController {

    @Autowired
    IInscriptionService inscriptionService;
    @Autowired
    IProfile profileService;
    @Autowired
    IUtilisateur utilisateurService;
    @Autowired
    Email email;
    @Autowired
    ITypeDeNotificationService typService;

    @PostMapping("inscription/create")
    public ServeurResponse createUtilisateur(@RequestBody Inscription inscription) {
        Utilisateur utilisateur = new Utilisateur();
        String npwd = PasswordGenerator.GenerateRandomString();
        utilisateur.setUtiPassword(encoder().encode(npwd));
        ServeurResponse response = new ServeurResponse();
        inscription.setInsDateCreation(new Date());
        utilisateur.setUtiPrenom(inscription.getInsPrenom());
        utilisateur.setUtiNom(inscription.getInsNom());
        utilisateur.setUtiUsername(inscription.getInsUsername());
        utilisateur.setUtiTelephone(inscription.getInsTelephone());
        utilisateur.setUtiEmail(inscription.getInsEmail());
        utilisateur.setUtiDateCreation(inscription.getInsDateCreation());
        Profil profil = profileService.findByNouvelleInscri();
        utilisateur.setUti_pro_id(profil);
        utilisateur.setUtiActived(true);
        utilisateur.setUtiPremierConnect(true);
        if (utilisateurService.findUsersByUtiEmail(utilisateur.getUtiEmail()) == 0) {
            if (utilisateurService.findByUtiUsername(utilisateur.getUtiUsername()) == null) {
                TypeDeNotification notif = typService.findByTdnContenu();
                try {
                    if (notif.getTdnContenu().isEmpty()) {
                        response.setStatut(false);
                        response.setDescription("configurez le contenu du mail !.");
                        response.setData(null);

                    } else {
                        if ("sms".equals(notif.getTdnTypeNotif())) {
                            Sms sms = new Sms();
                            sms.sendSms(utilisateur.getUtiTelephone(), notif.getTdnContenu() + ": \n Username: " + utilisateur.getUtiUsername() + "\n Password: " + npwd);
                            utilisateurService.addUtilisateur(utilisateur);
                            inscriptionService.addUtilisateur(inscription);
                            response.setStatut(true);
                            response.setDescription("Vos idenfiants de connexions sont envoyées par sms");
                        } else {
                            utilisateurService.addUtilisateur(utilisateur);
                            inscriptionService.addUtilisateur(inscription);
                            response.setStatut(true);                            
                            response.setDescription("Vos identifiants de connexion sont envoyées dans votre mail!");
                            email.sendMail("no-reply@gainde2000.sn", utilisateur.getUtiEmail(), "Bonjour "+utilisateur.getUtiPrenom()+" "+utilisateur.getUtiNom()+",\n Votre compte utilisateur a été créé. \n Vos paramétres de connexion sont: \n Nom d'utilisateur: "+utilisateur.getUtiUsername()+" \n Mot de passe: "+npwd, "Création de compte utilisateur");
                        }
                    }

                } catch (Exception ex) {
                    response.setStatut(false);
                    response.setDescription("Inscription non réussie.Vérifier les informations saisies!");
                    Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatut(false);
                response.setDescription("Le nom d'utilisateur est déjà  attribué.");
                response.setData(null);
            }
        } else {
            response.setStatut(false);
            response.setDescription("L'email est déjà  attribué.");
            response.setData(null);
        }

        return response;
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping(value = "inscription/desinscrire")
    public ServeurResponse desinscrire(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String username = (String) request.getParameter("username");
        String oldpwd = (String) request.getParameter("oldpwd");
        Utilisateur user = utilisateurService.findByUtiUsername(username);
        if (user != null) {
            if (!encoder().matches(oldpwd, user.getUtiPassword())) {
                response.setStatut(false);
                response.setDescription("Le mot de passe est incorrect. Veuillez réessayer.");
            } else {
                user.setUtiActived(false);
                utilisateurService.addUtilisateur(user);
                response.setStatut(true);
                response.setDescription("Desinscription reussi!");
                response.setData(user);
            }

        } else {
            response.setStatut(false);
            response.setDescription("Aucun utilisateur n'a été trouvé.");
            response.setData(null);
        }
        return response;
    }

}
