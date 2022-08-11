/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.repository.PwdCriteriaRepository;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;

/**
 *
 * @author asow
 */
@Service
public class PwdCriteriaServiceImpl implements IPwdCriteriaService{
    
    @Autowired
    PwdCriteriaRepository pwdCriteriaRepository;
    @Override
    public PwdCriteria save(PwdCriteria pwdCriteria) {
        return pwdCriteriaRepository.save(pwdCriteria);
    }

    @Override
    public PwdCriteria findByPwdCriteriaId(Long id) {
        return pwdCriteriaRepository.findByPwdId(id);
    }

    @Override
    public PwdCriteria update(PwdCriteria pwdCriteria) {
        return pwdCriteriaRepository.findByPwdId(Long.MIN_VALUE);
    }

	@Override
	public List<PwdCriteria> getListPwd() {
		// TODO Auto-generated method stub
		return pwdCriteriaRepository.findAll();
	}
    
}
