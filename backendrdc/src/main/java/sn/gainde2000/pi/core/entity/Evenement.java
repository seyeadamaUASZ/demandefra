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
@Table(name = "tr_evenement")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Evenement.findAll", query = "SELECT e FROM Evenement e")
})
public class Evenement implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "evn_id")
    private Long evnId;
  
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="evn_ntf_id", referencedColumnName = "ntf_id")    
    private Notification notification;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="evn_act_id", referencedColumnName = "act_id")    
    private Action action;

        public Evenement() {
        }        

        public Evenement(Notification n, Action a) {
            this.action = a;
            this.notification = n;
        }

        public Long getEvnId() {
            return evnId;
        }

        public void setEvnId(Long evnId) {
            this.evnId = evnId;
        }

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
        
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
        
    
}
