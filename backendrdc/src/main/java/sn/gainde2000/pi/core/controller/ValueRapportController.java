package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.ValueRapport;
import sn.gainde2000.pi.core.service.IValueRapport;

@RestController
public class ValueRapportController {
	 @Autowired
	 private IValueRapport iValueRapport;
	 
	 @GetMapping("valueRapport/list")
	 public ServeurResponse getAllValueRapport() {
	     Iterable<ValueRapport> value = iValueRapport.getListValue();
	     ServeurResponse response = new ServeurResponse();
	     if (value == null) {
	         response.setStatut(false);
	         response.setData(null);
	         response.setDescription("Aucune élèment trouvé.");

	     } else {
	         
	         response.setStatut(true);
	         response.setData(value);
	         response.setDescription("Données récupérées.");
	     }
	     return response;
	 }
	 @GetMapping("valueRapport/{id}")
	 public ServeurResponse getRapportValue(@PathVariable Long id) {
	     Iterable<ValueRapport> value = iValueRapport.listByRapportId(id);
	     ServeurResponse response = new ServeurResponse();
	     if (value == null) {
	         response.setStatut(false);
	         response.setData(null);
	         response.setDescription("Aucune élèment trouvé.");

	     } else {
	         
	         response.setStatut(true);
	         response.setData(value);
	         response.setDescription("Données récupérées.");
	     }
	     return response;
	 }
	 @PostMapping("valueRapport/create")
	 public ServeurResponse create(@RequestBody ValueRapport value) {
	    
	     ServeurResponse response = new ServeurResponse();
	    
	     iValueRapport.save(value);
	     response.setStatut(true);
	     response.setDescription("Enregistrement réussi");
	     response.setData(value);
	      
	     return response;
	 }
	 
	 @GetMapping("valueRapport/deletebyidrap/{id}")
	 public ServeurResponse deletebyidch(@PathVariable Long id) {
	    
	     ServeurResponse response = new ServeurResponse();
	    
	     iValueRapport.supprimerByRapportId(id);
	     response.setStatut(true);
	     response.setDescription("Suppression reussie");
	     return response;
	 }
	 
	 
	 
}
