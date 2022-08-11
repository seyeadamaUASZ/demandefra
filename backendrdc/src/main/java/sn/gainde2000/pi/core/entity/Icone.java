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

@Entity
@Table(name = "tp_icone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Icone.findAll", query = "SELECT i FROM Icone i")})
public class Icone implements Serializable {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "ico_id")
	    private Long icoId;
	    @Size(max = 255)
	    @Column(name = "ico_description")
	    private String icoDescription;
	    @Size(max = 255)
	    @Column(name = "ico_nom")
	    private String icoNom;
		public Long getIcoId() {
			return icoId;
		}
		public void setIcoId(Long icoId) {
			this.icoId = icoId;
		}
		public String getIcoDescription() {
			return icoDescription;
		}
		public void setIcoDescription(String icoDescription) {
			this.icoDescription = icoDescription;
		}
		public String getIcoNom() {
			return icoNom;
		}
		public void setIcoNom(String icoNom) {
			this.icoNom = icoNom;
		}
		public Icone() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
}
