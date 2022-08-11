/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.Application;

import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.Rapport;

/**
 *
 * @author asow
 */
public interface IRapportService {

    public Rapport save(Rapport rapport);

    public Rapport findById(Long id);

    public List<Rapport> listRapportNotGenerate();

    public List<Rapport> getListRapportGenerate();

    public List<Rapport> getListRapportGenerateEtLibre();
    
    public List<Rapport> getListRapportGenerateEtLibreOuSpecifique(Application id);

    void delete(Rapport rapport);

    public void lierFichier(String idApp, String idFichier);

    public void enleverLiasonFichier(String idFichier);
    
   public List<Rapport> getRapportByAppId(Long id);

    public List<Rapport> getAllFichierByIdApp(Long id);
    
    public List<Rapport> getAllRapportByMenu(Menu menu);
    
    public int nombreFichier();
    
    Rapport  findByrptNom(String rptNom);


}
