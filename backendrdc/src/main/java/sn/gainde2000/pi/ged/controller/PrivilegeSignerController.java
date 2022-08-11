package sn.gainde2000.pi.ged.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.entity.StatusDocument;
import sn.gainde2000.pi.ged.service.IDocumentService;
import sn.gainde2000.pi.ged.service.IPrivilegeSignerServce;

@RestController
public class PrivilegeSignerController {
  @Autowired
	IPrivilegeSignerServce iPrivilegeSignerServce;
 @Autowired
  IUtilisateur iutilisateur;
 @Autowired
 IDocumentService idoc;
 
/**
 * creation de privilege de signature de document.
 * @param request
 * @param id
 * @param document
 * @return 
 */
    @PostMapping("privilegeSigner/create")
    public PrivilegeSigner signer(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("document") Long  document){

    	PrivilegeSigner p = new PrivilegeSigner();
    
       Utilisateur utilisateur= iutilisateur.getUtilisateur(id);
       p.setUtilisateur(utilisateur);
        Document doc = idoc.findBydctId(document);
       p.setDocument(doc);
       p.setStatusDocument(StatusDocument.SIGNER);
       p.setStatus(false);
       return iPrivilegeSignerServce.save(p);
    } 
    
    /**
     * creation de privilege de certification de document.
     * @param request
     * @param id
     * @param document
     * @return 
     */
    @PostMapping("privilegeCertifier/create")
    public PrivilegeSigner certifier(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("document") Long  document){

    	PrivilegeSigner p = new PrivilegeSigner();
    
       Utilisateur utilisateur= iutilisateur.getUtilisateur(id);
       p.setUtilisateur(utilisateur);
        Document doc = idoc.findBydctId(document);
       p.setDocument(doc);
       p.setStatusDocument(StatusDocument.CERTIFIER);
       p.setStatus(false);
       return iPrivilegeSignerServce.save(p);
    } 
    @PostMapping("privilegeConsulter/create")
    public PrivilegeSigner consulter(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("document") Long  document){

    	PrivilegeSigner p = new PrivilegeSigner();
    
       Utilisateur utilisateur= iutilisateur.getUtilisateur(id);
       p.setUtilisateur(utilisateur);
        Document doc = idoc.findBydctId(document);
       p.setDocument(doc);
       p.setStatusDocument(StatusDocument.CONSULTER);
       p.setStatus(false);
       return iPrivilegeSignerServce.save(p);
    } 
    
    @PostMapping("privilegeApprouver/create")
    public PrivilegeSigner approuver(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("document") Long  document){

    	PrivilegeSigner p = new PrivilegeSigner();
    
       Utilisateur utilisateur= iutilisateur.getUtilisateur(id);
       p.setUtilisateur(utilisateur);
        Document doc = idoc.findBydctId(document);
       p.setDocument(doc);
       p.setStatusDocument(StatusDocument.APPROUVER);
       p.setStatus(false);
       return iPrivilegeSignerServce.save(p);
    } 
    
    /**
     * liste des document à signer par utilisateur.
     * @param iduser
     * @return 
     */
    @GetMapping("privilegeSigner/list/{iduser}")
    public ServeurResponse getAllDocumentByUser(@PathVariable Long iduser) {
        Iterable<Document> document = iPrivilegeSignerServce.listDocumentSigner(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    /**
     * liste des document à consulter par utilisateur.
     * @param iduser
     * @return 
     */
    
    @GetMapping("privilegeConsulter/list/{iduser}")
    public ServeurResponse getAllDocumentConsulter(@PathVariable Long iduser) {
        Iterable<Document> document = iPrivilegeSignerServce.listDocumentConsulter(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    /**
     * liste des document à certifier par utilisateur.
     * @param iduser
     * @return 
     */
    @GetMapping("privilegeCertifier/list/{iduser}")
    public ServeurResponse getAllDocumentCertifier(@PathVariable Long iduser) {
        Iterable<Document> document = iPrivilegeSignerServce.listDocumentCertifier(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    /**
     * liste des document à approuver par utilisateur.
     * @param iduser
     * @return 
     */
    @GetMapping("privilegeApprouver/list/{iduser}")
    public ServeurResponse getAllDocumentApprouver(@PathVariable Long iduser) {
        Iterable<Document> document = iPrivilegeSignerServce.listDocumentApprouver(iduser);
        ServeurResponse response = new ServeurResponse();
        if (document == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(document);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
}
