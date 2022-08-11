/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author asow
 */
@Entity
@Table(name = "td_champsRapport", schema = "")
public class ChampsRapport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "crt_id")
    private Long crtId;
    @Size(max = 255)
    @Column(name = "crt_class")
    private String crtClass;
    @Size(max = 255)
    @Column(name = "crt_label")
    private String crtLabel;
    @Size(max = 255)
    @Column(name = "crt_nom")
    private String crtNom;
    @Column(name = "crt_obligatoire")
    private Boolean crtObligatoire;
    @Size(max = 255)
    @Column(name = "crt_placeholder")
    private String crtPlaceholder;
    @Column(name = "crt_taille")
    private Integer crtTaille;
    @Size(max = 255)
    @Column(name = "crt_type")
    private String crtType;
    @Column(name = "crt_regex")
    private String crtRegex;
    @Column(name = "crt_icon")
    private String crtIcon;
    @Column(name = "crt_rpt_id")
    private Long crtRptId;
	public Long getCrtId() {
		return crtId;
	}
	public void setCrtId(Long crtId) {
		this.crtId = crtId;
	}
	public String getCrtClass() {
		return crtClass;
	}
	public void setCrtClass(String crtClass) {
		this.crtClass = crtClass;
	}
	public String getCrtLabel() {
		return crtLabel;
	}
	public void setCrtLabel(String crtLabel) {
		this.crtLabel = crtLabel;
	}
	public String getCrtNom() {
		return crtNom;
	}
	public void setCrtNom(String crtNom) {
		this.crtNom = crtNom;
	}
	public Boolean getCrtObligatoire() {
		return crtObligatoire;
	}
	public void setCrtObligatoire(Boolean crtObligatoire) {
		this.crtObligatoire = crtObligatoire;
	}
	public String getCrtPlaceholder() {
		return crtPlaceholder;
	}
	public void setCrtPlaceholder(String crtPlaceholder) {
		this.crtPlaceholder = crtPlaceholder;
	}

        public Integer getCrtTaille() {
            return crtTaille;
        }

        public void setCrtTaille(Integer crtTaille) {
            this.crtTaille = crtTaille;
        }
	
	public String getCrtType() {
		return crtType;
	}
	public void setCrtType(String crtType) {
		this.crtType = crtType;
	}
	public String getCrtRegex() {
		return crtRegex;
	}
	public void setCrtRegex(String crtRegex) {
		this.crtRegex = crtRegex;
	}
	public String getCrtIcon() {
		return crtIcon;
	}
	public void setCrtIcon(String crtIcon) {
		this.crtIcon = crtIcon;
	}
	public Long getCrtRptId() {
		return crtRptId;
	}
	public void setCrtRptId(Long crtRptId) {
		this.crtRptId = crtRptId;
	}

    


    
}
