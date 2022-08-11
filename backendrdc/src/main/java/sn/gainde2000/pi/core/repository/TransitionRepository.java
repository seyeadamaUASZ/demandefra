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
import sn.gainde2000.pi.core.entity.Transition;

/**
 *
 * @author yougadieng
 */
@Repository
public interface TransitionRepository extends JpaRepository<Transition,Long>{
    Transition findByTrnTskActuel(Long taskId);
     @Query(value = "SELECT * FROM tr_transition trn inner join tp_workflow w on  trn.trn_wkf_id=w.wkf_id WHERE w.wkf_app_id =:id", nativeQuery = true)
    public List<Transition> findByWorkflowTransByAppId(@Param("id") Long id);
}
