package sn.gainde2000.pi.ged.service;

import java.util.List;


import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.SignatureDocument;


public interface ISignatureDocument {
	public SignatureDocument save(SignatureDocument p);
	 public  List<Utilisateur> listUtilisateurDocumentSigner(Long id);
	 public  List<Document> listDocumentSignerByUser(Long id);
	
}
