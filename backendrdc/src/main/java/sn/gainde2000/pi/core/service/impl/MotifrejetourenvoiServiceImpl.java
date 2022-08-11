
package sn.gainde2000.pi.core.service.impl;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Motifrejetourenvoi;
import sn.gainde2000.pi.core.service.IMotifrejetourenvoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.repository.MotifrejetourenvoiRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MotifrejetourenvoiServiceImpl implements IMotifrejetourenvoiService {

	@Autowired
	private MotifrejetourenvoiRepository motifrejetourenvoiRepository;

	@Override
	public List<Motifrejetourenvoi> findAll() {

		return motifrejetourenvoiRepository.findAll();
	}

	@Override
	public Motifrejetourenvoi save(Motifrejetourenvoi motifrejetourenvoi) {

		return motifrejetourenvoiRepository.save(motifrejetourenvoi);
	}

	@Override
	public void delete(Motifrejetourenvoi motifrejetourenvoi) {

		motifrejetourenvoiRepository.delete(motifrejetourenvoi);
	}

	@Override
	public Motifrejetourenvoi getOneMotifrejetourenvoi(Long id) {
		return motifrejetourenvoiRepository.getOneMotifrejetourenvoi(id);
	}
	
	@Override
	public Iterable<Motifrejetourenvoi> listMotifrejetourenvoi(Long poOwner) {
		return motifrejetourenvoiRepository.listMotifrejetourenvoi(poOwner);
	}

	@Override
	public Iterable<Motifrejetourenvoi> listMotifrejetourenvoiById(Long owner) {
		return motifrejetourenvoiRepository.listMotifrejetourenvoiById(owner);
	}
	
	@Override
	public List<DemandeAutFra> listMotifrenvoyeeById(Long owner) {
		return motifrejetourenvoiRepository.listDemandeRenvoyeeById(owner);
	}
	
	@Override
	public List<DemandeAutFra> listMotifValidee(Long owner) {
		return motifrejetourenvoiRepository.listDemandeValideeById(owner);
	}

	@Override
	public Optional<Motifrejetourenvoi> findById(Long id) {
		return motifrejetourenvoiRepository.findById(id);
	}

	@Override
	public List<Motifrejetourenvoi> historiqueCommentaire(Long idLink, Long status) {
		return motifrejetourenvoiRepository.historiqueCommentaire(idLink, status);
	}
	
	@Override
	public Motifrejetourenvoi motifRenvoiChefDivision(Long idLink) {
		return motifrejetourenvoiRepository.motifRenvoiChefDivision(idLink);
	}
}


