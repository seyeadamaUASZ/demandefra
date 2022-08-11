package sn.gainde2000.pi.core.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.HistoriqueSession;

@Repository
public interface HistoriqueSessionRepository extends JpaRepository<HistoriqueSession, Long> {

     HistoriqueSession findByHseUtiId(Long hseId);

     @Query("SELECT h FROM HistoriqueSession h where h.hseUtiId=:id and h.hseDateDecConnexion=null")
     public HistoriqueSession SessionByIdUser(@Param("id") Long id);
     @Transactional
     @Modifying
     @Query(value="UPDATE td_historique_session SET hse_date_decconnexion=CURRENT_TIMESTAMP WHERE hse_ses_id=:id AND hse_date_decconnexion IS NULL",nativeQuery = true)
     void updateDate(@Param("id") Long id);

}
