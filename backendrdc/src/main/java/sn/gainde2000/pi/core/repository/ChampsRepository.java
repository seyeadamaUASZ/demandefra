package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.entity.Profil;

import java.math.BigInteger;
import java.util.List;


@Repository
public interface ChampsRepository extends JpaRepository<Champs,Long> {
    List<Champs> findBychFrmId(Long id);

	@Query("select ch from Champs ch where ch.chFrmId =:id ORDER BY ch.chpId DESC")
    public List<Champs> listByFrmId(@Param("id")Long id);
	@Query("select ch from Champs ch where ch.chFrmId =:id and ch.chpType='file' ORDER BY ch.chpId DESC")
    public List<Champs> listByFrmIdFile(@Param("id")Long id);
	
	@Query("select ch from Champs ch where ch.chFrmId =:id")
	public void supprimer(@Param("id")Long id);
	
	@Query("select ch from Champs ch where ch.chFrmId =:id and ch.chpType!='button' ORDER BY ch.chpId DESC")
    public List<Champs> listFieldByFrmId(@Param("id")Long id);
	
	@Query("select ch from Champs ch where ch.chFrmId =:id and ch.chpType='button' ORDER BY ch.chpId DESC")
    public List<Champs> listButtonByFrmId(@Param("id")Long id);
}
