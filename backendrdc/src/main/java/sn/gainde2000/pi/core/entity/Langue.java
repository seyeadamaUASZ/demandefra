/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_langue",schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Langue.findAll", query = "SELECT l FROM Langue l")})
public class Langue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "lng_id")
    private Long lngId;
    @Size(max = 255)
    @Column(name = "lng_code")
    private String lngCode;
    @Size(max = 255)
    @Column(name = "lng_disposition_text")
    private String lngDispositionText;
    @Size(max = 255)
    @Column(name = "lng_langue")
    private String lngLangue;
    @Size(max=255)
    @Column(name="lng_libelle_complet")
    private String lngLibelleComplet;
    @JsonIgnore
    @OneToMany(mappedBy = "param_lng_id")
    private Set<Parametre> parametre;
    private String lngIcone;
    /*@JsonIgnore
    @OneToMany(mappedBy = "uti_lng_id")
    private Set<Utilisateur> utilisateur;*/

    public Langue() {
    }

    public Langue(Long lngId) {
        this.lngId = lngId;
    }

    public Long getLngId() {
        return lngId;
    }

    public void setLngId(Long lngId) {
        this.lngId = lngId;
    }

    public String getLngCode() {
        return lngCode;
    }

    public void setLngCode(String lngCode) {
        this.lngCode = lngCode;
    }

    public String getLngDispositionText() {
        return lngDispositionText;
    }

    public void setLngDispositionText(String lngDispositionText) {
        this.lngDispositionText = lngDispositionText;
    }

    public String getLngLangue() {
        return lngLangue;
    }

    public void setLngLangue(String lngLangue) {
        this.lngLangue = lngLangue;
    }

    
    public String getLngLibelleComplet() {
		return lngLibelleComplet;
	}

	public void setLngLibelleComplet(String lngLibelleComplet) {
		this.lngLibelleComplet = lngLibelleComplet;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (lngId != null ? lngId.hashCode() : 0);
        return hash;
    }
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Langue)) {
            return false;
        }
        Langue other = (Langue) object;
        if ((this.lngId == null && other.lngId != null) || (this.lngId != null && !this.lngId.equals(other.lngId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Langue[ lngId=" + lngId + " ]";
    }
     /**
      * @return the parametre
      */
     public Set<Parametre> getParametre() {
          return parametre;
     }

     /**
      * @param parametre the parametre to set
      */
     public void setParametre(Set<Parametre> parametre) {
          this.parametre = parametre;
     }

    
    }   
    

