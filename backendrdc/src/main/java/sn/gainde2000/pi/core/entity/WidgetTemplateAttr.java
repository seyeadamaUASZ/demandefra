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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jsarr
 */
@Entity
@Table(name = "tr_template_widget")

@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "WidgetTemplateAttr.findAll", query = "SELECT wa FROM WidgetTemplateAttr wa")})
public class WidgetTemplateAttr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wta_id")
    private Long wtaId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wt_attr_id", referencedColumnName = "wt_id")
    private WidgetTemplate widgetTemplate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wgt_id", referencedColumnName = "wdg_id")
    private Widget widget;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wa_pro_id", referencedColumnName = "pro_id")
    private Profil profile;
    
    public WidgetTemplateAttr(Profil profil,Widget widget){  
        this.widget = widget; 
        this.profile = profil;
    }
   public WidgetTemplateAttr(){
    }

    public WidgetTemplateAttr(Profil profil, WidgetTemplate widget) {
        this.widgetTemplate = widget; 
        this.profile = profil;
    }

    
    public Long getWtaId() {
        return wtaId;
    }

    public void setWtaId(Long wtaId) {
        this.wtaId = wtaId;
    }

    public WidgetTemplate getWidgetTemplate() {
        return widgetTemplate;
    }

    public void setWidgetTemplate(WidgetTemplate widgetTemplate) {
        this.widgetTemplate = widgetTemplate;
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
