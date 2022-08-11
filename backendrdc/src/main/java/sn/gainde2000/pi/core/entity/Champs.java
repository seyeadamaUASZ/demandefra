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
@Table(name = "td_champs", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Champs.findAll", query = "SELECT c FROM Champs c")})
public class Champs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "chp_id")
    private Long chpId;
    @Size(max = 255)
    @Column(name = "chp_class")
    private String chpClass;
    @Size(max = 255)
    @Column(name = "chp_label")
    private String chpLabel;
    @Size(max = 255)
    @Column(name = "chp_nom")
    private String chpNom;
    @Column(name = "chp_obligatoire")
    private Boolean chpObligatoire;
    @Size(max = 255)
    @Column(name = "chp_placeholder")
    private String chpPlaceholder;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "chp_taille")
    private Double chpTaille;
    @Size(max = 255)
    @Column(name = "chp_type")
    private String chpType;
    @Column(name = "chp_regex")
    private String chpRegex;
    @Column(name = "chp_icon")
    private String chpIcon;
    @Column(name = "ch_frm_id")
    private Long chFrmId;
    @Column(name = "chp_champs")
    private String chpChamps;
    public Champs() {
    }

    public Champs(Long chpId) {
        this.chpId = chpId;
    }

    public Long getChpId() {
        return chpId;
    }

    public void setChpId(Long chpId) {
        this.chpId = chpId;
    }

    public String getChpClass() {
        return chpClass;
    }

    public void setChpClass(String chpClass) {
        this.chpClass = chpClass;
    }

    public String getChpLabel() {
        return chpLabel;
    }

    public void setChpLabel(String chpLabel) {
        this.chpLabel = chpLabel;
    }

    public String getChpNom() {
        return chpNom;
    }

    public void setChpNom(String chpNom) {
        this.chpNom = chpNom;
    }

    public Boolean getChpObligatoire() {
        return chpObligatoire;
    }

    public void setChpObligatoire(Boolean chpObligatoire) {
        this.chpObligatoire = chpObligatoire;
    }

    public String getChpPlaceholder() {
        return chpPlaceholder;
    }

    public void setChpPlaceholder(String chpPlaceholder) {
        this.chpPlaceholder = chpPlaceholder;
    }

    public Double getChpTaille() {
        return chpTaille;
    }

    public void setChpTaille(Double chpTaille) {
        this.chpTaille = chpTaille;
    }

    public String getChpType() {
        return chpType;
    }

    public void setChpType(String chpType) {
        this.chpType = chpType;
    }

    public Long getChFrmId() {
        return chFrmId;
    }

    public void setChFrmId(Long chFrmId) {
        this.chFrmId = chFrmId;
    }

    public String getChpRegex() {
        return chpRegex;
    }

    public void setChpRegex(String chpRegex) {
        this.chpRegex = chpRegex;
    }

    public String getChpIcon() {
        return chpIcon;
    }

    public void setChpIcon(String chpIcon) {
        this.chpIcon = chpIcon;
    }

    public String getChpChamps() {
        return chpChamps;
    }

    public void setChpChamps(String chpChamps) {
        this.chpChamps = chpChamps;
    }





    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chpId != null ? chpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Champs)) {
            return false;
        }
        Champs other = (Champs) object;
        if ((this.chpId == null && other.chpId != null) || (this.chpId != null && !this.chpId.equals(other.chpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Champs[ chpId=" + chpId + " ]";
    }

}
