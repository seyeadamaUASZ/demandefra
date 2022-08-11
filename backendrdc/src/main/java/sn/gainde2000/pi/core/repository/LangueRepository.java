package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Langue;

@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {

     @Transactional
     @Modifying

     //@Query(value ="UPDATE td_utilisateur set tp_langue=:id",nativeQuery = true)
     @Query(value ="UPDATE Utilisateur u set u.uti_lng_id=:id")
     void updateLangue(@Param("id")Long id);


}
