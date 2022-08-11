/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.repository.ApplicationRepository;
import sn.gainde2000.pi.core.service.IApplicationService;

/**
 *
 * @author yougadieng
 */
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> getListApplication() {
        return applicationRepository.findAll();
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public void delete(Application application) {
        applicationRepository.delete(application);
    }

	@Override
	public Application findByappId(Long appId) {
		// TODO Auto-generated method stub
		return applicationRepository.findByappId(appId);
	}

     @Override
     public void update(String etape,String idApp) {
          applicationRepository.updateEtapeCreation(etape,idApp);
     }

     @Override
     public Application findByappNom(String appNom) {
         return applicationRepository.findByAppNom(appNom);
     }

    @Override
    public void deleteJointureWorkflow(Application idApp) {
        applicationRepository.updateLiaisonWorkflow(idApp);
    }

    @Override
    public void deleteJointureFormulaire(Long idApp) {
        applicationRepository.updateLiaisonFormulaire(idApp);
    }

    @Override
    public void deleteJointureFichier(Application idApp) {
       applicationRepository.updateLiaisonFichier(idApp);

    }

     @Override
     public void deleteJointureTableRelationnel(Long idApp) {
          applicationRepository.updateLiaisonTableLiaison(idApp);
     }

	@Override
	public int nombreAplication() {
		// TODO Auto-generated method stub
		return applicationRepository.nombreAplication() ;
	}

	@Override
	public List<Application> listApplicationPubliee() {
		// TODO Auto-generated method stub
		return applicationRepository.listApplicationPubliee();
	}

	@Override
	public List<Application> list2LeastRecentApplicationPubliee() {
		// TODO Auto-generated method stub
		return applicationRepository.list2LeastRecentApplicationPubliee();
	}

	

}
