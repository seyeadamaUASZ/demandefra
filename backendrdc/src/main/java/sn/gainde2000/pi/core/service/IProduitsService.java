
package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Produits;
import java.util.List;
import java.util.Optional;

public interface IProduitsService {

	Optional<Produits> findById(Long id);

	List<Produits> findAll();

	Produits save(Produits produits);

	void delete(Produits produits);

	List<Produits> saveAll(Iterable<Produits> liste);
	
	List<Produits> ListeProduitsParDemande(Long idDemande);
	
	Produits updateProduits(Long id, Produits produits);
		
	Integer totalDemandeAcceptees();
	
	Integer totalDemandeRejetees();

	Produits getOneProduits(Long id);
	
	Produits validationFra(String numeroFra);
}