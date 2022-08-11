/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.ActionNotification;
import sn.gainde2000.pi.core.repository.ActionNotificationRepository;
import sn.gainde2000.pi.core.service.IActionNotificationService;

/**
 *
 * @author asow
 */
@Service
public class ActionNotificationImpl implements IActionNotificationService {

    @Autowired
    ActionNotificationRepository actionNotificationRepository;

    @Override
    public List<ActionNotification> getAll() {
        return actionNotificationRepository.findAll();
    }

}
