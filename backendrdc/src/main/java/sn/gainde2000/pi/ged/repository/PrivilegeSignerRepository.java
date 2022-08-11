package sn.gainde2000.pi.ged.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.PrivilegeSigner;

public interface PrivilegeSignerRepository extends JpaRepository<PrivilegeSigner, Long> {

    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=0 AND pri.status=0 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentSigner(@Param("id") Long id);

    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=1 AND pri.status=0 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentCertifier(@Param("id") Long id);
    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=1 AND pri.status=1 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentDejaCertifier(@Param("id") Long id);

    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=2 AND pri.status=0 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentConsulter(@Param("id") Long id);
   
    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=3 AND pri.status=0 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentApprouver(@Param("id") Long id);

    @Query("SELECT d FROM Document d INNER JOIN PrivilegeSigner pri ON (d.dctId=pri.document.dctId) WHERE pri.statusDocument=3 AND pri.status=1 AND pri.utilisateur.utiId =:id")
    public List<Document> listDocumentDejaApprouver(@Param("id") Long id);

    
    @Query("SELECT pri FROM PrivilegeSigner pri  WHERE pri.document.dctId=:idD AND  pri.utilisateur.utiId =:id")
    public PrivilegeSigner findByPrivByDoc(@Param("idD") Long idD, @Param("id") Long id);
}
