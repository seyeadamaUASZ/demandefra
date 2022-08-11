package sn.gainde2000.pi.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
* @author sagueye
*/

@Entity
@Table(name = "tr_menu_rapport", schema = "")

@XmlRootElement
@NamedQueries({
       @NamedQuery(name = "MenuRapport.findAll", query = "SELECT mr FROM MenuRapport mr")})

public class MenuRapport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "mnr_id")
    private Long mnrId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mnr_rpt_id", referencedColumnName = "rpt_id")    
    private Rapport rapport;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mnr_men_id", referencedColumnName = "men_id")    
    private Menu menu;
    
    public MenuRapport () {
    	
    }
    
    public MenuRapport (Rapport r, Menu m) {
    	 this.rapport = r;
         this.menu = m;
    }

	public Long getMnrId() {
		return mnrId;
	}

	public void setMnrId(Long mnrId) {
		this.mnrId = mnrId;
	}

	public Rapport getRapport() {
		return rapport;
	}

	public void setRapport(Rapport rapport) {
		this.rapport = rapport;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
    
    
}
