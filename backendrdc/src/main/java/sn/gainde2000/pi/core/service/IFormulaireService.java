package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Formulaire;

public interface IFormulaireService {

    Formulaire findByFrmId(Long frmId);
    Formulaire findByFrmNom(String frmNom);
    List<Formulaire> findByFrmAppId(Long id);

    List<Formulaire> getAllFormulaireLibreValider();

    List<Formulaire> listFormNotGenerate();

    List<Formulaire> listFormGenerate();

    List<Formulaire> getAllFormulaire();

    List<Formulaire> listFormByAppId(Long id);

    List<Formulaire> listFormByAppIdOulibre(Long id);

    Formulaire save(Formulaire formulaire);

    Optional<Formulaire> findById(Long id);
    Formulaire findByfrmId(Long frmId);
    void delete(Formulaire formulaire);

    // List<Formulaire> getAllFormulaireLibre();
    public void lierFormulaire(String idApp, String idFormulaire);

    public void enleverLiasonFormulaire(String idFormulaire);
    // List<Formulaire> getAllFormulaireLibre();
    public int NombreFormulaire();
    


}
