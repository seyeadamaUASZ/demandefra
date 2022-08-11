package sn.gainde2000.pi.core.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.ChampFormWorkflow;



public interface IChampFormWorkflowService {
	 public List<ChampFormWorkflow> getListChampFormWorkflow();

	    public List<ChampFormWorkflow> listChampFormByFrmId(Long id);

	    public ChampFormWorkflow save(ChampFormWorkflow champs);
	    public List<ChampFormWorkflow> getListChampsWorkflowByForm(Long chpWFrmId);

}
