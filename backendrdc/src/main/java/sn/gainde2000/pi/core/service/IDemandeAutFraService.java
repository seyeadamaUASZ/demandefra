
package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.DemandeAutFra;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

public interface IDemandeAutFraService {

	Optional<DemandeAutFra> findById(Long id);

	List<DemandeAutFra> findAll();

	DemandeAutFra save(DemandeAutFra demandeautfra);

	void delete(DemandeAutFra demandeautfra);

	Iterable<DemandeAutFra> listDemandeAutFra(Long poOwner);

	Iterable<DemandeAutFra> listDemandeAutFraById(Long owner);
	
	Iterable<DemandeAutFra> listDemandeEnBrouillon(Long owner);
	
	Iterable<DemandeAutFra> listDemandeAPayer(Long owner);
	
	Iterable<DemandeAutFra> listDemandeACorriger(Long owner);
	
	Iterable<DemandeAutFra> listDemandeEnCours(Long owner);
	
	Iterable<DemandeAutFra> listAutProDelivre(Long owner);
	
	Iterable<DemandeAutFra> listAutDefDelivre(Long owner);
	
	Iterable<DemandeAutFra> listDemandeRejetees(Long owner);

	DemandeAutFra getOneDemandeAutFra(Long id);
	
	Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefBureau();
	
	Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefDivision();
	
	Iterable<DemandeAutFra> listeDemandeAutFraRejetees();
	
	Iterable<DemandeAutFra> listeDemandeAutFraRenvoyees();
	
	Iterable<DemandeAutFra> listeDemandeAutFraRenvoyeesChefDivision();
	
	Iterable<DemandeAutFra> listeDemandeAutFraDelivrePro();
	
	Iterable<DemandeAutFra> listeDemandeAutFraATraiterANAC();
	
	Iterable<DemandeAutFra> listeDemandeAutFraTraiterANAC();
	
	Iterable<DemandeAutFra> listeDemandeAutFraTerminee();
	
	Iterable<DemandeAutFra> listeMesDemandes(String email); 
	
	Iterable<DemandeAutFra> listeMesDemandesRejetees(String email);
	
	Iterable<DemandeAutFra> listeMesDemandesAcceptees(String email);
		
	Integer totalDemandeATraiter();
		
	Integer totalDemandeSoumise();
	
	Integer totalDemandeTraitees();
	
	List<Object[]> statistiquesChefAntenne(Long owner);
	
	DemandeAutFra updateDemandeAutFra(Long id, DemandeAutFra demandeAutFra);
	
	Integer nombreDemandeRenvoyeesChefBureau();
	
	Integer nombreDemandeATraiterChefBureau();
	
	Integer nombreDemandeATraiterChefDivision();
	
	DemandeAutFra getDemandeAutFraByEmailAndNumero(String email, String numero);
	
	Long generateNumeroFature();
	
	List<Object[]> nbrDemandeAutFraByDate(Long powner);
	
	List<Object[]> nbDemandeTraitees();
	
	List<Object[]> nbrDemandeAutFraByMois(Long owner);
	
	List<Object[]> nbrDemandeAutFraByAnnee();
	
	List<Object[]> statistiqueDemandeur(String email);
	
	List<Object[]> statistiqueLanac();
	
	List<Map<String, Object>> circulaireDemandeur(String email);
	
	List<Map<String, Object>> circulaireChefBureauAndDivision(Long powner);
	
	List<Map<String, Object>> circulaireDivision();
	
	List<Map<String, Object>> circulaireDemandeAntenne(Long owner);
	
	List<Map<String, Object>> circulaireDemandeLaborantion();
	
	DemandeAutFra getDemandeByFactureId(Long numeroFacture);
	
	Integer nombreProduitsAcceptes();
	
	Integer nombreProduitsRejetes();
	
	Integer nombreDemandeATraiterANAC();
	
	Integer nombreDemandeTraiterANAC();
	
	Integer nombreDemandeSoumises(String email);
	
	Integer nombreDemandeEnCours(String email);
	
	boolean mailContact(String email, String nom, String message) throws MessagingException;
}