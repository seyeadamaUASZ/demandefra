/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "td_utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "uti_id")
    private Long utiId;
    @Size(max = 50)
    @Column(name = "uti_username")
    private String utiUsername;
    @Size(max = 255)
    @Column(name = "uti_adresse")
    private String utiAdresse;
    @Size(max = 255)
    @Column(name = "uti_couleur")
    private String utiCouleur;
    @Column(name = "uti_date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date utiDateCreation;
    @Column(name = "uti_date_modification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date utiDateModification;
    @Size(max = 255)
    @Column(name = "uti_email")
    private String utiEmail;
    @Size(max = 255)
    @Column(name = "uti_nom")
    private String utiNom;
    @JsonIgnore
    @Size(max = 255)
    @Column(name = "uti_password")
    private String utiPassword;
    @Size(max = 255)
    @Column(name = "uti_prenom")
    private String utiPrenom;
    @Size(max = 255)
    @Column(name = "uti_telephone")
    private String utiTelephone;
    @JsonIgnore
    @Column(name = "uti_logo", columnDefinition = "MEDIUMBLOB")
    private byte[] utiLogo;
    @Column(name = "uti_app_id")
    private BigInteger utiAppId;

    @Column(name = "uti_actived")
    private boolean utiActived;
    @Column(name = "uti_premier_connect")
    private boolean utiPremierConnect;
    // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Profil.class)
    @JoinColumn(name = "uti_pro_id", referencedColumnName = "pro_id", insertable = true, updatable = true)
    private Profil uti_pro_id;
    // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Theme.class)
    @JoinColumn(name = "uti_thm_id", referencedColumnName = "thm_id", insertable = true, updatable = true)
    private Theme uti_thm_id;
    // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Langue.class)
    @JoinColumn(name = "uti_lng_id", referencedColumnName = "lng_id", insertable = true, updatable = true)
    private Langue uti_lng_id;
    @Column(name = "uti_temps_session")
    private Date uti_temps_session;
    @Column(name = "uti_pwd_datemodif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date utiPwdDateModif;
    @Column(name = "uti_idempreinte")
    private int idEmpriente;
    @ManyToOne
    @JoinColumn(name="antenne")
    private AntenneRegionaleDepartementale antenne;

    public Utilisateur() {
    }

    public Utilisateur(Long utiId) {
        this.utiId = utiId;
    }

    public Long getUtiId() {
        return utiId;
    }

    public void setUtiId(Long utiId) {
        this.utiId = utiId;
    }

    public String getUtiAdresse() {
        return utiAdresse;
    }

    public void setUtiAdresse(String utiAdresse) {
        this.utiAdresse = utiAdresse;
    }

    public String getUtiCouleur() {
        return utiCouleur;
    }

    public void setUtiCouleur(String utiCouleur) {
        this.utiCouleur = utiCouleur;
    }

    public Date getUtiDateCreation() {
        return utiDateCreation;
    }

    public void setUtiDateCreation(Date utiDateCreation) {
        this.utiDateCreation = utiDateCreation;
    }

    public Date getUtiDateModification() {
        return utiDateModification;
    }

    public void setUtiDateModification(Date utiDateModification) {
        this.utiDateModification = utiDateModification;
    }

    public String getUtiEmail() {
        return utiEmail;
    }

    public void setUtiEmail(String utiEmail) {
        this.utiEmail = utiEmail;
    }

    public String getUtiNom() {
        return utiNom;
    }

    public void setUtiNom(String utiNom) {
        this.utiNom = utiNom;
    }

    public String getUtiPassword() {
        return utiPassword;
    }

    public void setUtiPassword(String utiPassword) {
        this.utiPassword = utiPassword;
    }

    public String getUtiPrenom() {
        return utiPrenom;
    }

    public void setUtiPrenom(String utiPrenom) {
        this.utiPrenom = utiPrenom;
    }

    public String getUtiTelephone() {
        return utiTelephone;
    }

    public void setUtiTelephone(String utiTelephone) {
        this.utiTelephone = utiTelephone;
    }

    public byte[] getUtiLogo() {
        return utiLogo;
    }

    public void setUtiLogo(byte[] utiLogo) {
        this.utiLogo = utiLogo;
    }

    public BigInteger getUtiAppId() {
        return utiAppId;
    }

    public void setUtiAppId(BigInteger utiAppId) {
        this.utiAppId = utiAppId;
    }

    public boolean isUtiActived() {
        return utiActived;
    }

    public void setUtiActived(boolean utiActived) {
        this.utiActived = utiActived;
    }

    public boolean isUtiPremierConnect() {
        return utiPremierConnect;
    }

    public void setUtiPremierConnect(boolean utiPremierConnect) {
        this.utiPremierConnect = utiPremierConnect;
    }

    public Date getUtiPwdDateModif() {
        return utiPwdDateModif;
    }

    public void setUtiPwdDateModif(Date utiPwdDateModif) {
        this.utiPwdDateModif = utiPwdDateModif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utiId != null ? utiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.utiId == null && other.utiId != null) || (this.utiId != null && !this.utiId.equals(other.utiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Utilisateur[ utiId=" + utiId + " ]";
    }

    /**
     * @return the utiUsername
     */
    public String getUtiUsername() {
        return utiUsername;
    }

    /**
     * @param utiUsername the utiUsername to set
     */
    public void setUtiUsername(String utiUsername) {
        this.utiUsername = utiUsername;
    }

    /**
     * @return the uti_pro_id
     */
    public Profil getUti_pro_id() {
        return uti_pro_id;
    }

    /**
     * @param uti_pro_id the uti_pro_id to set
     */
    public void setUti_pro_id(Profil uti_pro_id) {
        this.uti_pro_id = uti_pro_id;
    }

    public void setId(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the uti_thm_id
     */
    public Theme getUti_thm_id() {
        return uti_thm_id;
    }

    /**
     * @param uti_thm_id the uti_thm_id to set
     */
    public void setUti_thm_id(Theme uti_thm_id) {
        this.uti_thm_id = uti_thm_id;
    }

    /**
     * @return the uti_lng_id
     */
    public Langue getUti_lng_id() {
        return uti_lng_id;
    }

    /**
     * @param uti_lng_id the uti_lng_id to set
     */
    public void setUti_lng_id(Langue uti_lng_id) {
        this.uti_lng_id = uti_lng_id;
    }

    /**
     * @return the uti_temps_session
     */
    public Date getUti_temps_session() {
        return uti_temps_session;
    }

    /**
     * @param uti_temps_session the uti_temps_session to set
     */
    public void setUti_temps_session(Date uti_temps_session) {
        this.uti_temps_session = uti_temps_session;
    }

    public int getIdEmpriente() {
        return idEmpriente;
    }

    public void setIdEmpriente(int idEmpriente) {
        this.idEmpriente = idEmpriente;
    }

	public AntenneRegionaleDepartementale getAntenne() {
		return antenne;
	}

	public void setAntenne(AntenneRegionaleDepartementale antenne) {
		this.antenne = antenne;
	}

    
}
