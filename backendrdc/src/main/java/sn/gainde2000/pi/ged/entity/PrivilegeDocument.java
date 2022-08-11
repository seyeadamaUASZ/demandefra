package sn.gainde2000.pi.ged.entity;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import sn.gainde2000.pi.core.entity.Profil;

@Entity
@Table(name = "tr_privilege_document", schema = "")
public class PrivilegeDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "prid_id")
    private Long pridId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prid_pro_id", referencedColumnName = "pro_id")
    private Profil profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prid_tyd_id", referencedColumnName = "tyd_id")
    private TypeDocuments typedocuments;

    public PrivilegeDocument() {
        super();

    }

    public Long getPridId() {
        return pridId;
    }

    public PrivilegeDocument(Profil profile, TypeDocuments typedocuments) {
        super();
        this.profile = profile;
        this.typedocuments = typedocuments;
    }

    public void setPridId(Long pridId) {
        this.pridId = pridId;
    }

    public Profil getProfile() {
        return profile;
    }

    public void setProfile(Profil profile) {
        this.profile = profile;
    }

    public TypeDocuments getTypedocuments() {
        return typedocuments;
    }

    public void setTypedocuments(TypeDocuments typedocuments) {
        this.typedocuments = typedocuments;
    }

}
