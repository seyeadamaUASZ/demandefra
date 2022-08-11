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
@Entity(name="TaskTimer")
@Table(name = "tp_task_timer", schema = "")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "TaskTimer.findAll", query = "SELECT tst FROM TaskTimer tst")})
public class TimerTask implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "tm_id")
    private Long tmId;
    @Size(max = 255)
    @Column(name = "tm_titre")
    private String tmTitre;
    @Column(name = "tm_task_id")          
    private Long tmTaskId; 
    @Column(name = "tm_tasksuiv_id")          
    private Long tmTaskSuivId; 
    @Size(max = 255)
    @Column(name = "tm_timerdata")          
    private String tmTimerData;
    @Size(max = 255)
    @Column(name = "tm_timertype")          
    private String tmTimerType;
@Column(name = "tm_workflowid")          
    private String tmWorkflowId;

    public TimerTask(){
        
    }
     public TimerTask(Long tmId){
        this.tmId = tmId;
    }
     
    
    
    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }

    public String getTmTitre() {
        return tmTitre;
    }

    public void setTmTitre(String tmTitre) {
        this.tmTitre = tmTitre;
    }

    public Long getTmTaskId() {
        return tmTaskId;
    }

    public void setTmTaskId(Long tmTaskId) {
        this.tmTaskId = tmTaskId;
    }

    public Long getTmTaskSuivId() {
        return tmTaskSuivId;
    }

    public void setTmTaskSuivId(Long tmTaskSuivId) {
        this.tmTaskSuivId = tmTaskSuivId;
    }

    public String getTmTimerData() {
        return tmTimerData;
    }

    public void setTmTimerData(String tmTimerData) {
        this.tmTimerData = tmTimerData;
    }

    public String getTmTimerType() {
        return tmTimerType;
    }

    public void setTmTimerType(String tmTimerType) {
        this.tmTimerType = tmTimerType;
    }

    public String getTmWorkflowId() {
        return tmWorkflowId;
    }

    public void setTmWorkflowId(String tmWorkflowId) {
        this.tmWorkflowId = tmWorkflowId;
    }
    
    
}
