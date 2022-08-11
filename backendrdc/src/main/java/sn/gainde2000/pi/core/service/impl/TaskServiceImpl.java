/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.repository.TaskRepository;
import sn.gainde2000.pi.core.service.ITaskService;

/**
 *
 * @author jsarr
 */
@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EntityManager em;

    @Override
    public Task findByTskId(Long tskId) {
        return taskRepository.findByTskId(tskId);
    }

    @Override
    public List<Task> getListTaskByTskId(Long tskId) {
        return taskRepository.getListTaskByTskId(tskId);
    }

    @Override
    public Task save(Task t) {
        return taskRepository.save(t);
    }

    @Override
    public void delete(Long tskId) {
        taskRepository.deleteById(tskId);
    }

    @Override
    public Task findByWorkflowAndProfil(Long workflowId, Long profilId) {
        return taskRepository.getTaskByWorkflowAndProfilId(workflowId, profilId);
    }

    @Override
    public List<?> getListTaskByTaskIdAndForm(Long taskId, String form) {

        String s = "SELECT *From " + form + "WHERE tsk_id=:taskId";

        System.out.println(s);

        List<?> allTask = em.createNativeQuery(s).getResultList();

        return allTask;

    }


    @Override
    public List<Task> getListTaskByTskWkfId(Long tskWkfId) {
        return taskRepository.getListTaskByTskWkfId(tskWkfId);
    }


    @Override
    public List<Task> getAllTask() {
        // TODO Auto-generated method stub
        return taskRepository.findAll();
    }

    @Override
    public void updateWrkTask(Task t) {
        taskRepository.save(t);
    }

    @Override 
    public List<Task> getAllTaskStatusUser(Long tskId, Long profilId) {
       return taskRepository.getAllTaskStatusUser(tskId,profilId);
    }
 
     
    @Override
    public Task findByTskWkfId(Long WkfId) {
       return taskRepository.findByTskWkfId(WkfId);
    }

    @Override
    public List<Task> findByWorkflowTaskByAppId(Long id) {
        return taskRepository.findByWorkflowTaskByAppId(id);
    }

    
}
