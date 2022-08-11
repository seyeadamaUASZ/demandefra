package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Module;
import sn.gainde2000.pi.core.repository.ModuleRepository;
import sn.gainde2000.pi.core.service.ImoduleService;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ImoduleService {
    @Autowired
    ModuleRepository moduleRepository;
    @Override
    public List<Module> getListMod() {
        return moduleRepository.findAll();
    }

    @Override
    public Optional<Module> findByModId(Long id) {
        return moduleRepository.findById(id);
    }

    @Override
    public Module save(Module m) {
        return moduleRepository.save(m);
    }

    @Override
    public void delete(Module m) {
        moduleRepository.delete(m);
    }
	
	@Override
	public List<Module> findBymodAppId(Long id) {
		// TODO Auto-generated method stub
		return moduleRepository.findByModuleAppId(id);
	}
}
