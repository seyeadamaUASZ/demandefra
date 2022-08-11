/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.paiement.entity.OperateurPaiement;

/**
 *
 * @author yougadieng
 */
@Repository
public interface OperateurPaiementRepository extends JpaRepository<OperateurPaiement, Long> {

    @Modifying
    @Transactional
    @Query("Update OperateurPaiement op set op.opaIsActive=true where op.opaId=:idOpa")
    void activerOperateur(@Param("idOpa") Long idOpa);
    
    @Modifying
    @Transactional
    @Query("Update OperateurPaiement op set op.opaIsActive=false where op.opaId=:idOpa")
    void desactiverOperateur(@Param("idOpa") Long idOpa);
}
