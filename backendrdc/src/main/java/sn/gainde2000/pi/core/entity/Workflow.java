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
@Table(name = "tp_workflow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workflow.findAll", query = "SELECT w FROM Workflow w")})
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkf_id")
    private Long wkfId;
    @Size(max = 255)
    @Column(name = "wkf_secteur")
    private String wkfSecteur;
    @Size(max = 255)
    @Column(name = "wkf_artifact_id")
    private String wkfArtifactId;
    @Size(max = 255)
    @Column(name = "wkf_conteneur")
    private String wkfConteneur;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
     @Size(max = 255)
    @Column(name = "description")
    private String description;
     @Size(max = 255)
    @Column(name = "groupId")
    private String groupId;
     @Size(max = 255)
    @Column(name = "version")
    private String version;
    @Size(max = 255)
    @Column(name = "wkf_process_id")
    private String wkfProcess_id;
    @Size(max = 255)
    @Column(name = "wkf_etat")
    private String wkfEtat;
    @Size(max = 255)
    @Column(name = "wkf_labelwdgt")
    private String wkfLabelwdgt;
    @Size(max = 255)
    @Column(name = "wkf_calltoaction")
    private String wkfCalltoaction;
    @ManyToOne(targetEntity = Application.class)
    @JoinColumn(name = "wkf_app_id", referencedColumnName = "app_id", insertable = true, updatable = true)
    private Application wkfAppId;

    public Workflow() {
    }

    public Workflow(Long wkfId) {
        this.wkfId = wkfId;
    }

    public Workflow(String wkfSecteur,String wkfArtifactId, String wkfConteneur, String name, String description, String groupId, String version,String wkfProcess_id, String wkfEtat,String wkfCalltoaction,String wkfLabelwdgt,
                    Application wkfAppId) {
        this.wkfSecteur = wkfSecteur;
        this.wkfArtifactId = wkfArtifactId;
        this.wkfConteneur = wkfConteneur;
        this.name = name;
        this.description = description;
        this.groupId = groupId;
        this.version = version;
        this.wkfProcess_id = wkfProcess_id;
        this.wkfEtat = wkfEtat;
        this.wkfCalltoaction = wkfCalltoaction;
        this.wkfLabelwdgt = wkfLabelwdgt;
        this.wkfAppId = wkfAppId;
    }


    

    public Long getWkfId() {
        return wkfId;
    }

    public void setWkfId(Long wkfId) {
        this.wkfId = wkfId;
    }

    public String getWkfSecteur() {
        return wkfSecteur;
    }

    public void setWkfSecteur(String wkfSecteur) {
        this.wkfSecteur = wkfSecteur;
    }

    public String getWkfArtifactId() {
        return wkfArtifactId;
    }

    public void setWkfArtifactId(String wkfArtifactId) {
        this.wkfArtifactId = wkfArtifactId;
    }

    public String getWkfConteneur() {
        return wkfConteneur;
    }

    public void setWkfConteneur(String wkfConteneur) {
        this.wkfConteneur = wkfConteneur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWkfProcess_id() {
        return wkfProcess_id;
    }
    public void setWkfProcess_id(String wkfProcess_id) {
        this.wkfProcess_id = wkfProcess_id;
    }
    public String getWkfEtat() {
        return wkfEtat;
    }
    public void setWkfEtat(String wkfEtat) {
        this.wkfEtat = wkfEtat;
    }

    public String getWkfCalltoaction() {
        return wkfCalltoaction;
    }
    public void setWkfCalltoaction(String wkfCalltoaction) {
        this.wkfCalltoaction = wkfCalltoaction;
    }

    public String getWkfLabelwdgt() {
        return wkfLabelwdgt;
    }
    public void setWkfLabelwdgt(String wkfLabelwdgt) {
        this.wkfLabelwdgt = wkfLabelwdgt;
    }

    public Application getWkfAppId() {
        return wkfAppId;
    }

    public void setWkfAppId(Application wkfAppId) {
        this.wkfAppId = wkfAppId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkfId != null ? wkfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workflow)) {
            return false;
        }
        Workflow other = (Workflow) object;
        if ((this.wkfId == null && other.wkfId != null) || (this.wkfId != null && !this.wkfId.equals(other.wkfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Workflow[ wkfId=" + wkfId + " ]";
    }

}
