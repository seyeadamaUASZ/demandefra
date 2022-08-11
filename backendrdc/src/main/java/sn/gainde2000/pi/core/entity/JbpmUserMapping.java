package sn.gainde2000.pi.core.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "td_jbpm_user_mapping",  schema = "")

public class JbpmUserMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "jbpm_u_id")
    private Long jbpmUid;
    @Size(max = 255)
    @Column(name = "indus_user")
    private String indusUser;
    @Size(max = 255)
    @Column(name = "jbpm_user")
    private String jbpmUser;
    @Size(max = 255)
    @Column(name = "jbpm_pass")
    private String jbpmPass;
    @Size(max = 255)
    @Column(name = "jbpm_groupe")
    private String jbpmGroupe;
    public JbpmUserMapping( ) {

    }
    public JbpmUserMapping(String indusUser, String jbpmUser, String jbpmPass, String jbpmGroupe) {
        this.indusUser = indusUser;
        this.jbpmUser = jbpmUser;
        this.jbpmPass = jbpmPass;
        this.jbpmGroupe = jbpmGroupe;
    }

    public JbpmUserMapping(Long jbpmUid) {
        this.jbpmUid = jbpmUid;
    }

    public Long getJbpmUIdid() {
        return jbpmUid;
    }

    public void setJbpmUIdid(Long jbpmUid) {
        this.jbpmUid = jbpmUid;
    }

    public String getIndusUser() {
        return indusUser;
    }

    public void setIndusUser(String indusUser) {
        this.indusUser = indusUser;
    }

    public String getJbpmUser() {
        return jbpmUser;
    }

    public void setJbpmUser(String jbpmUser) {
        this.jbpmUser = jbpmUser;
    }

    public String getJbpmPass() {
        return jbpmPass;
    }

    public void setJbpmPass(String JbpmPass) {
        this.jbpmPass = jbpmPass;
    }

    public String getJbpmGroupe() {
        return jbpmGroupe;
    }

    public void setJbpmGroupe(String jbpmGroupe) {
        this.jbpmGroupe = jbpmGroupe;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jbpmUid != null ? jbpmUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JbpmUserMapping)) {
            return false;
        }
        JbpmUserMapping other = (JbpmUserMapping) object;
        if ((this.jbpmUid == null && other.jbpmUid != null) || (this.jbpmUid != null && !this.jbpmUid.equals(other.jbpmUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sn.gainde2000.pi.core.entity.JbpmUserMapping[ jbpmUid=" + jbpmUid + " ]";
    }

}

