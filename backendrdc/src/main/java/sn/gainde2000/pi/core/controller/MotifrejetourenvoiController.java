
package sn.gainde2000.pi.core.controller;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Motifrejetourenvoi;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.service.IDemandeAutFraService;
import sn.gainde2000.pi.core.service.IMotifrejetourenvoiService;
import sn.gainde2000.pi.core.service.IProduitsService;
import sn.gainde2000.pi.core.service.MailingService;
import sn.gainde2000.pi.core.tools.Email;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import javax.mail.MessagingException;
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
public class MotifrejetourenvoiController {

	@Autowired
	IMotifrejetourenvoiService motifrejetourenvoiService;
	@Autowired
	IDemandeAutFraService demandeService;
	@Autowired IProduitsService iProduitsService;
	@Autowired
    MailingService mailService;

	@GetMapping("motifrejetourenvoi/list/{owner}")
	public ServeurResponse getAllmotifrejetourenvoi(@PathVariable Long owner) {
		Iterable<Motifrejetourenvoi> motifrejetourenvoi = motifrejetourenvoiService.listMotifrejetourenvoiById(owner);
		ServeurResponse response = new ServeurResponse();
		if (motifrejetourenvoi == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(motifrejetourenvoi);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("motifrejetourenvoi/status/{id}/{status}")
	public ServeurResponse updateStatusMotifrejetourenvoi(@PathVariable Long id, @PathVariable Long status) {
		Motifrejetourenvoi motifrejetourenvoi = motifrejetourenvoiService.getOneMotifrejetourenvoi(id);
		motifrejetourenvoi.setStatus(status);
		motifrejetourenvoi = motifrejetourenvoiService.save(motifrejetourenvoi);
		ServeurResponse response = new ServeurResponse();
		if (motifrejetourenvoi == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(motifrejetourenvoi);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("motifrejetourenvoi/task/{poowner}")
	public ServeurResponse getAllTask(@PathVariable Long poowner) {
		Iterable<Motifrejetourenvoi> motifrejetourenvoi = motifrejetourenvoiService.listMotifrejetourenvoi(poowner);
		ServeurResponse response = new ServeurResponse();
		if (motifrejetourenvoi == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(motifrejetourenvoi);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("motifrejetourenvoi/list")
	public ServeurResponse getAllmotifrejetourenvoi() {
		Iterable<Motifrejetourenvoi> motifrejetourenvoi = motifrejetourenvoiService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (motifrejetourenvoi == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(motifrejetourenvoi);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@PostMapping("motifrejetourenvoi/delete")
	public ServeurResponse delete(@RequestBody Motifrejetourenvoi motifrejetourenvoi) {
		ServeurResponse response = new ServeurResponse();
		motifrejetourenvoiService.delete(motifrejetourenvoi);
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}
	
	@PostMapping("motifrejetourenvoi/create")
	public ServeurResponse create(@RequestBody Motifrejetourenvoi motifrejetourenvoi) {
		ServeurResponse response = new ServeurResponse();
		motifrejetourenvoi.setDateCommentaire(new Date());
		Motifrejetourenvoi motif = motifrejetourenvoiService.save(motifrejetourenvoi);
		response.setStatut(true);
        response.setData(motif);
        response.setDescription("Données enregistrées");
        DemandeAutFra demande = demandeService.getOneDemandeAutFra(motif.getIdLink());
        if(demande.getStatus() == 10) {
        	mailService.sendEmail("no-reply@gainde2000.sn", demande.getEmailResponsable(), "Bonjour, "+demande.getPrenomResponsable()+" "+demande.getNomResponsable()+"\nSuite à l’étude de votre demande d’autorisation FRA N° "+demande.getNumdemande()+",\nnous sommes au regret de vous informer qu’elle ne peut être délivrée.\nLe motif du refus est: "+motif.getMotif(), "Rejet Demande Autorisation FRA");
        } else if(demande.getStatus() == 9) {
        	mailService.sendEmail("no-reply@gainde2000.sn", demande.getEmailResponsable(), "Bonjour, "+demande.getPrenomResponsable()+" "+demande.getNomResponsable()+"\nSuite à l’étude de votre demande d’autorisation FRA N° "+demande.getNumdemande()+",\nnous sommes au regret de vous informer que votre dossier a été renvoyé.\nLe motif du renvoi est: "+motif.getMotif(), "Renvoi Demande Autorisation FRA");
        } 
		return response;
	}
	
	@PostMapping("motifrejetourenvoi/rejetproduit")
	public ServeurResponse rejetProduit(@RequestBody Motifrejetourenvoi motifrejetourenvoi) {
		ServeurResponse response = new ServeurResponse();
		motifrejetourenvoi.setDateCommentaire(new Date());
		Motifrejetourenvoi motif = motifrejetourenvoiService.save(motifrejetourenvoi);
		response.setStatut(true);
        response.setData(motif);
        response.setDescription("Données enregistrées");
        Produits produit = iProduitsService.getOneProduits(motif.getIdLink());
        if(produit.getStatus() == 10) {
        	mailService.sendEmail("no-reply@gainde2000.sn", produit.getDemandeautfra().getEmailResponsable(), "Bonjour, "+ produit.getDemandeautfra().getPrenomResponsable()+" "+ produit.getDemandeautfra().getNomResponsable()+"\nSuite à l’étude de votre demande d’autorisation FRA N° "+ produit.getDemandeautfra().getNumdemande()+",\nnous sommes au regret de vous informer qu’elle ne peut être délivrée.\nLe motif du refus est: "+motif.getMotif(), "Rejet Demande Autorisation FRA");
        } 
		return response;
	}
	
	@GetMapping("motifrejetourenvoi/rejetee/{owner}")
	public ServeurResponse listdemanderenvoyeees(@PathVariable Long owner) {
		ServeurResponse response = new ServeurResponse();
		List<DemandeAutFra> demandesrenvoyees = motifrejetourenvoiService.listMotifrenvoyeeById(owner);
		if(demandesrenvoyees.isEmpty()) {
			response.setData(null);
			response.setStatut(false);
			response.setDescription(" not changed!!");
		}else {
			response.setData(demandesrenvoyees);
			response.setStatut(true);
			response.setDescription("changed!!");
		}
		
		return response;
	}
	
	@GetMapping("motifrejetvalide/valide/{owner}")
	public ServeurResponse listDemandesValidees(@PathVariable Long owner) {
		ServeurResponse response = new ServeurResponse();
		List<DemandeAutFra> demandesValidees = motifrejetourenvoiService.listMotifValidee(owner);
		if(demandesValidees.isEmpty()) {
			response.setData(null);
			response.setDescription("no data");
			response.setStatut(false);
		}else {
			response.setData(demandesValidees);
			response.setDescription("les données");
			response.setStatut(true);
		}
		return response;
	}
	
	@GetMapping("motifrejetourenvoi/historique/{idLink}/{status}")
	public ServeurResponse historiqueCommentaire(@PathVariable Long idLink, @PathVariable Long status) {
		ServeurResponse response = new ServeurResponse();
		List<Motifrejetourenvoi> historique = motifrejetourenvoiService.historiqueCommentaire(idLink, status);
		if(historique == null) {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("Aucune élèment trouvé.");			
		}else {
			response.setData(historique);
			response.setStatut(true);
			response.setDescription("Données récupérées.");			
		}
		return response;
	}
	
	@GetMapping("motifrejetourenvoi/renvoichefdivision/{idLink}")
	public ServeurResponse renvoichefdivision(@PathVariable Long idLink) {
		ServeurResponse response = new ServeurResponse();
		Motifrejetourenvoi historique = motifrejetourenvoiService.motifRenvoiChefDivision(idLink);
		if(historique == null) {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("Aucune élèment trouvé.");			
		}else {
			response.setData(historique);
			response.setStatut(true);
			response.setDescription("Données récupérées.");			
		}
		return response;
	}
	
	@GetMapping("motifrejetourenvoi/motifrejet/{idLink}/{status}")
	public ServeurResponse motifRejet(@PathVariable Long idLink, @PathVariable Long status) {
		ServeurResponse response = new ServeurResponse();
		List<Motifrejetourenvoi> historique = motifrejetourenvoiService.historiqueCommentaire(idLink, status);
		if(historique == null) {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("Aucune élèment trouvé.");			
		}else {
			response.setData(historique);
			response.setStatut(true);
			response.setDescription("Données récupérées.");			
		}
		return response;
	}
}