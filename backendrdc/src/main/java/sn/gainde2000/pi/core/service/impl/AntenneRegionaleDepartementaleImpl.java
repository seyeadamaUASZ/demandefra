package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.AntenneRegionaleDepartementale;
import sn.gainde2000.pi.core.repository.AntenneRegionaleDepartementaleRepository;
import sn.gainde2000.pi.core.service.IAntenneRegionaleDepartementaleService;

@Service
public class AntenneRegionaleDepartementaleImpl implements IAntenneRegionaleDepartementaleService {
	@Autowired
	AntenneRegionaleDepartementaleRepository antenneRepository; 
	
	@Override
	public List<AntenneRegionaleDepartementale> findAll() {
		return antenneRepository.findAll();
	}

	@Override
	public AntenneRegionaleDepartementale save(AntenneRegionaleDepartementale antenneRegionaleDepartementale) {
		return antenneRepository.save(antenneRegionaleDepartementale);
	}

	@Override
	public List<AntenneRegionaleDepartementale> listeAntennesParRegion(Long id) {
		return antenneRepository.findAllByRegionId(id);
	}

	@Override
	public void updateAntenneRegionaleDepartementale(Long id,
			AntenneRegionaleDepartementale antenneRegionaleDepartementale) {
		antenneRegionaleDepartementale.setId(id);
		antenneRepository.save(antenneRegionaleDepartementale);
	}

	@Override
	public void supprimerAntenneRegionaleDepartementale(Long id) {
		antenneRepository.deleteById(id);
	}

	@Override
	public AntenneRegionaleDepartementale getOneAntenne(Long id) {
		return antenneRepository.findById(id).get();
	}
}
