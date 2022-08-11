package sn.gainde2000.pi.ged.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import sn.gainde2000.pi.core.entity.Utilisateur;



@Entity
@Table(name = "tr_privilege_signer", schema = "")
public class PrivilegeSigner {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pris_id")
    private Long prisId;
  
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pris_uti_id", referencedColumnName = "uti_id")    
    private Utilisateur utilisateur;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pris_dct_id", referencedColumnName = "dct_id")    
    private Document document;
    private StatusDocument statusDocument;
    
    private boolean status;
    
	public Long getPrisId() {
		return prisId;
	}

	public void setPrisId(Long prisId) {
		this.prisId = prisId;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public PrivilegeSigner(Utilisateur utilisateur, Document document, StatusDocument statusDocument, boolean status) {
		super();
		this.utilisateur = utilisateur;
		this.document = document;
		this.statusDocument = statusDocument;
		this.status = status;
	}

	public PrivilegeSigner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatusDocument getStatusDocument() {
		return statusDocument;
	}

	public void setStatusDocument(StatusDocument statusDocument) {
		this.statusDocument = statusDocument;
	}

    
    
}
