package sn.gainde2000.pi.opt.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "td_parametreotp", schema = "")
public class ParametreOtp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idParametreOtp;
	
	
	@Size(max = 255)
    @Column(name = "opt_libelle",unique = true)
	private String libelle;
	@Size(max = 255)
    @Column(name = "opt_evenement")
	private String evenement;
	@Size(max = 255)
    @Column(name = "opt_caractere")
	private String caractere;
	
   
    
    @Size(max=50)
    @Column(name="opt_dureee")
    private String dureee;
    
    @Column(name="opt_duree")
    private double duree;
    
    @OneToMany(targetEntity = OTPConfiguration.class,mappedBy = "parametreOtp",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<OTPConfiguration> otpconfs;
    
    @JsonIgnore
	public List<OTPConfiguration> getOtpconfs() {
		return otpconfs;
	}
	public void setOtpconfs(List<OTPConfiguration> otpconfs) {
		this.otpconfs = otpconfs;
	}
	public double getDuree() {
		return duree;
	}
	public void setDuree(double duree) {
		this.duree = duree;
	}
	public String getDureee() {
		return dureee;
	}
	public void setDureee(String dureee) {
		this.dureee = dureee;
	}
	

	@Size(max=50)
	@Column(name="otp_type")
	private String typeOtp;
	
	public String getTypeOtp() {
		return typeOtp;
	}
	public void setTypeOtp(String typeOtp) {
		this.typeOtp = typeOtp;
	}
	public Long getIdParametreOtp() {
		return idParametreOtp;
	}
	public void setIdParametreOtp(Long idParametreOtp) {
		this.idParametreOtp = idParametreOtp;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getEvenement() {
		return evenement;
	}
	public void setEvenement(String evenement) {
		this.evenement = evenement;
	}
	public String getCaractere() {
		return caractere;
	}
	public void setCaractere(String caractere) {
		this.caractere = caractere;
	}
	
	
	public ParametreOtp(@Size(max = 255) String libelle, @Size(max = 255) String evenement,
			@Size(max = 255) String caractere, @Size(max = 50) String dureee, @Size(max = 50) String typeOtp,double duree) {
		super();
		this.libelle = libelle;
		this.evenement = evenement;
		this.caractere = caractere;
		this.dureee = dureee;
		this.typeOtp = typeOtp;
		this.duree=duree;
	}
	public ParametreOtp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParametreOtp(Long idParametreOtp, @Size(max = 255) String libelle, @Size(max = 255) String evenement,
			@Size(max = 255) String caractere, @Size(max = 50) String dureee, double duree,
			@Size(max = 50) String typeOtp) {
		super();
		this.idParametreOtp = idParametreOtp;
		this.libelle = libelle;
		this.evenement = evenement;
		this.caractere = caractere;
		this.dureee = dureee;
		this.duree = duree;
		this.typeOtp = typeOtp;
	}
	
		
}
