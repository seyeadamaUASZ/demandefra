/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Evenement;
import sn.gainde2000.pi.core.entity.Notification;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.repository.EvenementRepository;
import sn.gainde2000.pi.core.service.IEvenementService;

/**
 *
 * @author indiaye
 */
@Service
public class EvenementServiceImpl implements IEvenementService{

    @Autowired
    EvenementRepository evRepository;
     
    @Override
    public List<Evenement> getListEvenements() {
        return evRepository.findAll();
    }

    @Override
    public Evenement save(Evenement ev) {
        return evRepository.save(ev);
    }

    @Override
    public Optional<Evenement> findById(Long id) {
        return evRepository.findById(id);
    }   

    @Override
    public void delete(Evenement ev) {
        evRepository.delete(ev);
    }

	@Override
	public List<Action> getListActionParNotification(Notification notification) {
		return evRepository.findByNotifications(notification);
	}

	@Override
	public void removedEvenement(Notification notification, Action action) {
		evRepository.removedEvenement(notification, action);
	}

	@Override
	public Iterable<Evenement> evenementAccord(Long IdNotification) {
		return evRepository.evenementAccord(IdNotification);
	}

	@Override
	public List<Evenement> getListEvenementParActions(Action action) {
		return evRepository.findByActions(action);
	}
    
}
