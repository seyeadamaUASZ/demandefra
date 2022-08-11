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
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.ChampsQrcode;

/**
 *
 * @author asow
 */
public interface ChampsQrcodeRepository extends JpaRepository<ChampsQrcode, Long> {

    List<ChampsQrcode> findByCqdQrcId(Long id);

     
     @Query("select ch from ChampsQrcode ch where ch.cqdQrcId =:id ORDER BY ch.cqdId DESC")
     public List<ChampsQrcode> listByQrcodeId(@Param("id")Long id);
     
     @Transactional
     @Modifying
     @Query("DELETE FROM ChampsQrcode ch WHERE ch.cqdQrcId =:id")
     public void supprimer(@Param("id")Long id);

}
