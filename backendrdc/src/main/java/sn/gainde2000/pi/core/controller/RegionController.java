package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.AntenneRegionaleDepartementale;
import sn.gainde2000.pi.core.entity.Region;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IRegionService;
import sn.gainde2000.pi.core.service.IUtilisateur;

@RestController
public class RegionController {
	@Autowired
	IRegionService regionService;
	@Autowired
	IUtilisateur utilisateurService;
	
	@PostMapping(value = "region/create")
	public ServeurResponse createRegion(@RequestBody Region region) {
		ServeurResponse response = new ServeurResponse();
		Region region1 = regionService.save(region);;
		if(region1 !=null) {
			response.setData(region1);
			response.setDescription("region created !!");
			response.setStatut(true);
		}else {
			response.setData(null);
			response.setDescription("region not created !!");
			response.setStatut(false);
		}
		return response;
	}
	
	@GetMapping(value="region/list")
	public ServeurResponse getAllRegion() {
		List<Region> regions = regionService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (regions == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(regions);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@PostMapping("region/update/{id}")
	public ServeurResponse updateRegion(@PathVariable(name = "id") Long id,
			@RequestBody Region region) {
		ServeurResponse response = new ServeurResponse();
		Region regionale = regionService.getOneRegion(id);
		if (regionale != null) {
			regionale.setCodeRegion(region.getCodeRegion());
			regionale.setNomRegion(region.getNomRegion());
			regionService.save(regionale);
			response.setStatut(true);
			response.setData(regionale);
			response.setDescription("région modifiée avec succés!!");
		} else {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("région non modifiée!!");
		}

		return response;
	}

	@PostMapping("region/delete")
	public ServeurResponse delete(@RequestBody Region region) {
		ServeurResponse response = new ServeurResponse();
		regionService.supprimerRegion(region.getId());
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}
}
