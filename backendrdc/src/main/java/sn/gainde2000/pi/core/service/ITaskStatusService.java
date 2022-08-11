/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.TaskStatus;

/**
 *
 * @author jsarr
 */
public interface ITaskStatusService {
    
     public TaskStatus findByStsId(Long stsId);  
     public List<TaskStatus> getAllTaskStatus();
     public TaskStatus save(TaskStatus ts);
}
