package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.TypeNotification;
import sn.gainde2000.pi.core.service.ITypeNotificationService;

/**
*
* @author sagueye
*/

@RestController
@CrossOrigin("*")
@RequestMapping("typenotification")
public class TypeNotificationController {
	@Autowired
	ITypeNotificationService iTypeNotificationService;
	
	@GetMapping
    public ServeurResponse getTypeNotifications() {
        // This returns a JSON or XML with the users
    	
    	
        Iterable<TypeNotification> typeNotifications = iTypeNotificationService.getListTypeNotification();
        ServeurResponse response = new ServeurResponse();
        if (typeNotifications == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(typeNotifications);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
}
