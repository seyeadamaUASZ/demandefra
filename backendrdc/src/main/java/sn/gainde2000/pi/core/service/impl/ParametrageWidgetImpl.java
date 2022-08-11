/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.ParametrageWidget;
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;
import sn.gainde2000.pi.core.repository.ParametrageWidgetRepository;
import sn.gainde2000.pi.core.service.IParametrageWidgetService;

/**
 *
 * @author jsarr
 */
@Service
public class ParametrageWidgetImpl implements IParametrageWidgetService{

    @Autowired
    ParametrageWidgetRepository parametrageWidgetRepository;
    
    @Override
    public List<ParametrageWidget> getListParametrageWidget() {
      return parametrageWidgetRepository.findAll();
    }

    @Override
    public Optional<ParametrageWidget> findById(Long id) {
     return  parametrageWidgetRepository.findById(id);
    }

    @Override
    public ParametrageWidget save(ParametrageWidget parametrageWidget) {
        return  parametrageWidgetRepository.save(parametrageWidget);
    }

    @Override
    public void delete(ParametrageWidget parametrageWidget) {
        parametrageWidgetRepository.delete(parametrageWidget);
    }

    @Override
    public List<ParametrageWidget> findByWkflId(Long idwrkf) {
         return parametrageWidgetRepository.findByWkflId(idwrkf);
    }

  

    
    @Override
    public List<ParametrageWidget> ExtractChampsFormParam(Long idwrkf) {
       return parametrageWidgetRepository.ExtractChampsFormParam(idwrkf); 
    }

     
     

    
     
}
