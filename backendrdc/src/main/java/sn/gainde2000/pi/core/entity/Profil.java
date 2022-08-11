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
@Table(name = "tp_profil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profil.findAll", query = "SELECT p FROM Profil p")})
public class Profil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pro_id")
    private Long proId;
    @Size(max = 255)
    @Column(name = "pro_description")
    private String proDescription;
    @Size(max = 255)
    @Column(name = "pro_libelle")
    private String proLibelle;
    @JsonIgnore
    @OneToMany(mappedBy = "uti_pro_id")
    private Set<Utilisateur> utilisateur;
    private boolean nouvelleInscri;

    public Profil() {
    }

    public Profil(Long proId) {
        super();
        this.proId = proId;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public String getProLibelle() {
        return proLibelle;
    }

    public void setProLibelle(String proLibelle) {
        this.proLibelle = proLibelle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profil)) {
            return false;
        }
        Profil other = (Profil) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Profil[ proId=" + proId + " ]";
    }

    /**
     * @return the utilisateur
     */
    public Set<Utilisateur> getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur the utilisateur to set
     */
    public void setUtilisateur(Set<Utilisateur> utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isNouvelleInscri() {
        return nouvelleInscri;
    }

    public void setNouvelleInscri(boolean nouvelleInscri) {
        this.nouvelleInscri = nouvelleInscri;
    }


    

}
