package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "td_champsworkflow", schema = "")
public class ChampFormWorkflow  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "chpW_id")
    private Long chpWId;
    @Size(max = 255)
    @Column(name = "chpW_name")
    private String chpWName;
    @Size(max = 255)
    @Column(name = "chpW_label")
    private String chpWLabel;
    @Column(name = "chpW_code")
    private String chpWCode;
    @Size(max = 255)
    @Column(name = "chpW_placeholder")
    private String chpWPlaceholder;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "chpW_maxLength")
    private Double chpWMaxLength;
    @Column(name = "chpw_frm_id")
    private Long chpWFrmId;
    
	public Long getChpWId() {
		return chpWId;
	}
	public void setChpWId(Long chpWId) {
		this.chpWId = chpWId;
	}
	public String getChpWName() {
		return chpWName;
	}
	public void setChpWName(String chpWName) {
		this.chpWName = chpWName;
	}
	public String getChpWLabel() {
		return chpWLabel;
	}
	public void setChpWLabel(String chpWLabel) {
		this.chpWLabel = chpWLabel;
	}
	public String getChpWCode() {
		return chpWCode;
	}
	public void setChpWCode(String chpWCode) {
		this.chpWCode = chpWCode;
	}
	public String getChpWPlaceholder() {
		return chpWPlaceholder;
	}
	public void setChpWPlaceholder(String chpWPlaceholder) {
		this.chpWPlaceholder = chpWPlaceholder;
	}
	public Double getChpWMaxLength() {
		return chpWMaxLength;
	}
	public void setChpWMaxLength(Double chpWMaxLength) {
		this.chpWMaxLength = chpWMaxLength;
	}
	
	public Long getChpWFrmId() {
		return chpWFrmId;
	}
	public void setChpWFrmId(Long chpWFrmId) {
		this.chpWFrmId = chpWFrmId;
	}
	public ChampFormWorkflow() {
		super();
		// TODO Auto-generated constructor stub
	}
   
    
}
