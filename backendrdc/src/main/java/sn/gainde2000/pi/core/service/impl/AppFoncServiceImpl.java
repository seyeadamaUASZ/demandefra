/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.AppFonc;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Fonctionnalite;
import sn.gainde2000.pi.core.repository.AppFoncRepository;
import sn.gainde2000.pi.core.service.IAppFoncService;

/**
 *
 * @author asow
 */
@Service
public class AppFoncServiceImpl implements IAppFoncService {

     @Autowired

     AppFoncRepository appFoncRepository;

     @Override
     public void ajoutAppFonction(AppFonc appFonc) {
          appFoncRepository.save(appFonc);
     }

     @Override
     public AppFonc findById(Long id) {
          return appFoncRepository.findByIdAppliFonc(id);
     }

    @Override
    public int VerifySiDejaActiver(Application idApp, Fonctionnalite idFonc) {
              return  appFoncRepository.findByIdAppliAndIdFonc(idApp,idFonc);

    }

    @Override
    public void Activer(Application idApp, Fonctionnalite idFonc) {
        appFoncRepository.ActiverFonctionnalite(idApp, idFonc);
    }

    @Override
    public void Desactiver(Application idApp, Fonctionnalite idFonc) {
        appFoncRepository.DesactiverFonctionnalite(idApp, idFonc);
        
    }



    @Override
    public AppFonc FindAppliFonIsActive(Long idApp, Long idFonc) {
        
        return appFoncRepository.FindAppliFonIsActive(idApp, idFonc);
        
    }

    @Override
    public List<AppFonc> getAllApp(Long fonModId, Application idApp) {
        return appFoncRepository.findByfonModId(fonModId, idApp);
    }

    
     
     
}
