package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationDestinataire;
import sn.gainde2000.pi.core.entity.Utilisateur;


/**
*
* @author sagueye
*/
public interface INotificationDestinataireService {
	public List<NotificationDestinataire> getListNotificationDestinataire();
	
	public List<Utilisateur> getListDestinataireParNotification(Notification notification);
	
	public List<Notification> getListNotificationParExpediteur(Utilisateur expediteur);
	
	public List<Notification> getListNotificationExpediteurParDestinataire(Utilisateur destinataire, Utilisateur expediteur);

    public NotificationDestinataire save(NotificationDestinataire notificationDestinataire);

    public Optional<NotificationDestinataire> findById(Long id);

	public void supprimerNotification(NotificationDestinataire notificationDestinataire);
    
    public void removedDestinataire(Notification notification, Utilisateur expediteur);
}
