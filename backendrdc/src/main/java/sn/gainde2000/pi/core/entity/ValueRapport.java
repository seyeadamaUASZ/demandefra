package sn.gainde2000.pi.core.entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "td_valuerapport", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValueRapport.findAll", query = "SELECT v FROM ValueRapport v")})
public class ValueRapport {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "id")
	    private Long  id;
	    @Size(max = 255)
	    @Column(name = "label")
	    private String label;
	    @Size(max = 255)
	    @Column(name = "value")
	    private String value;
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name="rpt_value_id", referencedColumnName = "rpt_id")    
	    private Rapport rapport;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Rapport getRapport() {
			return rapport;
		}
		public void setRapport(Rapport rapport) {
			this.rapport = rapport;
		}
	    
	    
	    
	    

}
