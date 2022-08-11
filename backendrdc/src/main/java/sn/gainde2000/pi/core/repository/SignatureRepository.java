package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.Signature;
import sn.gainde2000.pi.core.entity.Utilisateur;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
	@Query("select s from Signature s where s.utilisateur.utiId =:owner AND s.codePin =:code")
	public Signature verification(@Param("owner") Long owner, @Param("code") String code);
}
