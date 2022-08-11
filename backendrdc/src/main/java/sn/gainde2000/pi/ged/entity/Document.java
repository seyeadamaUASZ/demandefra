/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.ged.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Produits;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Utilisateur;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "td_document", schema = "")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "dct_id")
    private Long dctId;
    @Size(max = 255)
    @Column(name = "dct_titre")
    private String dctTitre;
    @Column(name = "dct_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dctDate;
    @Size(max = 255)
    @Column(name = "dct_auteur")
    private String dctAuteur;
     @Column(name = "dct_type")
    private String dctType;
    @ManyToOne(targetEntity = TypeDocuments.class)
    @JoinColumn(name = "dct_tyd_id", referencedColumnName = "tyd_id", insertable = true, updatable = true)
    private TypeDocuments typeDocuments;
    @Column(name = "dct_blob", columnDefinition = "LONGBLOB")
    private byte[] dctBlob;
    @ManyToOne(targetEntity = Utilisateur.class)
    @JoinColumn(name = "dct_user_id", referencedColumnName = "uti_id", insertable = true, updatable = true)
    private Utilisateur utilisateur; 
    @ManyToOne(targetEntity = DemandeAutFra.class)
    @JoinColumn(name = "dct_dmd_id", referencedColumnName = "id", insertable = true, updatable = true)
    private DemandeAutFra demande;
    @ManyToOne(targetEntity = Produits.class)
    @JoinColumn(name = "dct_produit_id", referencedColumnName = "id", insertable = true, updatable = true)
    private Produits produits;
    
    private StatusDocument statusDocument = null;
   
    private boolean statut;   

    public Document() {
    }

    public Document(Long dctId) {
        this.dctId = dctId;
    }

    public Long getDctId() {
        return dctId;
    }

    public void setDctId(Long dctId) {
        this.dctId = dctId;
    }

    public String getDctTitre() {
        return dctTitre;
    }

    public void setDctTitre(String dctTitre) {
        this.dctTitre = dctTitre;
    }

    public String getDctAuteur() {
        return dctAuteur;
    }

    public void setDctAuteur(String dctAuteur) {
        this.dctAuteur = dctAuteur;
    }

    public TypeDocuments getTypeDocuments() {
        return typeDocuments;
    }

    public void setTypeDocuments(TypeDocuments typeDocuments) {
        this.typeDocuments = typeDocuments;
    }

    public Date getDctDate() {
        return dctDate;
    }

    public void setDctDate(Date dctDate) {
        this.dctDate = dctDate;
    }

    public byte[] getDctBlob() {
        return dctBlob;
    }

    public void setDctBlob(byte[] dctBlob) {
        this.dctBlob = dctBlob;
    }

    public String getDctType() {
        return dctType;
    }

    public void setDctType(String dctType) {
        this.dctType = dctType;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	

	public StatusDocument getStatusDocument() {
		return statusDocument;
	}

	public void setStatusDocument(StatusDocument statusDocument) {
		this.statusDocument = statusDocument;
	}

	@Override
    public String toString() {
        return "Document{" + "dctId=" + dctId + '}';
    }

	public DemandeAutFra getDemande() {
		return demande;
	}

	public void setDemande(DemandeAutFra demande) {
		this.demande = demande;
	}

	public Produits getProduits() {
		return produits;
	}

	public void setProduits(Produits produits) {
		this.produits = produits;
	}
}
