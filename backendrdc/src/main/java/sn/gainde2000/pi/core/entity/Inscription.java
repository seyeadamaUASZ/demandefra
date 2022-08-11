/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "td_inscription")
public class Inscription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ins_id")
    private Long insId;
    @Size(max = 255)
    @Column(name = "ins_prenom")
    private String insPrenom;
    @Size(max = 255)
    @Column(name = "ins_nom")
    private String insNom;
    @Size(max = 50)
    @Column(name = "ins_username")
    private String insUsername;
    @Size(max = 255)
    @Column(name = "ins_telephone")
    private String insTelephone;
    @Size(max = 255)
    @Column(name = "ins_email")
    private String insEmail;
    @Column(name = "ins_date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDateCreation;

    public Inscription() {
    }

    public Inscription(Long insId) {
        this.insId = insId;
    }

    public Long getInsId() {
        return insId;
    }

    public void setInsId(Long insId) {
        this.insId = insId;
    }

    public String getInsUsername() {
        return insUsername;
    }

    public void setInsUsername(String insUsername) {
        this.insUsername = insUsername;
    }

    public String getInsEmail() {
        return insEmail;
    }

    public void setInsEmail(String insEmail) {
        this.insEmail = insEmail;
    }

    public String getInsNom() {
        return insNom;
    }

    public void setInsNom(String insNom) {
        this.insNom = insNom;
    }

    public String getInsPrenom() {
        return insPrenom;
    }

    public void setInsPrenom(String insPrenom) {
        this.insPrenom = insPrenom;
    }

    public String getInsTelephone() {
        return insTelephone;
    }

    public void setInsTelephone(String insTelephone) {
        this.insTelephone = insTelephone;
    }

    public Date getInsDateCreation() {
        return insDateCreation;
    }

    public void setInsDateCreation(Date insDateCreation) {
        this.insDateCreation = insDateCreation;
    }


 


    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscription)) {
            return false;
        }
        Inscription other = (Inscription) object;
        if ((this.insId == null && other.insId != null) || (this.insId != null && !this.insId.equals(other.insId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inscription{" + "insId=" + insId + '}';
    }

}
