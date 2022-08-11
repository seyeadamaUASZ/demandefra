/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tp_fonctionnalite", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fonctionnalite.findAll", query = "SELECT f FROM Fonctionnalite f")})
public class Fonctionnalite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "fon_id")
    private Long fonId;
    @Column(name = "fon_code")
    private String fonCode;
    @Size(max = 255)
    @Column(name = "fon_description")
    private String fonDescription;
    @Size(max = 255)
    @Column(name = "fon_nom")
    private String fonNom;
    @Column(name = "fon_men_id")
    private BigInteger fonMenId;
    @Column(name = "fon_mod_id")
    private Long fonModId;
    @JsonIgnore
    @OneToMany(mappedBy = "appliFoncFonId")
    private Set<AppFonc> fonctionnalite;


    public Fonctionnalite() {
    }

    public Fonctionnalite(Long fonId) {
        this.fonId = fonId;
    }
    

    public Long getFonId() {
        return fonId;
    }

    public void setFonId(Long fonId) {
        this.fonId = fonId;
    }

    public String getFonCode() {
        return fonCode;
    }

    public void setFonCode(String fonCode) {
        this.fonCode = fonCode;
    }

    public String getFonDescription() {
        return fonDescription;
    }

    public void setFonDescription(String fonDescription) {
        this.fonDescription = fonDescription;
    }

    public String getFonNom() {
        return fonNom;
    }

    public void setFonNom(String fonNom) {
        this.fonNom = fonNom;
    }

    public BigInteger getFonMenId() {
        return fonMenId;
    }

    public void setFonMenId(BigInteger fonMenId) {
        this.fonMenId = fonMenId;
    }


    public Long getFonModId() {
        return fonModId;
    }

    public void setFonModId(Long fonModId) {
        this.fonModId = fonModId;
    }



 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fonId != null ? fonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fonctionnalite)) {
            return false;
        }
        Fonctionnalite other = (Fonctionnalite) object;
        if ((this.fonId == null && other.fonId != null) || (this.fonId != null && !this.fonId.equals(other.fonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fonctionnalite{" + "fonId=" + fonId + ", fonModId=" + fonModId + '}';
    }





    public void isFonActived(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the fonctionnalite
     */
    public Set<AppFonc> getFonctionnalite() {
        return fonctionnalite;
    }

    /**
     * @param fonctionnalite the fonctionnalite to set
     */
    public void setFonctionnalite(Set<AppFonc> fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

}
