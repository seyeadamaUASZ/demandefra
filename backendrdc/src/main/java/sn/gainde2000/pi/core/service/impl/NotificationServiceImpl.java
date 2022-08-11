package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationProfil;
import sn.gainde2000.pi.core.repository.NotificationRepository;
import sn.gainde2000.pi.core.service.INotificationService;
import sn.gainde2000.pi.core.tools.SendNotification;

@Service
public class NotificationServiceImpl implements INotificationService{	    
	@Autowired
	public NotificationRepository notificationRepository;
        
        @Autowired
        SendNotification sendNotification;
	
	@Override
	public List<Notification> getListNotification() {
		return notificationRepository.findAll();
	}

	@Override
	public Notification save(Notification notification) {
		return notificationRepository.save(notification);
	}

	@Override
	public Optional<Notification> findById(Integer id) {
		return notificationRepository.findById(id);
	}

    @Override
    public void supprimerNotification(NotificationProfil notification) {
      // notificationRepository.delete(notification);
    }
               

}
