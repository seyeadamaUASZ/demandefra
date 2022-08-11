package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Icone;

@Repository
public interface IconeRepository extends JpaRepository<Icone, Long> {

}
