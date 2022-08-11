/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Inscription;
import sn.gainde2000.pi.core.service.IInscriptionService;
import sn.gainde2000.pi.core.repository.InscriptionRepository;

/**
 *
 * @author asow
 */
@Service
public class InscriptionServiceImpl implements IInscriptionService {
   @Autowired
    InscriptionRepository inscriptionRepository;

    @Override
    public Inscription addUtilisateur(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public void delete(Inscription inscription) {
        inscriptionRepository.delete(inscription);
    }

  


}
