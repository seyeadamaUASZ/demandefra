/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "td_historique_session", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriqueSession.findAll", query = "SELECT h FROM HistoriqueSession h")})
public class HistoriqueSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "hse_id")
    private Long hseId;
    @Column(name = "hse_date_connexion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hseDateConnexion;
    @Column(name = "hse_date_decconnexion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hseDateDecConnexion;
    @Size(max = 255)
    @Column(name = "hse_ip")
    private String hseIp;
    @Size(max = 255)
    @Column(name = "hse_login")
    private String hseLogin;
    @Column(name = "hse_uti_id")
    private Long hseUtiId;
    @Column(name = "hse_ses_id")
    private Long hseSesId;

    public HistoriqueSession() {
    }

    public HistoriqueSession(Long hseId) {
        this.hseId = hseId;
    }

    public Long getHseId() {
        return hseId;
    }

    public void setHseId(Long hseId) {
        this.hseId = hseId;
    }

    public Date getHseDateConnexion() {
        return hseDateConnexion;
    }

    public void setHseDateConnexion(Date hseDateConnexion) {
        this.hseDateConnexion = hseDateConnexion;
    }

    public String getHseIp() {
        return hseIp;
    }

    public void setHseIp(String hseIp) {
        this.hseIp = hseIp;
    }

    public String getHseLogin() {
        return hseLogin;
    }

    public void setHseLogin(String hseLogin) {
        this.hseLogin = hseLogin;
    }

    public Long getHseUtiId() {
        return hseUtiId;
    }

    public void setHseUtiId(Long hseUtiId) {
        this.hseUtiId = hseUtiId;
    }

    public Long getHseSesId() {
        return hseSesId;
    }

    public void setHseSesId(Long hseSesId) {
        this.hseSesId = hseSesId;
    }    
    
    public Date getHseDateDecConnexion() {
		return hseDateDecConnexion;
	}

	public void setHseDateDecConnexion(Date hseDateDecConnexion) {
		this.hseDateDecConnexion = hseDateDecConnexion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (hseId != null ? hseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriqueSession)) {
            return false;
        }
        HistoriqueSession other = (HistoriqueSession) object;
        if ((this.hseId == null && other.hseId != null) || (this.hseId != null && !this.hseId.equals(other.hseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.HistoriqueSession[ hseId=" + hseId + " ]";
    }
    
}
