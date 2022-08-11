package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.JbpmUserMapping;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.repository.JbpmUserRepository;
import sn.gainde2000.pi.core.service.IJbpmUserService;

import java.util.List;
import java.util.Optional;

@Service
public class JbpmUserServiceImpl implements IJbpmUserService {
    @Autowired
    JbpmUserRepository jbpmUserRepository;

    @Override
    public JbpmUserMapping addUserJbpm(JbpmUserMapping jbpmUserMapping) {
      return  this.jbpmUserRepository.save(jbpmUserMapping);

    }
    @Override
    public List<JbpmUserMapping> getListJbpmUser() {
        return jbpmUserRepository.findAll();
    }

    @Override
    public JbpmUserMapping findByJbpmUid(Long JbpmUid) {
        return jbpmUserRepository.findByJbpmUid(JbpmUid);
    }

    @Override
    public JbpmUserMapping findByIndusUser(String indusUser) {
        return jbpmUserRepository.findByIndusUser(indusUser);
    }

    @Override
    public JbpmUserMapping findByJbpmGroupe(String jbpmGroupe) {
        return jbpmUserRepository.findByJbpmGroupe(jbpmGroupe);
    }
}
