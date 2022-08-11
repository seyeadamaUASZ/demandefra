package sn.gainde2000.pi.core.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.entity.RapportAnalyse;
import sn.gainde2000.pi.core.repository.RapportAnalyseRepository;
import sn.gainde2000.pi.core.service.IRapportAnalyseService;

@Service
public class RapportAnalyseServiceImpl implements IRapportAnalyseService {
	@Autowired
	RapportAnalyseRepository rapportRepository;

	@Override
	public RapportAnalyse save(RapportAnalyse rapportAnalyse) {
		return rapportRepository.save(rapportAnalyse);
	}

	@Override
	public Iterable<RapportAnalyse> listRapportAnalyse() {
		return rapportRepository.findAll();
	}

	@Override
	public RapportAnalyse getOneRapportAnalyse(Long id) {
		return rapportRepository.getOne(id);
	}

	@Override
	public Optional<RapportAnalyse> findById(Long id) {
		return rapportRepository.findById(id);
	}

	@Override
	public RapportAnalyse rapportParProduit(Long idProduit) {
		return rapportRepository.findByProduitsId(idProduit);
	}
}
