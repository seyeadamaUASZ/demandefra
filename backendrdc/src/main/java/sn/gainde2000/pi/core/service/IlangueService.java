package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Langue;

import java.util.List;
import java.util.Optional;

public interface IlangueService {
    public List<Langue> getListLang();
    public Optional<Langue> findByLangId(Long id);
    public Langue save(Langue l);
    public void delete(Langue l);
}
