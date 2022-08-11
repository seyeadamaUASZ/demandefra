/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Inscription;

/**
 *
 * @author asow
 */
public interface IInscriptionService {

    public Inscription addUtilisateur(Inscription inscription);
    
    public void delete(Inscription inscription);

  

}
