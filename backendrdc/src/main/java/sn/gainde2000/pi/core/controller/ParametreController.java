/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Parametre;
import sn.gainde2000.pi.core.repository.ImageRepository;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.service.IparametreService;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("parametre")
public class ParametreController {

    @Autowired
    IparametreService iparametreService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    IUtilisateur useRepo;

    /*Mets à jour les paramètres de l'application lors de la création*/
    @PostMapping("update")
    public ServeurResponse updateParametre(@RequestBody Parametre parametre) {
        ServeurResponse response = new ServeurResponse();
        try {
            iparametreService.save(parametre);
            response.setStatut(true);
            response.setDescription("Paramètres changés avec succès");
        } catch (Exception ex) {
        }
        return response;

    }
     /*Mets à jour les paramètres de l'application lors de la création*/
    @PostMapping("updatee")
    public ServeurResponse updateParam(@RequestBody Parametre parametre) {
        ServeurResponse response = new ServeurResponse();
        Parametre p=new Parametre();
        //p.setParamId(Integer.MAX_VALUE);
        try {
            iparametreService.save(parametre);
            response.setStatut(true);
            response.setDescription("Paramètres changés avec succès");
        } catch (Exception ex) {
        }
        return response;

    }

    /* Récupére les paramètres de l'administrateur ou de l'intégrateur*/
    @GetMapping
    public ServeurResponse getParametreUser() {
        ServeurResponse response = new ServeurResponse();
        Page<Parametre> param = iparametreService.selectParam();

        if (param != null) {
            response.setStatut(true);
            response.setDescription("Liste des paramètres récupéré avec succès");
            response.setData(param.get());
        } else {
            response.setStatut(true);
            response.setDescription("Echec récupération");
            response.setData(null);

        }
        return response;
    }

}
