/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @author indiaye
 */
@Entity(name="TaskStatus")
@Table(name = "tp_task_status", schema = "")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "TaskStatus.findAll", query = "SELECT ts FROM TaskStatus ts")})
public class TaskStatus implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_id")
    private Long stsId;
    
    @Size(max = 255)
    @Column(name = "status_name")
    private String stsName;      
   
    public TaskStatus (){
        
    }
    public TaskStatus (Long stsId){
        this.stsId = stsId;
    }
     public TaskStatus (String stsName){
       
        this.stsName = stsName;
    }

    
    public Long getStsId() {
        return stsId;
    }

    public void setStsId(Long stsId) {
        this.stsId = stsId;
    }

    public String getStsName() {
        return stsName;
    }

    public void setStsName(String stsName) {
        this.stsName = stsName;
    }
       
}
