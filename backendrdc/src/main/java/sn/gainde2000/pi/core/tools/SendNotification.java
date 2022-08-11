package sn.gainde2000.pi.core.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Evenement;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.service.IActionService;
import sn.gainde2000.pi.core.service.IEvenementService;
import sn.gainde2000.pi.core.service.INotificationDestinataireService;
import sn.gainde2000.pi.core.service.INotificationProfilService;
import sn.gainde2000.pi.core.service.INotificationService;
import sn.gainde2000.pi.core.service.impl.UtilisateurImpl;
import sn.gainde2000.pi.core.tools.StringProcess;

/**
*
* @author sagueye
*/
@Service
public class SendNotification {
	 @Autowired
	 IEvenementService evService;
	 @Autowired
	 UtilisateurImpl userService;
	 /*@Autowired
	 INotificationService iNotificationService;*/
	 @Autowired
	 INotificationDestinataireService notDesService;
	 @Autowired
	 INotificationProfilService notProService;
	 @Autowired 
	 IActionService   actionService;
	 
	 @Autowired
	 Email email;
	 @Autowired
	 Sms sms;
	 
	public void checkedNotification (String action, String notifparam) {
	    Action a = actionService.findByCode(action);
            StringProcess sp = new StringProcess();   
            String[] notifInfos = null;
            if(notifparam!=null){                
                final ObjectMapper om= new ObjectMapper();
                notifInfos = notifparam.split("\\$");                              
            }
		String typeNotificationMail = "Mail";
		String typeNotificationSms = "Sms";
		List<Evenement> evenements = evService.getListEvenementParActions(a);
		evenements.forEach(even -> {
			Notification n = even.getNotification();
			Iterable<Utilisateur> utilisateurs = notDesService.getListDestinataireParNotification(n);
			Iterable<Profil> profils = notProService.getListProfilParNotifications(n);			
                         
			if (n.getNtfTntId().getTntNom().equals(typeNotificationMail)) {
				utilisateurs.forEach(user -> {
					try {
                                            //System.out.println("**********USER*************"+user.getUtiEmail());
                                        String from = "no-reply@gainde2000.sn";
				        String to = user.getUtiEmail();
				        String subject = n.getNtfObjet();
				        //String message = n.getNtfText();
                                        String message = n.getNtfText();                                                                                             
                                        //message = sp.replaceWordsStartWithDollar(message, notifInfos);                                                                               
				        email.sendMail(from, to, message, subject);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				});
				profils.forEach(profil -> {
					Iterable<Utilisateur> utilisateurParProfis = userService.findUsersByUtiProfil(profil);
					utilisateurParProfis.forEach(user -> {
						try {
							String from = "no-reply@gainde2000.sn";
					        String to = user.getUtiEmail();
					        String subject = n.getNtfObjet();
					        //String message = n.getNtfText();
                                                String message = n.getNtfText();                                                                                              
					        email.sendMail(from, to, message, subject);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					});
				});
				
			} else if (n.getNtfTntId().getTntNom().equals(typeNotificationSms)){
				utilisateurs.forEach(user -> {
					String to = user.getUtiTelephone();
					//String message = n.getNtfText();
                                        String message = n.getNtfText();                                        
					sms.sendSms(to, message);
				});
				profils.forEach(profil -> {
					Iterable<Utilisateur> utilisateurParProfis = userService.findUsersByUtiProfil(profil);
					utilisateurParProfis.forEach(user -> {
						String to = user.getUtiTelephone();
						//String message = n.getNtfText();
                                                String message = n.getNtfText();                                               
						sms.sendSms(to, message);
					});
				});
			}
			else {
				utilisateurs.forEach(user -> {
					
				});
			}
		});
	}
}
