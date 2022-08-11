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
@Table(name = "tp_qrcode", schema = "")
public class QrCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "qrc_id")
    private Long qrcId;
    @Size(max = 255)
    @Column(name = "qrc_nom")
    private String qrcNom;
    @Size(max = 255)
    @Column(name = "qrc_description")
    private String qrcDescription;
    @Size(max = 255)
    @Column(name = "qrc_valider")
    private String qrcValider;
    @Column(name = "qrc_status")
    private boolean qrcStatus;
    @Column(name = "qrc_couleur")
    private long qrcCouleur;
    
    @Column(name="qrc_img", columnDefinition = "LONGBLOB")
    private byte[] qrcImg;
    @Column(name="qrc_logo",  columnDefinition = "LONGBLOB")
    private byte[] qrcLogo;
    public QrCode() {
    }

    public QrCode(Long qrcId) {
        this.qrcId = qrcId;


    }

    public Long getQrcId() {
        return qrcId;
    }

    public void setQrcId(Long qrcId) {
        this.qrcId = qrcId;
    }

    public String getQrcNom() {
        return qrcNom;
    }

    public void setQrcNom(String qrcNom) {
        this.qrcNom = qrcNom;
    }

    public String getQrcDescription() {
        return qrcDescription;
    }

    public void setQrcDescription(String qrcDescription) {
        this.qrcDescription = qrcDescription;
    }

    public String getQrcValider() {
        return qrcValider;
    }

    public void setQrcValider(String qrcValider) {
        this.qrcValider = qrcValider;
    }

    public boolean isQrcStatus() {
        return qrcStatus;
    }

    public void setQrcStatus(boolean qrcStatus) {
        this.qrcStatus = qrcStatus;
    }

    public long getQrcCouleur() {
        return qrcCouleur;
    }

    public void setQrcCouleur(long qrcCouleur) {
        this.qrcCouleur = qrcCouleur;
    }

    public byte[] getQrcImg() {
        return qrcImg;
    }

    public void setQrcImg(byte[] qrcImg) {
        this.qrcImg = qrcImg;
    }

    public byte[] getQrcLogo() {
        return qrcLogo;
    }

    public void setQrcLogo(byte[] qrcLogo) {
        this.qrcLogo = qrcLogo;
    }
    


    @Override
    public String toString() {
        return "QrCode{" + "qrcId=" + qrcId + '}';
    }

   /* public Image get() {
        // TODO Auto-generated method stub
        return null;
    }*/



}
