package sn.gainde2000.pi.formgenerator.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;

public interface IFormulaireV2Service {
    List<FormulaireV2> getAllFormulaireLibreValider();
    List<FormulaireV2> findByFrmAppId(Long id);
	public FormulaireV2 save(FormulaireV2 formulaireV2);
	public void delete(FormulaireV2 formulaireV2);
	public FormulaireV2 findByFrmId(Long frmId);
	public Iterable<FormulaireV2> findAll();
    List<FormulaireV2> listFormByAppIdOulibre(Long id);
    public void lierFormulaire(String idApp, String idFormulaire);

    public void enleverLiasonFormulaire(String idFormulaire);
}
