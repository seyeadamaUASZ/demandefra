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
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;
import sn.gainde2000.pi.core.repository.ParametrageWidgetAttrRepository;
import sn.gainde2000.pi.core.service.IParametrageWidgetAttrService;

/**
 *
 * @author jsarr
 */
@Service
public class ParametrageWidgetAttrImpl implements IParametrageWidgetAttrService{
    
    @Autowired
    ParametrageWidgetAttrRepository parametrageWidgetAttrRepository;
    @Override
    public ParametrageWidgetAttr save(ParametrageWidgetAttr pw) {
       return  parametrageWidgetAttrRepository.save(pw);
    }

    @Override
    public ParametrageWidgetAttr findById(Long pwId) {
       return  parametrageWidgetAttrRepository.getOne(pwId);
    }

    @Override
    public List<ParametrageWidgetAttr> getListConfigWidget() {
        return  parametrageWidgetAttrRepository.findAll();
    }
    
    @Override
    public void saveOrUpdate(ParametrageWidgetAttr pw)   
    {  
    parametrageWidgetAttrRepository.save(pw);  
    }  

    @Override
    public ParametrageWidgetAttr deleteById(Long pw) {
       parametrageWidgetAttrRepository.deleteById(pw);
        return null;
    }

     
    
    
}
