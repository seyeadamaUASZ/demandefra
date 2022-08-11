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
@Entity(name="Workflowformconfig")
@Table(name = "tp_workflowformconfig" , schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workflowformconfig.findAll", query = "SELECT wfc FROM Workflowformconfig wfc")})
public class WorkflowFormConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wfc_id")
    private Long wfcId; 
    @Column(name = "wfc_idformulaire")
    private Long wfcIdFormulaire;  
    @ManyToOne(targetEntity = Workflow.class)
    @JoinColumn(name = "wfc_idworkflow", referencedColumnName = "wkf_id", insertable = true, updatable = true)
    private Workflow workflowNameId;
    @Column(name = "wfc_primaire")
    private Boolean wfcPrimaire;
    @Column(name = "status_wcf_workflow")
    private StatusWcfWorkflow statusWcfWorkflow;
    
    public WorkflowFormConfig(){
        
    }

    public Workflow getWorkflowname() {
        return workflowNameId;
    }

    public void setWorkflowname(Workflow workflowNameId) {
        this.workflowNameId = workflowNameId;
    }
    public WorkflowFormConfig(Long wfcId,Workflow workflowNameId){
        this.wfcId = wfcId;
        this.workflowNameId = workflowNameId;
    }

    public Long getWfcId() {
        return wfcId;
    }

    public void setWfcId(Long wfcId) {
        this.wfcId = wfcId;
    }

    public Long getWfcIdFormulaire() {
        return wfcIdFormulaire;
    }

    public void setWfcIdFormulaire(Long wfcIdFormulaire) {
        this.wfcIdFormulaire = wfcIdFormulaire;
    }

      

    public Boolean getWfcPrimaire() {
        return wfcPrimaire;
    }
 
    public void setWfcPrimaire(Boolean wfcPrimaire) {
        this.wfcPrimaire = wfcPrimaire;
    }

    public StatusWcfWorkflow getStatusWcfWorkflow() {
        return statusWcfWorkflow;
    }

    public void setStatusWcfWorkflow(StatusWcfWorkflow statusWcfWorkflow) {
        this.statusWcfWorkflow = statusWcfWorkflow;
    }

     
    
}
