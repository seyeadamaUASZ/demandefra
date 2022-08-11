package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.RapportAnalyse;

public interface RapportAnalyseRepository extends JpaRepository<RapportAnalyse, Long> {
	RapportAnalyse findByProduitsId(@Param("idProduit") Long idProduit);
}
