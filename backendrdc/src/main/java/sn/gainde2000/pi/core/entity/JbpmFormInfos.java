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
@Entity(name="Jbpmformulaire")
@Table(name = "tp_jbpmformulaire" , schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jbpmformulaire.findAll", query = "SELECT jf FROM Jbpmformulaire jf")})
public class JbpmFormInfos  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "jfrm_id")
    private Long jfrmId;
    @Size(max = 255)
    @Column(name = "jfrm_workflow")
    private String jfrmWorkflow;
    @Size(max = 255)
    @Column(name = "jfrm_formulaire")
    private String jfrmFormulaire;
    @Size(max = 255)
    @Column(name = "jfrm_urlfile")
    private String jfrmUrlfile;  
    private StatusFrmWorkflow statusFrmWorkflow;
    @Column(name = "jfrm_idworkflow")
    private Long jfrmIdworkflow;
    @Column(name = "jfrm_primaire")
    private Boolean jfrmPrimaire;
    public JbpmFormInfos(){
        
    }
    
    public JbpmFormInfos(Long jfrmId){
        this.jfrmId = jfrmId;
    }
    
    public JbpmFormInfos(String jfrmWorkflow,String jfrmFormulaire,String jfrmUrlfile,Long jfrmIdworkflow,Boolean jfrmPrimaire){
        this.jfrmWorkflow = jfrmWorkflow;
        this.jfrmFormulaire = jfrmFormulaire;
        this.jfrmIdworkflow = jfrmIdworkflow;
        this.jfrmPrimaire = jfrmPrimaire;
    }
    
    public Long getJfrmId(){
        return jfrmId;
    }
    public void setJfrmId(Long jfrmId){
        this.jfrmId = jfrmId;
    }
    
    public String getJfrmWorkflow(){
        return jfrmWorkflow;
    }
    
    public void setJfrmWorkflow(String jfrmWorkflow){
        this.jfrmWorkflow = jfrmWorkflow;
    }
    
     public String getJfrmFormulaire(){
        return jfrmFormulaire;
    }
    
    public void setJfrmFormulaire(String jfrmFormulaire){
        this.jfrmFormulaire = jfrmFormulaire;
    }
    
    public String getJfrmUrlfile(){
        return jfrmUrlfile;
    }
    
    public void setJfrmUrlfile(String jfrmUrlfile){
        this.jfrmUrlfile = jfrmUrlfile;
    }

	public StatusFrmWorkflow getStatusFrmWorkflow() {
		return statusFrmWorkflow;
	}

	public void setStatusFrmWorkflow(StatusFrmWorkflow statusFrmWorkflow) {
		this.statusFrmWorkflow = statusFrmWorkflow;
	}
    public Long getJfrmIdworkflow(){
        return jfrmIdworkflow;
    }
    
    public void setJfrmIdworkflow(Long jfrmIdworkflow){
        this.jfrmIdworkflow = jfrmIdworkflow;
    }
    public Boolean getJfrmPrimaire(){
        return jfrmPrimaire;
    }
    
    public void setJfrmJfrmPrimaire(Boolean jfrmPrimaire){
        this.jfrmPrimaire = jfrmPrimaire;
    }
    
    
}
