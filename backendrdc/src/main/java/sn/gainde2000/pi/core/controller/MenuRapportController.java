package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.MenuRapport;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.service.IMenuRapportService;

/**
*
* @author sagueye
*/
@RestController
public class MenuRapportController {
	@Autowired
	IMenuRapportService mrService;
	
        /**
         * Liste les évenements (menus liés au raports)
         * @return ServeurResponse
         */
	@GetMapping("menurapport")
        public ServeurResponse getEvenements() {    	
        Iterable<MenuRapport> mrs = mrService.getListMenuRapports();
        ServeurResponse response = new ServeurResponse();
        if (mrs == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(mrs);
            response.setDescription("Données récupérées.");
        }
        return response;
        }
	
        /**
         * récupère l'événement par notification
         * @param Rapport r
         * @return ServeurResponse
         */
	@PostMapping("menurapport/menurapportparrapport")
        @ResponseBody
            public ServeurResponse getEvenementByNotification(@RequestBody Rapport r){       
            ServeurResponse response = new ServeurResponse();
            response.setStatut(true);
            response.setData(mrService.getListMenuParRapport(r));
            response.setDescription("Menu rapport récupérés avec succès");
            return response;          
        }
    
            /**
             * récupère l'événement par action
             * @param m
             * @return ServeurResponse
             */
        @PostMapping("menurapport/menurapportparmenu")
        @ResponseBody
        public ServeurResponse getEvenementByAction(@RequestBody Menu m){       
            ServeurResponse response = new ServeurResponse();
            response.setStatut(true);
            response.setData(mrService.getListMenuRapportParMenus(m));
            response.setDescription("Menu rapport récupérés avec succès");
            return response;          
        }
    
        /**
         * Lie un menu à un rapport
         * @param mr
         * @return ServeurResponse
         */
        @PostMapping("menurapport/create")
        public ServeurResponse create(@RequestBody MenuRapport mr) {
            ServeurResponse response = new ServeurResponse();         
            if(mrService.save(mr)!=null){
                 response.setStatut(true);
                 response.setDescription("Menu rapport crée avec succès");
            } else {
                 response.setStatut(false);
                 response.setDescription("Erreur de création de menu rapport!");
            }
            return response;
        }

        /**
         * Met à jour la liaison entre menu et rapport
         * @param mr
         * @return ServeurResponse
         */
        @PostMapping("menurapport/update")
        public ServeurResponse update(@RequestBody MenuRapport mr) {
            ServeurResponse response = new ServeurResponse();
            if(mrService.save(mr)!=null){
               response.setStatut(true);
               response.setDescription("Menu rapport crée avec succès");
            } else {
               response.setStatut(false);
               response.setDescription("Erreur lors de la modification du menu rapport!");
            }
            return response;
        }

        /**
         * Supprime la liaison entre menu et rapport
         * @param mr
         * @return ServeurResponse
         */
        @PostMapping("menurapport/delete")
        public ServeurResponse delete(@RequestBody MenuRapport mr) {
          ServeurResponse response = new ServeurResponse();
          mrService.delete(mr);
          response.setStatut(true);
          response.setDescription("menu supprimé avec succès");
          return response;
        }
     
        /**
         * Lie l'action à la notification
         * @param String rapport
         * @param String removed
         * @param String added
         * @return ServeurResponse
         */
        @PostMapping("menurapport/allocatemenurapport")
        @ResponseBody
        public ServeurResponse allocateActionNotification(@RequestParam("rapport") String rapport, @RequestParam("removed") String removed, @RequestParam("added") String added){ 
            ObjectMapper mapper = new ObjectMapper();       
            try {
                Rapport r = mapper.readValue(rapport, Rapport.class);
                List<Menu> removedMenuRapports =  new ObjectMapper().readValue(removed, new TypeReference<List<Menu>>() { });           
                removedMenuRapports.forEach(menu -> {
                    mrService.removedMenuRapport(r, menu);
                });
                List<Menu> addedMenuRapports =  new ObjectMapper().readValue(added, new TypeReference<List<Menu>>() { });           
                addedMenuRapports.forEach(menu -> {
                    mrService.save(new MenuRapport(r,menu));
                });                                                 
            } catch (IOException ex) {
             Logger.getLogger(MenuRapportController.class.getName()).log(Level.SEVERE, null, ex);
        }                                           
         ServeurResponse response = new ServeurResponse();
         response.setStatut(true);       
         response.setDescription("Menu rapport mis à jour avec succès");       
         return response;          
     } 
}
