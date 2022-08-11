/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Fonctionnalite;

/**
 *
 * @author yougadieng
 */
public interface IFonctionnaliteService {

    public List<Fonctionnalite> getAllFormulaire();

    public Fonctionnalite save(Fonctionnalite f);

    public Fonctionnalite findById(Long id);
    
    public Fonctionnalite findByFonNom(String nom);

    public void delete(Fonctionnalite f);

    public List<Fonctionnalite> getFonctionnaliteByModId(Long id);

    public List<Fonctionnalite> findByfonModId(Long fonModId);

}
