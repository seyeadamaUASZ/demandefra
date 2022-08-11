package sn.gainde2000.pi.core.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import sn.gainde2000.pi.core.entity.ValueChamps;

public interface ValueRepository extends JpaRepository<ValueChamps, Long>{
	@Query("select ch from ValueChamps ch where ch.valueChp.chpId =:id ")
    public List<ValueChamps> listByChampId(@Param("id")Long id);
	
	@Query("select v from ValueChamps v where v.valueChp.chpId =:id")
	public void supprimerByChampId(@Param("id")Long id);
}
