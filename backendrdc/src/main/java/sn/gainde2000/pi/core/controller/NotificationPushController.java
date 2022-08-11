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
import sn.gainde2000.pi.core.entity.NotificationPush;
import sn.gainde2000.pi.core.entity.Transition;
import sn.gainde2000.pi.core.service.impl.NotificationPushImpl;
import sn.gainde2000.pi.core.service.impl.TaskServiceImpl;
import sn.gainde2000.pi.core.service.impl.TransitionServiceImpl;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("notificationPush")
public class NotificationPushController {

    @Autowired
    NotificationPushImpl notificationPushService;
    @PostMapping("create")
    public ServeurResponse createNotification(@RequestBody NotificationPush notificationPush) {
        ServeurResponse response = new ServeurResponse();
        notificationPushService.save(notificationPush);
        return response;
    }

    @PostMapping("delete")
    public ServeurResponse deleteNotification(@RequestBody NotificationPush notificationPush) {
        ServeurResponse response = new ServeurResponse();
        notificationPushService.delete(notificationPush);
        return response;
    }
     @GetMapping("list")
    public ServeurResponse getAllNotification() {
        ServeurResponse response = new ServeurResponse();
        List<NotificationPush> notif= notificationPushService.getAll();
        response.setData(notif);
        response.setStatut(true);
        response.setDescription("okk");
        return response;
    }
}
