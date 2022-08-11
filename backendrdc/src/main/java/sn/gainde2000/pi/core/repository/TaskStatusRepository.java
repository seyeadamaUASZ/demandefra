/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

/**
 *
 * @author jsarr
 */


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.TaskStatus;


@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>{
    
    public TaskStatus findByStsId(Long stsId);  
    @Query("select ts from TaskStatus ts ") 
     public List<TaskStatus> getAllTaskStatus();
    
}
