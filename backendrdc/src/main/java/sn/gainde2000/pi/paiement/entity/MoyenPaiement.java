/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.entity;

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
import sn.gainde2000.pi.paiement.entity.Paiement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_moyen_paiement",schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MoyenPaiement.findAll", query = "SELECT m FROM MoyenPaiement m")})
public class MoyenPaiement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "mpa_id")
    private Long mpaId;
    @Size(max = 255)
    @Column(name = "mpa_code")
    private String mpaCode;
    @Size(max = 255)
    @Column(name = "mpa_libelle")
    private String mpaLibelle;
     @JsonIgnore
    @OneToMany(mappedBy = "pai_mpa_id")
    private Set<Paiement> paiement;

    public MoyenPaiement() {
    }

    public MoyenPaiement(Long mpaId) {
        this.mpaId = mpaId;
    }

    public Long getMpaId() {
        return mpaId;
    }

    public void setMpaId(Long mpaId) {
        this.mpaId = mpaId;
    }

    public String getMpaCode() {
        return mpaCode;
    }

    public void setMpaCode(String mpaCode) {
        this.mpaCode = mpaCode;
    }

    public String getMpaLibelle() {
        return mpaLibelle;
    }

    public void setMpaLibelle(String mpaLibelle) {
        this.mpaLibelle = mpaLibelle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpaId != null ? mpaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MoyenPaiement)) {
            return false;
        }
        MoyenPaiement other = (MoyenPaiement) object;
        if ((this.mpaId == null && other.mpaId != null) || (this.mpaId != null && !this.mpaId.equals(other.mpaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.MoyenPaiement[ mpaId=" + mpaId + " ]";
    }

    /**
     * @return the paiement
     */
    public Set<Paiement> getPaiement() {
        return paiement;
    }

    /**
     * @param paiement the paiement to set
     */
    public void setPaiement(Set<Paiement> paiement) {
        this.paiement = paiement;
    }
    
}
