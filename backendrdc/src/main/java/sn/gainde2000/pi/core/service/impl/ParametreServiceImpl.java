package sn.gainde2000.pi.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Parametre;
import sn.gainde2000.pi.core.repository.ParametreRepository;
import sn.gainde2000.pi.core.service.IparametreService;

import java.util.List;
import java.util.Optional;
import static org.apache.poi.hssf.usermodel.HeaderFooter.page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ParametreServiceImpl implements IparametreService {
    @Autowired
    ParametreRepository parametreRepository;
    @Override
    public List<Parametre> getListParam() {
        return parametreRepository.findAll();
    }

    @Override
    public Optional<Parametre> findByParamId(Long id) {
        return parametreRepository.findById(id);
    }

    @Override
    public Page<Parametre> selectParam() {
     Pageable page = (Pageable) PageRequest.of(0, 1);
        return parametreRepository.selectParam(page);
    }

    @Override
    public Parametre save(Parametre p) {
        return parametreRepository.save(p);
    }

    @Override
    public void delete(Parametre p) {
        parametreRepository.delete(p);
    }
}
