/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author jsarr
 */

@Entity
@Table(name = "tp_parametrage_widget")

@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ParametrageWidget.findAll", query = "SELECT pw FROM ParametrageWidget pw")}) 

public class ParametrageWidget  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pw_id")
    private Long pwId;
    
    @Size(max = 255)
    @Column(name = "nomparam")
    private String nomparam;

    public String getNomparam() {
        return nomparam;
    }

    public void setNomparam(String nomparam) {
        this.nomparam = nomparam;
    }
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pw_wkf_id", referencedColumnName = "wkf_id")
    private Workflow workflow;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pw_wgt_id", referencedColumnName = "wdg_id")
    private Widget widget;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pw_pro_id", referencedColumnName = "pro_id")
    private Profil profile;
     

    public Long getPwId() {
        return pwId;
    }

    public void setPwId(Long pwId) {
        this.pwId = pwId;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Profil getProfile() {
        return profile;
    }

    public void setProfile(Profil profile) {
        this.profile = profile;
    }

    
    
    
      
}
