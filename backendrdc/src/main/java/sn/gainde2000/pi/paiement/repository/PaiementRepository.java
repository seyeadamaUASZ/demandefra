/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.paiement.entity.Paiement;

/**
 *
 * @author yougadieng
 */
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Paiement findByPaiReferencePaiement(String referencePaiement);

    List<Paiement> findBypaiReferenceClient(String username);

    Paiement findByPaiIdFacture(Long idFacture);

}
