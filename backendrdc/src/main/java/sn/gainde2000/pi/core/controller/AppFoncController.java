/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.AppFonc;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Fonctionnalite;
import sn.gainde2000.pi.core.service.IAppFoncService;

/**
 *
 * @author asow
 */
@RestController
@CrossOrigin("*")
public class AppFoncController {

    @Autowired
    IAppFoncService appFoncService;
    //@Autowired
    //FonctionnaliteServiceImpl fonctionnaliteService;

    @PostMapping("ajoutAppFonc")
    public ServeurResponse creerAppFon(@RequestBody AppFonc appFonc) {
        ServeurResponse response = new ServeurResponse();
        try {
            if (appFoncService.VerifySiDejaActiver(appFonc.getAppliFoncAppId(), appFonc.getAppliFoncFonId()) > 0) {
                //appFonc.setApplifonisActive(true); 
                appFoncService.Activer(appFonc.getAppliFoncAppId(), appFonc.getAppliFoncFonId());
    
                response.setDescription("vérification ok");
            } else {
                appFonc.setAppliFonIsActive(true);
                appFoncService.ajoutAppFonction(appFonc);
                //response.setStatut(true);
                response.setDescription("enregistrement reussi");
                response.setData(appFonc);
            }

        } catch (Exception e) {
            response.setStatut(false);
            response.setDescription("erreur serveur");
            response.setData(appFonc);
        }
        return response;

    }

    @GetMapping("listeAppFonc/{fonModId}/{idApp}")
    public ServeurResponse getAllFormulaire(@PathVariable Long fonModId, @PathVariable Application idApp ) {
        Iterable<AppFonc> appFonc = appFoncService.getAllApp(fonModId, idApp);
        ServeurResponse response = new ServeurResponse();
        if (appFonc == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(appFonc);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    //activee une fonctionnalité

    @GetMapping("ativeAppFonc/{idApp}/{idFon}")
    public ServeurResponse activerAppFon(@PathVariable Application idApp, @PathVariable Fonctionnalite idFon) {
        appFoncService.Activer(idApp, idFon);
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("fonctionnalité activé");
        return response;
    }
    //desactivee une fonctionnalité

    @GetMapping("desactiveAppFonc/{idApp}/{idFon}")
    public ServeurResponse desactiverAppFon(@PathVariable Application idApp, @PathVariable Fonctionnalite idFon) {
        appFoncService.Desactiver(idApp, idFon);
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("fonctionnalité desactivé");
        return response;
    }

    /*@GetMapping("moduleByAppId/{id}")
     public ServeurResponse ModuleByIdApp(@PathVariable Long id) {
          ServeurResponse response = new ServeurResponse();
          response.setStatut(true);
          response.setDescription("Recuperation des modules effectués avec succes");
          response.setData(appFoncService.findByAppId(id));
          return response;
     }*/
     
    
    @GetMapping("testeSiActiveOuNon/{idApp}/{idFon}")
    public ServeurResponse FindAppliFonIsActive(@PathVariable Long idApp, @PathVariable Long idFon) {
        appFoncService.FindAppliFonIsActive(idApp, idFon);
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("IsActive find");
        response.setData( appFoncService.FindAppliFonIsActive(idApp, idFon));
        
        return response;
    }
}
