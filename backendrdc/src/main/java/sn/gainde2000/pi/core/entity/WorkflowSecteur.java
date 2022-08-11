
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

@Entity
@Table(name = "tp_workflow_secteur")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "WorkflowSecteur.findAll", query = "SELECT w FROM WorkflowSecteur w")})
public class WorkflowSecteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wsid")
    private Long wsId;
    @Size(max = 255)
    @Column(name = "wsecteur")
    private String wsSecteur;
    @Size(max = 255)
    @Column(name = "codesecteur")
    private String codeSecteur;
    public WorkflowSecteur() {
    }

    public WorkflowSecteur(Long wsId) {
        this.wsId = wsId;
    }

    public WorkflowSecteur(String wsSecteur, String codeSecteur) {
        this.wsSecteur = wsSecteur;
         this.codeSecteur = codeSecteur;
    }

    public Long getWsId() {
        return wsId;
    }

    public void setWsId(Long wsId) {
        this.wsId = wsId;
    }

    public String getWsSecteur() {
        return wsSecteur;
    }

    public void setWsSecteur(String wsSecteur) {
        this.wsSecteur = wsSecteur;
    }

public String getCodeSecteur() {
        return codeSecteur;
    }

    public void setCodeSecteur(String codeSecteur) {
        this.codeSecteur = codeSecteur;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wsId != null ? wsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workflow)) {
            return false;
        }
        WorkflowSecteur other = (WorkflowSecteur) object;
        if ((this.wsId == null && other.wsId != null) || (this.wsId != null && !this.wsId.equals(other.wsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.WorkflowSecteur[ wsId=" + wsId + " ]";
    }

}

