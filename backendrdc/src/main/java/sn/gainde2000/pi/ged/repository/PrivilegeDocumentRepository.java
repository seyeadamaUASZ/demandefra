package sn.gainde2000.pi.ged.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.PrivilegeDocument;
import sn.gainde2000.pi.ged.entity.TypeDocuments;

@Repository
public interface PrivilegeDocumentRepository extends JpaRepository<PrivilegeDocument, Long>{
	  @Query("SELECT p FROM Profil p INNER JOIN PrivilegeDocument pr ON (p.proId=pr.profile.proId) WHERE pr.typedocuments =:typedocument ")
	    public  List<Profil> findByProfiles(@Param("typedocument") TypeDocuments typedocument);
	    @Transactional
	    @Modifying
	    @Query("DELETE FROM PrivilegeDocument p WHERE p.profile =:profil AND p.typedocuments =:typedocument")
	    public void removedPrivilegeDocument( @Param("typedocument") TypeDocuments typedocument,@Param("profil") Profil profil);  
 
	    public PrivilegeDocument findByPridId(String priviliges);

	    @Query("SELECT p.proLibelle FROM Profil p, PrivilegeDocument pri where p.proId = pri.profile  AND pri.typedocuments.tydId = :IdtypeDocument")
	    Iterable<PrivilegeDocument> PrivilegeaccordDocument(@Param("IdtypeDocument") Long IdtypeDocument);
	    
	    @Query("SELECT u FROM Profil p, PrivilegeDocument pri, Utilisateur u where p.proId = pri.profile  AND u.uti_pro_id.proId = p.proId AND pri.typedocuments.tydId = :IdtypeDocument")
	    Iterable<Utilisateur> utilisateurByTypeDocument(@Param("IdtypeDocument") Long IdtypeDocument);
}
