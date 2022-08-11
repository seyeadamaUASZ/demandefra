package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.JbpmUserMapping;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.entity.Workflow;

import java.util.List;
import java.util.Optional;

public interface IJbpmUserService {
    public JbpmUserMapping addUserJbpm(JbpmUserMapping jbpmUserMapping);
    public List<JbpmUserMapping> getListJbpmUser();

    public JbpmUserMapping findByJbpmUid(Long ibpmUid);
    public JbpmUserMapping findByIndusUser(String indusUser);
    public JbpmUserMapping findByJbpmGroupe(String jbpmGroupe);

}
