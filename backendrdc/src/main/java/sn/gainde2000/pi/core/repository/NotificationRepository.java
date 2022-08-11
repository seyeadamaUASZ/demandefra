
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Notification;

/**
 *
 * @author sagueye
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  

}

