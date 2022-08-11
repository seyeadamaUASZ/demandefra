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
@Table(name = "tr_notification_destinataire")

@XmlRootElement
@NamedQueries({
@NamedQuery(name = "NotificationDestinataire.findAll", query = "SELECT nds FROM NotificationDestinataire nds")})
public class NotificationDestinataire implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "nds_id")
    private Long ndsId;
  
       
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="nds_des_id", referencedColumnName = "uti_id")    
    private Utilisateur utilisateur;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="nds_ntf_id", referencedColumnName = "ntf_id")    
    private Notification notification;

    public NotificationDestinataire() {
    }        

    public NotificationDestinataire(Notification n, Utilisateur u) {
        this.notification = n;
        this.utilisateur = u;
    }

	public Long getNdsId() {
		return ndsId;
	}

	public void setNdsId(Long ndsId) {
		this.ndsId = ndsId;
	}
        
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
        
	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
    
    
}
