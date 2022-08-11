package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.ChampFormWorkflow;
import sn.gainde2000.pi.core.repository.ChampFormWorkflowRepository;
import sn.gainde2000.pi.core.service.IChampFormWorkflowService;

@Service
public class ChampFormWorkflowServiceImpl implements IChampFormWorkflowService{
		@Autowired
	 ChampFormWorkflowRepository champWorkflowRepo;
	@Override
	public List<ChampFormWorkflow> getListChampFormWorkflow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChampFormWorkflow> listChampFormByFrmId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChampFormWorkflow save(ChampFormWorkflow champs) {
		// TODO Auto-generated method stub
		return champWorkflowRepo.save(champs);
	}

	@Override
	public List<ChampFormWorkflow> getListChampsWorkflowByForm(Long chpWFrmId) {
		// TODO Auto-generated method stub
		return champWorkflowRepo.getListChampsWorkflowByForm(chpWFrmId);
	}

}
