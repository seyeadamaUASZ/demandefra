package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.ValueRapport;
import sn.gainde2000.pi.core.repository.ValueRapportRepository;
import sn.gainde2000.pi.core.service.IValueRapport;
@Service
public class ValueRapportImpl implements IValueRapport {
@Autowired
	
	private ValueRapportRepository valueRapportRepository;

	@Override
	public List<ValueRapport> getListValue() {

		return valueRapportRepository.findAll();
	}

	@Override
	public ValueRapport save(ValueRapport value) {

		return valueRapportRepository.save(value);
	}

	@Override
	public Optional<ValueRapport> findById(Long id) {
		return valueRapportRepository.findById(id);
	}

	@Override
	public void delete(ValueRapport value) {
		valueRapportRepository.delete(value);
		
	}

	@Override
	public List<ValueRapport> listByRapportId(Long id) {
		return valueRapportRepository.listByRapportId(id);
	}

	@Override
	public void supprimerByRapportId(Long idRapport) {
		valueRapportRepository.supprimerByRapportId(idRapport);
		
	}

}
