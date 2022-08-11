
package sn.gainde2000.pi.core.controller;

import sn.gainde2000.pi.core.entity.Suivredemande;
import sn.gainde2000.pi.core.service.ISuivredemandeService;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.File;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

@RestController
public class SuivredemandeController {

	@Autowired
	ISuivredemandeService suivredemandeService;

	@GetMapping("suivredemande/list/{owner}")
	public ServeurResponse getAllsuivredemande(@PathVariable Long owner) {
		Iterable<Suivredemande> suivredemande = suivredemandeService.listSuivredemandeById(owner);
		ServeurResponse response = new ServeurResponse();
		if (suivredemande == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(suivredemande);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("suivredemande/status/{id}/{status}")
	public ServeurResponse updateStatusSuivredemande(@PathVariable Long id, @PathVariable Long status) {
		Suivredemande suivredemande = suivredemandeService.getOneSuivredemande(id);
		suivredemande.setStatus(status);
		suivredemande = suivredemandeService.save(suivredemande);
		ServeurResponse response = new ServeurResponse();
		if (suivredemande == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(suivredemande);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("suivredemande/task/{poowner}")
	public ServeurResponse getAllTask(@PathVariable Long poowner) {
		Iterable<Suivredemande> suivredemande = suivredemandeService.listSuivredemande(poowner);
		ServeurResponse response = new ServeurResponse();
		if (suivredemande == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(suivredemande);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("suivredemande/list")
	public ServeurResponse getAllsuivredemande() {
		Iterable<Suivredemande> suivredemande = suivredemandeService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (suivredemande == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(suivredemande);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@PostMapping("suivredemande/delete")
	public ServeurResponse delete(@RequestBody Suivredemande suivredemande) {
		ServeurResponse response = new ServeurResponse();
		suivredemandeService.delete(suivredemande);
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}
	
	/*@PostMapping("suivredemande/create",consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},produces=MediaType.APPLICATION_JSON_VALUE)
	public ServeurResponse create(HttpServletRequest request) {

		ServeurResponse response = new ServeurResponse();

		Suivredemande suivredemande;
		try {
			suivredemande = new ObjectMapper().readValue(request.getParameter("suivredemande"),
					new TypeReference<Suivredemande>() {
					});

			suivredemandeService.save(suivredemande);
			response.setStatut(true);
			response.setDescription("Enregistrement réussi");
			response.setData(suivredemande);
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
	}*/
}