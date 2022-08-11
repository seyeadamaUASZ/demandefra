/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.entity.WidgetTemplateAttr;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.IWidgetAttrTemplateService;
import sn.gainde2000.pi.core.service.IWidgetService;
import sn.gainde2000.pi.core.service.IWidgetTemplateService;

/**
 *
 * @author jsarr
 */
@RestController
@RequestMapping("attribuertemplwidget")
public class WidgetTemplateAttrController {
     @Autowired
     IWidgetAttrTemplateService iWidgetAttrTemplateService;
     @Autowired
     IWidgetService iWidgetService;
     @Autowired
     IWidgetTemplateService iWidgetTemplateService;
     @Autowired
     IProfile  iProfile ;
     @PostMapping("attributionwidget")
    public ServeurResponse addAttribution(@RequestParam("idwidget") String idwidget ,@RequestParam("widgetTemAttr") String widgetTemAttr,@RequestParam("idprofil") String idprofil){
          ServeurResponse response = new ServeurResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
         
        JSONObject obj0 = new JSONObject(idprofil);
          Long idproInst = 1L;
        String idpro;
        idpro = (String) obj0.get("proId").toString();
         Integer proInsIdCast=Integer.parseInt(idpro);  
                idproInst = Long.valueOf(proInsIdCast); 
        JSONObject obj1 = new JSONObject(idwidget);
          Long idwdgtInst = 1L;
        String idwdgt;
        idwdgt = (String) obj1.get("wdgId").toString();
         Integer processInsIdCast=Integer.parseInt(idwdgt);  
         idwdgtInst = Long.valueOf(processInsIdCast);
         
        JSONArray arr2 = new JSONArray(widgetTemAttr);
         for (int i = 0; i < arr2.length(); i++) {
            JSONObject jsonobject = arr2.getJSONObject(i);
             Long wdgTemplId = jsonobject.getLong("wtId"); 
            Widget wg  = iWidgetService.findById(idwdgtInst).get(); 
            WidgetTemplate wt  = iWidgetTemplateService.findById(wdgTemplId).get();
            Profil pr = iProfile.findById(idproInst).get();
            WidgetTemplateAttr wta = new WidgetTemplateAttr();
            wta.setWidget(wg); 
            wta.setWidgetTemplate(wt); 
            wta.setProfile(pr);
              iWidgetAttrTemplateService.save(wta); 
        } 
         
            response.setStatut(true);
            response.setDescription("attribution privilege mis à jour avec succès");
            return response;
    }
    
    @PostMapping("updateattributionwidget")
    public ServeurResponse updtateAttribution(@RequestParam("idwidget") String idwidget ,@RequestParam("widgetTemAttr") String widgetTemAttr,@RequestParam("idprofil") String idprofil){
            ServeurResponse response = new ServeurResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
         
        JSONObject obj0 = new JSONObject(idprofil);
          Long idproInst = 1L;
        String idpro;
        idpro = (String) obj0.get("proId").toString();
         Integer proInsIdCast=Integer.parseInt(idpro);  
                idproInst = Long.valueOf(proInsIdCast); 
        Long idtattr = 1L;
        JSONObject obj1 = new JSONObject(idwidget);
          Long idwdgtInst = 1L;
        String idwdgt;
        idwdgt = (String) obj1.get("wdgId").toString();
         Integer processInsIdCast=Integer.parseInt(idwdgt);  
         idwdgtInst = Long.valueOf(processInsIdCast);
        JSONArray arr2 = new JSONArray(widgetTemAttr) ;
         for (int i = 0; i < arr2.length(); i++) {
          JSONObject jsonobject = arr2.getJSONObject(i);
             Long wdgTemplId = jsonobject.getLong("wtId"); 
          int delwta =  iWidgetAttrTemplateService.removedWidgetTemplAttribution(idproInst); 
        } 
         JSONArray arr3 = new JSONArray(widgetTemAttr) ;
         for (int i = 0; i < arr2.length(); i++) {
          JSONObject jsonobject = arr2.getJSONObject(i);
             Long wdgTemplId = jsonobject.getLong("wtId"); 
            Widget wg  = iWidgetService.findById(idwdgtInst).get(); 
            WidgetTemplate wt  = iWidgetTemplateService.findById(wdgTemplId).get();
            Profil pr = iProfile.findById(idproInst).get();
            WidgetTemplateAttr wta = new WidgetTemplateAttr();
            wta.setWidget(wg); 
            wta.setWidgetTemplate(wt); 
            wta.setProfile(pr);
               iWidgetAttrTemplateService.save(wta);
            
        } 
            response.setStatut(true);
            response.setDescription("attribution privilege mis à jour avec succès");
            return response;
    }
    
     
     @GetMapping("widgettemplatesAttr/{id}")
     public ServeurResponse listWidgetTemplateAttrs(@PathVariable("id") Long id){
         ServeurResponse response = new ServeurResponse();
         Iterable<WidgetTemplateAttr> attr = iWidgetAttrTemplateService.findByIdWidget(id); 
            response.setStatut(true);
            response.setData(attr);
            response.setDescription("Données récupérées.");
        return response;
     }
     
     @GetMapping("widgettemplatesNotAttr/{id}")
     public ServeurResponse listWidgetTemplateNotAttrs(@PathVariable("id") Long id){
         ServeurResponse response = new ServeurResponse();
         Iterable<WidgetTemplateAttr> noattr = iWidgetAttrTemplateService.findAllNotTempWidgets(id); 
            response.setStatut(true);
            response.setData(noattr);
            response.setDescription("Données récupérées.");
        return response;
     }
     
     @GetMapping("widgettemplatesAttrbyProfile/{id}")
     public ServeurResponse listWidgetTemplateAttrByProfile(@PathVariable("id") Long id){
         ServeurResponse response = new ServeurResponse();
         Iterable<WidgetTemplateAttr> attr = iWidgetAttrTemplateService.listeWidgetsTemplateAttrByProfile(id); 
            response.setStatut(true);
            response.setData(attr);
            response.setDescription("Données récupérées.");
        return response;
     }
     
     
      
}
