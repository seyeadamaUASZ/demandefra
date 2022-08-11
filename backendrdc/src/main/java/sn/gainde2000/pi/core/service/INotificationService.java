
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationProfil;

/**
 *
 * @author sagueye
 */
public interface INotificationService {

    public List<Notification> getListNotification();

    public Notification save(Notification notification);

    public Optional<Notification> findById(Integer id);

    public void supprimerNotification(NotificationProfil notification);
   
}
