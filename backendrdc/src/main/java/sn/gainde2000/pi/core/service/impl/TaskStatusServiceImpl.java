/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.TaskStatus;
import sn.gainde2000.pi.core.repository.TaskStatusRepository;
import sn.gainde2000.pi.core.service.ITaskStatusService;

/**
 *
 * @author jsarr
 */
@Service
public class TaskStatusServiceImpl implements ITaskStatusService{
    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus findByStsId(Long stsId) {
         return taskStatusRepository.findByStsId(stsId);
    }

    @Override
    public List<TaskStatus> getAllTaskStatus() {
       return taskStatusRepository.getAllTaskStatus();
    }
    
    @Override
    public TaskStatus save(TaskStatus ts){
        return taskStatusRepository.save(ts);
    }
}
