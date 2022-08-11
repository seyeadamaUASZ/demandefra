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
import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.repository.ChampsRepository;
import sn.gainde2000.pi.core.service.IChampsService;

/**
 *
 * @author yougadieng, sagueye
 */
@Service
public class ChampsServiceImpl implements IChampsService{
    @Autowired
    ChampsRepository champRepository;
    @Override
    public List<Champs> getListChamps() {
        return champRepository.findAll();
    }

    @Override
    public List<Champs> listByFrmId(Long id) {
        return champRepository.listByFrmId(id);
    }

    @Override
    public List<Champs> listFieldByFrmId(Long id) {
        return champRepository.findBychFrmId(id);
    }

    @Override
    public List<Champs> listButtonByFrmId(Long id) {
       return champRepository.listButtonByFrmId(id);

    }

    @Override
    public Champs save(Champs champs) {
        return champRepository.save(champs);
    }

    @Override
    public Optional<Champs> findById(Long id) {
          return champRepository.findById(id);
    }

    @Override
    public void delete(Champs champs) {
       champRepository.delete(champs);
    }

	@Override
	public void supprimerByFormulaire(Long id) {
		this.champRepository.supprimer(id);
		
	}

	@Override
	public List<Champs> listByFrmIdFile(Long id) {
		// TODO Auto-generated method stub
		return this.champRepository.listByFrmIdFile(id);
	}
    
}
