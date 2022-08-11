package sn.gainde2000.pi.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.service.IActionService;


@RestController

public class ActionController {
	 @Autowired 
	  IActionService   actionService;
	//Liste des actions

	@GetMapping("action/list")
	    public ServeurResponse getAllAction() {
	        Iterable<Action> action = actionService.getListAction();
	        ServeurResponse response = new ServeurResponse();
	        if (action == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("Aucune élèment trouvé.");

	        } else {
	            
	            response.setStatut(true);
	            response.setData(action);
	            response.setDescription("Données récupérées.");
	        }
	        return response;
	    }

            @GetMapping("/listactions/{id}")
	    public ServeurResponse getAllActionMenu(@PathVariable Long id) {
	        Iterable<Action> action = actionService.getListAction();
	        ServeurResponse response = new ServeurResponse();
	        if (action == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("Aucune élèment trouvé.");

	        } else {
	            
	            response.setStatut(true);
	            response.setData(action);
	            response.setDescription("Données récupérées.");
	        }
	        return response;
	    }
	  //Ajouter action
            @RequestMapping(value = "action/create", method = RequestMethod.POST, consumes = "multipart/form-data")
            @ResponseBody	   
	    public ServeurResponse create(@RequestParam("action") String action, @RequestParam("menu") String menu) throws IOException {
                               
	       /*byte b[] = action.getBytes(); 
                ByteArrayInputStream bi = new ByteArrayInputStream(b);
                ObjectInputStream si = new ObjectInputStream(bi);
                Action act;*/
                ServeurResponse response = new ServeurResponse();
                try {
                    Action actionAdd = new ObjectMapper().readValue(action, Action.class);
                    Menu menuAdd = new ObjectMapper().readValue(menu, Menu.class);
                    actionAdd.setActMenId(menuAdd);
                    actionService.saveAction(actionAdd);
                    response.setStatut(true);
                    response.setDescription("Enregistrement réussi");
                    response.setData(actionAdd);
                    return response;
                } catch (Exception ex) {
                    Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    return response;
                }	        	      	         
	        
	    }
	    //recuperation par id
	    @GetMapping("action/{id}")
		public Optional<Action> findById(@PathVariable Long id) {
		  return actionService.findById(id);
		}
	  //Supprimer un fonctionnalite
	     @PostMapping("action/delete")
	        public ServeurResponse deleteformulaire(@RequestBody Action action) {
	          ServeurResponse response = new ServeurResponse();
	          actionService.supprimer(action);
	          response.setStatut(true);
	          return response;
	     }

}
