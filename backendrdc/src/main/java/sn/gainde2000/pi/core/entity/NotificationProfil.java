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
* @author sagueye
*/

@Entity
@Table(name = "tr_notification_profil")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "NotificationProfil.findAll", query = "SELECT npr FROM NotificationProfil npr")})
public class NotificationProfil implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "npr_id")
    private Long nprId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="npr_pro_id", referencedColumnName = "pro_id")    
    private Profil profil;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="npr_ntf_id", referencedColumnName = "ntf_id")    
    private Notification notification;

	public NotificationProfil(Notification n, Profil p) {
		this.notification = n;
		this.profil = p;
	}
	
	public NotificationProfil() {
	}

	public Long getNprId() {
		return nprId;
	}

	public void setNprId(Long nprId) {
		this.nprId = nprId;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
    
    
}
