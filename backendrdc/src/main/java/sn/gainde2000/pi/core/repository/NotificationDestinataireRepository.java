package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.NotificationDestinataire;
import sn.gainde2000.pi.core.entity.Utilisateur;

@Repository
public interface NotificationDestinataireRepository extends JpaRepository<NotificationDestinataire, Long> {
	@Query("SELECT u FROM Utilisateur u INNER JOIN NotificationDestinataire nds ON (u.utiId=nds.utilisateur.utiId) WHERE nds.notification =:notification ")
    public  List<Utilisateur> findByNotifications(@Param("notification") Notification notification);
	@Transactional
    @Modifying
	@Query("SELECT n FROM Notification n INNER JOIN NotificationDestinataire nds ON (n.ntfId=nds.notification.ntfId) WHERE nds.utilisateur =:destinataire AND n.ntfExpediteurId=:expediteur")        
    public  List<Notification> findByDestinataireExpediteurs(@Param("destinataire") Utilisateur destinataire, @Param("expediteur") Utilisateur expediteur);
	
	@Query("SELECT n FROM Notification n INNER JOIN NotificationDestinataire nds ON (n.ntfId=nds.notification.ntfId) WHERE n.ntfExpediteurId=:expediteur")    
    public  List<Notification> findByExpediteurs(@Param("expediteur") Utilisateur expediteur);
    @Transactional
    @Modifying
    @Query("DELETE FROM NotificationDestinataire nd WHERE nd.notification =:notification AND nd.utilisateur =:utilisateur")
    public void removedDestinataire(@Param("notification") Notification notification, @Param("utilisateur") Utilisateur utilisateur);
}
