package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "td_type_notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeNotification.findAll", query = "SELECT tn FROM TypeNotification tn")})
public class TypeNotification implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "tnt_id")
    private Long tntId;
    @Size(max = 20)
    @Column(name = "tnt_nom")
    private String tntNom;
    @Size(max = 255)
    @Column(name = "tnt_description")
    private String tntDescription;
    
    public TypeNotification() {
    }

    public TypeNotification(Long tntId) {
        this.tntId = tntId;
    }

	public Long getTntId() {
		return tntId;
	}

	public void setTntId(Long tntId) {
		this.tntId = tntId;
	}

	public String getTntNom() {
		return tntNom;
	}

	public void setTntNom(String tntNom) {
		this.tntNom = tntNom;
	}

	public String getTntDescription() {
		return tntDescription;
	}

	public void setTntDescription(String tntDescription) {
		this.tntDescription = tntDescription;
	}
    
    
}
