package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.entity.Module;
import sn.gainde2000.pi.core.entity.Utilisateur;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long> {

    Formulaire findByfrmId(Long frmId);
    Formulaire findByfrmNom(String frmNom);

    List<Formulaire> findByfrmAppId(Long id);

    @Query("SELECT f FROM Formulaire f where f.frmValider= 'Modeliser'")
    List<Formulaire> listFormNotGenerate();

    @Query("SELECT f FROM Formulaire f where f.frmValider = 'Valider'")
    List<Formulaire> listFormGenerate();

    @Query("SELECT f FROM Formulaire f where f.frmAppId = null and f.frmValider = 'Valider'")
    List<Formulaire> listformLibreEtValider();

    @Query("SELECT f FROM Formulaire f where f.frmAppId =:id")
    List<Formulaire> listFormByApp(@Param("id") Long id);

    @Query("SELECT f FROM Formulaire f where f.frmAppId =:id or f.frmAppId=null and f.frmValider = 'Valider'")
    List<Formulaire> listFormByAppOuLibre(@Param("id") Long id);
    @Query("SELECT count(*) FROM Formulaire f where f.frmValider = 'Valider'")
    public int  nombreFormulaireGenerate();

    @Modifying
    @Transactional
    @Query(value = "update tp_formulaire set frm_app_id =:idApp where frm_id=:idFormulaire", nativeQuery = true)
    public void lierFormulaire(@Param("idApp") String idApp, @Param("idFormulaire") String idFormulaire);

    @Modifying
    @Transactional
    @Query(value = "update tp_formulaire set frm_app_id =null where frm_id=:idFormulaire", nativeQuery = true)
    public void enleverLiaisonidFormulaire(@Param("idFormulaire") String idFormulaire);

}
