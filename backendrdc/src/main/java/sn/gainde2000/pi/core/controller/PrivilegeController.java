
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

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.entity.Profil;

import sn.gainde2000.pi.core.repository.ProfileRepository;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.IprivilegeService;

import java.util.List;
/**
 *
 * @author asow
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {
    
    @Autowired
    IprivilegeService iprivilegeService;

    @Autowired
    IProfile profileRepository;

    @PostMapping("ajoutPrivilege")
    public Privilege addPrivilege(@RequestBody Profil profil, @RequestBody Action action){

       Privilege p = new Privilege();
       p.setProfile(profil);
       p.setAction(action);
       return iprivilegeService.save(p);
    }    
    
    @PostMapping("/privilegebyprofil")
    @ResponseBody
    public ServeurResponse getPrivilegeByProfil(@RequestBody Profil p){  
        System.out.println("Profil ID :"+p.getProId());       
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(iprivilegeService.findByProfiles(p.getProId()));
        response.setDescription("Privileges récupérés avec succès");
        System.out.println("Privileges récupérés avec succès");
        return response;          
    }

    @PostMapping("/allocateprivilege")
    @ResponseBody
    public ServeurResponse allocatePrivilegeProfil(@RequestParam("profil") String profil, @RequestParam("removed") String removed, @RequestParam("added") String added){                             
        System.out.println("Données privileges envoyées :"+added); 
        ObjectMapper mapper = new ObjectMapper();       
        try {
            Profil p = mapper.readValue(profil, Profil.class);
            //mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);                        
            
            List<Action> removedPrivileges =  new ObjectMapper().readValue(removed, new TypeReference<List<Action>>() { });           
            removedPrivileges.forEach(action -> {
                iprivilegeService.removedPrivilege(p,action);
            });
            
            List<Action> addedPrivileges =  new ObjectMapper().readValue(added, new TypeReference<List<Action>>() { });           
                addedPrivileges.forEach(action -> {
                     iprivilegeService.save(new Privilege(p,action));
            });            
                                    
            System.out.println("Données profil ID :"+p.getProId());
        } catch (IOException ex) {
            Logger.getLogger(PrivilegeController.class.getName()).log(Level.SEVERE, null, ex);
        }                       
       
           
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);       
        response.setDescription("Privileges mis à jour avec succès");       
        return response;          
    }    

    //Avoire le detail du compte de l'utilisateur
    //@PostMapping(value = "/listprivileges", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/listprivileges/{Idprofile}", method = RequestMethod.GET)
    public ServeurResponse getPrivileges(@PathVariable Long Idprofile) {
        System.out.println(Idprofile);
        Iterable<Privilege> priv = iprivilegeService.Privilegeaccord(Idprofile);
        ServeurResponse response = new ServeurResponse();
        if (priv == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("L'utilisateur '" + priv + "' n'a pas été trouvé.");
        } else {
            response.setStatut(true);
            response.setData(priv);
            response.setDescription("L'utilisateur '" + priv + "' a été trouvé.");
        }
        return response;
    }

}

