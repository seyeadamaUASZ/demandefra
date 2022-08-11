/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.ChampsRapport;
import sn.gainde2000.pi.core.repository.ChampsRapportRipository;
import sn.gainde2000.pi.core.service.IChampsRapportService;


/**
 *
 * @author asow, sagueye
 */
@Service
public class ChampsRapportServiceImpl implements IChampsRapportService{
    @Autowired
    ChampsRapportRipository champsRapportRipository;


    @Override
    public List<ChampsRapport> listBycrtId(Long id) {
        return champsRapportRipository.findByCrtId(id);
    }
    @Override
    public ChampsRapport save(ChampsRapport champRapport) {
        return champsRapportRipository.save(champRapport);
        
    }
    
    @Override
    public List<ChampsRapport> listByRapportId(Long id) {
        return champsRapportRipository.listByRepportId(id);
    }
	@Override
	public void delete(Long id) {
		champsRapportRipository.deleteById(id);
		
	}
	@Override
	public void supprimerByRapport(Long id) {
		champsRapportRipository.supprimer(id);
		
	}
    
}
