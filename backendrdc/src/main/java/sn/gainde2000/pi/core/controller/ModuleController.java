package sn.gainde2000.pi.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Module;
import sn.gainde2000.pi.core.service.IAppFoncService;
import sn.gainde2000.pi.core.service.ImoduleService;
import sn.gainde2000.pi.core.service.impl.AppFoncServiceImpl;

@RestController
@CrossOrigin("*")
public class ModuleController {

     @Autowired
     ImoduleService imoduleService;
     @Autowired
     IAppFoncService appFoncService;
     //Liste des modules

     @GetMapping("module")
     public ServeurResponse getAllModule() {
          Iterable<Module> module = imoduleService.getListMod();
          ServeurResponse response = new ServeurResponse();
          if (module == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(module);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     //Ajouter module

     @PostMapping("module")
     public ServeurResponse create(@RequestBody Module module) {
          ServeurResponse response = new ServeurResponse();
          imoduleService.save(module);
          response.setStatut(true);
          response.setDescription("Enregistrement réussi");
          response.setData(module);

          return response;
     }
     //recuperation par id

     @GetMapping("module/{id}")
     public Optional<Module> findById(@PathVariable Long id) {
          return imoduleService.findByModId(id);
     }
     //Supprimer un module

     @PostMapping("module/delete")
     public ServeurResponse deleteModule(@RequestBody Module module) {
          ServeurResponse response = new ServeurResponse();
          imoduleService.delete(module);
          response.setStatut(true);
          return response;
     }

    /* @GetMapping("moduleApp/{id}")
     public List<Module> findByIdApp(@PathVariable Long id) {
          return imoduleService.findBymodAppId(id);
     }*/
      @GetMapping("/get/moduleByAppId/{id}")
     public ServeurResponse ModuleByIdApp(@PathVariable Long id) {
          ServeurResponse response = new ServeurResponse();
          response.setStatut(true);
          response.setDescription("Recuperation des modules effectués avec succes");
          response.setData(imoduleService.findBymodAppId(id));
          return response;
     }
}
