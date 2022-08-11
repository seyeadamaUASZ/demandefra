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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asow
 */

@Entity
@Table(name = "tr_privilege", schema = "")

@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Privilege.findAll", query = "SELECT pr FROM Privilege pr")})
public class Privilege implements Serializable {
        private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pri_id")
    private Long priId;
  
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pri_pro_id", referencedColumnName = "pro_id")    
    private Profil profile;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pri_act_id", referencedColumnName = "act_id")    
    private Action action;

    public Privilege(Profil p, Action a) {
        this.action = a;
        this.profile = p;
    }

    public Long getPriId() {
        return priId;
    }

    public void setPriId(Long priId) {
        this.priId = priId;
    }

    public Profil getProfile() {
        return profile;
    }

    public void setProfile(Profil profile) {
        this.profile = profile;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Privilege() {
    }



}
