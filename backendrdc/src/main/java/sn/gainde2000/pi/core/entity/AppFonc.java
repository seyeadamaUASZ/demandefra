/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "tr_app_fonc")
@XmlRootElement

public class AppFonc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_appli_fonc")
    private Long idAppliFonc;
   /* @Column(name = "appli_fonc_app_id")
    private Long appliFoncAppId;  
    @Column(name = "appli_fonc_fon_id")
    private Long appliFoncFonId;*/
    @Column(name = "appli_fon_is_active")
    private Boolean appliFonIsActive;
    @ManyToOne(targetEntity = Fonctionnalite.class)
    @JoinColumn(name = "appli_fonc_fon_id", referencedColumnName = "fon_id", insertable = true, updatable = true)
    private Fonctionnalite appliFoncFonId;
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn(name = "appli_fonc_app_id", referencedColumnName = "app_id", insertable = true, updatable = true)
    private Application appliFoncAppId;

    public AppFonc() {
    }

    public AppFonc(Long idAppliFonc) {
        this.idAppliFonc = idAppliFonc;
    }

    public Long getIdAppliFonc() {
        return idAppliFonc;
    }

    public void setIdAppliFonc(Long idAppliFonc) {
        this.idAppliFonc = idAppliFonc;
    }

    public Boolean getAppliFonIsActive() {
        return appliFonIsActive;
    }

    public void setAppliFonIsActive(Boolean appliFonIsActive) {
        this.appliFonIsActive = appliFonIsActive;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAppliFonc != null ? idAppliFonc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppFonc)) {
            return false;
        }
        AppFonc other = (AppFonc) object;
        if ((this.idAppliFonc == null && other.idAppliFonc != null) || (this.idAppliFonc != null && !this.idAppliFonc.equals(other.idAppliFonc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.AppFonc[ idAppliFonc=" + idAppliFonc + " ]";
    }

    /**
     * @return the appliFoncFonId
     */
    public Fonctionnalite getAppliFoncFonId() {
        return appliFoncFonId;
    }

    /**
     * @param appliFoncFonId the appliFoncFonId to set
     */
    public void setAppliFoncFonId(Fonctionnalite appliFoncFonId) {
        this.appliFoncFonId = appliFoncFonId;
    }

    /**
     * @return the appliFoncAppId
     */
    public Application getAppliFoncAppId() {
        return appliFoncAppId;
    }

    /**
     * @param appliFoncAppId the appliFoncAppId to set
     */
    public void setAppliFoncAppId(Application appliFoncAppId) {
        this.appliFoncAppId = appliFoncAppId;
    }

     /**
      * @return the appliFoncAppId
      */
     /*public Long getAppliFoncAppId() {
          return appliFoncAppId;
     }*/

     /**
      * @param appliFoncAppId the appliFoncAppId to set
      */
    /* public void setAppliFoncAppId(Long appliFoncAppId) {
          this.appliFoncAppId = appliFoncAppId;
     }*/

     /**
      * @return the appliFoncFonId
      */
    /* public Long getAppliFoncFonId() {
          return appliFoncFonId;
     }*/

     /**
      * @param appliFoncFonId the appliFoncFonId to set
      */
    /* public void setAppliFoncFonId(Long appliFoncFonId) {
          this.appliFoncFonId = appliFoncFonId;
     }*/
     
    
    
}
