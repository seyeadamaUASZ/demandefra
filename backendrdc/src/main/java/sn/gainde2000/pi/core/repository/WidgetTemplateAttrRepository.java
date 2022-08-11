/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.entity.WidgetTemplateAttr;

/**
 *
 * @author jsarr
 */
public interface WidgetTemplateAttrRepository  extends JpaRepository<WidgetTemplateAttr, Long>{
   @Query("SELECT wgt FROM Widget wgt LEFT JOIN WidgetTemplateAttr wt ON (wgt.wdgId=wt.widget.wdgId) WHERE wt.widgetTemplate =:widgetTemplate ")
    public  List<Widget> findByWidgetTempl(@Param("widgetTemplate") WidgetTemplate widgetTemplate);
   @Transactional
    @Modifying 
   
    @Query("DELETE FROM WidgetTemplateAttr wa WHERE wa.profile.proId =:idproInst")
    public int removedWidgetTemplAttribution(@Param("idproInst") Long idproInst); 
    //public WidgetTemplateAttr findByWaId(String widgetAttribution);

    @Query("SELECT wdg.wdgNom FROM Widget wdg, WidgetTemplateAttr wa where wdg.wdgId = wa.widget  AND wa.widgetTemplate.wtId = :wtId")
    Iterable<WidgetTemplateAttr> attributionTemplWidget(@Param("wtId") Long wtId);
    
    
    @Query("SELECT wa FROM WidgetTemplateAttr wa WHERE wa.widgetTemplate =:widgetTemplate AND wa.widget =:widget")
    public  WidgetTemplateAttr findByTemplWidget(@Param("widgetTemplate") WidgetTemplate widgetTemplate, @Param("widget") Widget widget);
    
    @Query("SELECT wa FROM WidgetTemplateAttr wa WHERE   wa.widget.wdgId =:wdgId")
    public  List<WidgetTemplateAttr> findByIdWidget(@Param("wdgId") Long wdgId);
    
    @Query("SELECT wa FROM WidgetTemplateAttr wa WHERE   wa.widget.wdgId !=:wdgId")
    public  List<WidgetTemplateAttr> findAllNotTempWidgets(@Param("wdgId") Long wdgId);
    
    @Query("SELECT wdg FROM Widget wdg LEFT JOIN WidgetTemplateAttr wa ON (wdg.wdgId=wa.widget.wdgId) WHERE wa.widget.wdgId =:widId ") 
    public List<WidgetTemplate> findAllByTempWidgets(@Param("widId") Long widId); 
    
    @Query("select wdg from WidgetTemplateAttr wdg, Profil p where  wdg.profile.proId=p.proId and wdg.profile.proId=:idprofile")
    public List<WidgetTemplateAttr> listeWidgetsTemplateAttrByProfile(@Param("idprofile")Long idprofile);
    
    @Query("SELECT wa FROM WidgetTemplateAttr wa WHERE wa.profile =:profil AND wa.widget =:widget")
    public  WidgetTemplateAttr findByProfileWidget(@Param("profil") Profil profil, @Param("widget") Widget widget);
     
}
