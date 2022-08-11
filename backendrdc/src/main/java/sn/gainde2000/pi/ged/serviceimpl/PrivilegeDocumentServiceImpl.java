package sn.gainde2000.pi.ged.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.PrivilegeDocument;
import sn.gainde2000.pi.ged.entity.TypeDocuments;
import sn.gainde2000.pi.ged.repository.PrivilegeDocumentRepository;
import sn.gainde2000.pi.ged.service.IPrivilegeDocumentService;

@Service
public class PrivilegeDocumentServiceImpl implements IPrivilegeDocumentService{

	@Autowired
	PrivilegeDocumentRepository  privilegeDocumentRepository;
	
	@Override
	public List<Profil> findByProfiles(Long p) {
		// TODO Auto-generated method stub
		return privilegeDocumentRepository.findByProfiles(new TypeDocuments(p));
	}

	@Override
	public PrivilegeDocument save(PrivilegeDocument p) {
		// TODO Auto-generated method stub
		return privilegeDocumentRepository.save(p);
	}

	@Override
	public void removedPrivilege(TypeDocuments typeDocument,Profil p) {
		// TODO Auto-generated method stub
		privilegeDocumentRepository.removedPrivilegeDocument(typeDocument, p);
	}

	@Override
	public Iterable<PrivilegeDocument> Privilegeaccord(Long Idprofile) {
		// TODO Auto-generated method stub
		return privilegeDocumentRepository.PrivilegeaccordDocument(Idprofile);
	}

	@Override
	public void delete(PrivilegeDocument p) {
		// TODO Auto-generated method stub
		privilegeDocumentRepository.delete(p);
		
	}

	@Override
	public Iterable<Utilisateur> utilisateurByTypeDocument(Long IdtypeDocument) {
		// TODO Auto-generated method stub
		return privilegeDocumentRepository.utilisateurByTypeDocument(IdtypeDocument);
	}

}
