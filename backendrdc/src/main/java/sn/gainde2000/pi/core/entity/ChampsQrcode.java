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
@Table(name = "td_champs_qrcode", schema = "")
public class ChampsQrcode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "cqd_id")
    private Long cqdId;

    @Size(max = 255)
    @Column(name = "cqd_class")
    private String cqdClass;
    @Size(max = 255)
    @Column(name = "cqd_label")
    private String cqdLabel;
    @Size(max = 255)
    @Column(name = "cqd_nom")
    private String cqdNom;
    @Column(name = "cqd_obligatoire")
    private Boolean cqdObligatoire;
    @Size(max = 255)
    @Column(name = "cqd_placeholder")
    private String cqdPlaceholder;
    @Column(name = "cqd_taille")
    private Integer cqdTaille;
    @Size(max = 255)
    @Column(name = "cqd_type")
    private String cqdType;
    @Column(name = "cqd_regex")
    private String cqdRegex;
    @Column(name = "cqd_icon")
    private String cqdIcon;
    @Column(name = "cqd_qrc_id")
    private Long cqdQrcId;

    public Long getCqdId() {
        return cqdId;
    }

    public void setCqdId(Long cqdId) {
        this.cqdId = cqdId;
    }

    public String getCqdClass() {
        return cqdClass;
    }

    public void setCqdClass(String cqdClass) {
        this.cqdClass = cqdClass;
    }

    public String getCqdLabel() {
        return cqdLabel;
    }

    public void setCqdLabel(String cqdLabel) {
        this.cqdLabel = cqdLabel;
    }

    public String getCqdNom() {
        return cqdNom;
    }

    public void setCqdNom(String cqdNom) {
        this.cqdNom = cqdNom;
    }

    public Boolean getCqdObligatoire() {
        return cqdObligatoire;
    }

    public void setCqdObligatoire(Boolean cqdObligatoire) {
        this.cqdObligatoire = cqdObligatoire;
    }

    public String getCqdPlaceholder() {
        return cqdPlaceholder;
    }

    public void setCqdPlaceholder(String cqdPlaceholder) {
        this.cqdPlaceholder = cqdPlaceholder;
    }

    public Integer getCqdTaille() {
        return cqdTaille;
    }

    public void setCqdTaille(Integer cqdTaille) {
        this.cqdTaille = cqdTaille;
    }

    public String getCqdType() {
        return cqdType;
    }

    public void setCqdType(String cqdType) {
        this.cqdType = cqdType;
    }

    public String getCqdRegex() {
        return cqdRegex;
    }

    public void setCqdRegex(String cqdRegex) {
        this.cqdRegex = cqdRegex;
    }

    public String getCqdIcon() {
        return cqdIcon;
    }

    public void setCqdIcon(String cqdIcon) {
        this.cqdIcon = cqdIcon;
    }

    public Long getCqdQrcId() {
        return cqdQrcId;
    }

    public void setCqdQrcId(Long cqdQrcId) {
        this.cqdQrcId = cqdQrcId;
    }


    @Override
    public String toString() {
        return "ChampsQrcode{" + "cqdId=" + cqdId + '}';
    }
    
    
}