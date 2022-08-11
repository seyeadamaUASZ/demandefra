package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.WorkflowSecteur;

import java.util.List;

public interface IworkflowSecteur {
    public List<WorkflowSecteur> getListWorkflowSecteur();
    
    public WorkflowSecteur save(WorkflowSecteur workflowSecteur);
    
    public WorkflowSecteur findByWsSecteur(String wsSecteur);
    
    public void delete(WorkflowSecteur workflowSecteur);
}
