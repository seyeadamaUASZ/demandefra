/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "td_action_notification")
public class ActionNotification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "an_id")
    private Long anId;
    @Size(max = 20)
    @Column(name = "an_nom")
    private String anNom;
    @Size(max = 255)
    @Column(name = "an_description")
    private String anDescription;
    @JsonIgnore
    @OneToMany(mappedBy = "tdnAnId")
    private Set<TypeDeNotification> typeDeNotification;

    public ActionNotification() {
    }

    public Long getAnId() {
        return anId;
    }

    public void setAnId(Long anId) {
        this.anId = anId;
    }

    public String getAnNom() {
        return anNom;
    }

    public void setAnNom(String anNom) {
        this.anNom = anNom;
    }

    public String getAnDescription() {
        return anDescription;
    }

    public void setAnDescription(String anDescription) {
        this.anDescription = anDescription;
    }

    public Set<TypeDeNotification> getTypeDeNotification() {
        return typeDeNotification;
    }

    public void setTypeDeNotification(Set<TypeDeNotification> typeDeNotification) {
        this.typeDeNotification = typeDeNotification;
    }
    
    

    @Override
    public String toString() {
        return "ActionNotification{" + "anId=" + anId + '}';
    }

}
