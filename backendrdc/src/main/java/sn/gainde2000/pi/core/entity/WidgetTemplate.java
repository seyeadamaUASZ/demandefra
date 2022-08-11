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
@Table(name = "tp_widget_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WidgetTemplate.findAll", query = "SELECT wt FROM WidgetTemplate wt")})
public class WidgetTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wt_id" )
    private Long wtId;
    @Size(max = 255)
    @Column(name = "wt_nom")
    private String wtNom;
    
    public WidgetTemplate(){
        
    }
    
    public WidgetTemplate(Long wtId) {
        this.wtId = wtId;
    }
      
    public Long getWtId() {
        return wtId;
    }
    
     public void setWtId(Long wtId) {
        this.wtId = wtId;
    }

    public String getWtNom() {
        return wtNom;
    }

    public void setWtNom(String wtNom) {
        this.wtNom = wtNom;
    }
     
    
     
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (wtId != null ? wtId.hashCode() : 0);
        return hash;
    }
    
      @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WidgetTemplate)) {
            return false;
        }
        WidgetTemplate other = (WidgetTemplate) object;
        if ((this.wtId == null && other.wtId != null) || (this.wtId != null && !this.wtId.equals(other.wtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.WidgetTemplate[ wtId=" + wtId + " ]";
    }
}
