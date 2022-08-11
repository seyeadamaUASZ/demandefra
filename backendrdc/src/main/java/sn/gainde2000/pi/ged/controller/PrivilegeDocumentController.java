package sn.gainde2000.pi.ged.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.PrivilegeDocument;
import sn.gainde2000.pi.ged.entity.TypeDocuments;
import sn.gainde2000.pi.ged.service.IPrivilegeDocumentService;

@RestController
public class PrivilegeDocumentController {

	 @Autowired
	    IPrivilegeDocumentService iprivilegeDocumentService;
	 
         
         /**
          * créer un privilege du document
          * @param profil
          * @param typeDocument
          * @return 
          */
	    @PostMapping("privilegeDocument/create")
	    public PrivilegeDocument addPrivilege(@RequestBody Profil profil, @RequestBody TypeDocuments typeDocument){

	       PrivilegeDocument p = new PrivilegeDocument();
	       p.setProfile(profil);
	       p.setTypedocuments(typeDocument);
	       return iprivilegeDocumentService.save(p);
	    } 
	    
	    
	    /**
             * privilege par profil.
             * @param p
             * @return 
             */
	    @PostMapping("privilegeDocument/privilegebyprofil")
	    @ResponseBody
	    public ServeurResponse getPrivilegeByProfil(@RequestBody TypeDocuments p){       
	        ServeurResponse response = new ServeurResponse();
	        response.setStatut(true);
	        response.setData(iprivilegeDocumentService.findByProfiles(p.getTydId()));
	        response.setDescription("Privileges récupérés avec succès");
	        System.out.println("Privileges récupérés avec succès");
	        return response;          
	    }
	    
	    
	    @PostMapping("privilegeDocument/allocateprivilege")
	    @ResponseBody
	    public ServeurResponse allocatePrivilegeProfil(@RequestParam("typeDocument") String typeDocument, @RequestParam("removed") String removed, @RequestParam("added") String added){                             
	        ObjectMapper mapper = new ObjectMapper();       
	        try {
	            TypeDocuments p = mapper.readValue(typeDocument, TypeDocuments.class);                      
	            
	            List<Profil> removedPrivileges =  new ObjectMapper().readValue(removed, new TypeReference<List<Profil>>() { });           
	            removedPrivileges.forEach(profil -> {
	            	iprivilegeDocumentService.removedPrivilege(p,profil);
	            });
	            
	            List<Profil> addedPrivileges =  new ObjectMapper().readValue(added, new TypeReference<List<Profil>>() { });           
	                addedPrivileges.forEach(profil -> {
	                	iprivilegeDocumentService.save(new PrivilegeDocument(profil,p));
	            });            
	                                    
	          
	        } catch (IOException ex) {
	            Logger.getLogger(PrivilegeDocumentController.class.getName()).log(Level.SEVERE, null, ex);
	        }                       
	       
	           
	        ServeurResponse response = new ServeurResponse();
	        response.setStatut(true);       
	        response.setDescription("Privileges mis à jour avec succès");       
	        return response;          
	    }  
	    
	    /**
             * liste des privilege par profil
             * @param Idprofile
             * @return 
             */
	    @RequestMapping(value = "privilegeDocument/listprivileges/{Idprofile}", method = RequestMethod.GET)
	    public ServeurResponse getPrivileges(@PathVariable Long Idprofile) {
	        System.out.println(Idprofile);
	        Iterable<PrivilegeDocument> priv = iprivilegeDocumentService.Privilegeaccord(Idprofile);
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
            
            /**
             * privilege par type de document
             * @param IdTyped
             * @return 
             */
	    
	    @RequestMapping(value = "privilegeDocument/utilisateur/{IdTyped}", method = RequestMethod.GET)
	    public ServeurResponse getUtilisateurTypeD(@PathVariable Long IdTyped) {
	    
	        Iterable<Utilisateur> utilisateur = iprivilegeDocumentService.utilisateurByTypeDocument(IdTyped);
	        ServeurResponse response = new ServeurResponse();
	        if (utilisateur == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("L'utilisateur '" + utilisateur + "' n'a pas été trouvé.");
	        } else {
	            response.setStatut(true);
	            response.setData(utilisateur);
	            response.setDescription("L'utilisateur '" + utilisateur + "' a été trouvé.");
	        }
	        return response;
	    }

}
