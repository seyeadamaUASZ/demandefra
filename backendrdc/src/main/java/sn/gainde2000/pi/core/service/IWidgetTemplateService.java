/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
/**
 *aseye
 * @author jsarr
 */
public interface IWidgetTemplateService {
     public List<WidgetTemplate> getListWidgetTemplate();
     public Optional<WidgetTemplate> findById(Long wtId); 
     public  WidgetTemplate save(WidgetTemplate widgetTemplate);
     public void delete(WidgetTemplate widgetTemplate);
}
