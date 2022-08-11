/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.RegleGestion;
import sn.gainde2000.pi.core.repository.RegleGestionRepository;
import sn.gainde2000.pi.core.service.IRegleGestionService;

/**
 *
 * @author jsarr
 */
@Service
public class RegleGestionImpl implements IRegleGestionService{

    @Autowired
    RegleGestionRepository regleGestionRepository;
    
    @Override
    public RegleGestion save(RegleGestion rg) {
       return regleGestionRepository.save(rg);
    }

    @Override
    public RegleGestion findByRgwrflId(Long rgwrflId) {
       return regleGestionRepository.findByRgId(rgwrflId);
    }

    @Override
    public List<RegleGestion> findByRegleGestionWrkId(Long id) {
        return regleGestionRepository.findByRegleGestionWrkId(id);
    }
    
}
