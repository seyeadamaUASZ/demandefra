package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RapportAnalyse implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "id")
	private Long id;
	private String referentiel;
	@Column(length= 5000, nullable = true)
	private String commentaire;
	@Column(name = "analyse", columnDefinition = "MEDIUMBLOB")
	private byte[] analyse;
	@OneToOne()
	private Produits produits;
	
	public RapportAnalyse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferentiel() {
		return referentiel;
	}

	public void setReferentiel(String referentiel) {
		this.referentiel = referentiel;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	public byte[] getAnalyse() {
		return analyse;
	}

	public void setAnalyse(byte[] analyse) {
		this.analyse = analyse;
	}

	public Produits getProduits() {
		return produits;
	}

	public void setProduits(Produits produits) {
		this.produits = produits;
	}
}
