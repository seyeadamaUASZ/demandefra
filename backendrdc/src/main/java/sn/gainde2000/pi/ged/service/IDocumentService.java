/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.ged.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.ged.entity.Document;

/**
 *
 * @author asow
 */
public interface IDocumentService {

    public void saveDocument(Document document);

    public List<Document> getListDocument();

    public Optional<Document> findById(Long id);

    public Document findBydctId(Long id);
    public Document findBydctTitre(String dctTitre);
    public List<Document> findByStatut();

    public void supprimer(Document document);
    
    public List<Document> getListDocumentUtilisateur(Long iduser);
   
    public List<Document> getListDocumentUtilisateurSigner(Long iduser);
	
    public List<Document> getListDocumentUtilisateurCertifier(Long iduser);
    public List<Document> getListDocumentUtilisateurDejaCertifier(Long iduser);

    public List<Document> getListDocumentUtilisateurConsulter(Long iduser);
    public List<Document> getListDocumentUtilisateurAApprouver(Long iduser);
    public List<Document> getListDocumentUtilisateurDejaApprouver(Long iduser);

     public void supprimerDocument(Long id);

     Document findByDemande(Long id);
     
     Document findByProduits(Long id);
}
