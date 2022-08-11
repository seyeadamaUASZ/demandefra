/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_landingPage", schema = "")
public class LandingPage implements Serializable {


    @Id
    @Column(name = "lnd_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lndId;
    @Column(name = "lnd_im1", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm1;
    @Column(name = "lnd_im2", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm2;
    @Column(name = "lnd_im3", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm3;
    @Column(name = "lnd_im4", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm4;
    @Column(name = "lnd_im5", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm5;
    @Column(name = "lnd_im6", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm6;
    @Column(name = "lnd_im7", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm7;
    
    @Column(name = "lnd_im8", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm8;
    @Column(name = "lnd_im9", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm9;
     @Column(name = "lnd_im10", columnDefinition = "MEDIUMBLOB")
    private byte[] lndIm10;

    private String username;

     /**
     * @return the lndIm7
     */
    public byte[] getLndIm7() {
        return lndIm7;
    }

    /**
     * @param lndIm7 the lndIm7 to set
     */
    public void setLndIm7(byte[] lndIm7) {
        this.lndIm7 = lndIm7;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LandingPage(Long lndId) {
        this.lndId = lndId;
    }

    public LandingPage(Long lndId, byte[] lndIm1, byte[] lndIm2, byte[] lndIm3, byte[] lndIm4, byte[] lndIm5) {
        this.lndId = lndId;
        this.lndIm1 = lndIm1;
        this.lndIm2 = lndIm2;
        this.lndIm3 = lndIm3;
        this.lndIm4 = lndIm4;
        this.lndIm5 = lndIm5;
    }

    public LandingPage() {
    }

    public Long getLndId() {
        return lndId;
    }

    public void setLndId(Long lndId) {
        this.lndId = lndId;
    }

    public byte[] getLndIm1() {
        return lndIm1;
    }

    public void setLndIm1(byte[] lndIm1) {
        this.lndIm1 = lndIm1;
    }

    public byte[] getLndIm2() {
        return lndIm2;
    }

    public void setLndIm2(byte[] lndIm2) {
        this.lndIm2 = lndIm2;
    }

    public byte[] getLndIm3() {
        return lndIm3;
    }

    public void setLndIm3(byte[] lndIm3) {
        this.lndIm3 = lndIm3;
    }

    public byte[] getLndIm4() {
        return lndIm4;
    }

    public void setLndIm4(byte[] lndIm4) {
        this.lndIm4 = lndIm4;
    }

    public byte[] getLndIm5() {
        return lndIm5;
    }

    public void setLndIm5(byte[] lndIm5) {
        this.lndIm5 = lndIm5;
    }

    /**
     * @return the lndIm6
     */
    public byte[] getLndIm6() {
        return lndIm6;
    }

    /**
     * @param lndIm6 the lndIm6 to set
     */
    public void setLndIm6(byte[] lndIm6) {
        this.lndIm6 = lndIm6;
    }

    /**
     * @return the lndIm9
     */
    public byte[] getLndIm9() {
        return lndIm9;
    }

   

    /**
     * @return the lndIm8
     */
    public byte[] getLndIm8() {
        return lndIm8;
    }

    /**
     * @param lndIm8 the lndIm8 to set
     */
    public void setLndIm8(byte[] lndIm8) {
        this.lndIm8 = lndIm8;
    }

    /**
     * @return the lndIm10
     */
    public byte[] getLndIm10() {
        return lndIm10;
    }

    /**
     * @param lndIm10 the lndIm10 to set
     */
    public void setLndIm10(byte[] lndIm10) {
        this.lndIm10 = lndIm10;
    }

    /**
     * @param lndIm9 the lndIm9 to set
     */
    public void setLndIm9(byte[] lndIm9) {
        this.lndIm9 = lndIm9;
    }

   

}
