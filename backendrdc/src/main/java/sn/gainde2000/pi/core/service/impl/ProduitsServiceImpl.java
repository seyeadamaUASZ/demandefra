
package sn.gainde2000.pi.core.service.impl;

import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.NumeroProduit;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.repository.NumeroProduitRepository;
import sn.gainde2000.pi.core.service.IProduitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.repository.ProduitsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitsServiceImpl implements IProduitsService {

    @Autowired
    private ProduitsRepository produitsRepository;
    @Autowired
    private NumeroProduitRepository numeroProduitRepository;

    @Override
    public List<Produits> findAll() {

        return produitsRepository.findAll();
    }

    @Override
    @Transactional
    public Produits save(Produits produits) {
        NumeroProduit numeroProduit = new NumeroProduit(null);
        NumeroProduit numeroProduitSave = numeroProduitRepository.save(numeroProduit);
        produits.setAutFra(numeroProduitSave.getNumeroId());
        return produitsRepository.save(produits);
    }

    @Override
    public void delete(Produits produits) {

        produitsRepository.delete(produits);
    }

    @Override
    public Optional<Produits> findById(Long id) {
        return produitsRepository.findById(id);
    }

    @Override
    public Produits getOneProduits(Long id) {
        return produitsRepository.getOneProduits(id);
    }

    @Override
    public List<Produits> saveAll(Iterable<Produits> liste) {
        return produitsRepository.saveAll(liste);
    }   
    
    @Override
	public Integer totalDemandeAcceptees() {
		return produitsRepository.totalDemandeAcceptees();
	}

	@Override
	public Integer totalDemandeRejetees() {
		return produitsRepository.totalDemandeRejetees();
	}

	@Override
	public Produits validationFra(String numeroFra) {
		return produitsRepository.validationFra(numeroFra);
	}

	@Override
	public List<Produits> ListeProduitsParDemande(Long idDemande) {
		return produitsRepository.findAllByDemandeautfraId(idDemande);
	}

	@Override
	public Produits updateProduits(Long id, Produits produits) {
		produits.setId(id);
		return produitsRepository.saveAndFlush(produits);
	}
}