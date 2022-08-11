package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.TypeNotification;

/**
*
* @author sagueye
*/
@Repository
public interface TypeNotificationRepository extends JpaRepository<TypeNotification, Integer>  {
    public TypeNotification findByTntNom(String tntNom);
}
