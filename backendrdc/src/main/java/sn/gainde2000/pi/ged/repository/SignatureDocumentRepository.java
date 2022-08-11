package sn.gainde2000.pi.ged.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.SignatureDocument;
@Repository
public interface SignatureDocumentRepository extends JpaRepository<SignatureDocument, Long>{
	@Query("SELECT u FROM Utilisateur u INNER JOIN SignatureDocument pri ON (u.utiId=pri.utilisateur.utiId) WHERE pri.document.dctId =:id ")
    public  List<Utilisateur> listUtilisateurDocumentSigner(@Param("id") Long id);
	
	@Query("SELECT d FROM Document d INNER JOIN SignatureDocument pri ON (d.dctId=pri.document.dctId) WHERE  pri.utilisateur.utiId =:id ")
    public  List<Document> listDocumentSignerByUser(@Param("id") Long id);
}
