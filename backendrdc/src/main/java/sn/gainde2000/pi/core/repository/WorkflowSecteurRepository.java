package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.entity.WorkflowSecteur;

@Repository
public interface WorkflowSecteurRepository extends JpaRepository<WorkflowSecteur, String> {

    public WorkflowSecteur findByWsSecteur(String wsSecteur);
    @Query("select w from WorkflowSecteur w ")
    public List<WorkflowSecteur> getListWorkflowSecteur();

}
