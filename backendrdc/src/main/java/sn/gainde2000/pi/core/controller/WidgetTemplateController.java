/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse; 
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
import sn.gainde2000.pi.core.service.IWidgetTemplateService;

/**
 *
 * @author jsarr
 */
@RestController
public class WidgetTemplateController {
    
    @Autowired
    IWidgetTemplateService iwidgetTemplateService;
    
    @GetMapping("listwidgettemplate")
    public ServeurResponse getAllWidget() {
        Iterable<WidgetTemplate> widgetTemplate = iwidgetTemplateService.getListWidgetTemplate();
        ServeurResponse response = new ServeurResponse();
        if (widgetTemplate == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(widgetTemplate);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @PostMapping("widgettemplate")
    public ServeurResponse create(@RequestBody WidgetTemplate widgettemplate) { 
        ServeurResponse response = new ServeurResponse(); 
        iwidgetTemplateService.save(widgettemplate);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(widgettemplate);

        return response;
    }
    
    
     @PostMapping("widgettemplate/update")
    public ServeurResponse update(@RequestBody WidgetTemplate widgettemplate) {
  ServeurResponse response = new ServeurResponse();
   ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
            JSONObject obj = new JSONObject(widgettemplate);
            Long wtId = 1L;
            wtId = (Long) obj.get("wtId");
            WidgetTemplate findTempWidget = iwidgetTemplateService.findById(wtId).get();
  
        if (findTempWidget != null) {  
            findTempWidget.setWtNom(widgettemplate.getWtNom());
            iwidgetTemplateService.save(findTempWidget);
            response.setStatut(true);
            response.setDescription("++++Enregistrement rÃ©ussi+++");
            response.setData(findTempWidget);
        } else {
            response.setStatut(false);
            response.setDescription("+++Echec d'enregistrement+++");
        }

        return response;
    }
    //Supprimer un fonctionnalite
    @PostMapping("widgettemplate/delete")
    public ServeurResponse deletewidget(@RequestBody WidgetTemplate widgettemplate) {
        ServeurResponse response = new ServeurResponse();
        iwidgetTemplateService.delete(widgettemplate);
        response.setStatut(true);
        return response;
    }
    
}
