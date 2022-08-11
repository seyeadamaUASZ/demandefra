package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.service.IWidgetService;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;

/**
*
* @author JO Sarr, Serigne Asse Gueye
*/

@RestController
public class WidgetController {
    @Autowired
    IWidgetService iWidgetService;

    @GetMapping("listwidget")
    public ServeurResponse getAllWidget() {
        Iterable<Widget> widget = iWidgetService.getListWidget();
        ServeurResponse response = new ServeurResponse();
        if (widget == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(widget);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    	
    @PostMapping("listwidgetprofil")
    public ServeurResponse getAllWidgetProfil(@RequestBody Profil p) {
        Iterable<Widget> widget = iWidgetService.getListWidgetProfil(p);
        ServeurResponse response = new ServeurResponse();
        if (widget == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(widget);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("listwidgetbyprofilid/{id}")
    public ServeurResponse getAllWidgetByProfilId(@PathVariable Long id) {
        Iterable<Widget> widget = iWidgetService.getListWidgetProfilId(id);
        ServeurResponse response = new ServeurResponse();
        if (widget == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(widget);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @PostMapping("listwidgetprofilnoattr")
    public ServeurResponse getAllWidgetProfilNoAttr(@RequestBody Profil p) {
        Iterable<Widget> widget = iWidgetService.getListWidgetProfilNoAttr(p);
        ServeurResponse response = new ServeurResponse();
        if (widget == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(widget);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    //Ajouter action
    @PostMapping("widget")
    public ServeurResponse create(@RequestBody Widget widget) {

        ServeurResponse response = new ServeurResponse();

        iWidgetService.save(widget);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(widget);

        return response;
    }
    
    @PostMapping("widget/update")
    public ServeurResponse update(@RequestBody Widget widget) {
  ServeurResponse response = new ServeurResponse();
   ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
            JSONObject obj = new JSONObject(widget);
            Long jfrmId = 1L;
            jfrmId = (Long) obj.get("wdgId");
            Widget findWidget = iWidgetService.findById(jfrmId).get();
  
        if (findWidget != null) { 
            findWidget.setWdgCode(widget.getWdgCode());
            findWidget.setWdgNom(widget.getWdgNom());
            findWidget.setWdgDescription(widget.getWdgDescription());
            iWidgetService.save(findWidget);
            response.setStatut(true);
            response.setDescription("++++Enregistrement rÃ©ussi+++");
            response.setData(findWidget);
        } else {
            response.setStatut(false);
            response.setDescription("+++Echec d'enregistrement+++");
        }

        return response;
    }
    
    //recuperation par id
    @GetMapping("widget/{id}")
    public Optional<Widget> findById(@PathVariable Long id) {
        return iWidgetService.findById(id);
    }
    //Supprimer un fonctionnalite
    @PostMapping("widget/delete")
    public ServeurResponse deletewidget(@RequestBody Widget widget) {
        ServeurResponse response = new ServeurResponse();
        iWidgetService.delete(widget);
        response.setStatut(true);
        return response;
    }
    
    

}
