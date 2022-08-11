/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.ChampsRapport;

/**
 *
 * @author asow, sagueye
 */
@Repository
public interface ChampsRapportRipository extends JpaRepository<ChampsRapport, Long>{
     List<ChampsRapport> findByCrtId(Long id);
     
     @Query("select ch from ChampsRapport ch where ch.crtRptId =:id ORDER BY ch.crtId DESC")
     public List<ChampsRapport> listByRepportId(@Param("id")Long id);
     
     @Transactional
     @Modifying
     @Query("DELETE FROM ChampsRapport ch WHERE ch.crtRptId =:id")
//     @Query("delete ch from ChampsRapport ch where ch.crtRptId =:id")
     public void supprimer(@Param("id")Long id);
    
}
