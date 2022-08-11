package sn.gainde2000.pi.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Icone;
import sn.gainde2000.pi.core.service.IIconeService;

@RestController
public class IconeController {
	 @Autowired 
	 IIconeService iconeService;
	 
	 @GetMapping("icone/list")
	    public ServeurResponse getAllAction() {
	        Iterable<Icone> icone = iconeService.getListIcone();
	        ServeurResponse response = new ServeurResponse();
	        if (icone == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("Aucune élèment trouvé.");

	        } else {
	            
	            response.setStatut(true);
	            response.setData(icone);
	            response.setDescription("Données récupérées.");
	        }
	        return response;
	    }
	  @PostMapping("icone/create")
	    public ServeurResponse create(@RequestBody Icone icone) {
	       
	        ServeurResponse response = new ServeurResponse();
	       
	        iconeService.saveIcone(icone);
	        response.setStatut(true);
	        response.setDescription("Enregistrement réussi");
	        response.setData(icone);
	         
	        return response;
	    }
	  @GetMapping("icone/{id}")
		public Optional<Icone> findById(@PathVariable Long id) {
		  return iconeService.findById(id);
		}
	  
	  @PostMapping("icone/delete")
      public ServeurResponse deleteformulaire(@RequestBody Icone icone) {
        ServeurResponse response = new ServeurResponse();
        iconeService.supprimer(icone);
        response.setStatut(true);
        return response;
   }
}
