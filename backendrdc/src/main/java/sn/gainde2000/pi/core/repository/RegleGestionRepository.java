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
import sn.gainde2000.pi.core.entity.RegleGestion; 

/**
 *
 * @author jsarr
 */
@Repository
public interface RegleGestionRepository extends JpaRepository<RegleGestion, Long>{
    
    public RegleGestion findByRgId(Long RgId);
     @Query(value = "SELECT * FROM tp_regle_gestion rg inner join tp_workflow w on  rg.rg_wrfl_id=w.wkf_id WHERE w.wkf_app_id =:id", nativeQuery = true)
    public List<RegleGestion> findByRegleGestionWrkId(@Param("id") Long id);
    
}
