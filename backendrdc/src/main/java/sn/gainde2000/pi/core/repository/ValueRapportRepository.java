package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.pi.core.entity.ValueRapport;

public interface ValueRapportRepository extends JpaRepository<ValueRapport, Long> {
	
	@Query("select ch from ValueRapport ch where ch.rapport.rptId =:id ")
    public List<ValueRapport> listByRapportId(@Param("id")Long id);
	
	@Query("select v from ValueRapport v where v.rapport.rptId =:id")
	public void supprimerByRapportId(@Param("id")Long id);

}
