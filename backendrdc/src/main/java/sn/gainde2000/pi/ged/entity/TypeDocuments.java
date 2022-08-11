package sn.gainde2000.pi.ged.entity;

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

@Entity
@Table(name = "tp_type_documents", schema = "")
public class TypeDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "tyd_id")
    private Long tydId;

    @Size(max = 255)
    @Column(name = "tyd_libelle")
    private String tydLibelle;

    @Size(max = 255)
    @Column(name = "tyd_description")
    private String tydDescription;

    public TypeDocuments() {
        super();
    }

    public Long getTydId() {
        return tydId;
    }

    public void setTydId(Long tydId) {
        this.tydId = tydId;
    }

    public String getTydLibelle() {
        return tydLibelle;
    }

    public void setTydLibelle(String tydLibelle) {
        this.tydLibelle = tydLibelle;
    }

    public String getTydDescription() {
        return tydDescription;
    }

    public void setTydDescription(String tydDescription) {
        this.tydDescription = tydDescription;
    }

    public TypeDocuments(@NotNull Long tydId) {
        super();
        this.tydId = tydId;
    }

}
