package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;


import sn.gainde2000.pi.core.entity.ValueRapport;

public interface IValueRapport {
	public List<ValueRapport> getListValue();


    public ValueRapport save(ValueRapport value);

    public Optional<ValueRapport> findById(Long id);

    public void delete(ValueRapport value);
    public List<ValueRapport> listByRapportId(Long id);
    public void supprimerByRapportId(Long idRapport);
}
