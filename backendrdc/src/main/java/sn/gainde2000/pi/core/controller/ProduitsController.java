
package sn.gainde2000.pi.core.controller;

import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.service.IProduitsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

@RestController
public class ProduitsController {

	@Autowired
	IProduitsService produitsService;

	@GetMapping("produits/list")
	public ServeurResponse getAllproduits() {
		Iterable<Produits> produits = produitsService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (produits == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(produits);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("produits/status/{id}/{status}")
	public ServeurResponse updateStatusProduits(@PathVariable Long id, @PathVariable Long status) {
		Produits produits = produitsService.getOneProduits(id);
		produits.setStatus(status);
		produits = produitsService.save(produits);
		ServeurResponse response = new ServeurResponse();
		if (produits == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(produits);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("produits/list/{idDemande}")
	public ServeurResponse getAllProduitsByDemande(@PathVariable Long idDemande) {
		List<Produits> produits = produitsService.ListeProduitsParDemande(idDemande);
		ServeurResponse response = new ServeurResponse();
		if (produits == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(produits);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@PostMapping("produits/delete")
	public ServeurResponse delete(@RequestBody Produits produits) {
		ServeurResponse response = new ServeurResponse();
		produitsService.delete(produits);
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}

	@PostMapping(value = "produits/create", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServeurResponse create(HttpServletRequest request,
			@Nullable @RequestPart("etiquetteouemballage") MultipartFile etiquetteouemballage) {
		ServeurResponse response = new ServeurResponse();
		Produits produits;
		try {
			produits = new ObjectMapper().readValue(request.getParameter("produits"), new TypeReference<Produits>() {
			});

			produitsService.save(produits);
			response.setStatut(true);
			response.setDescription("Enregistrement réussi");
			response.setData(produits);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@PostMapping("produits/validationfra")
	private ServeurResponse validationFRA(HttpServletRequest request) {
		ServeurResponse response = new ServeurResponse();
	    String fra = request.getParameter("numeroFra");
	    Produits produits = produitsService.validationFra(fra);
	    if(produits != null) {
	    	response.setStatut(true);
			response.setDescription("Données récupérées avec succés");
			response.setData(produits);
	    } else {
	    	response.setStatut(true);
			response.setDescription("Aucune données récupérées");
			response.setData(null);
	    }			
		
		return response;
	}

	@GetMapping("demandeautfra/totaldemandeacceptees")
	private ServeurResponse totalDemandeAcceptees() {
		Integer totaldemandeacceptees = produitsService.totalDemandeAcceptees();
		ServeurResponse response = new ServeurResponse();
		response.setStatut(true);
		response.setDescription("Nombre Commerciaux");
		response.setData(totaldemandeacceptees);
		return response;
	}

	@GetMapping("demandeautfra/totaldemanderejetees")
	private ServeurResponse totalDemandeRejetees() {
		Integer totaldemanderejetees = produitsService.totalDemandeRejetees();
		ServeurResponse response = new ServeurResponse();
		response.setStatut(true);
		response.setDescription("Nombre Commerciaux");
		response.setData(totaldemanderejetees);
		return response;
	}
	
	/*@GetMapping("produits/nombreproduitstraitees/{powner}")
    public ServeurResponse nombreProduitstraitees(@PathVariable Long powner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> nombres = produitsService.nbrDemandeAutFraTraiteesChefDivisionDate(powner);
    	if(nombres.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(nombres);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
	
	@GetMapping("produits/nombrecirculairechefdivision/{powner}")
    public ServeurResponse circulaireDemandesChefDivision(@PathVariable Long powner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaires = produitsService.circulaireDemandeAutFraTraiteesChefDivisionDate(powner);
    	if(circulaires.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaires);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }*/
}