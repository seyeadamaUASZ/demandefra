package sn.gainde2000.pi.core.service;

import java.util.Optional;

import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.entity.RapportAnalyse;

public interface IRapportAnalyseService {
	Optional<RapportAnalyse> findById(Long id);
	RapportAnalyse save(RapportAnalyse rapportAnalyse);
	Iterable<RapportAnalyse> listRapportAnalyse();
	RapportAnalyse getOneRapportAnalyse(Long id);
	RapportAnalyse rapportParProduit(Long idProduit);
}
