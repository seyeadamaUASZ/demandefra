/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.WorkflowFormConfig;

/**
 *
 * @author jsarr
 */ 
 @Repository
public interface WorkflowFormConfigRepository extends JpaRepository<WorkflowFormConfig, Long> {    
    public WorkflowFormConfig findByWfcIdFormulaire(Long wfcIdFormulaire);
    public WorkflowFormConfig findByWfcId(Long wfcId);
    @Query("select wcf from Workflowformconfig wcf ")
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfig();
   
     @Query("select wcf from Workflowformconfig wcf where wcf.wfcId =:wcfId")
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigById(@Param("wcfId") Long wcfId);
     
     @Query("select wcf from Workflowformconfig wcf where wcf.workflowNameId =:wrfkId AND wcf.statusWcfWorkflow =0 ")
     public List<WorkflowFormConfig> getListWfcWorkflowFormConfigGenerer(@Param("wrfkId") Long wrfkId);
    /* @Query("select wcf from Workflowformconfig wcf where wcf.statusFrmWorkflow =:1")
     public List<WorkflowFormConfig> getListWfcWorkflowFormConfigNoGenerer();*/
     
     @Query("select wcf from Workflowformconfig wcf where wcf.workflowNameId.wkfId =:wrfkId")
     public List<WorkflowFormConfig> getListWfcWorkflowFormConfigByWorkflow(@Param("wrfkId") Long wrfkId);
     
     @Query("select wcf from Workflowformconfig wcf where wcf.wfcPrimaire=1 AND wcf.workflowNameId.wkfId =:wrfkId")
     public List<WorkflowFormConfig> getListWfcPrimaryFormConfigByWorkflow(@Param("wrfkId") Long wrfkId);
     
}
