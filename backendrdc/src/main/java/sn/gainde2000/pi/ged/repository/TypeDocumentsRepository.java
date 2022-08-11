package sn.gainde2000.pi.ged.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.ged.entity.TypeDocuments;

public interface TypeDocumentsRepository extends JpaRepository<TypeDocuments, Long>{
	 @Query("SELECT tydoc FROM TypeDocuments tydoc LEFT JOIN PrivilegeDocument p ON tydoc.tydId=p.typedocuments.tydId WHERE p.profile.proId = :profil")
	    public List<TypeDocuments> getListTypeDocumentProfil(@Param("profil") Long profil);
}
