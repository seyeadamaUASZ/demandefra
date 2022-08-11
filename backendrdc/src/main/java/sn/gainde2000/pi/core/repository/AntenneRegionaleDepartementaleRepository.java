package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sn.gainde2000.pi.core.entity.AntenneRegionaleDepartementale;

public interface AntenneRegionaleDepartementaleRepository extends JpaRepository<AntenneRegionaleDepartementale, Long> {
	//@Query("select a from AntenneRegionaleDepartementale where a.region.id =:id")
	List<AntenneRegionaleDepartementale> findAllByRegionId(Long id);
}
