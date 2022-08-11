
package sn.gainde2000.pi.core.repository;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Motifrejetourenvoi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MotifrejetourenvoiRepository extends JpaRepository<Motifrejetourenvoi, Long> {
	@Query("SELECT s from Motifrejetourenvoi s WHERE s.status IN (SELECT t.tskStatusId from Task t WHERE t.poOwner.proId=:poOwner )")
	Iterable<Motifrejetourenvoi> listMotifrejetourenvoi(@Param("poOwner") Long poOwner);

	@Query("SELECT s from Motifrejetourenvoi s WHERE s.owner =:owner ")
	Iterable<Motifrejetourenvoi> listMotifrejetourenvoiById(@Param("owner") Long owner);

	@Query("SELECT s from Motifrejetourenvoi s WHERE s.id =:id ")
	Motifrejetourenvoi getOneMotifrejetourenvoi(@Param("id") Long id);

	@Query("SELECT d from DemandeAutFra d, Motifrejetourenvoi s where s.idLink = d.id and s.status = 9 and s.owner =:owner ")
	List<DemandeAutFra> listDemandeRenvoyeeById(@Param("owner") Long owner);

	@Query("SELECT d from DemandeAutFra d, Motifrejetourenvoi s where s.idLink = d.id  and s.status = 3 and s.owner =:owner ")
	List<DemandeAutFra> listDemandeValideeById(@Param("owner") Long owner);
	
	@Query("SELECT s from Motifrejetourenvoi s where s.idLink =:idLink AND (s.poOwner = 4 OR s.poOwner = 5) AND s.status =:status")
	List<Motifrejetourenvoi> historiqueCommentaire(@Param("idLink") Long idLink, @Param("status") Long status);
	
	@Query("SELECT s from Motifrejetourenvoi s where s.idLink =:idLink AND s.status = -9 AND (s.poOwner = 4 OR s.poOwner = 5)")
	Motifrejetourenvoi motifRenvoiChefDivision(@Param("idLink") Long idLink);
}