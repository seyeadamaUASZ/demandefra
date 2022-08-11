package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.JbpmUserMapping;
import sn.gainde2000.pi.core.entity.Workflow;

import java.util.List;
@Repository

public interface JbpmUserRepository  extends JpaRepository<JbpmUserMapping, Long> {
    JbpmUserMapping findByJbpmUid(Long JbpmUid);
    public JbpmUserMapping findByIndusUser(String indusUser);
    public JbpmUserMapping findByJbpmGroupe(String jbpmGroupe);
  /* @Query("SELECT j FROM Jbpm_user_mapping j")
    List<JbpmUserMapping> listofJbpmuser();*/

}
