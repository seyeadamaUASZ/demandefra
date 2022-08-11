
package sn.gainde2000.pi.core.repository;

import sn.gainde2000.pi.core.entity.Suivredemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SuivredemandeRepository extends JpaRepository<Suivredemande, Long> {
	 @Query("SELECT s from Suivredemande s WHERE s.status IN (SELECT t.tskStatusId from Task t WHERE t.poOwner.proId=:poOwner )")
	    Iterable<Suivredemande> listSuivredemande(@Param("poOwner") Long poOwner);
	 
	 @Query("SELECT s from Suivredemande s WHERE s.owner =:owner ")
	    Iterable<Suivredemande> listSuivredemandeById(@Param("owner") Long owner);
 @Query("SELECT s from Suivredemande s WHERE s.id =:id ")
	   Suivredemande getOneSuivredemande(@Param("id") Long id);

}