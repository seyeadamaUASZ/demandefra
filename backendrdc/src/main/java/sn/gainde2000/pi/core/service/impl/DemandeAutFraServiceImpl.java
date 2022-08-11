
package sn.gainde2000.pi.core.service.impl;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.NumeroDemande;
import sn.gainde2000.pi.core.service.IDemandeAutFraService;
import sn.gainde2000.pi.core.tools.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.repository.DemandeAutFraRepository;
import sn.gainde2000.pi.core.repository.NumeroDemandeRepository;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;

@Service
public class DemandeAutFraServiceImpl implements IDemandeAutFraService {

    @Autowired
    private DemandeAutFraRepository demandeautfraRepository;
    @Autowired
    private NumeroDemandeRepository numeroDemandeRepository;
    @Autowired
    private Email email;

    @Override
    public List<DemandeAutFra> findAll() {

        return demandeautfraRepository.findAll();
    }

    @Override
    public DemandeAutFra save(DemandeAutFra demandeautfra) {
    	if (demandeautfra.getNumdemande().equals("")) {
    		NumeroDemande numeroDemande = new NumeroDemande();
    		NumeroDemande numeroDemandeSave = numeroDemandeRepository.save(numeroDemande);
    		demandeautfra.setNumdemande(numeroDemandeSave.getNumeroDemandeId());
    	}
        return demandeautfraRepository.save(demandeautfra);
    }

    @Override
    public void delete(DemandeAutFra demandeautfra) {
        demandeautfraRepository.delete(demandeautfra);
    }

    @Override
    public Optional<DemandeAutFra> findById(Long id) {
        return demandeautfraRepository.findById(id);
    }

    @Override
    public DemandeAutFra getOneDemandeAutFra(Long id) {
        return demandeautfraRepository.getOneDemandeAutFra(id);
    }

    @Override
    public Iterable<DemandeAutFra> listDemandeAutFra(Long poOwner) {
        return demandeautfraRepository.listDemandeAutFra(poOwner);
    }

    @Override
    public Iterable<DemandeAutFra> listDemandeAutFraById(Long owner) {
        return demandeautfraRepository.listDemandeAutFraById(owner);
    }

    @Override
    public Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefBureau() {
        return demandeautfraRepository.listeDemandeAutFraATraiterChefBureau();
    }

    @Override
    public Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefDivision() {
        return demandeautfraRepository.listeDemandeAutFraATraiterChefDivision();
    }

    @Override
    public Iterable<DemandeAutFra> listeDemandeAutFraRejetees() {
        return demandeautfraRepository.listeDemandeAutFraRejetees();
    }
    
    @Override
    public Iterable<DemandeAutFra> listeDemandeAutFraRenvoyees() {
        return demandeautfraRepository.listeDemandeAutFraRenvoyees();
    }

	@Override
	public Iterable<DemandeAutFra> listeDemandeAutFraDelivrePro() {
		return demandeautfraRepository.listeDemandeAutFraPro();
	}

	@Override
	public Iterable<DemandeAutFra> listeDemandeAutFraTerminee() {
		return demandeautfraRepository.listeDemandeAutFraTerminees();
	}

	@Override
	public Integer totalDemandeATraiter() {
		return demandeautfraRepository.totalDemandeATraiter();
	}
	
	@Override
	public Integer totalDemandeSoumise() {
		return demandeautfraRepository.totalDemandeSoumise();
	}

	@Override
	public Integer totalDemandeTraitees() {
		return demandeautfraRepository.totalDemandeTraitees();
	}

	@Override
	public DemandeAutFra updateDemandeAutFra(Long id, DemandeAutFra demandeAutFra) {
		demandeAutFra.setId(id);
		return demandeautfraRepository.saveAndFlush(demandeAutFra);
	}

	@Override
	public Integer nombreDemandeRenvoyeesChefBureau() {
		return demandeautfraRepository.nombreDemandeRenvoyeeChefDeBureau();
	}

	@Override
	public Integer nombreDemandeATraiterChefBureau() {
		return demandeautfraRepository.nombreDemandeATraiterChefDeBureau();
	}

	@Override
	public Integer nombreDemandeATraiterChefDivision() {
		return demandeautfraRepository.nombreDemandeATraiterChefDivision();
	}
	
	@Override
	public List<Object[]> statistiquesChefAntenne(Long owner) {
		return demandeautfraRepository.statistiquesChefAntenne(owner);
	}
		
	@Override
	public DemandeAutFra getDemandeAutFraByEmailAndNumero(String email, String numero) {
		return demandeautfraRepository.getDemandeAutFraByEmailAndNumero(email, numero);
	}

	@Override
	public Long generateNumeroFature() {
		int start = ThreadLocalRandom.current().nextInt(1000, 9999);
	    int end = ThreadLocalRandom.current().nextInt(1000, 9999);
	    DecimalFormat df = new DecimalFormat("0000");
	    String s = df.format(start);
	    String e = df.format(end);
	    return Long.parseLong(s + e);
	}

	@Override
	public List<Object[]> nbrDemandeAutFraByDate(Long powner) {
		return demandeautfraRepository.nbrDemandeAutFraByDate(powner);
	}
	
