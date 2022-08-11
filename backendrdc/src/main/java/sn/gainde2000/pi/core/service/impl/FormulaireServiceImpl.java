package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.repository.FormulaireRepository;
import sn.gainde2000.pi.core.service.IFormulaireService;

@Service
public class FormulaireServiceImpl implements IFormulaireService {

    @Autowired
    FormulaireRepository formuliareRepository;

    @Override
    public Formulaire findByFrmId(Long frmId) {

        return formuliareRepository.findByfrmId(frmId);
    }

    @Override
    public List<Formulaire> findByFrmAppId(Long id) {
        // TODO Auto-generated method stub
        return formuliareRepository.findByfrmAppId(id);
    }

    @Override
    public List<Formulaire> listFormNotGenerate() {
        // TODO Auto-generated method stub
        return formuliareRepository.listFormNotGenerate();
    }

    @Override
    public List<Formulaire> listFormGenerate() {
        // TODO Auto-generated method stub
        return formuliareRepository.listFormGenerate();
    }

    @Override
    public List<Formulaire> getAllFormulaire() {
        // TODO Auto-generated method stub
        return formuliareRepository.findAll();
    }

    @Override
    public Formulaire save(Formulaire formulaire) {
        // TODO Auto-generated method stub
        return formuliareRepository.save(formulaire);
    }

    @Override
    public Optional<Formulaire> findById(Long id) {
        // TODO Auto-generated method stub
        return formuliareRepository.findById(id);
    }

    @Override
    public void delete(Formulaire formulaire) {
        formuliareRepository.delete(formulaire);

    }

    public List<Formulaire> getAllFormulaireLibreValider() {
        return formuliareRepository.listformLibreEtValider();
    }

     @Override
     public void lierFormulaire(String idApp, String idFormulaire) {
          formuliareRepository.lierFormulaire(idApp, idFormulaire);
     }

     @Override
     public void enleverLiasonFormulaire(String idFormulaire) {
          formuliareRepository.enleverLiaisonidFormulaire(idFormulaire);
     }

     @Override
     public List<Formulaire> listFormByAppId(Long id) {
          return formuliareRepository.listFormByApp(id);
     }

    @Override
    public List<Formulaire> listFormByAppIdOulibre(Long id) {
        return formuliareRepository.listFormByAppOuLibre(id);
    }

	@Override
	public Formulaire findByFrmNom(String frmNom) {
		// TODO Auto-generated method stub
		return formuliareRepository.findByfrmNom(frmNom) ;
	}

     @Override
     public int NombreFormulaire() {
          return formuliareRepository.nombreFormulaireGenerate();
     }

	@Override
	public Formulaire findByfrmId(Long frmId) {
		// TODO Auto-generated method stub
		return formuliareRepository.findByfrmId(frmId);
	}

}
