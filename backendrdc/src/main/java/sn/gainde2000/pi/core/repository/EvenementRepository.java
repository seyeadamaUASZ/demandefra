/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Evenement;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.entity.Profil;

/**
 *
 * @author indiaye
 */
@Repository
public interface EvenementRepository extends JpaRepository<Evenement,Long>{
	@Query("SELECT a FROM Action a INNER JOIN Evenement e ON (a.actId=e.action.actId) WHERE e.notification =:notification ")
    public  List<Action> findByNotifications(@Param("notification") Notification notification);
    @Transactional
    @Modifying
    @Query("DELETE FROM Evenement e WHERE e.notification =:notification AND e.action =:action")
    public void removedEvenement(@Param("notification") Notification notification, @Param("action") Action action);

    @Query("SELECT act.actNom FROM Action act, Evenement event where act.actId = event.action  AND event.notification.ntfId = :IdNotification")
    Iterable<Evenement> evenementAccord(@Param("IdNotification") Long IdNotification);
    
    @Query("SELECT e FROM Evenement e WHERE e.action =:action ")
    public  List<Evenement> findByActions(@Param("action") Action action);
}
