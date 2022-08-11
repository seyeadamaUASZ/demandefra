/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.NotificationPush;
import sn.gainde2000.pi.core.repository.NotificationPushRepository;
import sn.gainde2000.pi.core.service.INotificationPushService;

/**
 *
 * @author yougadieng
 */
@Service
public class NotificationPushImpl implements INotificationPushService {

    @Autowired
    public NotificationPushRepository notificationRepo;

    @Override
    public void save(NotificationPush notificationPush) {
        notificationRepo.save(notificationPush);
    }

    @Override
    public void delete(NotificationPush notificationPush) {
        notificationRepo.delete(notificationPush);
    }

    @Override
    public List<NotificationPush> getAll() {
        return notificationRepo.findAll();
    }

}
