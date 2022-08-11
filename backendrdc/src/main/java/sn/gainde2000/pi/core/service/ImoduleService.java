package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Module;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

public interface ImoduleService {
    public List<Module> getListMod();
    public Optional<Module> findByModId(Long id);
    public Module save(Module m);
    public void delete(Module m);    
    List<Module> findBymodAppId(Long id);
}
