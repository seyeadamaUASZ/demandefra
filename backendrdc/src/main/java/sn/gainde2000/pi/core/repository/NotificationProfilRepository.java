
package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationProfil;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;

@Repository
public interface NotificationProfilRepository extends JpaRepository<NotificationProfil, Long> {

    @Query("SELECT p FROM Profil p INNER JOIN NotificationProfil npr ON (p.proId=npr.profil.proId) WHERE npr.notification =:notification ")
    public List<Profil> findProfilByNotifications(@Param("notification") Notification notification);

    @Query("SELECT n FROM Notification n INNER JOIN NotificationProfil npr ON (n.ntfId=npr.notification.ntfId) WHERE npr.profil =:profil AND n.ntfExpediteurId=:expediteur")
    public List<Notification> findByProfilExpediteurs(@Param("profil") Profil profil, @Param("expediteur") Utilisateur expediteur);

    @Query("SELECT n FROM Notification n INNER JOIN NotificationProfil npr ON (n.ntfId=npr.notification.ntfId) WHERE n.ntfExpediteurId=:expediteur")
    public List<Notification> findByExpediteurs(@Param("expediteur") Utilisateur expediteur);

    @Transactional
    @Modifying
    @Query("DELETE FROM NotificationProfil npr WHERE npr.notification =:notification AND npr.profil =:profil")
    public void removedProfil(@Param("notification") Notification notification, @Param("profil") Profil profil);

    @Query("SELECT n FROM NotificationProfil n WHERE n.notification.ntfObjet LIKE %:Keyword%")
    public List<NotificationProfil> seach(@Param("Keyword") String Keyword);
}

