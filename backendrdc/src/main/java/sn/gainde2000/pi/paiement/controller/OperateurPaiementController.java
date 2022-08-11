/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.controller;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.paiement.entity.OperateurPaiement;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.impl.OperateurPaiementServiceImpl;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("operateurPaiement")
public class OperateurPaiementController {

    @Autowired
    OperateurPaiementServiceImpl operateurPaiementRepo;
    //Récupère les opérateurs de paiement
    @GetMapping("list")
    public ServeurResponse getAll() {
        ServeurResponse response = new ServeurResponse();
        List<OperateurPaiement> op = operateurPaiementRepo.getAll();
        op = operateurPaiementRepo.getAll();
        if (op == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {
            response.setStatut(true);
            response.setData(op);
            response.setDescription("Operateur récupérés.");
        }
        return response;
    }
    //créer un opérateur de paiement
    @PostMapping("create")
    public ServeurResponse create(@RequestBody OperateurPaiement operateur) {
        ServeurResponse response = new ServeurResponse();
        operateurPaiementRepo.CreateOperateurPaiement(operateur);
        response.setStatut(true);
        response.setDescription("Operateur crées");
        return response;
    }
    //Activer et rendre visible un opérateur de paiement
    @GetMapping("activer/{idOpa}")
    public ServeurResponse activerOperateur(@PathVariable Long idOpa) {
        ServeurResponse response = new ServeurResponse();
        operateurPaiementRepo.activerOperateurPaiement(idOpa);
        response.setStatut(true);
        response.setDescription("Operateur activer");
        return response;
    }
    //Désactiver un opérateur de paiement
    @GetMapping("desactiver/{idOpa}")
    public ServeurResponse desactiverOperateur(@PathVariable Long idOpa) {
        ServeurResponse response = new ServeurResponse();
        operateurPaiementRepo.desactiverOperateurPaiement(idOpa);
        response.setStatut(true);
        response.setDescription("Operateur desactiver");
        return response;
    }

  

}
