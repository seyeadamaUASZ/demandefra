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
@Entity(name="RegleGestion")
@Table(name = "tp_regle_gestion", schema = "")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "RegleGestion.findAll", query = "SELECT rg FROM RegleGestion rg")})
public class RegleGestion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
     @NotNull
    @Column(name = "rg_id")
    private Long rgId;
    @Size(max = 255)
    @Column(name = "rg_titre")
    private String rgTitre;
    @Column(name = "rg_var_form")          
    private Long rgVarFomrId; 
    @Size(max = 255)
    @Column(name = "rg_condition")
    private String rgCondition;
    @Size(max = 255)
    @Column(name = "rg_donnee_condition")
    private String rgDonneeCondition;
    @Column(name = "rg_tsk_id")          
    private Long rgTskId;
    @Column(name = "rg_wrfl_id")          
    private Long rgwrflId;

    public Long getRgTskId() {
        return rgTskId;
    }

    public void setRgTskId(Long rgTskId) {
        this.rgTskId = rgTskId;
    }

    public Long getRgwrflId() {
        return rgwrflId;
    }

    public void setRgwrflId(Long rgwrflId) {
        this.rgwrflId = rgwrflId;
    }
    
    public RegleGestion(){
        
    }
    
    public RegleGestion(Long rgId){
        this.rgId = rgId;
    }
    
    public Long getRgId() {
        return rgId;
    }

    public void setRgId(Long rgId) {
        this.rgId = rgId;
    }

    public String getRgTitre() {
        return rgTitre;
    }

    public void setRgTitre(String rgTitre) {
        this.rgTitre = rgTitre;
    }

    public Long getRgVarFomrId() {
        return rgVarFomrId;
    }

    public void setRgVarFomrId(Long rgVarFomrId) {
        this.rgVarFomrId = rgVarFomrId;
    }

    public String getRgCondition() {
        return rgCondition;
    }

    public void setRgCondition(String rgCondition) {
        this.rgCondition = rgCondition;
    }

    public String getRgDonneeCondition() {
        return rgDonneeCondition;
    }

    public void setRgDonneeCondition(String rgDonneeCondition) {
        this.rgDonneeCondition = rgDonneeCondition;
    }
    
    
}
