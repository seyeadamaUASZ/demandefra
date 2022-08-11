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
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.repository.WidgetTemplateRepository;
import sn.gainde2000.pi.core.service.IWidgetTemplateService;

/**
 *
 * @author jsarr
 */
@Service
public class WidgetTemplateImpl implements IWidgetTemplateService{
    @Autowired
    WidgetTemplateRepository widgetTemplateRepository;
    @Override
    public List<WidgetTemplate> getListWidgetTemplate() {
       return widgetTemplateRepository.findAll();
    }

    @Override
    public Optional<WidgetTemplate> findById(Long wtId) {
        return widgetTemplateRepository.findById(wtId);
    }

    @Override
    public WidgetTemplate save(WidgetTemplate widgetTemplate) {
        return widgetTemplateRepository.save(widgetTemplate);
    }

    @Override
    public void delete(WidgetTemplate widgetTemplate) {
          widgetTemplateRepository.delete(widgetTemplate);
    }

     

    
    
}
