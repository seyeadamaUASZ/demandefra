package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.ValueChamps;



public interface IValue {
	public List<ValueChamps> getListValue();


    public ValueChamps save(ValueChamps value);

    public Optional<ValueChamps> findById(Long id);

    public void delete(ValueChamps value);
    public List<ValueChamps> listByChampId(Long id);
    public void supprimerByChampId(Long idChamps);
    
}