	@Override
	public List<Object[]> nbrDemandeAutFraByMois(Long owner) {
		return demandeautfraRepository.nbrDemandeAutFraByMois(owner);
	}
	
	@Override
	public List<Object[]> nbDemandeTraitees() {
		return demandeautfraRepository.nbDemandeTraitees();
	}
	
	@Override
	public List<Object[]> nbrDemandeAutFraByAnnee() {
		return demandeautfraRepository.nbrDemandeAutFraByAnnee();
	}
	
	@Override
	public List<Object[]> statistiqueDemandeur(String email) {
		return demandeautfraRepository.statistiqueDemandeur(email);
	}
	
	@Override
	public List<Object[]> statistiqueLanac() {
		return demandeautfraRepository.statistiqueLanac();
	}
	
	@Override
	public List<Map<String, Object>> circulaireDemandeur(String email) {
		return demandeautfraRepository.circulaireDemandeur(email);
	}
	
	@Override
	public List<Map<String, Object>> circulaireChefBureauAndDivision(Long powner) {
		return demandeautfraRepository.circulaireChefBureauAndDivision(powner);
	}
	
	@Override
	public List<Map<String, Object>> circulaireDivision() {
		return demandeautfraRepository.circulaireChefDivision();
	}
	
	@Override
	public List<Map<String, Object>> circulaireDemandeAntenne(Long owner) {
		return demandeautfraRepository.circulaireDemandesAntenne(owner);
	}
	
	@Override
	public List<Map<String, Object>> circulaireDemandeLaborantion() {
		return demandeautfraRepository.circulaireLaborantin();
	}

	@Override
	public Iterable<DemandeAutFra> listeMesDemandes(String email) {
		return demandeautfraRepository.findByEmailResponsable(email);
	}

	@Override
	public Iterable<DemandeAutFra> listeMesDemandesRejetees(String email) {
		return demandeautfraRepository.mesDemandesRejetees(email);
	}

	@Override
	public Iterable<DemandeAutFra> listeMesDemandesAcceptees(String email) {
		return demandeautfraRepository.mesDemandesAcceptees(email);
	}

	@Override
	public DemandeAutFra getDemandeByFactureId(Long numeroFacture) {
		return demandeautfraRepository.findByNumeroFacture(numeroFacture);
	}	

	@Override
	public Integer nombreProduitsAcceptes() {
		return demandeautfraRepository.totalProduitsAcceptes();
	}

	@Override
	public Integer nombreProduitsRejetes() {
		return demandeautfraRepository.totalProduitsRejetes();
	}

	@Override
	public Iterable<DemandeAutFra> listeDemandeAutFraATraiterANAC() {
		return demandeautfraRepository.listeDemandeAutFraATraiterANAC();
	}

	@Override
	public Iterable<DemandeAutFra> listeDemandeAutFraTraiterANAC() {
		return demandeautfraRepository.listeDemandeAutFraTraiterANAC();
	}

	@Override
	public Iterable<DemandeAutFra> listDemandeEnBrouillon(Long owner) {
		return demandeautfraRepository.listeDemandeEnBrouillon(owner);
	}

	@Override
	public Iterable<DemandeAutFra> listDemandeACorriger(Long owner) {
		return demandeautfraRepository.listeDemandeACorriger(owner);
	}

	@Override
	public Iterable<DemandeAutFra> listDemandeEnCours(Long owner) {
		return demandeautfraRepository.listeDemandeEnCours(owner);
	}
	
	@Override
	public Iterable<DemandeAutFra> listDemandeAPayer(Long owner) {
		return demandeautfraRepository.listeDemandeAPayer(owner);
	}
	
	@Override
	public Iterable<DemandeAutFra> listAutProDelivre(Long owner) {
		return demandeautfraRepository.listeAutProvDelivre(owner);
	}
	
	@Override
	public Iterable<DemandeAutFra> listAutDefDelivre(Long owner) {
		return demandeautfraRepository.listeAutDefDelivre(owner);
	}

	@Override
	public Iterable<DemandeAutFra> listDemandeRejetees(Long owner) {
		return demandeautfraRepository.listeDemandeRejetees(owner);
	}

	@Override
	public Integer nombreDemandeATraiterANAC() {
		return demandeautfraRepository.nombreDemandeATraiterANAC();
	}

	@Override
	public Integer nombreDemandeTraiterANAC() {
		return demandeautfraRepository.nombreDemandeTraiterANAC();
	}

	@Override
	public Integer nombreDemandeSoumises(String email) {
		return demandeautfraRepository.nombreDemandeSoumises(email);
	}

	@Override
	public Integer nombreDemandeEnCours(String email) {
		return demandeautfraRepository.nombreDemandeEnCours(email);
	}

	@Override
	public Iterable<DemandeAutFra> listeDemandeAutFraRenvoyeesChefDivision() {
		return demandeautfraRepository.listeDemandeAutFraRenvoyeesChefDivision();
	}
	
	@Override
    public boolean mailContact(String emails, String name, String message) throws MessagingException {
        return email.sendMailContact("no-reply@gainde2000.sn", "dci2022dcsc@gmail.com", "De la part de " + name + ", " + message + ". Veuillez me contacter sur le mail suivant " + email, "Information de contact");
    }
}
