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
 * @author Administrateur_Aly
 */
@Entity
@Table(name = "td_fichier", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fichier.findAll", query = "SELECT f FROM Fichier f")})
public class Fichier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "fhr_id")
    private Long fhrId;
    @Size(max = 20)
    @Column(name = "fhr_nom")
    private String fhrNom;
    @Size(max = 255)
    @Column(name = "fhr_type")
    private String fhrType;
    @Column(name = "fhr_jrxmlFileId")
    private Long fhrJrxmlFileId;
    @Column(name = "fhr_app_id")
    private Long fhrAppId;
    

    public Fichier() {
    }

    public Fichier(Long fhrId) {
        this.fhrId = fhrId;
    }

    public Fichier(String fhrNom, String fhrType) {
        this.fhrNom = fhrNom;
        this.fhrType = fhrType;
    }

    public Long getFhrId() {
        return fhrId;
    }

    public void setFhrId(Long fhrId) {
        this.fhrId = fhrId;
    }

    public String getFhrNom() {
        return fhrNom;
    }

    public void setFhrNom(String fhrNom) {
        this.fhrNom = fhrNom;
    }

    public String getFhrType() {
        return fhrType;
    }

    public void setFhrType(String fhrType) {
        this.fhrType = fhrType;
    }

    public Long getFhrJrxmlFileId() {
        return fhrJrxmlFileId;
    }

    public void setFhrJrxmlFileId(Long fhrJrxmlFileId) {
        this.fhrJrxmlFileId = fhrJrxmlFileId;
    }

    /**
     * @return the fhrAppId
     */
    public Long getFhrAppId() {
        return fhrAppId;
    }

    /**
     * @param fhrAppId the fhrAppId to set
     */
    public void setFhrAppId(Long fhrAppId) {
        this.fhrAppId = fhrAppId;
    }

}
