/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.WorkflowFormConfig;
import sn.gainde2000.pi.core.repository.WorkflowFormConfigRepository;
import sn.gainde2000.pi.core.service.IWorkflowFormConfigService;

/**
 *
 * @author jsarr
 */
@Service
public class WorkflowFormConfigImpl implements IWorkflowFormConfigService{
    @Autowired
    WorkflowFormConfigRepository workflowFormConfigRepository;
 
     

    @Override
    public WorkflowFormConfig save(WorkflowFormConfig wcf) {
         return workflowFormConfigRepository.save(wcf);
    }

    @Override
    public void delete(Long wcfId) {
         workflowFormConfigRepository.deleteById(wcfId);
    }
 
    @Override
    public WorkflowFormConfig findByWfcIdFormulaire(Long wfcIdFormulaire) {
        return workflowFormConfigRepository.findByWfcIdFormulaire(wfcIdFormulaire);
    }
 

    @Override
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfig() {
       return workflowFormConfigRepository.getListWfcWorkflowFormConfig();
    }

    @Override
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigById(Long wcfId) {
       return workflowFormConfigRepository.getListWfcWorkflowFormConfigById(wcfId);
    }

    @Override
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigGenerer(Long wrfkId) {
        return workflowFormConfigRepository.getListWfcWorkflowFormConfigGenerer(wrfkId);
    }

  /*  @Override
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigNoGenerer() {
        return workflowFormConfigRepository.getListWfcWorkflowFormConfigNoGenerer();
    }*/

    @Override
    public List<WorkflowFormConfig> getListWfcWorkflowFormConfigByWorkflow(Long wrfkId) {
        return workflowFormConfigRepository.getListWfcWorkflowFormConfigByWorkflow(wrfkId);
    }

    @Override
    public WorkflowFormConfig findByWfcId(Long wfcId) {
         return workflowFormConfigRepository.findByWfcId(wfcId);
    }

    @Override
    public List<WorkflowFormConfig> getListWfcPrimaryFormConfigByWorkflow(Long wrfkId) {
        return workflowFormConfigRepository.getListWfcPrimaryFormConfigByWorkflow(wrfkId);
    }

     
     
      
     
    
}
