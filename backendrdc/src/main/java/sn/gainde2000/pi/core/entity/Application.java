/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "td_application", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a")})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_id")
    private Long appId;
    @Size(max = 255)
    @Column(name = "app_adresse")
    private String appAdresse;
    @Column(name = "app_date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appDateCreation;
    @Size(max = 255)
    @Column(name = "app_email")
    private String appEmail;
    @Size(max = 255)
    @Column(name = "app_ninea")
    private String appNinea;
    @Size(max = 255)
    @Column(name = "app_nom")
    private String appNom;
    @Size(max = 255)
    @Column(name = "app_nom_entreprise")
    private String appNomEntreprise;
    @Size(max = 255)
    @Column(name = "app_secteur")
    private String appSecteur;
    @Size(max = 255)
    @Column(name = "app_telephone_fixe")
    private String appTelephoneFixe;
    @Size(max = 255)
    @Column(name = "app_telephone_mobile")
    private String appTelephoneMobile;

    @Column(name = "app_lien")
    private String appLien;
    @ManyToOne
    @JoinColumn(name = "parametre", referencedColumnName = "param_id", insertable = true, updatable = true)
    private Parametre param_app_id;

    /*@ManyToOne(targetEntity = Utilisateur.class)
    @JoinColumn(name = "app_uti_id", referencedColumnName = "uti_id", insertable = true, updatable = true)
    private Utilisateur app_uti_Id;*/
    @Column(name = "app_etape_creation")
    private Integer appEtapeCreation;
    @JsonIgnore
    @OneToMany(mappedBy = "appliFoncAppId")
    private Set<AppFonc> application;
    
    // status to control publication and download
    @Enumerated
    @Column(name="app_status",columnDefinition = "int default 0")
    private ApplicationStatus appStatus = ApplicationStatus.MODELISATION;
    @Column(name="app_url")
    private String appUrl;
    //used to save image for published app
    @Column(name = "app_img", columnDefinition = "MEDIUMBLOB")
    private byte[] appImg;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date appDatePub;
    
    public Application() {
    }

    public Application(Long appId) {
        this.appId = appId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppAdresse() {
        return appAdresse;
    }

    public void setAppAdresse(String appAdresse) {
        this.appAdresse = appAdresse;
    }

    public Date getAppDateCreation() {
        return appDateCreation;
    }

    public void setAppDateCreation(Date appDateCreation) {
        this.appDateCreation = appDateCreation;
    }

    public String getAppEmail() {
        return appEmail;
    }

    public void setAppEmail(String appEmail) {
        this.appEmail = appEmail;
    }

    public String getAppNinea() {
        return appNinea;
    }

    public void setAppNinea(String appNinea) {
        this.appNinea = appNinea;
    }

    public String getAppNom() {
        return appNom;
    }

    public void setAppNom(String appNom) {
        this.appNom = appNom;
    }

    public String getAppNomEntreprise() {
        return appNomEntreprise;
    }

    public void setAppNomEntreprise(String appNomEntreprise) {
        this.appNomEntreprise = appNomEntreprise;
    }

    public String getAppSecteur() {
        return appSecteur;
    }

    public void setAppSecteur(String appSecteur) {
        this.appSecteur = appSecteur;
    }

    public String getAppTelephoneFixe() {
        return appTelephoneFixe;
    }

    public void setAppTelephoneFixe(String appTelephoneFixe) {
        this.appTelephoneFixe = appTelephoneFixe;
    }

    public String getAppTelephoneMobile() {
        return appTelephoneMobile;
    }

    public void setAppTelephoneMobile(String appTelephoneMobile) {
        this.appTelephoneMobile = appTelephoneMobile;
    }

    public String getAppLien() {
        return appLien;
    }

    public void setAppLien(String appLien) {
        this.appLien = appLien;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appId != null ? appId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.appId == null && other.appId != null) || (this.appId != null && !this.appId.equals(other.appId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Application[ appId=" + appId + " ]";
    }

    /**
     * @return the param_app_id
     */
    public Parametre getParam_app_id() {
        return param_app_id;
    }

    /**
     * @param param_app_id the param_app_id to set
     */
    public void setParam_app_id(Parametre param_app_id) {
        this.param_app_id = param_app_id;
    }

    public Integer getAppEtapeCreation() {
        return appEtapeCreation;
    }

    public void setAppEtapeCreation(Integer appEtapeCreation) {
        this.appEtapeCreation = appEtapeCreation;
    }

    /**
     * @return the application
     */
    public Set<AppFonc> getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(Set<AppFonc> application) {
        this.application = application;
    }

	public ApplicationStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(ApplicationStatus appStatus) {
		this.appStatus = appStatus;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public byte[] getAppImg() {
		try {
			return decompressBytes(appImg);
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void setAppImg(byte[] appImg) {
		this.appImg = compressBytes(appImg);
	}

	
	public Date getAppDatePub() {
		return appDatePub;
	}

	public void setAppDatePub(Date appDatePub) {
		this.appDatePub = appDatePub;
	}

	/**
     * Compresse une donnée byte pour réduire la taille
     *
     * @param data
     * @return static byte[]
     */
    public static byte[] compressBytes(byte[] data) {
        if(data==null)return null;
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }

    /**
     * decompressé la taille d'une donnée avant de le retourner sur application
     * angular
     *
     * @param data
     * @return static byte[]
     * @throws DataFormatException
     */
    public static byte[] decompressBytes(byte[] data) throws DataFormatException {
    	if(data==null)return null;
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        }
        return outputStream.toByteArray();
    }
    
    
}
