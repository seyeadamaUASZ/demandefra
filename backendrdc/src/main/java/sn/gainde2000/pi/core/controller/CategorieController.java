package sn.gainde2000.pi.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Categorie;
import sn.gainde2000.pi.core.repository.CategoryRepository;
import sn.gainde2000.pi.core.service.CategorieService;

@RestController
public class CategorieController {
	@Autowired
	CategorieService categorieService;
	@Autowired
	CategoryRepository repo;
	
	@GetMapping("categorie/list")
	public ServeurResponse listCategories() {
		ServeurResponse response = new ServeurResponse();
		List<Categorie> categories = categorieService.listCategories();
		if(categories == null) {
			response.setData(null);			
			response.setStatut(false);
			response.setDescription("Aucune élèment trouvé.");
		}else {
			response.setData(categories);			
			response.setStatut(true);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	
	@PostMapping("categorie/create")
    public ServeurResponse createCategorie(@RequestBody Categorie categorie) {
		ServeurResponse response = new ServeurResponse();
		Categorie categorie2 = categorieService.addCategorie(categorie);
		if(categorie2 !=null) {
			response.setData(categorie2);
			response.setDescription("categorie created !!");
			response.setStatut(true);
		}else {
			response.setData(null);
			response.setDescription("categorie  not created !!");
			response.setStatut(false);
		}
		return response;
	}
	
	@PostMapping("categorie/update/{id}")
    public ServeurResponse updateParametreOtp(@PathVariable(name = "id") Long id, @RequestBody Categorie categorie) {
        ServeurResponse response = new ServeurResponse();
        Categorie categori = repo.findById(id).get();
        if(categori !=null) {
        	categori.setNom(categorie.getNom());
            categori.setPrix(categorie.getPrix());
            categorieService.addCategorie(categori);
            response.setStatut(true);
            response.setData(categori);
            response.setDescription("categorie modifiée avec succés!!");
        }else {
        	response.setStatut(false);
            response.setData(null);
            response.setDescription("categorie non modifiée avec succés!!");
        }
        
        return response;
    }
	
	 @PostMapping("categorie/delete")
	    public ServeurResponse delete(@RequestBody Categorie categorie) {
	        ServeurResponse response = new ServeurResponse();
	        categorieService.supprimerCategorie(categorie.getId());
	        response.setStatut(true);
	        response.setDescription("Données supprimé avec succès");
	        return response;
	    }
}
