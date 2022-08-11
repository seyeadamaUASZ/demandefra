package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="region")
public class Region implements Serializable {
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
	private String codeRegion;
	private String nomRegion;
	@OneToMany(mappedBy = "region")
	private List<AntenneRegionaleDepartementale> antenneRegionaleDepartementals;
	
	public Region() {
		super();
	}
		
	public Region(@NotNull Long id, String codeRegion, String nomRegion) {
		super();
		this.id = id;
		this.codeRegion = codeRegion;
		this.nomRegion = nomRegion;
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
	
	public String getCodeRegion() {
		return codeRegion;
	}
	
	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}
	
	public String getNomRegion() {
		return nomRegion;
	}
	
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	@JsonIgnore
	public List<AntenneRegionaleDepartementale> getAntenneRegionaleDepartementals() {
		return antenneRegionaleDepartementals;
	}

	public void setAntenneRegionaleDepartementals(List<AntenneRegionaleDepartementale> antenneRegionaleDepartementals) {
		this.antenneRegionaleDepartementals = antenneRegionaleDepartementals;
	}	
}
