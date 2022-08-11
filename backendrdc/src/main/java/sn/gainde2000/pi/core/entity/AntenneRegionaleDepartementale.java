package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="AntenneRegionaleDepartementale")
public class AntenneRegionaleDepartementale implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;
	private Long status;
	private Long poOwner;
	private Long owner;
	private Long idLink;
	private String codeAntenne;
	private String nomAntenne;
	@ManyToOne
	@JoinColumn(name="region_id", nullable=false)
	private Region region;
	
	public AntenneRegionaleDepartementale() {
		super();
	}
		
	public AntenneRegionaleDepartementale(@NotNull Long id, Long status, Long poOwner, Long owner, Long idLink,
			String codeAntenne, String nomAntenne, Region region) {
		super();
		this.id = id;
		this.status = status;
		this.poOwner = poOwner;
		this.owner = owner;
		this.idLink = idLink;
		this.codeAntenne = codeAntenne;
		this.nomAntenne = nomAntenne;
		this.region = region;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public String getCodeAntenne() {
		return codeAntenne;
	}
	
	public void setCodeAntenne(String codeAntenne) {
		this.codeAntenne = codeAntenne;
	}
	
	public String getNomAntenne() {
		return nomAntenne;
	}
	
	public void setNomAntenne(String nomAntenne) {
		this.nomAntenne = nomAntenne;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
