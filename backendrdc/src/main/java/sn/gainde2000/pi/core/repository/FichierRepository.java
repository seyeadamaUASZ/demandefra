/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.entity.Fichier;

/**
 *
 * @author Administrateur_Aly
 */
@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {

    Fichier findByfhrNom(String fhrNom);
/**
 *
 * @lister les fichier qui ne sont liées à une application
 */
    @Query("SELECT f FROM Fichier f where f.fhrAppId = null")
    List<Fichier> getFichierByApp();
/**
 *
 * @lister les fichier  liées à une application
 */
    @Query("SELECT f FROM Fichier f where f.fhrAppId =:id")
    List<Fichier> getFichierByAppId(@Param("id") Long id);
/**
 *
 * @lister les fichier  liées  ou non liée à une application
 */
    @Query("SELECT f FROM Fichier f where f.fhrAppId =:id or f.fhrAppId=null")
    List<Fichier> getFichierByAppIdOuLibre(@Param("id") Long id);
/**
 *
 * @Lier une fichier avec une application
 */
    @Modifying
    @Transactional
    @Query("update Fichier set fhrAppId=:idApp where fhrId=:idFichier")
    public void lierFichier(@Param("idApp") String idApp, @Param("idFichier") String idFichier);
/**
 *
 * Enlever une liaison entre une fichier et une applicatiion
 */
    @Modifying
    @Transactional
    @Query("update Fichier set fhrAppId =null where fhrId=:idFichier")
    public void enleverLiaisonFichier(@Param("idFichier") String idFichier);
}
