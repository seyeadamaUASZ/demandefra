
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.repository.NotificationProfilRepository;
import sn.gainde2000.pi.core.service.INotificationProfilService;
import sn.gainde2000.pi.core.entity.NotificationProfil;

@Service
public class NotificationProfilServiceImpl implements INotificationProfilService {

    @Autowired
    NotificationProfilRepository notificationProfilRepository;

    @Override
    public List<NotificationProfil> getListNotificationProfil() {
        return notificationProfilRepository.findAll();
    }

    @Override
    public List<Profil> getListProfilParNotifications(Notification notification) {
        return notificationProfilRepository.findProfilByNotifications(notification);
    }

    @Override
    public NotificationProfil save(NotificationProfil notificationProfil) {
        return notificationProfilRepository.save(notificationProfil);
    }

    @Override
    public Optional<NotificationProfil> findById(Long id) {
        return notificationProfilRepository.findById(id);
    }

    @Override
    public void supprimerNotification(NotificationProfil NotificationProfil) {
        notificationProfilRepository.delete(NotificationProfil);
    }

    @Override
    public Notification SendEmailToClient(Long id, String contenu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotificationProfil> search(String keyword) {
        
        return notificationProfilRepository.seach(keyword);
    }

}
