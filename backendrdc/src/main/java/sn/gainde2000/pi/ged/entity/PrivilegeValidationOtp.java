package sn.gainde2000.pi.ged.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import sn.gainde2000.pi.core.entity.Utilisateur;

@Entity
@Table(name = "tr_privilege_validation_otp", schema = "")
public class PrivilegeValidationOtp {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pris_val_id")
    private Long prisValId;
  
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pris_val_prisid", referencedColumnName = "pris_id")
    private PrivilegeSigner privilegeSigner;
    
    private Date prisValDateCreation = new Date();
    private String prisValCode;
    private Long prisValDuree;
	public Long getPrisValId() {
		return prisValId;
	}
	public void setPrisValId(Long prisValId) {
		this.prisValId = prisValId;
	}
	
	public PrivilegeSigner getPrivilegeSigner() {
		return privilegeSigner;
	}
	public void setPrivilegeSigner(PrivilegeSigner privilegeSigner) {
		this.privilegeSigner = privilegeSigner;
	}
	public Date getPrisValDateCreation() {
		return prisValDateCreation;
	}
	public void setPrisValDateCreation(Date prisValDateCreation) {
		this.prisValDateCreation = prisValDateCreation;
	}
	public String getPrisValCode() {
		return prisValCode;
	}
	public void setPrisValCode(String prisValCode) {
		this.prisValCode = prisValCode;
	}
	public Long getPrisValDuree() {
		return prisValDuree;
	}
	public void setPrisValDuree(Long prisValDuree) {
		this.prisValDuree = prisValDuree;
	}
    
    
    
    
    
     
}
