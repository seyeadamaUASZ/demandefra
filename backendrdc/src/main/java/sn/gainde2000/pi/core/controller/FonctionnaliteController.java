package sn.gainde2000.pi.core.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Fonctionnalite;
import sn.gainde2000.pi.core.service.IFonctionnaliteService;
import sn.gainde2000.pi.core.service.impl.FonctionnaliteServiceImpl;

@RestController
@CrossOrigin("*")
public class FonctionnaliteController {

    @Autowired
    IFonctionnaliteService fonctionnaliteService;
    //Liste des fonctionnalite


    @GetMapping("fonctionnalites")
    public ServeurResponse getAllFormulaire() {
        Iterable<Fonctionnalite> fonctionnalite = fonctionnaliteService.getAllFormulaire();
        ServeurResponse response = new ServeurResponse();
        if (fonctionnalite == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(fonctionnalite);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    //Ajouter fonctionnalite

    @PostMapping("fonctionnalite")
    public ServeurResponse create(@RequestBody Fonctionnalite fonctionnalite) {

        ServeurResponse response = new ServeurResponse();
        if (fonctionnaliteService.findByFonNom(fonctionnalite.getFonNom()) == null) {
            fonctionnaliteService.save(fonctionnalite);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(fonctionnalite);
        } else {
            response.setStatut(false);
            response.setDescription("cette fonctionnalité existe déja!");
            response.setData(fonctionnalite);

        }

        return response;
    }
    //recuperation par id

    @GetMapping("fonctionnalite/{id}")
    public Fonctionnalite findById(@PathVariable Long id) {
        return fonctionnaliteService.findById(id);
    }
    //Supprimer un fonctionnalite

    @PostMapping("fonctionnalite/delete")
    public ServeurResponse deleteformulaire(@RequestBody Fonctionnalite fonctionnalite) {
        ServeurResponse response = new ServeurResponse();
        fonctionnaliteService.delete(fonctionnalite);
        response.setStatut(true);
        return response;
    }

    @GetMapping("fonctionnaliteModule/{id}")
    public ServeurResponse getFonctionnaliteByMod(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        List<Fonctionnalite> f = fonctionnaliteService.getFonctionnaliteByModId(id);
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    @PostMapping("updatefonctionnalite")
    public ServeurResponse update(@RequestBody Fonctionnalite fonctionnalite) {

        ServeurResponse response = new ServeurResponse();
        Fonctionnalite findfonc = fonctionnaliteService.save(fonctionnalite);
        if (findfonc != null) {
            response.setStatut(true);
            response.setDescription("++++Enregistrement réussi+++");
            response.setData(findfonc);
        } else {
            response.setStatut(false);
            response.setDescription("+++Echec d'enregistrement+++");
        }

        return response;
    }

}
