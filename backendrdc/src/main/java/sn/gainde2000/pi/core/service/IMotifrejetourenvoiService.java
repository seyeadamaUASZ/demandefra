
package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Motifrejetourenvoi;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

public interface IMotifrejetourenvoiService {

	Optional<Motifrejetourenvoi> findById(Long id);

	List<Motifrejetourenvoi> findAll();

	Motifrejetourenvoi save(Motifrejetourenvoi motifrejetourenvoi);

	void delete(Motifrejetourenvoi motifrejetourenvoi);

	Iterable<Motifrejetourenvoi> listMotifrejetourenvoi(Long poOwner);

	Iterable<Motifrejetourenvoi> listMotifrejetourenvoiById(Long owner);

	Motifrejetourenvoi getOneMotifrejetourenvoi(Long id);

	List<DemandeAutFra> listMotifrenvoyeeById(Long owner);

	List<DemandeAutFra> listMotifValidee(Long owner);
	
	List<Motifrejetourenvoi> historiqueCommentaire(Long idLink, Long status);
	
	Motifrejetourenvoi motifRenvoiChefDivision(Long idLink);
}