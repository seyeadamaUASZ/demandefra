/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
 * @author yougadieng
 */
@Entity
@Table(name = "tp_parametre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametre.findAll", query = "SELECT p FROM Parametre p")
    , @NamedQuery(name = "Parametre.findByParamId", query = "SELECT p FROM Parametre p WHERE p.paramId = :paramId")
    , @NamedQuery(name = "Parametre.findByParamUtiUsername", query = "SELECT p FROM Parametre p WHERE p.paramUtiUsername = :paramUtiUsername")})
public class Parametre implements Serializable{

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "param_id")
    private Integer paramId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "param_uti_username")
    private String paramUtiUsername;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Langue.class)
    @JoinColumn(name = "param_lng_id", referencedColumnName = "lng_id", insertable = true, updatable = true)
    private Langue param_lng_id;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Theme.class)
    @JoinColumn(name = "param_thm_id", referencedColumnName = "thm_id", insertable = true, updatable = true)
    private Theme param_thm_id;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Image.class)
    @JoinColumn(name = "param_img_id", referencedColumnName = "img_id", insertable = true, updatable = true)
    private Image param_img_id;
    private String param_nom_app;

    public Parametre() {
    }

    public Parametre(Integer paramId) {
        this.paramId = paramId;
    }

    public Parametre(Integer paramId, String paramUtiUsername) {
        this.paramId = paramId;
        this.paramUtiUsername = paramUtiUsername;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamUtiUsername() {
        return paramUtiUsername;
    }

    public void setParamUtiUsername(String paramUtiUsername) {
        this.paramUtiUsername = paramUtiUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramId != null ? paramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametre)) {
            return false;
        }
        Parametre other = (Parametre) object;
        if ((this.paramId == null && other.paramId != null) || (this.paramId != null && !this.paramId.equals(other.paramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Parametre[ paramId=" + paramId + " param_img_id=" + param_img_id + "]";
    }

    /**
     * @return the param_lng_id
     */
    public Langue getParam_lng_id() {
        return param_lng_id;
    }

    /**
     * @param param_lng_id the param_lng_id to set
     */
    public void setParam_lng_id(Langue param_lng_id) {
        this.param_lng_id = param_lng_id;
    }

    /**
     * @return the param_thm_id
     */
    public Theme getParam_thm_id() {
        return param_thm_id;
    }

    public void setParam_thm_id(Theme param_thm_id) {
        this.param_thm_id = param_thm_id;
    }
     public Image getParam_img_id() {
        return param_img_id;
    }

    public void setParam_img_id(Image param_img_id) {
        this.param_img_id = param_img_id;
    }

    /**
     * @return the param_nom_app
     */
    public String getParam_nom_app() {
        return param_nom_app;
    }

    /**
     * @param param_nom_app the param_nom_app to set
     */
    public void setParam_nom_app(String param_nom_app) {
        this.param_nom_app = param_nom_app;
    }

}
