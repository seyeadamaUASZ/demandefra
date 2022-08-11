package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.Workflow;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    public Workflow findByName(String name);
    public Workflow findByWkfSecteur(String wkfSecteur);
    public Workflow findByWkfConteneur(String wkfConteneur);
   

    @Query("select wf from Workflow wf  where wf.wkfAppId.appId =:wkfAppId")
    public List<Workflow> findBywkfAppId(@Param("wkfAppId") Long wkfAppId);

    @Query("select wf from Workflow wf  where wf.wkfAppId.appId =:wkfAppId or wf.wkfAppId.appId=null ")
    public List<Workflow> getWorkflowByAppIdOulibre(@Param("wkfAppId") Long wkfAppId);

    @Query("select wf from Workflow wf  where wf.wkfAppId.appId = null")
    public List<Workflow> getWorkflowLibre();
     @Query("SELECT count(*) from Workflow wf")
    public int  nombreWorkflowGenerate();

    @Query("select wf from Workflow wf where wf.wkfAppId =:id")
    public List<Workflow> getWorkflowByAppId(@Param("id") Long id);
    
   /* @Query("select t  from Workflow wf,Task t where t.tskWkfId = wf.wkfId AND  wf.wkfAppId =:id")
    public List<Task> getWorkflowTaskByAppId(@Param("id") Long id);*/
     
    
    @Query("select wf from Workflow wf where wf.wkfConteneur !='' AND wf.wkfSecteur =:secteur")
    public List<Workflow> getWorkflowBySector(@Param("secteur") String secteur);
    @Modifying
    @Transactional
    @Query(value = "update tp_workflow set wkf_app_id =:idApp where wkf_id=:idWorkflow", nativeQuery = true)
    public void lierWorkflow(@Param("idApp") String idApp, @Param("idWorkflow") String idWorkflow);

    @Modifying
    @Transactional
    @Query(value = "update tp_workflow set wkf_app_id =null where wkf_id=:idWorkflow", nativeQuery = true)
    public void enleverLiaisonWorkflow(@Param("idWorkflow") String idWorkflow);

}
