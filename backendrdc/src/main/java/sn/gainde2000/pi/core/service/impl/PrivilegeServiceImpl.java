package sn.gainde2000.pi.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.repository.PrivilegeRepository;
import sn.gainde2000.pi.core.service.IprivilegeService;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements IprivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public List<Action> findByProfiles(Long p) {
        return privilegeRepository.findByProfiles(new Profil(p));
    }

    @Override
    public Privilege save(Privilege p) {
        return privilegeRepository.save(p);
    }

    @Override
    public void removedPrivilege(Profil p, Action action) {
        privilegeRepository.removedPrivilege(p, action);
    }

    @Override
    public Iterable<Privilege> Privilegeaccord(Long Idprofile) {
        return privilegeRepository.Privilegeaccord(Idprofile);
    }


    @Override
    public void delete(Privilege p) {
        privilegeRepository.delete(p);
    }


}
