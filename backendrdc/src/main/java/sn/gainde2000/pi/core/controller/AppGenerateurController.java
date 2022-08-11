package sn.gainde2000.pi.core.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.repository.ApplicationRepository;
import sn.gainde2000.pi.core.repository.ChampsRepository;
import sn.gainde2000.pi.core.repository.FormulaireRepository;
import sn.gainde2000.pi.core.service.Crud;
import sn.gainde2000.pi.core.service.IApplicationService;
import sn.gainde2000.pi.core.service.IChampsService;
import sn.gainde2000.pi.core.service.IFormulaireService;

@RestController
@CrossOrigin("*")
@RequestMapping("generate")
public class AppGenerateurController {

    @Autowired
    Environment environment;
    @Autowired
    IFormulaireService formulaireService;
    @Autowired
    IChampsService champsRepository;
    IApplicationService applicationRepository;
    
    //Liste des Champs


    @RequestMapping(value = "/recupforms/{id}", method = RequestMethod.GET)

    public Formulaire getAllChamps(@PathVariable Long id) throws IOException {
    	Crud crud = new Crud();
        ServeurResponse response = new ServeurResponse();
        Formulaire form = formulaireService.findByfrmId(id);
        List<Champs> listChamps = this.champsRepository.listFieldByFrmId(id);
        List<Champs> listChampsfile = this.champsRepository.listByFrmIdFile(id);
        if (form == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {
        	 form.setFrmStatus(true);
        	 formulaireService.save(form);
            String nomform = form.getFrmNom();
             // crud.generationFileModelBack(nomform, id);
              crud.generationFileControllerBack(nomform, listChamps,listChampsfile);
              crud.generationFileServiceBack(nomform);
              crud.generationFileRepositoryBack(nomform);
            response.setStatut(true);
            response.setData(form);
            response.setDescription("Données récupérées.");
        }
        return form;
    }
}
