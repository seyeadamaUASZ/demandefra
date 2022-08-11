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
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.entity.WidgetTemplateAttr;
import sn.gainde2000.pi.core.repository.WidgetTemplateAttrRepository;
import sn.gainde2000.pi.core.service.IWidgetAttrTemplateService;

/**
 *
 * @author jsarr
 */
@Service
public class WidgetAttrTemplateImpl implements IWidgetAttrTemplateService {

    @Autowired
	private WidgetTemplateAttrRepository widgetTemplateAttrRepository;
    
     @Override
    public List<Widget> getWidgetTempl(WidgetTemplate widgetTemplate) {
       return widgetTemplateAttrRepository.findByWidgetTempl(widgetTemplate);
    }

    @Override
    public WidgetTemplateAttr getWidgetTempl(WidgetTemplate widgetTemplate, Widget widget) {
       return widgetTemplateAttrRepository.findByTemplWidget(widgetTemplate,widget);
    }

    @Override
    public Iterable<WidgetTemplateAttr> attributionTemplWidget(Long id) {
      return widgetTemplateAttrRepository.attributionTemplWidget(id);
    }

    @Override
    public WidgetTemplateAttr save(WidgetTemplateAttr wta) {
      return widgetTemplateAttrRepository.save(wta);
    }

    /*@Override
    public void removedWidgetTemplAttribution(WidgetTemplate widgetTemplate, Widget widget) {
       widgetTemplateAttrRepository.removedWidgetTemplAttribution(widgetTemplate,widget);
    }*/

    @Override
    public List<WidgetTemplateAttr> findByIdWidget(Long widgetid) {
       return widgetTemplateAttrRepository.findByIdWidget(widgetid);
    }

    @Override
    public List<WidgetTemplateAttr> findAllNotTempWidgets(Long widgetid) {
         return widgetTemplateAttrRepository.findAllNotTempWidgets(widgetid);
    }

	@Override
	public List<WidgetTemplateAttr> listeWidgetsTemplateAttrByProfile(Long idprofile) {
		// TODO Auto-generated method stub
		return widgetTemplateAttrRepository.listeWidgetsTemplateAttrByProfile(idprofile);
	}

    @Override
    public int removedWidgetTemplAttribution(Long idproInst) {
       return widgetTemplateAttrRepository.removedWidgetTemplAttribution(idproInst);
    } 

    @Override
    public WidgetTemplateAttr getWidgetProfil(Profil profil, Widget Widget) {
        return widgetTemplateAttrRepository.findByProfileWidget(profil,Widget);
    }

    
     
     
     
    
}
