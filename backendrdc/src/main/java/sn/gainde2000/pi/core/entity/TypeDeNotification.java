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
 * @author asow
 */
@Entity
@Table(name = "td_type_de_notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeDeNotification.findAll", query = "SELECT t FROM TypeDeNotification t")})
public class TypeDeNotification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "tdn_id")
    private Long tdnId;
    @Size(max = 20)
    @Column(name = "tdn_type")
    private String tdnType;
    @Size(max = 20)
    @Column(name = "tdn_typenotif")
    private String tdnTypeNotif;
    @Size(max = 255)
    @Column(name = "tdn_contenu")
    private String tdnContenu;
    @ManyToOne(targetEntity = ActionNotification.class)
    @JoinColumn(name = "tdn_an_id", referencedColumnName = "an_id", insertable = true, updatable = true)
    private ActionNotification tdnAnId;

    public TypeDeNotification() {
    }

    public Long getTdnId() {
        return tdnId;
    }

    public void setTdnId(Long tdnId) {
        this.tdnId = tdnId;
    }

    public String getTdnType() {
        return tdnType;
    }

    public void setTdnType(String tdnType) {
        this.tdnType = tdnType;
    }

    public String getTdnContenu() {
        return tdnContenu;
    }

    public void setTdnContenu(String tdnContenu) {
        this.tdnContenu = tdnContenu;
    }

    public ActionNotification getTdnAnId() {
        return tdnAnId;
    }

    public void setTdnAnId(ActionNotification tdnAnId) {
        this.tdnAnId = tdnAnId;
    }

    public String getTdnTypeNotif() {
        return tdnTypeNotif;
    }

    public void setTdnTypeNotif(String tdnTypeNotif) {
        this.tdnTypeNotif = tdnTypeNotif;
    }

    @Override
    public String toString() {
        return "TypeDeNotification{" + "tdnId=" + tdnId + '}';
    }

}
