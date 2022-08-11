package sn.gainde2000.pi.formgenerator.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "td_value_v2", schema = "")
public class ValueChampsV2 implements Serializable{
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
    @ManyToOne(targetEntity = ChampsV2.class)
    @JoinColumn(name="chps_value_id", referencedColumnName = "chp_id")  
    @JsonIgnore
    private ChampsV2 valueChp;

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
	
	
	
	public ChampsV2 getValueChp() {
		return valueChp;
	}
	public void setValueChp(ChampsV2 valueChp) {
		this.valueChp = valueChp;
	}
	public ValueChampsV2() {

	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
	
    
}
