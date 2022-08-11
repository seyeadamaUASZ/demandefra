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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author asow
 */
@Entity
@Table(name = "tp_pwd_criteria",schema = "")
public class PwdCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pwd_id")
    private Long pwdId;
    @Size(max = 20)
    @Column(name = "pwd_car_min")
    private String pwdCarMin;
    @Size(max = 255)
    @Column(name = "pwd_maj_min")
    private String pwdMajMin;
    @Size(max = 255)
    @Column(name = "pwd_dig_min")
    private String pwdDigMin;
    @Size(max = 255)
    @Column(name = "pwd_spc_min")
    private String pwdSpcMin;
    @Column(name = "pwd_dure")
    private String pwdDure;

    public PwdCriteria() {
    }

    public PwdCriteria(Long pwdId) {
        this.pwdId = pwdId;
    }

    public PwdCriteria(String pwdCarMin, String pwdMajMin, String pwdDigMin, String pwdSpcMin, String pwdDure) {
        this.pwdCarMin = pwdCarMin;
        this.pwdMajMin = pwdMajMin;
        this.pwdDigMin = pwdDigMin;
        this.pwdSpcMin = pwdSpcMin;
        this.pwdDure = pwdDure;
    }

    public Long getPwdId() {
        return pwdId;
    }

    public void setPwdId(Long pwdId) {
        this.pwdId = pwdId;
    }

    public String getPwdCarMin() {
        return pwdCarMin;
    }

    public void setPwdCarMin(String pwdCarMin) {
        this.pwdCarMin = pwdCarMin;
    }

    public String getPwdMajMin() {
        return pwdMajMin;
    }

    public void setPwdMajMin(String pwdMajMin) {
        this.pwdMajMin = pwdMajMin;
    }

    public String getPwdDigMin() {
        return pwdDigMin;
    }
    
    public void setPwdDigMin(String pwdDigMin) {
        this.pwdDigMin = pwdDigMin;
    }

    public String getPwdSpcMin() {
        return pwdSpcMin;
    }

    public void setPwdSpcMin(String pwdSpcMin) {
        this.pwdSpcMin = pwdSpcMin;
    }

    public String getPwdDure() {
        return pwdDure;
    }

    public void setPwdDure(String pwdDure) {
        this.pwdDure = pwdDure;
    }   
}
