package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

import sn.gainde2000.pi.core.entity.*;

import sn.gainde2000.pi.core.service.IWidgetAttributionService;

import java.util.List;

/**
*
* @author JO Sarr, Serigne Asse Gueye
*/

@RestController
@RequestMapping("attribuer")
public class WidgetAttributionController {

        @Autowired
        IWidgetAttributionService iWidgetAttributionService;
//        @Autowired
//        WidgetRepository widgetRepository;
    @PostMapping("attributionwidget")
    public WidgetAttribution addAttribution(@RequestBody Profil profil, @RequestBody Widget widget){
        WidgetAttribution wa = new WidgetAttribution();
        wa.setProfile(profil);
        wa.setWidget(widget);
        System.out.println(profil);
        return iWidgetAttributionService.save(wa);
    }
    
        @PostMapping("/widgetbyprofil")
        public ServeurResponse getWidgetByProfil(@RequestBody Profil p){
            System.out.println("*** Profil ID :"+p.getProId());
            ServeurResponse response = new ServeurResponse();
            response.setStatut(true);
            response.setData(iWidgetAttributionService.getListWidgetProfil(p));
            response.setDescription("Privileges récupérés avec succès");
            System.out.println("Privileges récupérés avec succès");
            return response;
        }

        @PostMapping("/allocatewidget")
        @ResponseBody
        public ServeurResponse attribuerWidgetProfil(@RequestParam("profil") String profil, @RequestParam("removed") String removed, @RequestParam("added") String added, @RequestParam("updated") String updated){
            System.out.println("Données d'attribution de widget envoyées :"+added);
            ObjectMapper mapper = new ObjectMapper();
            try {
                Profil p = mapper.readValue(profil, Profil.class);
                //mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

                List<Widget> removedWidgetAttribution =  new ObjectMapper().readValue(removed, new TypeReference<List<Widget>>() { });
                removedWidgetAttribution.forEach(widget -> {
                	iWidgetAttributionService.removedWidgetAttribution(p,widget);
                });

                List<Widget> addedWidget =  new ObjectMapper().readValue(added, new TypeReference<List<Widget>>() { });
                addedWidget.forEach(widget -> {
                	WidgetAttribution wa = new WidgetAttribution(p,widget);
                	wa.setWaOrdre(widget.getWdgOrdre());
                	iWidgetAttributionService.save(wa);
                });
                
                List<Widget> updatedWidget =  new ObjectMapper().readValue(updated, new TypeReference<List<Widget>>() { });
                updatedWidget.forEach(widget -> {
                	WidgetAttribution wa = iWidgetAttributionService.getWidgetProfil(p, widget);
                	wa.setWaOrdre(widget.getWdgOrdre());
                	iWidgetAttributionService.save(wa);
                });

                System.out.println("Données profil ID :"+p.getProId());
            } catch (IOException ex) {
                Logger.getLogger(PrivilegeController.class.getName()).log(Level.SEVERE, null, ex);
            }


            ServeurResponse response = new ServeurResponse();
            response.setStatut(true);
            response.setDescription("attribution privilege mis à jour avec succès");
            return response;
        }

        //Avoire le detail du compte de l'utilisateur
        //@PostMapping(value = "/listprivileges", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
        @RequestMapping(value = "/listattribution/{Idprofile}", method = RequestMethod.GET)
        public ServeurResponse getPrivileges(@PathVariable Long Idprofile) {
            System.out.println(Idprofile);
            Iterable<WidgetAttribution> attr = iWidgetAttributionService.attributionWidget(Idprofile);
            ServeurResponse response = new ServeurResponse();
            if (attr == null) {
                response.setStatut(false);
                response.setData(null);
                response.setDescription("L'utilisateur '" + attr + "' n'a pas été trouvé.");
            } else {
                response.setStatut(true);
                response.setData(attr);
                response.setDescription("L'utilisateur '" + attr + "' a été trouvé.");
            }
            return response;
        }

}