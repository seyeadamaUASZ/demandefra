package sn.gainde2000.pi.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class NumeroDemande {
	@Id
	@GenericGenerator(name = "numero_demande_id", strategy = "sn.gainde2000.pi.core.generate.DemandeIdGenerator")
	@GeneratedValue(generator = "numero_demande_id")
	@Column(name = "numero_demande_id", length = 20)
	private String numeroDemandeId;

	public NumeroDemande() {
		super();
	}

	public String getNumeroDemandeId() {
		return numeroDemandeId;
	}

	public void setNumeroDemandeId(String numeroDemandeId) {
		this.numeroDemandeId = numeroDemandeId;
	}

	public NumeroDemande(String numeroDemandeId) {
		super();
		this.numeroDemandeId = numeroDemandeId;
	}
}
