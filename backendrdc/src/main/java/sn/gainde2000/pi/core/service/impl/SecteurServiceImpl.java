/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Secteur;
import sn.gainde2000.pi.core.repository.SecteurRepository;
import sn.gainde2000.pi.core.service.ISecteurService;

/**
 *
 * @author yougadieng
 */
@Service
public class SecteurServiceImpl implements ISecteurService {

    @Autowired
    SecteurRepository secteurRepo;

    @Override
    public Secteur create(Secteur secteur) {
        return secteurRepo.save(secteur);
    }

    @Override
    public List<Secteur> getAllSecteur() {
        return secteurRepo.findAll();
    }

     @Override
     public void deleteSecteur(Secteur secteur) {
          secteurRepo.delete(secteur);
     }

     @Override
     public Secteur getSecteurById(Long id) {
          return secteurRepo.findBySecId(id);
     }

    

}
