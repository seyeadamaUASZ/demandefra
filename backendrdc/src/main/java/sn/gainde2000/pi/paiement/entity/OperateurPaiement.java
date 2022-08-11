/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_operateur_paiement",schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperateurPaiement.findAll", query = "SELECT o FROM OperateurPaiement o")})
public class OperateurPaiement implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "opa_id")
    private Long opaId;
    private String opaNom;
    private boolean opaIsActive;
    private String OpaCode;

    /**
     * @return the opaId
     */
    public Long getOpaId() {
        return opaId;
    }

    /**
     * @param opaId the opaId to set
     */
    public void setOpaId(Long opaId) {
        this.opaId = opaId;
    }

    /**
     * @return the opaNom
     */
    public String getOpaNom() {
        return opaNom;
    }

    /**
     * @param opaNom the opaNom to set
     */
    public void setOpaNom(String opaNom) {
        this.opaNom = opaNom;
    }

    /**
     * @return the opaIsActive
     */
    public boolean isOpaIsActive() {
        return opaIsActive;
    }

    /**
     * @param opaIsActive the opaIsActive to set
     */
    public void setOpaIsActive(boolean opaIsActive) {
        this.opaIsActive = opaIsActive;
    }

    /**
     * @return the OpaCode
     */
    public String getOpaCode() {
        return OpaCode;
    }

    /**
     * @param OpaCode the OpaCode to set
     */
    public void setOpaCode(String OpaCode) {
        this.OpaCode = OpaCode;
    }
    
}
