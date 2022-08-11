/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.entity;

import java.util.Date;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;
import sn.gainde2000.pi.core.entity.Profil;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_paiement", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paiement.findAll", query = "SELECT p FROM Paiement p")})
public class Paiement {
      @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    private Long paiId;
    //private Date paiDateOrdre;
    private String paiMontant;
    private Long paiIdFacture;
    private String paiReferencePaiement;
    private Date paiDatePaiement;
    private String paiReferenceClient;
    private String paiStatusTransaction;
    private String paiPrenomPayeur;
    private String paiNomPayeur;
    @ManyToOne(targetEntity = MoyenPaiement.class)
    @JoinColumn(name = "pai_mpa_id", referencedColumnName = "mpa_id", insertable = true, updatable = true)
    private MoyenPaiement pai_mpa_id;

    /**
     * @return the paiId
     */
    public Long getPaiId() {
        return paiId;
    }

    /**
     * @param paiId the paiId to set
     */
    public void setPaiId(Long paiId) {
        this.paiId = paiId;
    }

  

    /**
     * @return the paiMontant
     */
    public String getPaiMontant() {
        return paiMontant;
    }

    /**
     * @param paiMontant the paiMontant to set
     */
    public void setPaiMontant(String paiMontant) {
        this.paiMontant = paiMontant;
    }

    /**
     * @return the paiReferencePaiement
     */
    public String getPaiReferencePaiement() {
        return paiReferencePaiement;
    }

    /**
     * @param paiReferencePaiement the paiReferencePaiement to set
     */
    public void setPaiReferencePaiement(String paiReferencePaiement) {
        this.paiReferencePaiement = paiReferencePaiement;
    }

    /**
     * @return the pai_datePaiement
     */
    public Date getPai_datePaiement() {
        return getPaiDatePaiement();
    }

    /**
     * @param pai_datePaiement the pai_datePaiement to set
     */
    public void setPai_datePaiement(Date pai_datePaiement) {
        this.setPaiDatePaiement(pai_datePaiement);
    }

    /**
     * @return the paiReferenceClient
     */
    public String getPaiReferenceClient() {
        return paiReferenceClient;
    }

    /**
     * @param paiReferenceClient the paiReferenceClient to set
     */
    public void setPaiReferenceClient(String paiReferenceClient) {
        this.paiReferenceClient = paiReferenceClient;
    }

    /**
     * @return the paiStatusTransaction
     */
    public String getPaiStatusTransaction() {
        return paiStatusTransaction;
    }

    /**
     * @param paiStatusTransaction the paiStatusTransaction to set
     */
    public void setPaiStatusTransaction(String paiStatusTransaction) {
        this.paiStatusTransaction = paiStatusTransaction;
    }

    /**
     * @return the paiDatePaiement
     */
    public Date getPaiDatePaiement() {
        return paiDatePaiement;
    }

    /**
     * @param paiDatePaiement the paiDatePaiement to set
     */
    public void setPaiDatePaiement(Date paiDatePaiement) {
        this.paiDatePaiement = paiDatePaiement;
    }

    /**
     * @return the pai_mpa_id
     */
    public MoyenPaiement getPai_mpa_id() {
        return pai_mpa_id;
    }

    /**
     * @param pai_mpa_id the pai_mpa_id to set
     */
    public void setPai_mpa_id(MoyenPaiement pai_mpa_id) {
        this.pai_mpa_id = pai_mpa_id;
    }

    /**
     * @return the paiIdFacture
     */
    public Long getPaiIdFacture() {
        return paiIdFacture;
    }

    /**
     * @param paiIdFacture the paiIdFacture to set
     */
    public void setPaiIdFacture(Long paiIdFacture) {
        this.paiIdFacture = paiIdFacture;
    }

    /**
     * @return the paiPrenomPayeur
     */
    public String getPaiPrenomPayeur() {
        return paiPrenomPayeur;
    }

    /**
     * @param paiPrenomPayeur the paiPrenomPayeur to set
     */
    public void setPaiPrenomPayeur(String paiPrenomPayeur) {
        this.paiPrenomPayeur = paiPrenomPayeur;
    }

    /**
     * @return the paiNomPayeur
     */
    public String getPaiNomPayeur() {
        return paiNomPayeur;
    }

    /**
     * @param paiNomPayeur the paiNomPayeur to set
     */
    public void setPaiNomPayeur(String paiNomPayeur) {
        this.paiNomPayeur = paiNomPayeur;
    }

   }
