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
@Table(name = "td_partenaire", schema = "")
public class Partenaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partId;
    private String partNom;
    @Column(name = "part_im", columnDefinition = "MEDIUMBLOB")
    private byte[] partIm;

    /**
     * @return the partId
     */
    public Long getPartId() {
        return partId;
    }

    /**
     * @param partId the partId to set
     */
    public void setPartId(Long partId) {
        this.partId = partId;
    }

    /**
     * @return the partIm
     */
    public byte[] getPartIm() {
        return partIm;
    }

    /**
     * @param partIm the partIm to set
     */
    public void setPartIm(byte[] partIm) {
        this.partIm = partIm;
    }

    /**
     * @return the partNom
     */
    public String getPartNom() {
        return partNom;
    }

    /**
     * @param partNom the partNom to set
     */
    public void setPartNom(String partNom) {
        this.partNom = partNom;
    }

}
