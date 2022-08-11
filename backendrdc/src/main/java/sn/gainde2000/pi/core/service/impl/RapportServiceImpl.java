/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Application;

import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.repository.RapportRepository;
import sn.gainde2000.pi.core.service.IRapportService;

/**
 *
 * @author asow
 */
@Service
public class RapportServiceImpl implements IRapportService{
    @Autowired
    RapportRepository rapportRepository;

    @Override
    public Rapport save(Rapport rapport) {
       return rapportRepository.save(rapport);
    }

    public Rapport findById(Long id) {
        return rapportRepository.findByrptId(id);
    }

    @Override
    public List<Rapport> listRapportNotGenerate() {
        return rapportRepository.listrapportnotgenerate();
    }

    @Override
    public List<Rapport> getListRapportGenerate() {
          return rapportRepository.listrapportgenerate();
        
    }

    @Override
	public void delete(Rapport rapport) {
    	rapportRepository.delete(rapport);
	}

    @Override
    public List<Rapport> getListRapportGenerateEtLibre() {
        return rapportRepository.listrapportgenerateLibre();
    }

    @Override
    public void lierFichier(String idApp, String idFichier) {
        rapportRepository.LierFicherEtApp(idApp,idFichier);
    }

    @Override
    public void enleverLiasonFichier(String idFichier) {
        rapportRepository.enleverLiaisonFichier(idFichier);
    }

    @Override
    public List<Rapport> getAllFichierByIdApp(Long id) {
        return rapportRepository.getFichierByAppId(id);
    }

    @Override
    public List<Rapport> getListRapportGenerateEtLibreOuSpecifique(Application id) {
        return rapportRepository.listrapportgenerateLibreOuSpecifique(id);
    }

     @Override
     public int nombreFichier() {
          return rapportRepository.nombreRapportGenerate();
     }

	@Override
	public List<Rapport> getAllRapportByMenu(Menu menu) {
		return rapportRepository.getAllRapportByMenu(menu);
	}

	@Override
	public List<Rapport> getRapportByAppId(Long id) {
		// TODO Auto-generated method stub
		return rapportRepository.getRapportByAppId(id);
	}

	@Override
	public Rapport findByrptNom(String rptNom) {
		// TODO Auto-generated method stub
		return rapportRepository.findByrptNom(rptNom);
	}
    
}
