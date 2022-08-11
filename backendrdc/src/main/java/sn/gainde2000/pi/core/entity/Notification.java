package sn.gainde2000.pi.core.entity;

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
* @author sagueye
*/
@Entity
@Table(name = "td_notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")})
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ntf_id")
    private Long ntfId;
    @Size(max = 255)
    @Column(name = "ntf_text")
    private String ntfText;
    @Column(name = "ntf_audio", columnDefinition = "MEDIUMBLOB")
    private byte[] ntfAudio;
    @Size(max = 255)
    @Column(name = "ntf_objet")
    private String ntfObjet;
    @Size(max = 255)
    @Column(name = "ntf_signature")
    private String ntfSignature;
    @ManyToOne(targetEntity = TypeNotification.class)
    @JoinColumn(name = "ntf_tnt_id", referencedColumnName = "tnt_id", insertable = true, updatable = true)
    private TypeNotification ntfTntId;
    @ManyToOne(targetEntity = Utilisateur.class)
    @JoinColumn(name = "ntf_expediteur_id", referencedColumnName = "uti_id", insertable = true, updatable = true)
    private Utilisateur ntfExpediteurId;
    
    public Notification() {
    }

    public Notification(Utilisateur ntfExpediteurId, TypeNotification ntfTntId) {
        //this.ntfExpediteurId = ntfExpediteurId;
        this.ntfTntId = ntfTntId;
    }

	public Long getNtfId() {
		return ntfId;
	}

	public void setNtfId(Long ntfId) {
		this.ntfId = ntfId;
	}
	
	public String getNtfText() {
		return ntfText;
	}

	public void setNtfText(String ntfText) {
		this.ntfText = ntfText;
	}

	public byte[] getNtfAudio() {
		return ntfAudio;
	}

	public void setNtfAudio(byte[] ntfAudio) {
		this.ntfAudio = ntfAudio;
	}

	public TypeNotification getNtfTntId() {
		return ntfTntId;
	}

	public void setNtfTntId(TypeNotification ntfTntId) {
		this.ntfTntId = ntfTntId;
	}

	public String getNtfObjet() {
		return ntfObjet;
	}

	public void setNtfObjet(String ntfObjet) {
		this.ntfObjet = ntfObjet;
	}

	public String getNtfSignature() {
		return ntfSignature;
	}

	public void setNtfSignature(String ntfSignature) {
		this.ntfSignature = ntfSignature;
	}
        
	public Utilisateur getNtfExpediteurId() {
		return ntfExpediteurId;
	}

	public void setNtfExpediteurId(Utilisateur ntfExpediteurId) {
		this.ntfExpediteurId = ntfExpediteurId;
	} 
        
    
    
}
