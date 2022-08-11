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
@Table(name = "tp_theme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theme.findAll", query = "SELECT t FROM Theme t")
   })
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "thm_id")
    private Long thmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "thm_primary")
    private String thmPrimary;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "thm_accent")
    private String thmAccent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "thm_name")
    private String thmName;
    @Column(name = "thm_isDark")
    private Boolean thmisDark;
    @Column(name = "thm_isDefault")
    private Boolean thmisDefault;
    @JsonIgnore
    @OneToMany(mappedBy = "param_thm_id")
    private Set<Parametre> parametre;

    public Theme() {
    }

    public Theme(Long thmId) {
        this.thmId = thmId;
    }

    public Theme(Long thmId, String thmPrimary, String thmAccent, String thmName) {
        this.thmId = thmId;
        this.thmPrimary = thmPrimary;
        this.thmAccent = thmAccent;
        this.thmName = thmName;
    }

    public Long getThmId() {
        return thmId;
    }

    public void setThmId(Long thmId) {
        this.thmId = thmId;
    }

    public String getThmPrimary() {
        return thmPrimary;
    }

    public void setThmPrimary(String thmPrimary) {
        this.thmPrimary = thmPrimary;
    }

    public String getThmAccent() {
        return thmAccent;
    }

    public void setThmAccent(String thmAccent) {
        this.thmAccent = thmAccent;
    }

    public String getThmName() {
        return thmName;
    }

    public void setThmName(String thmName) {
        this.thmName = thmName;
    }

    public Boolean getThmisDark() {
        return thmisDark;
    }

    public void setThmisDark(Boolean thmisDark) {
        this.thmisDark = thmisDark;
    }

    public Boolean getThmisDefault() {
        return thmisDefault;
    }

    public void setThmisDefault(Boolean thmisDefault) {
        this.thmisDefault = thmisDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (thmId != null ? thmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.thmId == null && other.thmId != null) || (this.thmId != null && !this.thmId.equals(other.thmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.TpTheme[ thmId=" + thmId + " ]";
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
