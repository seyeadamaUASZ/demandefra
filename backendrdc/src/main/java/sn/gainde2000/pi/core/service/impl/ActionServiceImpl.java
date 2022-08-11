package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.repository.ActionRepository;
import sn.gainde2000.pi.core.service.IActionService;

@Service
public class ActionServiceImpl implements IActionService{
	
	@Autowired
	public ActionRepository actionRepository;

	@Override
	public void saveAction(Action action) {
		actionRepository.save(action);
		
	}

	@Override
	public List<Action> getListAction() {
		return actionRepository.findAll();
	}
        
        @Override
	public List<Action> findActionByMenId(Menu m) {
		return actionRepository.findByactMenId(m);
	}

	@Override
	public Optional<Action> findById(Long id) {
		return actionRepository.findById(id);
	}

	@Override
	public void supprimer(Action action) {
		actionRepository.delete(action);
		
	}

	@Override
	public Action findByCode(String code) {
		return actionRepository.findByActCode(code);
	}

}
