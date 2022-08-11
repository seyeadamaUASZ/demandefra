package sn.gainde2000.pi.core.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class NumeroProduit {
    @Id
    @GenericGenerator(name = "numero_id", strategy = "sn.gainde2000.pi.core.generate.ProduitIdGenerator")
    @GeneratedValue(generator = "numero_id")
    @Column(name = "numero_id", length = 20)
    private String numeroId;

    public NumeroProduit() {
    }

    public NumeroProduit(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }
}
