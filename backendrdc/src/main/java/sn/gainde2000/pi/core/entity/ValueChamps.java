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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "td_value", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValueChamps.findAll", query = "SELECT v FROM ValueChamps v")})
public class ValueChamps implements Serializable{
	private static final long serialVersionUID = 1L;
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
    @JoinColumn(name="chps_value_id", referencedColumnName = "chp_id")    
    private Champs valueChp;

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
	
	
	
	public Champs getValueChp() {
		return valueChp;
	}
	public void setValueChp(Champs valueChp) {
		this.valueChp = valueChp;
	}
	public ValueChamps() {

	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
	
    
}
