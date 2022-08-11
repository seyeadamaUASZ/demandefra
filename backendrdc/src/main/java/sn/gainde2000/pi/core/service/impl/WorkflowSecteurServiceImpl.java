package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.WorkflowSecteur;
import sn.gainde2000.pi.core.repository.WorkflowSecteurRepository;
import sn.gainde2000.pi.core.service.IworkflowSecteur;

import java.util.List;

@Service
public class WorkflowSecteurServiceImpl implements IworkflowSecteur {
    @Autowired
    WorkflowSecteurRepository workflowSecteurRepository;

    @Override
    public List<WorkflowSecteur> getListWorkflowSecteur() {
        return workflowSecteurRepository.findAll();
    }

    @Override
    public WorkflowSecteur save(WorkflowSecteur workflowSecteur) {
        return workflowSecteurRepository.save(workflowSecteur);
    }

    @Override
    public WorkflowSecteur findByWsSecteur(String wsSecteur) {
        return workflowSecteurRepository.findByWsSecteur(wsSecteur);
    }


    @Override
    public void delete(WorkflowSecteur workflowSecteur) {
        workflowSecteurRepository.delete(workflowSecteur);
    }

}

