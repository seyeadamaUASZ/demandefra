/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.JbpmFormInfos; 
import sn.gainde2000.pi.core.repository.JbpmFormInfosRepository;
import sn.gainde2000.pi.core.service.IJbpmFormInfos;

/**
 *
 * @author jsarr
 */
@Service
public class JbpmFormInfosImpl implements IJbpmFormInfos{
    @Autowired
    JbpmFormInfosRepository jbpmFormInfosRepository;
            
    @Override
    public JbpmFormInfos findByJfrmFormulaire(String jfrmFormulaire) {
         return jbpmFormInfosRepository.findByJfrmFormulaire(jfrmFormulaire);
    }

    @Override
    public JbpmFormInfos save(JbpmFormInfos jf) {
       return jbpmFormInfosRepository.save(jf);
    }

    @Override
    public JbpmFormInfos findByJfrmWorkflow(String jfrmWorkflow) {
         return jbpmFormInfosRepository.findByJfrmWorkflow(jfrmWorkflow);
    }

   
    @Override
     public List<JbpmFormInfos> getListJbpmFormInfosBycontainer(String jfrmWorkflow) {
         return jbpmFormInfosRepository.getListJbpmFormInfosBycontainer(jfrmWorkflow);
    }

    @Override
    public List<JbpmFormInfos> getListJbpmFormInfosById(Long jfrmId) {
        return jbpmFormInfosRepository.getListJbpmFormInfosById(jfrmId);
    }

	@Override
	public JbpmFormInfos findByJfrmId(Long jfrmId) {
		// TODO Auto-generated method stub
		return jbpmFormInfosRepository.findByJfrmId(jfrmId);
	}

	@Override
	public List<JbpmFormInfos> getListJbpmFormInfosGenerer(String containerId) {
		// TODO Auto-generated method stub
		return jbpmFormInfosRepository.getListJbpmFormInfosGenerer(containerId);
	}

	@Override
	public List<JbpmFormInfos> getListJbpmFormInfosNoGenerer() {
		// TODO Auto-generated method stub
		return jbpmFormInfosRepository.getListJbpmFormInfosNoGenerer();
	}


	@Override
	public List<JbpmFormInfos> getListJbpmFormInfos(String containerId) {
		// TODO Auto-generated method stub
		return jbpmFormInfosRepository.getListJbpmFormInfos(containerId);
	}


    @Override
    public void delete(Long jfrmId) {
       jbpmFormInfosRepository.deleteById(jfrmId);
    }

     
     
    
}
