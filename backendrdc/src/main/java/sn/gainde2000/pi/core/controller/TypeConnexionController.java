/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Transition;
import sn.gainde2000.pi.core.entity.TypeConnexion;
import sn.gainde2000.pi.core.service.impl.TypeConnexionImpl;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("typeConnexion")
public class TypeConnexionController {

    @Autowired
    TypeConnexionImpl typeConnexionService;

    /* Récupère l'ensemble des types de connexion existants*/
    @GetMapping("list")
    public ServeurResponse findAll() {
        ServeurResponse response = new ServeurResponse();
        List<TypeConnexion> typ = typeConnexionService.getAll();
        response.setData(typ);
        response.setStatut(true);
        return response;
    }

    /*Permet d'activer ou de désactiver un type de connexion*/
    @PostMapping("update")
    public ServeurResponse createTransition(@RequestBody TypeConnexion typeConnexion) {
        ServeurResponse response = new ServeurResponse();

        TypeConnexion type = typeConnexionService.findById(typeConnexion.getTypId());
        if (type.getTypIsActive()) {
            typeConnexion.setTypIsActive(false);
            response.setDescription("desactiver");

        } else {
            typeConnexion.setTypIsActive(true);
            response.setDescription("activer");

        }
        typeConnexionService.update(typeConnexion);
        response.setStatut(true);
        return response;
    }

}
