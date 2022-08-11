/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.util.Date;
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
@Table(name = "td_notification_push", schema = "")
public class NotificationPush {
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ntfId;
    private String ntfLibelle;
    private String ntfTitre;
    private Date ntfDate;



    /**
     * @return the ntfId
     */
    public Long getNtfId() {
        return ntfId;
    }

    /**
     * @param ntfId the ntfId to set
     */
    public void setNtfId(Long ntfId) {
        this.ntfId = ntfId;
    }

    /**
     * @return the ntfLibelle
     */
    public String getNtfLibelle() {
        return ntfLibelle;
    }

    /**
     * @param ntfLibelle the ntfLibelle to set
     */
    public void setNtfLibelle(String ntfLibelle) {
        this.ntfLibelle = ntfLibelle;
    }

    /**
     * @return the ntfTitre
     */
    public String getNtfTitre() {
        return ntfTitre;
    }

    /**
     * @param ntfTitre the ntfTitre to set
     */
    public void setNtfTitre(String ntfTitre) {
        this.ntfTitre = ntfTitre;
    }

    /**
     * @return the ntfDate
     */
    public Date getNtfDate() {
        return ntfDate;
    }

    /**
     * @param ntfDate the ntfDate to set
     */
    public void setNtfDate(Date ntfDate) {
        this.ntfDate = ntfDate;
    }
    
}
