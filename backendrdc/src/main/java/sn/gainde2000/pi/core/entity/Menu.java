/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "tp_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "men_id")
    private Long menId;
    @Size(max = 255)
    @Column(name = "men_icone")
    private String menIcone;
    @Size(max = 50)
    @Column(name = "men_icone_color")
    private String menIconeColor;
    @Column(name = "men_id_parent")
    private Long menIdParent;
    @Size(max = 255)
    @Column(name = "men_nom")
    private String menNom;
    @Size(max = 255)
    @Column(name = "men_path")
    private String menPath;
    
    @OneToMany(mappedBy = "menIdParent",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Menu> sousMenus;
    
    //@JsonBackReference
    @OneToMany(mappedBy = "actMenId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Action> actions;

    public Menu() {
    }

    public Menu(Long menId) {
        this.menId = menId;
    }

    public Long getMenId() {
        return menId;
    }

    public void setMenId(Long menId) {
        this.menId = menId;
    }

    public String getMenIcone() {
        return menIcone;
    }

    public void setMenIcone(String menIcone) {
        this.menIcone = menIcone;
    }

    public Long getMenIdParent() {
        return menIdParent;
    }

    public void setMenIdParent(Long id) {
        this.menIdParent = id;
    }

    public String getMenNom() {
        return menNom;
    }

    public void setMenNom(String menNom) {
        this.menNom = menNom;
    }

    public String getMenPath() {
        return menPath;
    }

    public void setMenPath(String menPath) {
        this.menPath = menPath;
    }

    public String getMenIconeColor() {
        return menIconeColor;
    }

    public void setMenIconeColor(String menIconeColor) {
        this.menIconeColor = menIconeColor;
    }        

    public Collection<Action> getActions() {
        return actions;
    }

    public void setActions(Collection<Action> actions) {
        this.actions = actions;
    }        

    public Collection<Menu> getSousMenus() {
        return sousMenus;
    }

    public void setSousMenus(Collection<Menu> sousMenus) {
        this.sousMenus = sousMenus;
    }        

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menId != null ? menId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menId == null && other.menId != null) || (this.menId != null && !this.menId.equals(other.menId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.Menu[ menId=" + menId + " ]";
    }
    
}
