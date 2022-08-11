/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Fonctionnalite;
import sn.gainde2000.pi.core.repository.FonctionnaliteRepository;
import sn.gainde2000.pi.core.service.IFonctionnaliteService;

/**
 *
 * @author yougadieng
 */
@Service
public class FonctionnaliteServiceImpl implements IFonctionnaliteService {

     @Autowired
     FonctionnaliteRepository fonctionnaliteRepository;

     @Override
     public List<Fonctionnalite> getAllFormulaire() {
          return fonctionnaliteRepository.findAll();
     }


    @Override
    public Fonctionnalite save(Fonctionnalite f) {
        return fonctionnaliteRepository.save(f);
    }




   /* @Override
    public List<Fonctionnalite> listByModule(Long id) {
       return fonctionnaliteRepository.listByModule(id);
    }*/


     @Override
     public Fonctionnalite findById(Long id) {
          return fonctionnaliteRepository.findByFonId(id);
     }

     @Override
     public void delete(Fonctionnalite f) {
          fonctionnaliteRepository.delete(f);

     }


    public Fonctionnalite update(Fonctionnalite fonctionnalite) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Fonctionnalite> getFonctionnaliteByModId(Long id) {
        return fonctionnaliteRepository.findByfonModId(id);
    }

    @Override
    public List<Fonctionnalite> findByfonModId(Long fonModId) {
        return fonctionnaliteRepository.findByfonModId(fonModId);
    }

    @Override
    public Fonctionnalite findByFonNom(String nom) {
       return fonctionnaliteRepository.findByFonNom(nom);
    }

}
