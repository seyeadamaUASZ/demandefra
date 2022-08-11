package sn.gainde2000.pi.core.service;

import java.awt.print.Pageable;
import sn.gainde2000.pi.core.entity.Parametre;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IparametreService {
    public List<Parametre> getListParam();
    public Optional<Parametre> findByParamId(Long id);
    public Page<Parametre> selectParam();
    public Parametre save(Parametre p);
    public void delete(Parametre p);
}
