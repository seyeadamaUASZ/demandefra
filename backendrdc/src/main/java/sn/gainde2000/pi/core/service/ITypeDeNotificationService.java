/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.ActionNotification;
import sn.gainde2000.pi.core.entity.TypeDeNotification;

/**
 *
 * @author asow
 */
public interface ITypeDeNotificationService {

    public TypeDeNotification saveNotification(TypeDeNotification type);

    public List<TypeDeNotification> getListNotification();

    public void supprime(TypeDeNotification type);

    public TypeDeNotification findByTdnContenu();
    
    // public TypeDeNotification findByTdnAnId(ActionNotification ac);
    
      public List<TypeDeNotification> seach(String Keyword);
}
