/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.TypeDeNotification;
import sn.gainde2000.pi.core.service.ITypeDeNotificationService;

/**
 *
 * @author asow
 */
@RestController
public class TypeDeNotificationController {

    @Autowired
    ITypeDeNotificationService typeDeNotification;

    @PostMapping("typeDeNotification/create")
    public ServeurResponse createTypeNotification(@RequestBody TypeDeNotification notif) {
        ServeurResponse response = new ServeurResponse();
        typeDeNotification.saveNotification(notif);
        response.setStatut(true);
        response.setDescription("enregistrement reussi");
        response.setData(notif);

        return response;
    }

    @GetMapping("typeDeNotification/list")
    public ServeurResponse getAllNotification() {

        Iterable<TypeDeNotification> not = typeDeNotification.getListNotification();
        ServeurResponse response = new ServeurResponse();
        if (not == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(not);
            response.setDescription("Données récupérées.");
        }
        return response;

    }

    @PostMapping("typeDeNotification/delete")
    public ServeurResponse deleteNotification(@RequestBody TypeDeNotification notif) {
        ServeurResponse response = new ServeurResponse();

        typeDeNotification.supprime(notif);
        response.setStatut(true);
        response.setDescription("suppression reussi!!!");
        return response;
    }

    @PostMapping("typeDeNotification/update")
    public ServeurResponse updateNotification(@RequestBody TypeDeNotification notification) {
        ServeurResponse response = new ServeurResponse();
        typeDeNotification.saveNotification(notification);
        response.setStatut(true);
        response.setData(notification);
        response.setDescription("Enregistrement reussi");
        return response;
    }

    @GetMapping("typeDeNotification/{search}")
    public ServeurResponse search(@PathVariable("search") String keyword) {
        ServeurResponse response = new ServeurResponse();
        List<TypeDeNotification> notif = typeDeNotification.seach(keyword);
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
