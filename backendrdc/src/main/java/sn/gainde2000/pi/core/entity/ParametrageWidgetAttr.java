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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author jsarr
 */

@Entity
@Table(name = "tr_param_attr_widget")

@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ParametrageWidgetAttr.findAll", query = "SELECT pwa FROM ParametrageWidgetAttr pwa")})
public class ParametrageWidgetAttr  implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy=GenerationType.AUTO) // automatically generated primary key.
    @Id  // Primary key.
    @Column(name="pwattrid")
    private Long pwattrid;
      
     
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pwt_wt_id", referencedColumnName = "wt_id")
    private WidgetTemplate widgetTemplate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pwc_pw_id", referencedColumnName = "pw_id")
    private ParametrageWidget parametrageWidget;
    
    @Size(max = 255)
    @Column(name = "attributabscisse")
    private String attributabscisse;  
    @Column(name = "variableordonnee")
    private Long variableordonnee;

    public  ParametrageWidgetAttr(WidgetTemplate widgetTemplate,ParametrageWidget parametrageWidget) {
       this.widgetTemplate = widgetTemplate; 
       this.parametrageWidget = parametrageWidget;
    }
    public  ParametrageWidgetAttr() {
      
    }
    public Long getPwattrid() {
      return pwattrid ;
       
    }

    public WidgetTemplate getWidgetTemplate() {
        return widgetTemplate;
    }

    public void setWidgetTemplate(WidgetTemplate widgetTemplate) {
        this.widgetTemplate = widgetTemplate;
    }

    public ParametrageWidget getParametrageWidget() {
        return parametrageWidget;
    }

    public void setParametrageWidget(ParametrageWidget parametrageWidget) {
        this.parametrageWidget = parametrageWidget;
    }
    
    

    public void setPwattrid(Long pwattrid) {
        this.pwattrid = pwattrid;
    }
 
    public String getAttributabscisse() {
        return attributabscisse;
    }

    public void setAttributabscisse(String attributabscisse) {
        this.attributabscisse = attributabscisse;
    }

     

    public Long getVariableordonnee() {
        return variableordonnee;
    }

    public void setVariableordonnee(Long variableordonnee) {
        this.variableordonnee = variableordonnee;
    }
    
      
}
