package sn.gainde2000.pi.ged.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.service.IDocumentService;
import sn.gainde2000.pi.ged.service.ISignatureDocument;

@RestController
public class SignatureDocumentController {
	 @Autowired
	  IUtilisateur iutilisateur;
	 @Autowired
	 IDocumentService idoc;
	 @Autowired
	 ISignatureDocument iSignatureDocument;
	 
	    @GetMapping("signatureDocument/listdocument/{iduser}")
	    public ServeurResponse getAllDocumentByUser(@PathVariable Long iduser) {
	        Iterable<Document> document = iSignatureDocument.listDocumentSignerByUser(iduser);
	        ServeurResponse response = new ServeurResponse();
	        if (document == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("Aucune élèment trouvé.");

	        } else {

	            response.setStatut(true);
	            response.setData(document);
	            response.setDescription("Données récupérées.");
	        }
	        return response;
	    }
	    @GetMapping("signatureDocument/listutilisateur/{idDoc}")
	    public ServeurResponse getAllUtilisateurByDocument(@PathVariable Long idDoc) {
	        Iterable<Utilisateur> utilisateur = iSignatureDocument.listUtilisateurDocumentSigner(idDoc);
	        ServeurResponse response = new ServeurResponse();
	        if (utilisateur == null) {
	            response.setStatut(false);
	            response.setData(null);
	            response.setDescription("Aucune élèment trouvé.");

	        } else {

	            response.setStatut(true);
	            response.setData(utilisateur);
	            response.setDescription("Données récupérées.");
	        }
	        return response;
	    }
}
