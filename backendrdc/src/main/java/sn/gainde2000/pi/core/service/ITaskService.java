/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Task;

/**
 *
 * @author jsarr
 */
public interface ITaskService {
    public Task findByTskId(Long tskId);
     public Task findByTskWkfId(Long WkfId);
    public Task findByWorkflowAndProfil(Long workflowId,Long profilId);
    public List<?> getListTaskByTaskIdAndForm(Long taskId,String form);
    public List<Task> getListTaskByTskId(Long tskId); 
     public List<Task> getListTaskByTskWkfId(Long tskWkfId);
     public List<Task> getAllTask();
     public List<Task> getAllTaskStatusUser(Long tskId,Long profilId); 
    public void updateWrkTask(Task t);
    public Task save(Task t);  
    void delete(Long tskId); 
     public List<Task> findByWorkflowTaskByAppId(Long id);
}
