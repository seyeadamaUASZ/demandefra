/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional; 
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.entity.WidgetTemplateAttr;

/**
 *
 * @author jsarr
 */

public interface IWidgetAttrTemplateService {
    public  List<Widget> getWidgetTempl(WidgetTemplate widgetTemplate);
     public WidgetTemplateAttr getWidgetTempl(WidgetTemplate widgetTemplate, Widget Widget);
     public WidgetTemplateAttr getWidgetProfil(Profil profil, Widget Widget);
    public Iterable<WidgetTemplateAttr>  attributionTemplWidget(Long id);
     
    public WidgetTemplateAttr save(WidgetTemplateAttr wta);
  public int removedWidgetTemplAttribution(Long idproInst);
    public List<WidgetTemplateAttr> findByIdWidget(Long widgetid); 
    public List<WidgetTemplateAttr> findAllNotTempWidgets(Long widgetid); 
    public List<WidgetTemplateAttr> listeWidgetsTemplateAttrByProfile(Long idprofile);
     
}
