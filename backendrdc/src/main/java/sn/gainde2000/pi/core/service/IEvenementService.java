/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Evenement;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.Privilege;

/**
 *
 * @author indiaye
 */
public interface IEvenementService {
    public List<Evenement> getListEvenements();
    
    public List<Evenement> getListEvenementParActions(Action action);
    
    public List<Action> getListActionParNotification(Notification notification);
    
    public Evenement save(Evenement ev);

    public Optional<Evenement> findById(Long id);  

    public void delete(Evenement ev);
    
    public void removedEvenement(Notification notification, Action action);
    
    public  Iterable<Evenement> evenementAccord(Long IdNotification);
      
}
