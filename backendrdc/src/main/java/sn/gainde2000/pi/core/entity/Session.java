/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "td_session",schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")})
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ses_id")
    private Long sesId;
    @Column(name = "ses_date_connexion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sesDateConnexion;
    @Size(max = 255)
    @Column(name = "ses_ip")
    private String sesIp;
    @Size(max = 255)
    @Column(name = "ses_login")
    private String sesLogin;
    @Column(name = "ses_uti_id")
    private Long sesUtiId;
   /* @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ses_uti_id", referencedColumnName = "uti_id")
    private Utilisateur sesUtiId;*/

    public Session() {
    }

    public Session(Long sesId) {
        this.sesId = sesId;
    }

    public Long getSesId() {
        return sesId;
    }

    public void setSesId(Long sesId) {
        this.sesId = sesId;
    }

    public Date getSesDateConnexion() {
        return sesDateConnexion;
    }

    public void setSesDateConnexion(Date sesDateConnexion) {
        this.sesDateConnexion = sesDateConnexion;
    }

    public String getSesIp() {
        return sesIp;
    }

    public void setSesIp(String sesIp) {
        this.sesIp = sesIp;
    }

    public String getSesLogin() {
        return sesLogin;
    }

    public void setSesLogin(String sesLogin) {
        this.sesLogin = sesLogin;
    }

    public Long getSesUtiId() {
        return sesUtiId;
    }

    public void setSesUtiId(Long sesUtiId) {
        this.sesUtiId = sesUtiId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sesId != null ? sesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.sesId == null && other.sesId != null) || (this.sesId != null && !this.sesId.equals(other.sesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Session[ sesId=" + sesId + " ]";
    }
    
}
