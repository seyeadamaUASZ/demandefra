package sn.gainde2000.pi.formgenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.formgenerator.entity.Step;
import sn.gainde2000.pi.formgenerator.repository.StepRepository;
import sn.gainde2000.pi.formgenerator.service.IStepService;

@Service
public class StepImpl implements IStepService{

	@Autowired StepRepository stepRepository;
	@Override
	public Step save(Step step) {
		return stepRepository.save(step);
	}

	@Override
	public void delete(Step step) {
		stepRepository.delete(step);
	}

}
