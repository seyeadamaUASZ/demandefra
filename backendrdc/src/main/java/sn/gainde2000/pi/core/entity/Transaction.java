package sn.gainde2000.pi.core.entity;


import sn.gainde2000.pi.core.tools.TypeTransaction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="td_transaction")
public class Transaction {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateTransaction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;
    private TypeTransaction typeTransaction;
    private String description;
    private String nomApplication;

    public String getNomApplication() {
        return nomApplication;
    }

    public void setNomApplication(String nomApplication) {
        this.nomApplication = nomApplication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}
