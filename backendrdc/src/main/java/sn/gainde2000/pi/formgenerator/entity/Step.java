package sn.gainde2000.pi.formgenerator.entity;

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

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tr_step",schema = "")
public class Step {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "step_id")
    private Long id;
    
    private String stepName;
    
    @ManyToOne(targetEntity = FormulaireV2.class)
    @JoinColumn(name="formulaire" ,nullable = false, referencedColumnName = "frm_id")
    @JsonIgnore
    private FormulaireV2 formulaire;
    
    @OneToMany(targetEntity = ChampsV2.class,mappedBy = "step", cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<ChampsV2> champs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public FormulaireV2 getFormulaire() {
		return formulaire;
	}

	public void setFormulaire(FormulaireV2 formulaire) {
		this.formulaire = formulaire;
	}

	public List<ChampsV2> getChamps() {
		return champs;
	}

	public void setChamps(List<ChampsV2> champs) {
		this.champs = champs;
	}
    
    
    
    
    
    
}
