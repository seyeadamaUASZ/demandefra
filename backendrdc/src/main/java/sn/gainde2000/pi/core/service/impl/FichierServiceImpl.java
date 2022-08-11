/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Fichier;
import sn.gainde2000.pi.core.repository.FichierRepository;
import sn.gainde2000.pi.core.service.IFichierService;

/**
 *
 * @author Administrateur_Aly
 */
@Service
public class FichierServiceImpl implements IFichierService{
    
    @Autowired
    FichierRepository fichierRepository;

    @Override
    public void supprimer(Fichier fichier) {
        
         fichierRepository.delete(fichier);
        

    }

    @Override
    public List<Fichier> getAllFichier() {
       return fichierRepository.findAll();
    }

    @Override
    public List<Fichier> getAllFichierLibre() {
      return fichierRepository.getFichierByApp();

    }

     @Override
     public List<Fichier> getAllFichierByIdApp(Long id) {
          return fichierRepository.getFichierByAppId(id);
     }

    @Override
    public List<Fichier> getAllFichierByIdAppOuLibre(Long id) {
        return fichierRepository.getFichierByAppIdOuLibre(id);
    }

	@Override
	public Fichier findByfhrNom(String fhrNom) {
		// TODO Auto-generated method stub
		return fichierRepository.findByfhrNom(fhrNom);
	}

     
}
