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
@Table(name = "tp_payeur", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payeur.findAll", query = "SELECT p FROM Payeur p")})
public class Payeur implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_Facture")
    private Long idFacture;
    private String montantFacture;
    private String nomPayeur;
    private String prenomPayeur;
    private String usernamePayeur;
    
    /**
     * @return the idFacture
     */
    public Long getIdFacture() {
        return idFacture;
    }

    /**
     * @param idFacture the idFacture to set
     */
    public void setIdFacture(Long idFacture) {
        this.idFacture = idFacture;
    }

    /**
     * @return the montantFacture
     */
    public String getMontantFacture() {
        return montantFacture;
    }

    /**
     * @param montantFacture the montantFacture to set
     */
    public void setMontantFacture(String montantFacture) {
        this.montantFacture = montantFacture;
    }

    /**
     * @return the nomPayeur
     */
    public String getNomPayeur() {
        return nomPayeur;
    }

    /**
     * @param nomPayeur the nomPayeur to set
     */
    public void setNomPayeur(String nomPayeur) {
        this.nomPayeur = nomPayeur;
    }

    /**
     * @return the prenomPayeur
     */
    public String getPrenomPayeur() {
        return prenomPayeur;
    }

    /**
     * @param prenomPayeur the prenomPayeur to set
     */
    public void setPrenomPayeur(String prenomPayeur) {
        this.prenomPayeur = prenomPayeur;
    }

    /**
     * @return the usernamePayeur
     */
    public String getUsernamePayeur() {
        return usernamePayeur;
    }

    /**
     * @param usernamePayeur the usernamePayeur to set
     */
    public void setUsernamePayeur(String usernamePayeur) {
        this.usernamePayeur = usernamePayeur;
    }


}
