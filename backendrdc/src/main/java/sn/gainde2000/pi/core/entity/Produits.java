
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Produits")
public class Produits implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;
	private Long status;
	@Column(name = "nature")
	private String nature;
	@Column(name = "marque")
	private String marque;
	@Column(name = "contenance")
	private String contenance;
	@Column(name = "typeemballage")
	private String typeemballage;
	private String descriptionEtiquette;
	@Column(name="etiquetteouemballage", columnDefinition = "MEDIUMBLOB")
	@Lob
	private String etiquetteouemballage;
	private String autFra;
	@ManyToOne	
	@JoinColumn(name="demandeautfra")
	private DemandeAutFra demandeautfra;	
	@OneToOne()
	private Categorie categorie;
	
	public Produits() {
		super();
	}
	
	public Produits(@NotNull Long id, Long status, String nature, String marque, String contenance,
			String typeemballage, String descriptionEtiquette, String etiquetteouemballage, String autFra,
			DemandeAutFra demandeautfra, Categorie categorie) {
		super();
		this.id = id;
		this.status = status;
		this.nature = nature;
		this.marque = marque;
		this.contenance = contenance;
		this.typeemballage = typeemballage;
		this.descriptionEtiquette = descriptionEtiquette;
		this.etiquetteouemballage = etiquetteouemballage;
		this.autFra = autFra;
		this.demandeautfra = demandeautfra;
		this.categorie = categorie;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getContenance() {
		return contenance;
	}

	public void setContenance(String contenance) {
		this.contenance = contenance;
	}

	public String getTypeemballage() {
		return typeemballage;
	}

	public void setTypeemballage(String typeemballage) {
		this.typeemballage = typeemballage;
	}

	public String getDescriptionEtiquette() {
		return descriptionEtiquette;
	}

	public void setDescriptionEtiquette(String descriptionEtiquette) {
		this.descriptionEtiquette = descriptionEtiquette;
	}

	public String getEtiquetteouemballage() {
		return etiquetteouemballage;
	}

	public void setEtiquetteouemballage(String etiquetteouemballage) {
		this.etiquetteouemballage = etiquetteouemballage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getAutFra() {
		return autFra;
	}

	public void setAutFra(String autFra) {
		this.autFra = autFra;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	@JsonIgnore
	public DemandeAutFra getDemandeautfra() {
		return demandeautfra;
	}

	public void setDemandeautfra(DemandeAutFra demandeautfra) {
		this.demandeautfra = demandeautfra;
	}	
}