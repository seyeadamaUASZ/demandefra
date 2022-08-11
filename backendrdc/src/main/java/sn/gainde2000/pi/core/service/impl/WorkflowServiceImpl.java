/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.repository.WorkflowRepository;
import sn.gainde2000.pi.core.service.IWorkflowService;

/**
 *
 * @author asow
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {

     @Autowired
     WorkflowRepository workflowRepository;

     @Override
     public List<Workflow> getListWorkflow() {
          return workflowRepository.findAll();

     }

     @Override
     public List<Workflow> getWorkflowBySector(String secteur){
               return workflowRepository.getWorkflowBySector(secteur);
     }

     @Override
     public Workflow save(Workflow w) {
          return workflowRepository.save(w);
     }

     @Override
     public Workflow findByWkfId(Long id) {
          return workflowRepository.getOne(id);
     }

     @Override
     public Workflow findByName(String name) {
          return workflowRepository.findByName(name);
     }
     @Override
     public Workflow findByWkfSecteur(String wkfSecteur) {
          return workflowRepository.findByWkfSecteur(wkfSecteur);
     }
     @Override
     public Workflow findByWkfConteneur(String wkfConteneur) {
          return workflowRepository.findByWkfConteneur(wkfConteneur);
     }

     @Override
     public void delete(Workflow workflow) {
          workflowRepository.delete(workflow);
     }


     @Override
     public List<Workflow> findBywkfAppId(Long wkfAppId) {
          // TODO Auto-generated method stub
          return workflowRepository.findBywkfAppId(wkfAppId);
     }

     @Override
     public List<Workflow> getListWorkflowLibre() {
          return workflowRepository.getWorkflowLibre();
     }

     @Override
     public void lierWorkflow(String idApp, String idWorkflow) {
          workflowRepository.lierWorkflow(idApp, idWorkflow);
     }

     @Override
     public void enleverLiasonWorkflow(String idWorkflow) {
          workflowRepository.enleverLiaisonWorkflow(idWorkflow);

     }

     @Override
     public List<Workflow> getWorkflowByAppId(Long id) {
          return workflowRepository.findBywkfAppId(id);
     }

    @Override
    public List<Workflow> getWorkflowByAppIdOuLibre(Long id) {
        return workflowRepository.getWorkflowByAppIdOulibre(id);
    }

     @Override
     public int nombreWorkflow() {
          return workflowRepository.nombreWorkflowGenerate();
     }

    

}
