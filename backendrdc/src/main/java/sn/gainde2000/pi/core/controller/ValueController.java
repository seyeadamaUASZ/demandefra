package sn.gainde2000.pi.core.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

import sn.gainde2000.pi.core.entity.ValueChamps;
import sn.gainde2000.pi.core.service.IValue;
import sn.gainde2000.pi.core.service.impl.ValueServiceImpl;

@RestController
public class ValueController {
 @Autowired
 private IValue valueServiceImpl;
 
 @GetMapping("value/list")
 public ServeurResponse getAllValue() {
     Iterable<ValueChamps> value = valueServiceImpl.getListValue();
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
 @GetMapping("value/{id}")
 public ServeurResponse getChampsValue(@PathVariable Long id) {
     Iterable<ValueChamps> value = valueServiceImpl.listByChampId(id);
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
 @PostMapping("value/create")
 public ServeurResponse create(@RequestBody ValueChamps value) {
    
     ServeurResponse response = new ServeurResponse();
    
     valueServiceImpl.save(value);
     response.setStatut(true);
     response.setDescription("Enregistrement réussi");
     response.setData(value);
      
     return response;
 }
 
 @GetMapping("value/deletebyidch/{id}")
 public ServeurResponse deletebyidch(@PathVariable Long id) {
    
     ServeurResponse response = new ServeurResponse();
    
     valueServiceImpl.supprimerByChampId(id);
     response.setStatut(true);
     response.setDescription("Suppression reussie");
     return response;
 }
 
 
 
}
