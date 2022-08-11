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
import sn.gainde2000.pi.core.entity.TypeDeNotification;
import sn.gainde2000.pi.core.repository.TypeDeNotificationRepository;
import sn.gainde2000.pi.core.service.ITypeDeNotificationService;

/**
 *
 * @author asow
 */
@Service
public class TypeDeNotificationServiceImpl implements ITypeDeNotificationService {

    @Autowired
    TypeDeNotificationRepository typeRepository;

    @Override
    public TypeDeNotification saveNotification(TypeDeNotification type) {
        //To change body of generated methods, choose Tools | Templates.
        return typeRepository.save(type);
    }

    @Override
    public List<TypeDeNotification> getListNotification() {
        //To change body of generated methods, choose Tools | Templates.
        return typeRepository.findAll();
    }

    @Override
    public void supprime(TypeDeNotification type) {
        //To change body of generated methods, choose Tools | Templates.
        typeRepository.delete(type);
    }

    @Override
    public TypeDeNotification findByTdnContenu() {
        //To change body of generated methods, choose Tools | Templates.
        return typeRepository.findByTdnContenu();
    }

    @Override
    public List<TypeDeNotification> seach(String Keyword) {
            return typeRepository.seach(Keyword);
    }

}
