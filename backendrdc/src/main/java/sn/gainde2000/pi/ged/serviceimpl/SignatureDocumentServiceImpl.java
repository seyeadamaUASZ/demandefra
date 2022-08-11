package sn.gainde2000.pi.ged.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.SignatureDocument;
import sn.gainde2000.pi.ged.repository.SignatureDocumentRepository;
import sn.gainde2000.pi.ged.service.ISignatureDocument;

@Service
public class SignatureDocumentServiceImpl implements ISignatureDocument{
	
	@Autowired
	SignatureDocumentRepository signatureRepository;

	@Override
	public SignatureDocument save(SignatureDocument p) {
		// TODO Auto-generated method stub
		return signatureRepository.save(p);
	}

	@Override
	public List<Utilisateur> listUtilisateurDocumentSigner(Long id) {
		// TODO Auto-generated method stub
		return signatureRepository.listUtilisateurDocumentSigner(id);
	}

	@Override
	public List<Document> listDocumentSignerByUser(Long id) {
		// TODO Auto-generated method stub
		return signatureRepository.listDocumentSignerByUser(id);
	}

}
