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
import sn.gainde2000.pi.core.entity.AppFonc;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Fonctionnalite;

/**
 *
 * @author asow
 */
@Repository
public interface AppFoncRepository extends JpaRepository<AppFonc, Long> {

    public AppFonc findByIdAppliFonc(Long id);

    /*@Query(value="SELECT *FROM tp_module m left join tp_fonctionnalite f on m.mod_id=f.fon_mod_id left join tr_app_fonc t on f.fon_id=t.appli_fonc_fon_id where t.appli_fonc_app_id=:id GROUP BY m.mod_id",nativeQuery = true)
     public List<AppFonc> findByModuleAppId(@Param("id") Long id);*/
    @Query(value = "SELECT COUNT(*) from tr_app_fonc where appli_fonc_app_id=:idApp and appli_fonc_fon_id=:idFon", nativeQuery = true)
    public int findByIdAppliAndIdFonc(@Param("idApp") Application idApp, @Param("idFon") Fonctionnalite idFon);

    @Modifying
    @Transactional
    @Query(value = "update tr_app_fonc set appli_fon_is_active=true where appli_fonc_app_id=:idApp and appli_fonc_fon_id=:idFon", nativeQuery = true)
    public void ActiverFonctionnalite(@Param("idApp") Application idApp, @Param("idFon") Fonctionnalite idFon);

    @Modifying
    @Transactional
    @Query(value = "update tr_app_fonc set appli_fon_is_active=false where appli_fonc_app_id=:idApp and appli_fonc_fon_id=:idFon", nativeQuery = true)
    public void DesactiverFonctionnalite(@Param("idApp") Application idApp, @Param("idFon") Fonctionnalite idFon);

     @Query(value = "SELECT *from tr_app_fonc where appli_fonc_app_id=:idApp and appli_fonc_fon_id=:idFon ", nativeQuery = true)
     public AppFonc FindAppliFonIsActive(@Param("idApp") Long idApp, @Param("idFon") Long idFon);
     
         @Query( value = "SELECT *FROM tr_app_fonc tr JOIN tp_fonctionnalite f ON (f.fon_id = tr.appli_fonc_fon_id) where f.fon_mod_id=:fonModId and tr.appli_fonc_app_id=:appId ", nativeQuery = true)
    public List<AppFonc> findByfonModId(@Param("fonModId") Long fonModId, @Param("appId") Application appId);
    

}
