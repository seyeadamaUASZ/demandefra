package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.entity.Profil;
import java.util.List;


public interface IprivilegeService {
    public List<Action> findByProfiles(Long p);
    public Privilege save(Privilege p);
    public void removedPrivilege(Profil p, Action action);
    public  Iterable<Privilege> Privilegeaccord(Long Idprofile);
    public void delete(Privilege p);
}
