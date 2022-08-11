package sn.gainde2000.pi.core.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Session;

@Repository

/*public interface SessionRepository extends JpaRepository<Session,Long>{
	
	    @Query("SELECT COUNT(*) FROM Session s")
	     int  nombreUserConnecter();
               @Transactional
               @Modifying
	   @Query("DELETE from Session s where s.sesUtiId =:utilisateur")
	   public void supprimerSession(@Param("utilisateur") Utilisateur utilisateur);*/

public interface SessionRepository extends JpaRepository<Session, Long> {

     @Query("SELECT COUNT(*) FROM Session s")
     int nombreUserConnecter();

     @Transactional
     @Modifying
     @Query("DELETE FROM Session s WHERE s.sesId=:id")
     void deleteByIdSession(@Param("id") long id);


     @Transactional
     @Modifying
     @Query("DELETE FROM Session s WHERE s.sesUtiId=:id")
     void deleteUtiSession(@Param("id") long id);

     @Query("SELECT s FROM Session s where s.sesId=:id")
     public Session SessionByIdUser(@Param("id") Long id);

     @Query("SELECT COUNT(*) FROM Session s WHERE s.sesLogin=:username")
     public int VerifierSession(@Param("username") String username);

}



