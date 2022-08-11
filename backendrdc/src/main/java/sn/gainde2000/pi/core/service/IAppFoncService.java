/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.AppFonc;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Fonctionnalite;

/**
 *
 * @author asow
 */
public interface IAppFoncService {

    public void ajoutAppFonction(AppFonc appFonc);

    public List<AppFonc> getAllApp(Long fonModId, Application idApp);

    public AppFonc findById(Long id);

    public void Activer(Application idApp, Fonctionnalite idFonc);

    public void Desactiver(Application idApp, Fonctionnalite idFonc);

    public int VerifySiDejaActiver(Application idApp, Fonctionnalite idFonc);

    public AppFonc FindAppliFonIsActive(Long idApp, Long idFonc);
}
