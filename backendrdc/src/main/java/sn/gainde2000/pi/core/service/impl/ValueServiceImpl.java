package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.ValueChamps;
import sn.gainde2000.pi.core.repository.ValueRepository;
import sn.gainde2000.pi.core.service.IValue;

@Service
public class ValueServiceImpl implements IValue{
	@Autowired
	
	private ValueRepository valueRepository;

	@Override
	public List<ValueChamps> getListValue() {
		// TODO Auto-generated method stub
		return valueRepository.findAll();
	}

	

	@Override
	public ValueChamps save(ValueChamps value) {
		// TODO Auto-generated method stub
		return valueRepository.save(value);
	}

	@Override
	public Optional<ValueChamps> findById(Long id) {
		// TODO Auto-generated method stub
		return valueRepository.findById(id);
	}

	@Override
	public void delete(ValueChamps value) {
		
		valueRepository.delete(value);
	}



	@Override
	public List<ValueChamps> listByChampId(Long id) {
		// TODO Auto-generated method stub
		return valueRepository.listByChampId(id);
	}



	@Override
	public void supprimerByChampId(Long idChamps) {
		valueRepository.supprimerByChampId(idChamps);
		
	}

}
