
package sn.gainde2000.pi.core.service.impl;

import sn.gainde2000.pi.core.entity.Suivredemande;
import sn.gainde2000.pi.core.service.ISuivredemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.repository.SuivredemandeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class SuivredemandeServiceImpl implements ISuivredemandeService {

    @Autowired
    private SuivredemandeRepository suivredemandeRepository;

    @Override
    public List<Suivredemande> findAll() {

        return suivredemandeRepository.findAll();
    }
    @Override
    public Suivredemande save(Suivredemande suivredemande) {

        return suivredemandeRepository.save(suivredemande);
    }
    @Override
    public void delete(Suivredemande suivredemande) {

  suivredemandeRepository.delete(suivredemande);
    }
	@Override
	public Optional<Suivredemande> findById(Long id) {
		return suivredemandeRepository.findById(id);
	}@Override
	public Suivredemande getOneSuivredemande(Long id) {
		return suivredemandeRepository.getOneSuivredemande(id);
	}
@Override
public Iterable<Suivredemande> listSuivredemande(Long poOwner) {
    return suivredemandeRepository.listSuivredemande(poOwner);
}
@Override
public Iterable<Suivredemande> listSuivredemandeById(Long owner) {
    return suivredemandeRepository.listSuivredemandeById(owner);
}}