/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tr_transition", schema = "")
public class Transition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trnId;
    private String trnAction;
    private Long trnTskActuel;
    private Long trnTskSuiv; 
       @ManyToOne(targetEntity = Workflow.class)
    @JoinColumn(name = "trn_wkf_id", referencedColumnName = "wkf_id", insertable = true, updatable = true)
    private Workflow trnWkfId; 

    public Long getTrnId() {
        return trnId;
    }

    public void setTrnId(Long trnId) {
        this.trnId = trnId;
    }

    public Workflow getTrnWkfId() {
        return trnWkfId;
    }

    public void setTrnWkfId(Workflow trnWkfId) {
        this.trnWkfId = trnWkfId;
    }

    
    
    public String getTrnAction() {
        return trnAction;
    }

    public void setTrnAction(String trnAction) {
        this.trnAction = trnAction;
    }

    public Long getTrnTskActuel() {
        return trnTskActuel;
    }

    public void setTrnTskActuel(Long trnTskActuel) {
        this.trnTskActuel = trnTskActuel;
    }

    public Long getTrnTskSuiv() {
        return trnTskSuiv;
    }

    public void setTrnTskSuiv(Long trnTskSuiv) {
        this.trnTskSuiv = trnTskSuiv;
    }
  
   /* @ManyToOne
    @JoinColumn(name = "trn_tsk_actuel", referencedColumnName = "tsk_id", insertable = true, updatable = true)*/
   /* @ManyToOne
    @JoinColumn(name = "trn_tsk_suiv", referencedColumnName = "tsk_id", insertable = true, updatable = true)*/
    

    public Transition() {
    }
    
    @Override
    public String toString() {
        return "Transition{" + "trnId=" + trnId + ", trnAction=" + trnAction + ", trnTskActuel=" + trnTskActuel + ", trnTskSuiv=" + trnTskSuiv + '}';
    }
    

}
