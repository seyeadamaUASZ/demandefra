/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.ParametrageWidget;
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;

/**
 *
 * @author jsarr
 */
@Repository
public interface ParametrageWidgetRepository extends JpaRepository<ParametrageWidget, Long> {
     public ParametrageWidget findByPwId(String pwId); 
    /*@Modifying
    @Transactional
    @Query(value = "select * from tp_parametrage_widget pw where pw.pw_wkf_id =:idwrkf", nativeQuery = true)*/
     @Query(value = "select pw from ParametrageWidget pw,Workflow w where w.wkfId =:idwrkf")
    public List<ParametrageWidget> findByWkflId(@Param("idwrkf") Long idwrkf);
    
   /* @Modifying
    @Transactional
    @Query(value = "SELECT tp_jbpmformulaire.jfrm_id,tp_jbpmformulaire.jfrm_idworkflow,td_champsworkflow.chpw_id, td_champsworkflow.chpw_name,td_champsworkflow.chpw_frm_id FROM tp_jbpmformulaire LEFT JOIN td_champsworkflow ON tp_jbpmformulaire.jfrm_id=td_champsworkflow.chpw_frm_id WHERE tp_jbpmformulaire.jfrm_idworkflow=:idwrkf", nativeQuery = true)
    public List<Map<String, Object>> ExtractChampsFormParam(@Param("idwrkf") Long idwrkf);*/
    /*@Modifying
    @Transactional
    @Query(value = "SELECT td_champs_v2.chp_id,td_champs_v2.chp_nom,td_champs_v2.step_id FROM td_champs_v2,tr_step WHERE td_champs_v2.step_id=tr_step.step_id AND tr_step.formulaire=:idwrkf", nativeQuery = true)*/
     @Query(value = "SELECT cv  FROM ChampsV2 cv ,Step st WHERE st.formulaire=:idwrkf")
    public List<ParametrageWidget> ExtractChampsFormParam(@Param("idwrkf") Long idwrkf);
    //public List<Map<String, Object>> ExtractChampsFormParam(@Param("idwrkf") Long idwrkf);
    
    
     
    
     
}
    