/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Fichier;
import sn.gainde2000.pi.core.entity.Formulaire;

/**
 *
 * @author Administrateur_Aly
 */
public interface IFichierService {

     public void supprimer(Fichier fichier);

     public List<Fichier> getAllFichier();
     
     public List<Fichier> getAllFichierByIdApp(Long id);
     
     public List<Fichier> getAllFichierByIdAppOuLibre(Long id);
     
     public List<Fichier> getAllFichierLibre();
     
     public Fichier  findByfhrNom(String fhrNom);

 

}
