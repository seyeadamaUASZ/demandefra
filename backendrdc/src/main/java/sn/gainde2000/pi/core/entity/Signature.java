package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Signature")
public class Signature implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;
	private Long idSignataire;
	private String cleSignataire;
	private String codePin;
	@OneToOne()
	private Utilisateur utilisateur;
	
	public Signature() {
		super();
	}

	public Signature(@NotNull Long id, Long idSignataire, String cleSignataire, String codePin,
			Utilisateur utilisateur) {
		super();
		this.id = id;
		this.idSignataire = idSignataire;
		this.cleSignataire = cleSignataire;
		this.codePin = codePin;
		this.utilisateur = utilisateur;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSignataire() {
		return idSignataire;
	}

	public void setIdSignataire(Long idSignataire) {
		this.idSignataire = idSignataire;
	}

	public String getCleSignataire() {
		return cleSignataire;
	}

	public void setCleSignataire(String cleSignataire) {
		this.cleSignataire = cleSignataire;
	}

	public String getCodePin() {
		return codePin;
	}

	public void setCode_pin(String codePin) {
		this.codePin = codePin;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
