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
@Table(name = "tr_signature_document", schema = "")
public class SignatureDocument {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pris_id")
    private Long sigdId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sigd_uti_id", referencedColumnName = "uti_id")    
    private Utilisateur utilisateur;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sigd_dct_id", referencedColumnName = "dct_id")    
    private Document document;

	public Long getSigdId() {
		return sigdId;
	}

	public void setSigdId(Long sigdId) {
		this.sigdId = sigdId;
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

	public SignatureDocument(Utilisateur utilisateur, Document document) {
		super();
		this.utilisateur = utilisateur;
		this.document = document;
	}

	public SignatureDocument() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
