package sn.gainde2000.pi.opt.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "td_otpconfiguration", schema = "")
public class OTPConfiguration  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idOtp")
	private Long idOtp;
	private String Valcode;
	private Long ValDuree;
	private Date otpValDateCreation = new Date();
	private boolean dejavalide;
	
	
	 public boolean isDejavalide() {
		return dejavalide;
	}
	public void setDejavalide(boolean dejavalide) {
		this.dejavalide = dejavalide;
	}
	public Date getOtpValDateCreation() {
		return otpValDateCreation;
	}
	public void setOtpValDateCreation(Date otpValDateCreation) {
		this.otpValDateCreation = otpValDateCreation;
	}
	public ParametreOtp getParametreOtp() {
		return parametreOtp;
	}
	public void setParametreOtp(ParametreOtp parametreOtp) {
		this.parametreOtp = parametreOtp;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name="parametre_otp", referencedColumnName = "idParametreOtp")
	 private ParametreOtp parametreOtp;
	 
	public Long getIdOtp() {
		return idOtp;
	}
	public void setIdOtp(Long idOtp) {
		this.idOtp = idOtp;
	}
	
	public String getValcode() {
		return Valcode;
	}
	public void setValcode(String valcode) {
		Valcode = valcode;
	}
	public Long getValDuree() {
		return ValDuree;
	}
	public void setValDuree(Long valDuree) {
		ValDuree = valDuree;
	}
	public OTPConfiguration(String valcode, Long valDuree) {
		super();
		Valcode = valcode;
		ValDuree = valDuree;
	}
	
	public OTPConfiguration() {
		super();
	}
	
	
	

}
