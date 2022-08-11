package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asow
 */
@Entity
@Table(name = "td_image",schema = "")
@XmlRootElement
public class Image implements Serializable{
    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;
    @Column(name = "img_name")
    private String imgName;
    @Column(name = "img_type")
    private String imgType;
    @Column(name = "img_logoByte", columnDefinition = "MEDIUMBLOB")
    private byte[] imgLogoByte;  
    
    public Image(){
        super();
    }

    public Image(Long imgId) {
        this.imgId = imgId;
    }

    public Image(String imgName) {
        this.imgName = imgName;
    }
    
    

    public Image(String imgName, String imgType, byte[] imgLogoByte) {
        this.imgName = imgName;
        this.imgType = imgType;
        this.imgLogoByte = imgLogoByte;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public byte[] getImgLogoByte() {
        return imgLogoByte;
    }

    public void setImgLogoByte(byte[] imgLogoByte) {
        this.imgLogoByte = imgLogoByte;
    }

  

}
