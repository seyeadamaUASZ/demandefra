package sn.gainde2000.pi.ged.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.PrivilegeDocument;
import sn.gainde2000.pi.ged.entity.TypeDocuments;



public interface IPrivilegeDocumentService {
	 public List<Profil> findByProfiles(Long p);
	    public PrivilegeDocument save(PrivilegeDocument p);
	    public void removedPrivilege(TypeDocuments typeDocument,Profil p);
	    public  Iterable<PrivilegeDocument> Privilegeaccord(Long Idprofile);
	    public void delete(PrivilegeDocument p); 
	    Iterable<Utilisateur> utilisateurByTypeDocument(Long IdtypeDocument);
}
