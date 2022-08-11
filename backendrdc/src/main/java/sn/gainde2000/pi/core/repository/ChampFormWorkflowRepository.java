package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.ChampFormWorkflow;


@Repository
public interface ChampFormWorkflowRepository extends JpaRepository<ChampFormWorkflow, Long> {
	  @Query("select chpw from ChampFormWorkflow chpw where chpw.chpWFrmId =:chpWFrmId")
	     public List<ChampFormWorkflow> getListChampsWorkflowByForm(@Param("chpWFrmId")Long chpWFrmId);
	  
	  /*@Query("select chpw from ChampFormWorkflow chpw, Workflow w, JbpmFormInfos jf where w.wkfId=jf.jfrmIdworkflow AND jf.jfrmId=chpw.chpWFrmId AND w.wkfId=:wkfId ")
	     public List<ChampFormWorkflow> getListChampsWorkflowByWorkflow(@Param("wkfId")Long wkfId);*/
}
