/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Secteur;

/**
 *
 * @author yougadieng
 */
public interface ISecteurService {
    public Secteur create(Secteur secteur);
    public Secteur getSecteurById(Long id);
    public void deleteSecteur(Secteur secteur);
    public List<Secteur> getAllSecteur();
    
}
