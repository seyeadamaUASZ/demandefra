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
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "tr_widget_profil")

@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "WidgetAttribution.findAll", query = "SELECT wa FROM WidgetAttribution wa")})
public class WidgetAttribution implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "wa_id")
    private Long waId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wa_pro_id", referencedColumnName = "pro_id")
    private Profil profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wa_attr_id", referencedColumnName = "wdg_id")
    private Widget widget;
    
    @Column(name = "wa_attr_ordre")
    private String waOrdre;

    public WidgetAttribution(Profil p, Widget wdg) {
        this.widget = wdg;
        this.profile = p;
    }

    public Long getWaId() {
        return waId;
    }

    public void setWaId(Long waId) {
        this.waId = waId;
    }

    public Profil getProfile() {
        return profile;
    }

    public void setProfile(Profil profile) {
        this.profile = profile;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
    

    public String getWaOrdre() {
		return waOrdre;
	}

	public void setWaOrdre(String waOrdre) {
		this.waOrdre = waOrdre;
	}

	public WidgetAttribution() {
    }



}