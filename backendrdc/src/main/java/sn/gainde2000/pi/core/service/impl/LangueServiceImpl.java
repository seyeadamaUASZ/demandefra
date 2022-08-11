package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Langue;
import sn.gainde2000.pi.core.repository.LangueRepository;
import sn.gainde2000.pi.core.service.IlangueService;

import java.util.List;
import java.util.Optional;

@Service
public class LangueServiceImpl implements IlangueService {
    @Autowired
    LangueRepository langueRepository;
    @Override
    public List<Langue> getListLang() {
        return langueRepository.findAll();
    }

    @Override
    public Optional<Langue> findByLangId(Long id) {
        return langueRepository.findById(id);
    }

    @Override
    public Langue save(Langue l) {
        return langueRepository.save(l);
    }

    @Override
    public void delete(Langue l) {  langueRepository.delete(l); }
}
