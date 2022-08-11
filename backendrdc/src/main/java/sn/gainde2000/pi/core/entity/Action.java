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
@Table(name = "tp_action", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a")})
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "act_id")
    private Long actId;
    @Size(max = 20)
    @Column(name = "act_code")
    private String actCode;
    @Size(max = 255)
    @Column(name = "act_description")
    private String actDescription;
    @Size(max = 255)
    @Column(name = "act_nom")
    private String actNom;
    /*
    @Column(name = "act_men_id")
    private BigInteger actMenId;*/
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "act_men_id", referencedColumnName = "men_id", insertable = true, updatable = true)
    private Menu actMenId;

    public Action() {
    }

    public Action(Long actId) {
        this.actId = actId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }    

    public String getActDescription() {
        return actDescription;
    }

    public void setActDescription(String actDescription) {
        this.actDescription = actDescription;
    }

    public String getActNom() {
        return actNom;
    }

    public void setActNom(String actNom) {
        this.actNom = actNom;
    }

    public Menu getActMenId() {
        return actMenId;
    }

    public void setActMenId(Menu actMenId) {
        this.actMenId = actMenId;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actId != null ? actId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.actId == null && other.actId != null) || (this.actId != null && !this.actId.equals(other.actId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Action[ actId=" + actId + ", actMenId=" + actMenId + " , actCode=" + actCode + "]";
    }

    }
