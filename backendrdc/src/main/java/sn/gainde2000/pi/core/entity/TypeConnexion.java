/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

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
@Table(name = "tp_typeConnexion", schema = "")
public class TypeConnexion {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long typId;
    private String typLibelle;
    private String typDescription;
    private Boolean typIsActive;


    /**
     * @return the typId
     */
    public Long getTypId() {
        return typId;
    }

    /**
     * @param typId the typId to set
     */
    public void setTypId(Long typId) {
        this.typId = typId;
    }

    /**
     * @return the typLibelle
     */
    public String getTypLibelle() {
        return typLibelle;
    }

    /**
     * @param typLibelle the typLibelle to set
     */
    public void setTypLibelle(String typLibelle) {
        this.typLibelle = typLibelle;
    }

    /**
     * @return the typDescription
     */
    public String getTypDescription() {
        return typDescription;
    }

    /**
     * @param typDescription the typDescription to set
     */
    public void setTypDescription(String typDescription) {
        this.typDescription = typDescription;
    }

    /**
     * @return the typIsActive
     */
    public Boolean getTypIsActive() {
        return typIsActive;
    }

    /**
     * @param typIsActive the typIsActive to set
     */
    public void setTypIsActive(Boolean typIsActive) {
        this.typIsActive = typIsActive;
    }
    
}
