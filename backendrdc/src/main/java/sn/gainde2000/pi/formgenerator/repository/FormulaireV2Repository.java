package sn.gainde2000.pi.formgenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;

public interface FormulaireV2Repository extends JpaRepository<FormulaireV2, Long> {
   

    List<FormulaireV2> findByfrmAppId(Long id);
    @Query("SELECT f FROM FormulaireV2 f where f.frmAppId = null and f.frmValider = 'Valider'")
    List<FormulaireV2> listformLibreEtValider();
    @Query("SELECT f FROM FormulaireV2 f where f.frmAppId =:id or f.frmAppId=null and f.frmValider = 'Valider'")
    List<FormulaireV2> listFormByAppOuLibre(@Param("id") Long id);
    

    @Modifying
    @Transactional
    @Query(value = "update FormulaireV2 f set f.frmAppId =:idApp where f.frmId=:idFormulaire")
    public void lierFormulaire(@Param("idApp") String idApp, @Param("idFormulaire") String idFormulaire);
   
    @Modifying
    @Transactional
    @Query(value = "update FormulaireV2 f set f.frmAppId =null where f.frmId=:idFormulaire")
    public void enleverLiaisonidFormulaire(@Param("idFormulaire") String idFormulaire);
}
