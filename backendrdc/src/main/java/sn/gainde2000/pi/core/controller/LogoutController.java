/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.service.IHistoriqueSession;
import sn.gainde2000.pi.core.service.ISession;
import sn.gainde2000.pi.core.service.IUtilisateur;

/**u.getUtiId()
 *
 * @author asow
 */
@RestController
@CrossOrigin("*")
@RequestMapping("disconnect")
public class LogoutController {

     @Autowired
     public ISession sessionRepository;
     @Autowired
     public IHistoriqueSession historiqueSessionRepository;
     @Autowired
     public IUtilisateur utilisateurRepository;
     @GetMapping("/logout/{id}")
     public ServeurResponse fetchSignoutSite(@PathVariable Long id) {
          ServeurResponse response = new ServeurResponse();
          historiqueSessionRepository.updateDate(id);
          System.out.println("id historique : " + id);
          sessionRepository.deleteByIdSession(id);
          System.out.println("id session : " + id);
          System.out.println("ok");
          response.setStatut(true);
          response.setDescription("Vous avez été déconnecté");
          return response;


     }
}
