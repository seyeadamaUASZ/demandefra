
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import sn.gainde2000.pi.core.*;
import java.util.Date;
@Entity
@Table(name = "Suivredemande")
public class Suivredemande implements Serializable {
    private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Basic(optional = false)
@NotNull
@Column(name = "id")
private Long id;
 private Long status; 
 private Long poOwner ;
  private Long owner ;
 private Long idLink; 
@Column(name = "numerodemande")
private String numerodemande;

@Column(name = "email")
private String email;

public String getNumerodemande() {
        return numerodemande;
    }
    public void setNumerodemande(String numerodemande) {
        this.numerodemande = numerodemande;
    }   

public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }   

public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}   

public Long getPoOwner() {
		return poOwner;
	}
	public void setPoOwner(Long poOwner) {
		this.poOwner = poOwner;
	}   

public Long getOwner() {
		return owner;
	}
	public void setOwner(Long owner) {
		this.owner = owner;
	}   

public Long getIdLink() {
		return idLink;
	}
	public void setIdLink(Long idLink) {
		this.idLink = idLink;
	}   


}