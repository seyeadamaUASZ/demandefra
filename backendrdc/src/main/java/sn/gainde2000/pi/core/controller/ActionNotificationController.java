/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.ActionNotification;
import sn.gainde2000.pi.core.service.IActionNotificationService;

/**
 *
 * @author asow
 */
@RestController
public class ActionNotificationController {

    @Autowired
    IActionNotificationService actionNotificationService;

    @GetMapping("actionNotification/list")
    public ServeurResponse getActionNotifications() {

        Iterable<ActionNotification> actionNotifications = actionNotificationService.getAll();
        ServeurResponse response = new ServeurResponse();
        if (actionNotifications == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(actionNotifications);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

}
