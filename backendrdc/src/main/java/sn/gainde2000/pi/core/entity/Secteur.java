/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_secteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secteur.findAll", query = "SELECT s FROM Secteur s")
    , @NamedQuery(name = "Secteur.findBySecId", query = "SELECT s FROM Secteur s WHERE s.secId = :secId")
    , @NamedQuery(name = "Secteur.findBySecNom", query = "SELECT s FROM Secteur s WHERE s.secNom = :secNom")
    , @NamedQuery(name = "Secteur.findBySecDescription", query = "SELECT s FROM Secteur s WHERE s.secDescription = :secDescription")})
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sec_id")
    private Long secId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "sec_nom")
    private String secNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "sec_description")
    private String secDescription;

    public Secteur() {
    }

    public Secteur(Long secId) {
        this.secId = secId;
    }

    public Secteur(Long secId, String secNom, String secDescription) {
        this.secId = secId;
        this.secNom = secNom;
        this.secDescription = secDescription;
    }

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public String getSecNom() {
        return secNom;
    }

    public void setSecNom(String secNom) {
        this.secNom = secNom;
    }

    public String getSecDescription() {
        return secDescription;
    }

    public void setSecDescription(String secDescription) {
        this.secDescription = secDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secId != null ? secId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secteur)) {
            return false;
        }
        Secteur other = (Secteur) object;
        if ((this.secId == null && other.secId != null) || (this.secId != null && !this.secId.equals(other.secId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Secteur[ secId=" + secId + " ]";
    }
    
}
