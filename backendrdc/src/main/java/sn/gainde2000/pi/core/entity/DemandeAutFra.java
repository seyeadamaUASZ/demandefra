
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
public class DemandeAutFra implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;
	private Long status;
	private Long poOwner;
	private Long owner;
	private Long idLink;
	private String raisonsociale;
	private String adresseEntreprise;
	private String emailEntreprise;
	private String ninea;
	private String registrecommerce;
	private String statutJuridique;
	private String telephoneEntreprise;
	private String prenomResponsable;
	private String nomResponsable;
	private String civiliteResponsable;
	private String adresseResponsable;
	private String telephoneResponsable;
	private String emailResponsable;	
	@Column(name = "certificatanalysesproduit", columnDefinition = "MEDIUMBLOB")
	private byte[] certificatanalysesproduit;
	@Column(name = "registrecommerceetcreditmobilier", columnDefinition = "MEDIUMBLOB")
	private byte[] registrecommerceetcreditmobilier;
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] juridique;
	@Column(name = "processusfabrication", columnDefinition = "MEDIUMBLOB")
	private byte[] processusfabrication;
	@Column(name = "copieninea", columnDefinition = "MEDIUMBLOB")
	private byte[] copieninea;
	@Column(name = "cnipasseport", columnDefinition = "MEDIUMBLOB")
	private byte[] cnipasseport;
	
	private boolean paiementManuel=false;
	@Column(name = "recu_paiment", columnDefinition = "MEDIUMBLOB")
	private byte[] recuPaiment;
	private String recuFileType;
	
	@Temporal(TemporalType.DATE)
    private Date dateSoumission;
	@Column(name = "numdemande", length=254, unique=true, nullable=false)
	private String numdemande;
	@OneToMany(mappedBy="demandeautfra")
	private List<Produits> produits;
	private String objetDemande;
	@OneToOne()
	private AntenneRegionaleDepartementale antenneRegionaleDepartementale;
	@OneToOne()
	private Region region;
	private Long numeroFacture;

	public DemandeAutFra() {
		super();
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

	public Long getPoOwner() {
		return poOwner;
	}

	public void setPoOwner(Long poOwner) {
		this.poOwner = poOwner;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Long getIdLink() {
		return idLink;
	}

	public void setIdLink(Long idLink) {
		this.idLink = idLink;
	}

	public String getRaisonsociale() {
		return raisonsociale;
	}

	public void setRaisonsociale(String raisonsociale) {
		this.raisonsociale = raisonsociale;
	}

	public String getAdresseEntreprise() {
		return adresseEntreprise;
	}

	public void setAdresseEntreprise(String adresseEntreprise) {
		this.adresseEntreprise = adresseEntreprise;
	}

	public String getEmailEntreprise() {
		return emailEntreprise;
	}

	public void setEmailEntreprise(String emailEntreprise) {
		this.emailEntreprise = emailEntreprise;
	}

	public String getNinea() {
		return ninea;
	}

	public void setNinea(String ninea) {
		this.ninea = ninea;
	}

	public String getRegistrecommerce() {
		return registrecommerce;
	}

	public void setRegistrecommerce(String registrecommerce) {
		this.registrecommerce = registrecommerce;
	}

	public String getStatutJuridique() {
		return statutJuridique;
	}

	public void setStatutJuridique(String statutJuridique) {
		this.statutJuridique = statutJuridique;
	}

	public String getTelephoneEntreprise() {
		return telephoneEntreprise;
	}

	public void setTelephoneEntreprise(String telephoneEntreprise) {
		this.telephoneEntreprise = telephoneEntreprise;
	}

	public String getPrenomResponsable() {
		return prenomResponsable;
	}

	public void setPrenomResponsable(String prenomResponsable) {
		this.prenomResponsable = prenomResponsable;
	}

	public String getNomResponsable() {
		return nomResponsable;
	}

	public void setNomResponsable(String nomResponsable) {
		this.nomResponsable = nomResponsable;
	}

	public String getCiviliteResponsable() {
		return civiliteResponsable;
	}

	public void setCiviliteResponsable(String civiliteResponsable) {
		this.civiliteResponsable = civiliteResponsable;
	}

	public String getAdresseResponsable() {
		return adresseResponsable;
	}

	public void setAdresseResponsable(String adresseResponsable) {
		this.adresseResponsable = adresseResponsable;
	}

	public String getTelephoneResponsable() {
		return telephoneResponsable;
	}

	public void setTelephoneResponsable(String telephoneResponsable) {
		this.telephoneResponsable = telephoneResponsable;
	}

	public String getEmailResponsable() {
		return emailResponsable;
	}

	public void setEmailResponsable(String emailResponsable) {
		this.emailResponsable = emailResponsable;
	}

	public byte[] getCertificatanalysesproduit() {
		return certificatanalysesproduit;
	}

	public void setCertificatanalysesproduit(byte[] certificatanalysesproduit) {
		this.certificatanalysesproduit = certificatanalysesproduit;
	}

	public byte[] getRegistrecommerceetcreditmobilier() {
		return registrecommerceetcreditmobilier;
	}

	public void setRegistrecommerceetcreditmobilier(byte[] registrecommerceetcreditmobilier) {
		this.registrecommerceetcreditmobilier = registrecommerceetcreditmobilier;
	}

	public byte[] getJuridique() {
		return juridique;
	}

	public void setJuridique(byte[] juridique) {
		this.juridique = juridique;
	}

	public byte[] getProcessusfabrication() {
		return processusfabrication;
	}

	public void setProcessusfabrication(byte[] processusfabrication) {
		this.processusfabrication = processusfabrication;
	}

	public byte[] getCopieninea() {
		return copieninea;
	}

	public void setCopieninea(byte[] copieninea) {
		this.copieninea = copieninea;
	}

	public byte[] getCnipasseport() {
		return cnipasseport;
	}

	public void setCnipasseport(byte[] cnipasseport) {
		this.cnipasseport = cnipasseport;
	}

	public Date getDateSoumission() {
		return dateSoumission;
	}

	public void setDateSoumission(Date dateSoumission) {
		this.dateSoumission = dateSoumission;
	}

	public String getNumdemande() {
		return numdemande;
	}

	public void setNumdemande(String numdemande) {
		this.numdemande = numdemande;
	}

	public List<Produits> getProduits() {
		return produits;
	}

	public void setProduits(List<Produits> produits) {
		this.produits = produits;
	}

	public String getObjetDemande() {
		return objetDemande;
	}

	public void setObjetDemande(String objetDemande) {
		this.objetDemande = objetDemande;
	}

	public AntenneRegionaleDepartementale getAntenneRegionaleDepartementale() {
		return antenneRegionaleDepartementale;
	}

	public void setAntenneRegionaleDepartementale(AntenneRegionaleDepartementale antenneRegionaleDepartementale) {
		this.antenneRegionaleDepartementale = antenneRegionaleDepartementale;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Long getNumeroFacture() {
		return numeroFacture;
	}

	public void setNumeroFacture(Long numeroFacture) {
		this.numeroFacture = numeroFacture;
	}

	public boolean isPaiementManuel() {
		return paiementManuel;
	}

	public void setPaiementManuel(boolean paiementManuel) {
		this.paiementManuel = paiementManuel;
	}

	public byte[] getRecuPaiment() {
		return recuPaiment;
	}

	public void setRecuPaiment(byte[] recuPaiment) {
		this.recuPaiment = recuPaiment;
	}

	public String getRecuFileType() {
		return recuFileType;
	}

	public void setRecuFileType(String recuFileType) {
		this.recuFileType = recuFileType;
	}
	
	
}