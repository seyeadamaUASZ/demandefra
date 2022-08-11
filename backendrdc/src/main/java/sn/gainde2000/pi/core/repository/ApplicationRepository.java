package sn.gainde2000.pi.core.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Champs findByappId(Long appId);
    Application findByappId(Long appId);

    Application findByAppEmail(String email);

    Application findByAppNom(String appNom);

    //Application findByAppEmail(String email);
    @Query("SELECT COUNT(*) FROM Application p")
    int nombreAplication();

    @Modifying
    @Transactional
    @Query("update Application a set a.appEtapeCreation=:etape  where a.appId=:idApp")
    void updateEtapeCreation(@Param("etape") String etape, @Param("idApp") String idApp);

    @Modifying
    @Transactional
    @Query("update Workflow w set w.wkfAppId=null where w.wkfAppId=:idApp")
    void updateLiaisonWorkflow(@Param("idApp") Application idApp);

    @Modifying
    @Transactional
    @Query("update Formulaire f set f.frmAppId=null where f.frmAppId=:idApp")
    void updateLiaisonFormulaire(@Param("idApp") Long idApp);

    @Modifying
    @Transactional
    @Query("update Rapport r set r.rptAppId=null where r.rptAppId=:idApp")
    void updateLiaisonFichier(@Param("idApp") Application idApp);

    @Modifying
    @Transactional
    @Query("delete from AppFonc a  where a.appliFoncAppId=:idApp")
    void updateLiaisonTableLiaison(@Param("idApp") Long idApp);

    @Query("select p from Application p where p.appStatus=2")
    List<Application> listApplicationPubliee();

    @Query(value = "select * from td_application where app_status=2 order by app_date_pub desc limit 2", nativeQuery = true)
    List<Application> list2LeastRecentApplicationPubliee();

}
