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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "tp_rapport")
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_id")
    private Long rptId;
    @Size(max = 255)
    @Column(name = "rpt_description")
    private String rptDescription;
    @Size(max = 255)
    @Column(name = "rpt_nom")
    private String rptNom;
    @Column(name = "rpt_jrxmlFile", columnDefinition = "MEDIUMBLOB")
    private byte[] rptJrxmlFile;
    @Column(name = "rpt_status")
    private boolean rptStatus;
    @Size(max = 255)
    @Column(name = "rpt_valider")
    private String rptValider;
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn(name = "rpt_app_id", referencedColumnName = "app_id", insertable = true, updatable = true)
    private Application rptAppId;
    @Column(name = "qrcode", columnDefinition = "MEDIUMBLOB")
    private byte[] qrcode;

    public Rapport() {
    }

    public Rapport(String rptDescription, String rptNom, String rptValider) {
        this.rptDescription = rptDescription;
        this.rptNom = rptNom;
        this.rptValider = rptValider;
    }

    public Long getRptId() {
        return rptId;
    }

    public void setRptId(Long rptId) {
        this.rptId = rptId;
    }

    public String getRptDescription() {
        return rptDescription;
    }

    public void setRptDescription(String rptDescription) {
        this.rptDescription = rptDescription;
    }

    public String getRptNom() {
        return rptNom;
    }

    public void setRptNom(String rptNom) {
        this.rptNom = rptNom;
    }

    public boolean isRptStatus() {
        return rptStatus;
    }

    public void setRptStatus(boolean rptStatus) {
        this.rptStatus = rptStatus;
    }

    public String getRptValider() {
        return rptValider;
    }

    public void setRptValider(String rptValider) {
        this.rptValider = rptValider;
    }

    public byte[] getRptJrxmlFile() {
        return rptJrxmlFile;
    }

    public void setRptJrxmlFile(byte[] rptJrxmlFile) {
        this.rptJrxmlFile = rptJrxmlFile;
    }

    @Override
    public String toString() {
        return "Rapport{" + "rptId=" + rptId + ", rptDescription=" + rptDescription + ", rptNom=" + rptNom + ", rptValider=" + rptValider + '}';
    }

    /**
     * @return the rptAppId
     */
    public Application getRptAppId() {
        return rptAppId;
    }

    /**
     * @param rptAppId the rptAppId to set
     */
    public void setRptAppId(Application rptAppId) {
        this.rptAppId = rptAppId;
    }

    public byte[] getQrcode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }
    
    

}
