/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse; 
import sn.gainde2000.pi.core.entity.ParametrageWidget;
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.entity.WidgetTemplateAttr;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.service.IParametrageWidgetAttrService;
import sn.gainde2000.pi.core.service.IParametrageWidgetService;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.ITaskStatusService;
import sn.gainde2000.pi.core.service.IWidgetAttrTemplateService;
import sn.gainde2000.pi.core.service.IWidgetService;
import sn.gainde2000.pi.core.service.IWidgetTemplateService;
import sn.gainde2000.pi.core.service.IWorkflowService;

/**
 *
 * @author jsarr
 */
@RestController
public class ParametrageWidgetController {
      
     @Autowired
     IWidgetService iWidgetService;
     @Autowired
     IWidgetTemplateService iWidgetTemplateService;
     @Autowired
     IProfile  iProfile;
     @Autowired
     IWorkflowService iWorkflow;
     @Autowired
     ITaskStatusService iTaskStatusService;
     @Autowired
     IParametrageWidgetService iParametrageWidgetService;
     @Autowired
     IParametrageWidgetAttrService iParametrageWidgetAttrService;
     //Ajouter action
     
     @GetMapping("listconfigwidget")
    public ServeurResponse getAllConfigWidget() {
        Iterable<ParametrageWidgetAttr> parametrageWidgetAttr = iParametrageWidgetAttrService.getListConfigWidget();
        ServeurResponse response = new ServeurResponse();
        if (parametrageWidgetAttr == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(parametrageWidgetAttr);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
  @PostMapping("createparamwidget")
   public ServeurResponse attribuerWidgetProfil(@RequestParam("paramwidgetgenform") String paramwidgetgenform ){ 
        ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
        JSONObject obj = new JSONObject(paramwidgetgenform); 
        String nomparam = obj.get("nomparam").toString();
          Long idproInst = 1L;
        String idpro;
        idpro = (String) obj.get("proId").toString();
         Integer proInsIdCast=Integer.parseInt(idpro);  
                idproInst = Long.valueOf(proInsIdCast); 
                 
          Long idwdgtInst = 1L;
        String idwdgt;
        idwdgt = (String) obj.get("wdgId").toString();
         Integer widgetIdCast=Integer.parseInt(idwdgt);  
         idwdgtInst = Long.valueOf(widgetIdCast);
         
         
         
         Long idworkflow = 1L;
        String idwrkfl;
        idwrkfl = (String) obj.get("workflowid").toString();
         Integer workflowIdCast=Integer.parseInt(idwrkfl);  
         idworkflow = Long.valueOf(workflowIdCast);
         
            Workflow wo  = iWorkflow.findByWkfId(idworkflow);
            Widget wg  = iWidgetService.findById(idwdgtInst).get();  
            Profil pr = iProfile.findById(idproInst).get();
            ParametrageWidget pwt = new ParametrageWidget();
            pwt.setNomparam(nomparam);
            pwt.setWidget(wg);
            pwt.setWorkflow(wo); 
            pwt.setProfile(pr); 
            iParametrageWidgetService.save(pwt);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi"); 
            response.setData(pwt);

        return response;
    }
   
   @GetMapping("listparamwidget/{idwrkf}")
    public ServeurResponse getAllParamWidget(@PathVariable Long idwrkf) {
        Iterable<ParametrageWidget> parametrageWidget = iParametrageWidgetService.findByWkflId(idwrkf);
        ServeurResponse response = new ServeurResponse();
        if (parametrageWidget == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(parametrageWidget);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    
    @GetMapping("recupchampsformparam/{idwrkf}")
    public ServeurResponse getAllChampsformParam(@PathVariable Long idwrkf) {
        List<ParametrageWidget> ectraxtch = iParametrageWidgetService.ExtractChampsFormParam(idwrkf);
        ServeurResponse response = new ServeurResponse();
        if (ectraxtch == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(ectraxtch);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    
    @PostMapping("attributionconfigwidget")
    public ServeurResponse addAttributionConfig(@RequestParam("configform") String configform){
        ServeurResponse response = new ServeurResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
        JSONObject obj = new JSONObject(configform); 
        Long idparamwidgetInst = 1L;
        String idparamwid;
        idparamwid = (String) obj.get("idparamwidget").toString();
        Integer paramwidgetInsIdCast=Integer.parseInt(idparamwid);  
        idparamwidgetInst = Long.valueOf(paramwidgetInsIdCast); 
        
        Long idwtemplateInst = 1L;
        String idwtemplate;
        idwtemplate = (String) obj.get("wtemplate").toString();
        Integer wtemplateInsIdCast=Integer.parseInt(idwtemplate);  
        idwtemplateInst = Long.valueOf(wtemplateInsIdCast); 
        
        Long idvariableordonneeInst = 1L;
        String idvariableordonnee;
        idvariableordonnee = (String) obj.get("variableordonnee").toString();
        Integer variableordonneeInsIdCast=Integer.parseInt(idvariableordonnee);  
        idvariableordonneeInst = Long.valueOf(variableordonneeInsIdCast);
         
         String attributabscisse = obj.get("attributabscisse").toString();
         WidgetTemplate wt  = iWidgetTemplateService.findById(idwtemplateInst).get();
          ParametrageWidget paw = iParametrageWidgetService.findById(idparamwidgetInst).get();
          ParametrageWidgetAttr pwa = new ParametrageWidgetAttr();
         pwa.setParametrageWidget(paw);
         pwa.setWidgetTemplate(wt);
         pwa.setAttributabscisse(attributabscisse);
         pwa.setVariableordonnee(idvariableordonneeInst); 
         iParametrageWidgetAttrService.save(pwa); 
        response.setStatut(true);
        response.setDescription("attribution privilege mis à jour avec succès");
        return response;
    }
    
    @PostMapping("updateattributionconfigwidget")
    public ServeurResponse updtateAttribution(@RequestParam("configform") String configform){
       ServeurResponse response = new ServeurResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
        JSONObject obj = new JSONObject(configform); 
        Long idparamwidgetInst = 1L;
        String idparamwid;
        idparamwid = (String) obj.get("idparamwidget").toString();
        Integer paramwidgetInsIdCast=Integer.parseInt(idparamwid);  
        idparamwidgetInst = Long.valueOf(paramwidgetInsIdCast); 
        
        Long idparamattrwidgetInst = 1L;
        String idparamattrwid;
        idparamattrwid = (String) obj.get("idparamattrwidget").toString();
        Integer paramattrewidgetInsIdCast=Integer.parseInt(idparamattrwid);  
        idparamattrwidgetInst = Long.valueOf(paramattrewidgetInsIdCast); 
        
                
        Long idwtemplateInst = 1L;
        String idwtemplate;
        idwtemplate = (String) obj.get("wtemplate").toString();
        Integer wtemplateInsIdCast=Integer.parseInt(idwtemplate);  
        idwtemplateInst = Long.valueOf(wtemplateInsIdCast); 
        
        Long idvariableordonneeInst = 1L;
        String idvariableordonnee;
        idvariableordonnee = (String) obj.get("variableordonnee").toString();
        Integer variableordonneeInsIdCast=Integer.parseInt(idvariableordonnee);  
        idvariableordonneeInst = Long.valueOf(variableordonneeInsIdCast); 
        String attributabscisse = obj.get("attributabscisse").toString();
         
         WidgetTemplate wt  = iWidgetTemplateService.findById(idwtemplateInst).get();
          ParametrageWidget paw = iParametrageWidgetService.findById(idparamwidgetInst).get();
          ParametrageWidgetAttr pwattr = iParametrageWidgetAttrService.findById(idparamattrwidgetInst);
          ParametrageWidgetAttr pwa = new ParametrageWidgetAttr();
          WidgetTemplate wit = new WidgetTemplate();
          
          iParametrageWidgetAttrService.deleteById(idparamattrwidgetInst);     
         pwa.setParametrageWidget(paw);
         pwa.setWidgetTemplate(wt);
         pwa.setAttributabscisse(attributabscisse);
         pwa.setVariableordonnee(idvariableordonneeInst); 
         iParametrageWidgetAttrService.saveOrUpdate(pwa); 
        response.setStatut(true);
        response.setDescription("attribution  mis à jour avec succès");
          
        return response;
    }
    
     @PostMapping("deleteattributionconfigwidget")
    public ServeurResponse deleteAttribution(@RequestParam("configform") String configform){
       ServeurResponse response = new ServeurResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
        JSONObject obj = new JSONObject(configform); 
        Long pwattridInst = 1L;
        String pwattridwid;
        pwattridwid = (String) obj.get("pwattrid").toString();
        Integer pwattridInsIdCast=Integer.parseInt(pwattridwid);  
        pwattridInst = Long.valueOf(pwattridInsIdCast); 
         
         iParametrageWidgetAttrService.deleteById(pwattridInst);       
        response.setStatut(true);
        response.setDescription("attribution  supprimer avec succès");
          
        return response;
    }
}
