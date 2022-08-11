/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.service.IActionService;
import sn.gainde2000.pi.core.service.IMenuService;

/**
 *
 * @author yougadieng, sagueye
 */
@RestController
@CrossOrigin("*")
@RequestMapping("menu")
public class MenuController {

    @Autowired
    IMenuService menuService;
    @Autowired
    IActionService actionService;

    @GetMapping
    public ServeurResponse getDep() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(menuService.getListMenu());
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @GetMapping("/listhierarchique")
    public ServeurResponse getMenuRacine() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(menuService.getListMenuRacine());
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @GetMapping("/list")
    public ServeurResponse getListMenu() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(menuService.getListMenu());
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @GetMapping("/profil/{p}")
    public ServeurResponse getMenuProfil(@PathVariable Long p) {
        //System.out.println("---------------------getDep---------------------");
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(menuService.getListMenuProfil(p));
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @PostMapping("/path")
    public ServeurResponse getMenuByMenPath(@RequestBody String menPath) {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(menuService.getMenuByPath(menPath));
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @PostMapping("/create")
    public ServeurResponse createDep(@RequestBody Menu menu) {
        ServeurResponse response = new ServeurResponse();

        Menu m = menuService.findByMenNom(menu.getMenNom());
        if (m != null) {
            response.setStatut(false);
            response.setDescription("Echec ajout: ce menu existe déjà.");
        } else {
            menuService.save(menu);
            response.setStatut(true);
            response.setDescription("menu crée avec succès");
        }
        return response;
    }

    @PostMapping("/createsousmenurapport")
    public ServeurResponse createSousMenuRapport(@RequestBody Menu menu) {
        ServeurResponse response = new ServeurResponse();
        Menu menuParent = menuService.findByMenNom("rapport");
        Menu m = menuService.findByMenNom(menu.getMenNom());
        Action a = new Action();
        if (m != null) {
            response.setStatut(false);
            response.setDescription("Echec ajout: ce menu existe déjà.");
        } else {
            menu.setMenIdParent(menuParent.getMenId());
            menu.setMenIconeColor("secondary");
            menu.setMenPath("/fichier/rapport");
            Menu menuSave = menuService.save(menu);
            a.setActCode("view_" + menuSave.getMenNom());
            a.setActMenId(menuSave);
            a.setActNom("Consulter les fichier de Type: " + menuSave.getMenNom());
            actionService.saveAction(a);
            response.setStatut(true);
            response.setDescription("menu crée avec succès");
        }
        return response;
    }

    @GetMapping("/sousmenurapport")
    public ServeurResponse getSousMenuRapport() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        Menu menuParent = menuService.findByMenNom("rapport");
        response.setData(menuService.getListSousMenuRapport(menuParent.getMenId()));
        System.out.println(menuService.getListSousMenuRapport(menuParent.getMenId()));
        response.setDescription("menu récupéré avec succès");
        return response;
    }

    @PostMapping("/update")
    public ServeurResponse updateDep(@RequestBody Menu menu) {
        ServeurResponse response = new ServeurResponse();

        menuService.save(menu);
        response.setStatut(true);
        response.setDescription("menu mis à jour");
        return response;
    }

    @PostMapping("/delete")
    public ServeurResponse deleteDep(@RequestBody Menu menu) {
        ServeurResponse response = new ServeurResponse();
        menuService.delete(menu);
        response.setStatut(true);
        response.setDescription("menu supprimé avec succès");
        return response;
    }

    @GetMapping("recherche/{search}")
    public ServeurResponse search(@PathVariable("search") String keyword) {
        ServeurResponse response = new ServeurResponse();
        List<Menu> notif = menuService.menu(keyword);
        if (notif != null) {
            response.setData(notif);
            response.setStatut(true);
            response.setDescription("menu trouvé!");
        } else {
            response.setData(notif);
            response.setStatut(false);
            response.setDescription("Aucun menu trouvé!");
        }

        return response;

    }

}
