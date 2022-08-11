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
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Task;

/**
 *
 * @author jsarr
 */
@Repository
public interface TaskRepository extends JpaRepository<Task , Long>{
     
     public Task findByTskId(Long tskId);
     public Task findByTskWkfId(Long WkfId);
      
      @Query(value = "SELECT * FROM tp_task WHERE tsk_wkf_id =:tskWkfId", nativeQuery = true)
     public List<Task> getListTaskByTskWkfId(@Param("tskWkfId") Long tskWkfId);
     
     @Query("select ts from Task ts where ts.tskId =:tskId") 
     public List<Task> getListTaskByTskId(@Param("tskId") Long tskId);
     
     @Query("select ts from Task ts where ts.tskWkfId =:workflowId and ts.poOwner=:profilId") 
     public Task getTaskByWorkflowAndProfilId(@Param("workflowId") Long workflowId,@Param("profilId") Long profilId);
     
     @Query("select ts from Task ts where ts.tskId =:tskId and ts.poOwner=:profilId") 
     public List<Task> getAllTaskStatusUser(@Param("tskId") Long tskId,@Param("profilId") Long profilId); 
     
     @Query(value = "SELECT * FROM tp_task t inner join tp_workflow w on  t.tsk_wkf_id=w.wkf_id WHERE w.wkf_app_id =:id", nativeQuery = true)
    public List<Task> findByWorkflowTaskByAppId(@Param("id") Long id);
    
}
