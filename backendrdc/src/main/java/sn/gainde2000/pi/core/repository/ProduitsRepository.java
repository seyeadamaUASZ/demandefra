
package sn.gainde2000.pi.core.repository;

import sn.gainde2000.pi.core.entity.Produits;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Long> {
    @Query("SELECT s from Produits s WHERE s.id =:id ")
    Produits getOneProduits(@Param("id") Long id);
    
    @Query("SELECT count(s.id) from Produits s Where s.status = 6")
    Integer totalDemandeAcceptees();
    
    @Query("SELECT count(s.id) from Produits s Where s.status = 10")
    Integer totalDemandeRejetees();
    
    @Query("SELECT s FROM Produits s WHERE s.autFra =:numeroFra")
    Produits validationFra(String numeroFra);
    
    List<Produits> findAllByDemandeautfraId(@Param("idDemande") Long idDemande);
}