/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.Workflow;

/**
 *
 * @author asow
 */
public interface IWorkflowService {

    public List<Workflow> getListWorkflow();
    public List<Workflow> getWorkflowBySector(String secteur);
    public List<Workflow> getListWorkflowLibre();
    public List<Workflow> getWorkflowByAppIdOuLibre(Long id);
    public List<Workflow> getWorkflowByAppId(Long id);
    public Workflow save(Workflow w);
    public Workflow findByWkfId(Long id);
    public Workflow findByName(String name);
    public Workflow findByWkfSecteur(String wkfSecteur);
    public Workflow findByWkfConteneur(String wkfConteneur);
    public void delete(Workflow workflow);
    public void lierWorkflow(String idApp,String idWorkflow);
    public void enleverLiasonWorkflow(String idWorkflow);
    public List<Workflow>  findBywkfAppId(Long wkfAppId);
   
    
    public int nombreWorkflow();


}
