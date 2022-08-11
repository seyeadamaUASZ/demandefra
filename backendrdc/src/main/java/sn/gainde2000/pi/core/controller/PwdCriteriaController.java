/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;
import sn.gainde2000.pi.core.service.impl.PwdCriteriaServiceImpl;

/**
 *
 * @author asow
 */
@RestController
@RequestMapping("pwdcriteria")
public class PwdCriteriaController {

    @Autowired
    IPwdCriteriaService pwdcriteriService;

//ajouter des criteres de mot de passe
    @PostMapping("create")
    public ServeurResponse create(@RequestBody PwdCriteria pwdcriteria) {
        ServeurResponse response = new ServeurResponse();
        pwdcriteriService.save(pwdcriteria);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi!!!");
        response.setData(pwdcriteria);

        return response;
    }
//Afficher les detials du critere de mot de passe.
    @GetMapping("details")
    public ServeurResponse listpwdById() {
        List<PwdCriteria> pwdCriterias = pwdcriteriService.getListPwd();   
        ServeurResponse response = new ServeurResponse();
        if (pwdCriterias == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun element trouvé.");
        } else {
            response.setStatut(true);
            response.setData(pwdCriterias);
            response.setDescription("Donnée recuperée.");

        }

        return response;

    }
    //Mettre en jours les informations du critere de mot de passe.
    @PostMapping("update")
    public ServeurResponse update(@RequestBody PwdCriteria pwdCriteria) {
        ServeurResponse response = new ServeurResponse();
        PwdCriteria findPwdCriteria = pwdcriteriService.save(pwdCriteria);
        if (findPwdCriteria != null) {
            response.setStatut(true);
            response.setDescription("++++Enregistrement rÃ©ussi+++");
            response.setData(findPwdCriteria);
        } else {
            response.setStatut(false);
            response.setDescription("+++Echec d'enregistrement+++");
        }

        return response;
    }

    
}
