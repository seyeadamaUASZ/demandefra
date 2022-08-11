package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Fonctionnalite;

@Repository
public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalite, Long> {

    public Fonctionnalite findByFonId(Long id);

    @Query("select f from Fonctionnalite f  where f.fonModId=:fonModId")
    public List<Fonctionnalite> findByfonModId(@Param("fonModId") Long fonModId);
    
     Fonctionnalite findByFonNom(String nom);

     /*@Query(value = "SELECT f.fon_id,f.fon_nom,f.fon_code,f.fon_description,f.fon_men_id,f.fon_mod_id, tr.appli_fon_is_active\n"
            + "FROM   tp_fonctionnalite f JOIN tr_app_fonc tr\n"
            + "ON     (f.fon_id = tr.appli_fonc_fon_id) where f.fon_mod_id=:fonModId group By f.fon_id", nativeQuery = true)
    public List<Fonctionnalite> findByfonModId(@Param("fonModId") Long fonModId);*/
    
   /* @Query(value = "SELECT tp.fon_id,tp.fon_nom,tp.fon_code,tp.fon_description,tp.fon_men_id,tp.fon_mod_id, t.appli_fon_is_active\n"
            +" from tp_fonctionnalite as tp LEFT OUTER JOIN tr_app_fonc as t ON t.appli_fonc_fon_id=tp.fon_id\n"
            + " where tp.fon_mod_id=:fonModId group By tp.fon_id", nativeQuery = true)
    public List<Fonctionnalite> findByfonModId(@Param("fonModId") Long fonModId);*/

}
