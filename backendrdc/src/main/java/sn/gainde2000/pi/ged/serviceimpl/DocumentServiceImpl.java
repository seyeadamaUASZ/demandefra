/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.ged.serviceimpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.repository.DocumentRepository;
import sn.gainde2000.pi.ged.service.IDocumentService;

/**
 *
 * @author asow
 */
@Service
public class DocumentServiceImpl implements IDocumentService {
    	@Autowired
        DocumentRepository documentRepository;

    @Override
    public void saveDocument(Document document) {
       documentRepository.save(document);
    }

    @Override
    public List<Document> getListDocument() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public void supprimer(Document document) {
        documentRepository.delete(document);
    }

    @Override
    public Document findBydctId(Long id) {
       return documentRepository.findBydctId(id);
    }


	@Override
	public List<Document> getListDocumentUtilisateur(Long iduser) {
		return documentRepository.getListDocumentUtilisateur(iduser);
	}

	@Override
	public List<Document> getListDocumentUtilisateurSigner(Long iduser) {
		return documentRepository.getListDocumentUtilisateurSigner(iduser);
	}

	@Override
	public List<Document> getListDocumentUtilisateurCertifier(Long iduser) {
		return documentRepository.getListDocumentUtilisateurCertifier(iduser);
	}

	@Override
	public List<Document> getListDocumentUtilisateurConsulter(Long iduser) {
		return documentRepository.getListDocumentUtilisateurConsulter(iduser);
	}


    @Override
    public void supprimerDocument(Long id) {
        documentRepository.deleteById(id);
        
    }

    @Override
    public List<Document> findByStatut() {
        return documentRepository.findByStatut();
    }
    

	@Override
	public List<Document> getListDocumentUtilisateurAApprouver(Long iduser) {
		return documentRepository.getListDocumentUtilisateurApprouver(iduser);
	}

	@Override
	public List<Document> getListDocumentUtilisateurDejaCertifier(Long iduser) {
		return documentRepository.getListDocumentUtilisateurDejaCertifier(iduser);
	}

	@Override
	public List<Document> getListDocumentUtilisateurDejaApprouver(Long iduser) {
		return documentRepository.getListDocumentUtilisateurDejaApprouver(iduser);
	}
	@Override
	public Document findBydctTitre(String dctTitre) {
		return documentRepository.findBydctTitre(dctTitre);
	}

	@Override
	public Document findByDemande(Long id) {
		return documentRepository.findByDemande(id);
	}

	@Override
	public Document findByProduits(Long id) {
		return documentRepository.findByProduits(id);
	}    
}
