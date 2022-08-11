package sn.gainde2000.pi.core.entity;

public class ProduitSearchResult {
	private Categorie  categorie;
	private Long status;
	private DemandeAutFra demandeautfra;
	private String nature;
   private String etiquetteouemballage;
     private String typeemballage;
    private String marque;
     private String contenance;
     private String descriptionEtiquette;
    private String autFra;
    
	public Categorie getCategorie() {
		return categorie;
	}
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}

	public DemandeAutFra getDemandeautfra() {
		return demandeautfra;
	}

	public void setDemandeautfra(DemandeAutFra demandeautfra) {
		this.demandeautfra = demandeautfra;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getEtiquetteouemballage() {
		return etiquetteouemballage;
	}

	public void setEtiquetteouemballage(String etiquetteouemballage) {
		this.etiquetteouemballage = etiquetteouemballage;
	}

	public String getTypeemballage() {
		return typeemballage;
	}

	public void setTypeemballage(String typeemballage) {
		this.typeemballage = typeemballage;
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

	public String getDescriptionEtiquette() {
		return descriptionEtiquette;
	}

	public void setDescriptionEtiquette(String descriptionEtiquette) {
		this.descriptionEtiquette = descriptionEtiquette;
	}

	public String getAutFra() {
		return autFra;
	}

	public void setAutFra(String autFra) {
		this.autFra = autFra;
	}
	
	
	
	
}
