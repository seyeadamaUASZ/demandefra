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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.AntenneRegionaleDepartementale;
import sn.gainde2000.pi.core.entity.Categorie;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IAntenneRegionaleDepartementaleService;
import sn.gainde2000.pi.core.service.IUtilisateur;

@RestController
public class AntenneRegionaleDepartementaleController {
	@Autowired
	IAntenneRegionaleDepartementaleService antenneService;
	@Autowired
	IUtilisateur utilisateurService;

	@PostMapping(value = "antenneRegionaleDepartementale/create")
	public ServeurResponse createAntenneRegionaleDepartementale(@RequestBody AntenneRegionaleDepartementale antenne) {
		ServeurResponse response = new ServeurResponse();
		AntenneRegionaleDepartementale antenneRegionaleDepartementale = antenneService.save(antenne);;
		if(antenneRegionaleDepartementale !=null) {
			response.setData(antenneRegionaleDepartementale);
			response.setDescription("antenne régionale départementale created !!");
			response.setStatut(true);
		}else {
			response.setData(null);
			response.setDescription("antenne régionale départementale not created !!");
			response.setStatut(false);
		}
		return response;
	}

	@GetMapping(value = "antenneRegionaleDepartementale/list")
	public ServeurResponse getAllAntenneRegionaleDepartementale() {
		List<AntenneRegionaleDepartementale> antenneRegionaleDepartementales = antenneService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (antenneRegionaleDepartementales == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(antenneRegionaleDepartementales);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping(value = "antenneRegionaleDepartementale/listeAntenneParRegion/{id}")
	public ServeurResponse getAllAntenneRegionaleDepartementaleParRegion(@PathVariable Long id) {
		List<AntenneRegionaleDepartementale> antennesParRegionale = antenneService.listeAntennesParRegion(id);
		ServeurResponse response = new ServeurResponse();
		if (antennesParRegionale == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(antennesParRegionale);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@PostMapping("antenneRegionaleDepartementale/update/{id}")
	public ServeurResponse updateAntenne(@PathVariable(name = "id") Long id,
			@RequestBody AntenneRegionaleDepartementale antenneRegionaleDepartementale) {
		ServeurResponse response = new ServeurResponse();
		AntenneRegionaleDepartementale antenne = antenneService.getOneAntenne(id);
		if (antenne != null) {
			antenne.setCodeAntenne(antenneRegionaleDepartementale.getCodeAntenne());
			antenne.setNomAntenne(antenneRegionaleDepartementale.getNomAntenne());
			antenne.setRegion(antenneRegionaleDepartementale.getRegion());
			antenneService.save(antenne);
			response.setStatut(true);
			response.setData(antenne);
			response.setDescription("antenne modifiée avec succés!!");
		} else {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("antenne non modifiée!!");
		}

		return response;
	}

	@PostMapping("antenneRegionaleDepartementale/delete")
	public ServeurResponse delete(@RequestBody AntenneRegionaleDepartementale antenne) {
		ServeurResponse response = new ServeurResponse();
		antenneService.supprimerAntenneRegionaleDepartementale(antenne.getId());
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}
}
