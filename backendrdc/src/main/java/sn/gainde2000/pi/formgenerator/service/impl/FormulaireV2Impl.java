package sn.gainde2000.pi.formgenerator.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.formgenerator.entity.Step;
import sn.gainde2000.pi.formgenerator.entity.ValueChampsV2;
import sn.gainde2000.pi.formgenerator.repository.ChampsV2Repository;
import sn.gainde2000.pi.formgenerator.repository.FormulaireV2Repository;
import sn.gainde2000.pi.formgenerator.repository.StepRepository;
import sn.gainde2000.pi.formgenerator.repository.ValueChampsV2Repository;
import sn.gainde2000.pi.formgenerator.service.IFormulaireV2Service;

@Service
public class FormulaireV2Impl implements IFormulaireV2Service {

	@Autowired FormulaireV2Repository formulaireV2Repository;
	@Autowired StepRepository stepRepository;
	@Autowired ChampsV2Repository champsV2Repository;
	@Autowired ValueChampsV2Repository valueChampsV2Repository;

	@Override
	public FormulaireV2 save(FormulaireV2 formulaireV2) {
		return formulaireV2Repository.save(formulaireV2);
	}

	@Transactional
	@Modifying
	@Override
	public void delete(FormulaireV2 formulaireV2) {
		 
		 for(Step step : formulaireV2.getSteps()) {
			 for(ChampsV2 champsV2 : step.getChamps()) {
				 
				 for(ValueChampsV2 valueChampsV2:champsV2.getValues()) {
					 valueChampsV2Repository.delete(valueChampsV2);
				 }
				 champsV2Repository.delete(champsV2);
			 }
			 stepRepository.delete(step);
		 }
		 formulaireV2Repository.delete(formulaireV2);

	}

	@Override
	public FormulaireV2 findByFrmId(Long frmId) {
		Optional<FormulaireV2>  fo =formulaireV2Repository.findById(frmId);
		if(fo.get()==null) {
			return null;
		}else {
			return fo.get();
		}
	}

	@Override
	public Iterable<FormulaireV2> findAll() {
		return formulaireV2Repository.findAll();
	}
	
	 @Override
	    public List<FormulaireV2> findByFrmAppId(Long id) {
	        // TODO Auto-generated method stub
	        return formulaireV2Repository.findByfrmAppId(id);
	    }
	 
	 public List<FormulaireV2> getAllFormulaireLibreValider() {
	        return formulaireV2Repository.listformLibreEtValider();
	    }
	 
	 @Override
	    public List<FormulaireV2> listFormByAppIdOulibre(Long id) {
	        return formulaireV2Repository.listFormByAppOuLibre(id);
	    }
	 
	 
	 @Override
     public void lierFormulaire(String idApp, String idFormulaire) {
          formulaireV2Repository.lierFormulaire(idApp, idFormulaire);
     }

     @Override
     public void enleverLiasonFormulaire(String idFormulaire) {
          formulaireV2Repository.enleverLiaisonidFormulaire(idFormulaire);
     }

}
