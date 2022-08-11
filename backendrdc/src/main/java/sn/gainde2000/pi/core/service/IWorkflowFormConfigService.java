/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.WorkflowFormConfig;

/**
 *
 * @author jsarr
 */
public interface IWorkflowFormConfigService {
     public WorkflowFormConfig findByWfcIdFormulaire(Long wfcIdFormulaire);
    
 public WorkflowFormConfig findByWfcId(Long wfcId);
    public WorkflowFormConfig save(WorkflowFormConfig wcf);
    void delete(Long wcfId); 
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfig(); 
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigById(Long wcfId);
     public List<WorkflowFormConfig> getListWfcWorkflowFormConfigGenerer(Long wrfkId);
     //public List<WorkflowFormConfig> getListWfcWorkflowFormConfigNoGenerer();
     public List<WorkflowFormConfig> getListWfcWorkflowFormConfigByWorkflow(Long wrfkId);
     public List<WorkflowFormConfig> getListWfcPrimaryFormConfigByWorkflow(Long wrfkId);
}
