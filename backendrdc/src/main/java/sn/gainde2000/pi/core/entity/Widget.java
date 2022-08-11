package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "tp_widget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Widget.findAll", query = "SELECT wdg FROM Widget wdg")})
public class Widget implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wdg_id")
    private Long wdgId;
    @Size(max = 255)
    @Column(name = "wdg_nom")
    private String wdgNom;
    @Size(max = 255)
    @Column(name = "wdg_Code")
    private String wdgCode;
    @Size(max = 255)
    @Column(name = "wdg_description")
    private String wdgDescription;
    @Column(name = "wdg_ordre")
    private String wdgOrdre;
    private String wdgType;
    private int wdgLongueur;

    public Widget() {
    }

    public Widget(Long wdgId) {
        this.wdgId = wdgId;
    }

    public Long getWdgId() {
        return wdgId;
    }

    public void setWdgId(Long wdgId) {
        this.wdgId = wdgId;
    }

    public String getWdgNom() {
        return wdgNom;
    }

    public void setWdgNom(String wdgNom) {
        this.wdgNom = wdgNom;
    }

    public String getWdgCode() {
        return wdgCode;
    }

    public void setWdgCode(String wdgCode) {
        this.wdgCode = wdgCode;
    }

    public String getWdgDescription() {
        return wdgDescription;
    }

    public void setWdgDescription(String wdgDescription) {
        this.wdgDescription = wdgDescription;
    }

    public String getWdgOrdre() {
        return wdgOrdre;
    }

    public void setWdgOrdre(String wdgOrdre) {
        this.wdgOrdre = wdgOrdre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wdgId != null ? wdgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Widget)) {
            return false;
        }
        Widget other = (Widget) object;
        if ((this.wdgId == null && other.wdgId != null) || (this.wdgId != null && !this.wdgId.equals(other.wdgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Widget[ wdgId=" + wdgId + " ]";
    }

    /**
     * @return the wdgType
     */
    public String getWdgType() {
        return wdgType;
    }

    /**
     * @param wdgType the wdgType to set
     */
    public void setWdgType(String wdgType) {
        this.wdgType = wdgType;
    }

    /**
     * @return the wdgLongueur
     */
    public int getWdgLongueur() {
        return wdgLongueur;
    }

    /**
     * @param wdgLongueur the wdgLongueur to set
     */
    public void setWdgLongueur(int wdgLongueur) {
        this.wdgLongueur = wdgLongueur;
    }

}
