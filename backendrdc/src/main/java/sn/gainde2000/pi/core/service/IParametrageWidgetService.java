/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import sn.gainde2000.pi.core.entity.ParametrageWidget;
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;

/**
 *
 * @author jsarr
 */
public interface IParametrageWidgetService {
    public List<ParametrageWidget> getListParametrageWidget(); 
    public Optional<ParametrageWidget>  findById(Long pwId);
    public  List<ParametrageWidget> findByWkflId(Long idwrkf);
    public  List<ParametrageWidget> ExtractChampsFormParam(Long idwrkf); 
    public ParametrageWidget save(ParametrageWidget parametrageWidget);
    public void delete(ParametrageWidget parametrageWidget);
    
    
}
