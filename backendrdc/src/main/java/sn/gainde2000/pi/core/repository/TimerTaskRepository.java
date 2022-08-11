/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; 
import sn.gainde2000.pi.core.entity.TimerTask;

/**
 *
 * @author jsarr
 */
@Repository
public interface  TimerTaskRepository extends JpaRepository<TimerTask , Long>{
    public TimerTask findByTmId(Long tmId);
    
    @Query(value = "SELECT * FROM tp_task_timer,tp_task WHERE tp_task_timer.tm_task_id =tp_task.tsk_id AND tp_task_timer.tm_id=:tmId", nativeQuery = true)
    public List<Map<String, Object>> fetchdonneetaskandtimer(@Param("tmId") Long tmId);
    
    @Query(value = "SELECT * FROM tp_task_timer ttt inner join tp_workflow w on  ttt.tm_workflowid=w.wkf_id WHERE w.wkf_app_id =:id", nativeQuery = true)
    public List<TimerTask> findByTimerTaskWrkId(@Param("id") Long id);
}
