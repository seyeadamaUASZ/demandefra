package sn.gainde2000.pi.core.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.entity.RapportAnalyse;
import sn.gainde2000.pi.core.service.IRapportAnalyseService;

@RestController
public class RapportAnalyseController {
	@Autowired
	IRapportAnalyseService rapportService;
	
	@PostMapping(value = "rapportanalyse/create", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServeurResponse create(HttpServletRequest request, @Nullable @RequestPart("analyse") MultipartFile analyse) {
		ServeurResponse response = new ServeurResponse();
		RapportAnalyse rapportAnalyse;
		try {
			rapportAnalyse = new ObjectMapper().readValue(request.getParameter("rapportAnalyse"), new TypeReference<RapportAnalyse>() {
			});
			rapportAnalyse.setAnalyse(analyse.getBytes());
			rapportService.save(rapportAnalyse);
			response.setStatut(true);
			response.setDescription("Enregistrement réussi");
			response.setData(rapportAnalyse);
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
	
	@GetMapping("rapportanalyse/list")
	public ServeurResponse getAllRapportAnalyse() {
		Iterable<RapportAnalyse> rapport = rapportService.listRapportAnalyse();
		ServeurResponse response = new ServeurResponse();
		if (rapport == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(rapport);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("rapportanalyse/{id}")
	public ServeurResponse getOneRapportAnalyse(@PathVariable Long id) {
		RapportAnalyse rapport = rapportService.getOneRapportAnalyse(id);
		ServeurResponse response = new ServeurResponse();
		if (rapport == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(rapport);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("rapportanalyse/produit/{idProduit}")
	public ServeurResponse RapportAnalyseParProduit(@PathVariable Long idProduit) {
		RapportAnalyse rapport = rapportService.rapportParProduit(idProduit);
		ServeurResponse response = new ServeurResponse();
		if (rapport == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(rapport);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("rapportanalyse/produits/{idProduit}")
	public ServeurResponse RapportAnalyseSuivi(@PathVariable Long idProduit) {
		RapportAnalyse rapport = rapportService.rapportParProduit(idProduit);
		ServeurResponse response = new ServeurResponse();
		if (rapport == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(rapport);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("rapportanalyse/downloadFile/analyse/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileAnalyse(
			@PathVariable String analyse, @PathVariable Long id, HttpServletResponse response) {

		Optional<RapportAnalyse> data = rapportService.findById(id);
		File file = new File(data.get().getReferentiel().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getAnalyse()));

	}
}
