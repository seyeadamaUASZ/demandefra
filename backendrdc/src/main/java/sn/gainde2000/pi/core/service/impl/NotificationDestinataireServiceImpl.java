package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationDestinataire;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.repository.NotificationDestinataireRepository;
import sn.gainde2000.pi.core.service.INotificationDestinataireService;

@Service
public class NotificationDestinataireServiceImpl implements INotificationDestinataireService {
	
	@Autowired
	NotificationDestinataireRepository notificationDestinataireRepository;

	@Override
	public List<Notification> getListNotificationParExpediteur(Utilisateur expediteur) {
		return notificationDestinataireRepository.findByExpediteurs(expediteur);
	}

	@Override
	public List<Notification> getListNotificationExpediteurParDestinataire(Utilisateur destinataire, Utilisateur expediteur) {
		return notificationDestinataireRepository.findByDestinataireExpediteurs(destinataire, expediteur);
	}

	@Override
	public NotificationDestinataire save(NotificationDestinataire nd) {
		return notificationDestinataireRepository.save(nd);
	}

	@Override
	public void supprimerNotification(NotificationDestinataire notificationDestinataire) {
		notificationDestinataireRepository.delete(notificationDestinataire);
	}

	@Override
	public Optional<NotificationDestinataire> findById(Long id) {
		return notificationDestinataireRepository.findById(id);
	}

	@Override
	public List<NotificationDestinataire> getListNotificationDestinataire() {
		return notificationDestinataireRepository.findAll();
	}

	@Override
	public List<Utilisateur> getListDestinataireParNotification(Notification notification) {
		return notificationDestinataireRepository.findByNotifications(notification);
	}

	@Override
	public void removedDestinataire(Notification notification, Utilisateur expediteur) {
		notificationDestinataireRepository.removedDestinataire(notification, expediteur);
	}

}
