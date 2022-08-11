/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.ged.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.ged.entity.Document;

/**
 *
 * @author asow
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statut=true")
    public List<Document> getListDocumentUtilisateur(@Param("iduser") Long iduser);

    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=0 AND doc.statut=true")
    public List<Document> getListDocumentUtilisateurSigner(@Param("iduser") Long iduser);

    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=1 AND doc.statut=1")
    public List<Document> getListDocumentUtilisateurCertifier(@Param("iduser") Long iduser);

    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=1 AND doc.statut=0")
    public List<Document> getListDocumentUtilisateurDejaCertifier(@Param("iduser") Long iduser);

    
    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=2 AND doc.statut=true")
    public List<Document> getListDocumentUtilisateurConsulter(@Param("iduser") Long iduser);

    
    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=3 AND doc.statut=1")
    public List<Document> getListDocumentUtilisateurApprouver(@Param("iduser") Long iduser);

    @Query("SELECT doc FROM Document doc  WHERE doc.utilisateur.utiId = :iduser AND doc.statusDocument=3 AND doc.statut=0")
    public List<Document> getListDocumentUtilisateurDejaApprouver(@Param("iduser") Long iduser);
    
    public Document findBydctId(Long id);
    public Document findBydctTitre(String dctTitre);

    @Query("select d from Document d  where d.statut=true")
    List<Document> findByStatut();

    @Modifying
    @Transactional
    @Query("update Document d set d.statut=false where d.dctId=:id")
    public void supprimeDocument(@Param("id") Long id);
    
    @Query(value="select * from td_document where td_document.dct_dmd_id=:id ORDER BY td_document.dct_id DESC LIMIT 1", nativeQuery = true)
    Document findByDemande(@Param("id") Long id);
    
    @Query(value="select * from td_document where td_document.dct_produit_id=:id ORDER BY td_document.dct_id DESC LIMIT 1", nativeQuery = true)
    Document findByProduits(@Param("id") Long id);
}
