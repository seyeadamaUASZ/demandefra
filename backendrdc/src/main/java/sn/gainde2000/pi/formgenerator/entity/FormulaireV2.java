package sn.gainde2000.pi.formgenerator.entity;



import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import sn.gainde2000.pi.core.entity.Workflow;

/**
 *
 * @author pbf
 */
@Entity
@Table(name = "tp_formulaire_v2",schema = "")
public class FormulaireV2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "frm_id")
    private Long frmId;
    @Size(max = 255)
    @Column(name = "frm_description")
    private String frmDescription;
    @Size(max = 255)
    @Column(name = "frm_nom")
    private String frmNom;
    @Column(name = "frm_status")
    private boolean frmStatus;
    @Size(max = 255)
    @Column(name = "frm_valider")
    private String frmValider;
    @Column(name = "frm_app_id")
    private Long frmAppId;
    @Column(name = "frm_modeliser")
    private boolean frmModeliser;
    
    
    private Long numberOfFile;

    @OneToMany(targetEntity = Step.class,mappedBy = "formulaire", cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Step> steps;
    
    public FormulaireV2() {
    }

    public FormulaireV2(Long frmId) {
        this.frmId = frmId;
    }

    public Long getFrmId() {
        return frmId;
    }

    public void setFrmId(Long frmId) {
        this.frmId = frmId;
    }

    public String getFrmDescription() {
        return frmDescription;
    }

    public void setFrmDescription(String frmDescription) {
        this.frmDescription = frmDescription;
    }

    public String getFrmNom() {
        return frmNom;
    }

    public void setFrmNom(String frmNom) {
        this.frmNom = frmNom;
    }

    public Long getFrmAppId() {
        return frmAppId;
    }

    public void setFrmAppId(Long frmAppId) {
        this.frmAppId = frmAppId;
    }

    public Boolean getFrmStatus() {
        return frmStatus;
    }

    public void setFrmStatus(boolean frmStatus) {
        this.frmStatus = frmStatus;
    }
    
    
	public String getFrmValider() {
		return frmValider;
	}

	public void setFrmValider(String frmValider) {
		this.frmValider = frmValider;
	}
	



	public boolean isFrmModeliser() {
		return frmModeliser;
	}

	public void setFrmModeliser(boolean frmModeliser) {
		this.frmModeliser = frmModeliser;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (frmId != null ? frmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FormulaireV2)) {
            return false;
        }
        FormulaireV2 other = (FormulaireV2) object;
        if ((this.frmId == null && other.frmId != null) || (this.frmId != null && !this.frmId.equals(other.frmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Formulaire[ frmId=" + frmId + " ]";
    }

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public Long getNumberOfFile() {
		return numberOfFile;
	}

	public void setNumberOfFile(Long numberOfFile) {
		this.numberOfFile = numberOfFile;
	}

	
    
    
}
