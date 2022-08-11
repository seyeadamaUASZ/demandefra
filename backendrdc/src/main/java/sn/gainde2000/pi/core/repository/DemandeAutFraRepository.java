
package sn.gainde2000.pi.core.repository;

import sn.gainde2000.pi.core.entity.DemandeAutFra;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DemandeAutFraRepository extends JpaRepository<DemandeAutFra, Long> {
    @Query("SELECT s from DemandeAutFra s WHERE s.status IN (SELECT t.tskStatusId from Task t WHERE t.poOwner.proId=:poOwner )")
    Iterable<DemandeAutFra> listDemandeAutFra(@Param("poOwner") Long poOwner);

    @Query("SELECT s from DemandeAutFra s WHERE s.owner =:owner ")
    Iterable<DemandeAutFra> listDemandeAutFraById(@Param("owner") Long owner);

    @Query("SELECT s from DemandeAutFra s WHERE s.id =:id ")
    DemandeAutFra getOneDemandeAutFra(@Param("id") Long id);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 1 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeEnBrouillon(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 11 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAPayer(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 9 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeACorriger(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 2 OR s.status = 3 OR s.status = 6 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeEnCours(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 4 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeAutProvDelivre(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 7 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeAutDefDelivre(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s where s.status = 10 AND s.owner =:owner order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeRejetees(@Param("owner") Long owner);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 2 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefBureau();

    @Query("SELECT s from DemandeAutFra s WHERE s.status = 3 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraATraiterChefDivision();

    @Query("SELECT s from DemandeAutFra s WHERE s.status = 10 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraRejetees();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 9 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraRenvoyees();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = -9 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraRenvoyeesChefDivision();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 4 OR s.status = 6 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraPro();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 4 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraATraiterANAC();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 6 OR s.status = 7 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraTraiterANAC();
    
    @Query("SELECT s from DemandeAutFra s WHERE s.status = 7 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> listeDemandeAutFraTerminees();
    
    Iterable<DemandeAutFra> findByEmailResponsable(@Param("email") String email);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.emailResponsable =:email AND (s.status = 9 OR s.status = 10) order by s.dateSoumission desc")
    Iterable<DemandeAutFra> mesDemandesRejetees(@Param("email") String email);
    
    @Query("SELECT s from DemandeAutFra s WHERE s.emailResponsable =:email AND s.status = 7 order by s.dateSoumission desc")
    Iterable<DemandeAutFra> mesDemandesAcceptees(@Param("email") String email);
    
    DemandeAutFra findByNumeroFacture(Long numeroFacture);

    @Query("SELECT count(s.id) from DemandeAutFra s Where s.status = 3")
    Integer totalDemandeATraiter();
        
    @Query("SELECT count(p.id) from Produits p, Motifrejetourenvoi m where m.idLink = p.id and m.status=4")
    Integer totalProduitsAcceptes();
    
    @Query("SELECT count(p.id) from Produits p,Motifrejetourenvoi m where m.idLink = p.id and m.status = 10")
    Integer totalProduitsRejetes();
    
    @Query("SELECT count(s.id) from DemandeAutFra s")
    Integer totalDemandeSoumise();
    
    @Query("SELECT count(s.id) from DemandeAutFra s Where s.status = 7")
    Integer totalDemandeTraitees();

    @Query("SELECT d from DemandeAutFra d where d.emailResponsable =:email and d.numdemande =:numero")
    DemandeAutFra getDemandeAutFraByEmailAndNumero(@Param("email") String email,@Param("numero")String numero);
   
    @Query("select count(*) from DemandeAutFra d where d.status = 9")
   Integer nombreDemandeRenvoyeeChefDeBureau();
   
   @Query("select count (*) from DemandeAutFra d where d.status=3")
   Integer nombreDemandeValideeChefBureau();
   
   @Query("select count (*) from DemandeAutFra d where d.status=3 or d.status = 4 or d.status = 5 or d.status = 6 or d.status = 7 or d.status = 8 or d.status = 10")
   Integer nombreDemandeATraiterChefDivision();
   
   @Query("select count(*) from DemandeAutFra d where d.status = 2")
   Integer nombreDemandeATraiterChefDeBureau();
   
   @Query("select count(*) from DemandeAutFra d where d.status = 4")
   Integer nombreDemandeATraiterANAC();
   
   @Query("select count(*) from DemandeAutFra d where d.status = 6 or d.status = 7")
   Integer nombreDemandeTraiterANAC();
   
   @Query("select count(*) from DemandeAutFra d where (d.status = 2 or d.status = 3 or d.status = 4 or d.status = 5 or d.status = 6 or d.status = 7 or d.status = 8 or d.status = 9 or d.status = 10 or d.status = 11 or d.status = 12) and d.emailResponsable =:email")
   Integer nombreDemandeSoumises(@Param("email") String email);
   
   @Query("select count(*) from DemandeAutFra d where (d.status = 2 or d.status = 3 or d.status = 4 or d.status = 5 or d.status = 6 or d.status = 8 or d.status = 11 or d.status = 12) and d.emailResponsable =:email")
   Integer nombreDemandeEnCours(@Param("email") String email);
      
   @Query(value="select count(*) as total,"    		
		   + "sum(case when status = 2 or status = 3 or status = 4 or status = 5 or status = 6 or status = 7 or status = 8 or status = 9 or status = 10 or status = 11 or status =12 then 1 else 0 end) as soumises,"    		
		   + "sum(case when status = 2 or status = 3 or status = 4 or status = 6 then 1 else 0 end) as encours,"    		
		   + "sum(case when status = 9 then 1 else 0 end) as corriger,"    		
		   + "sum(case when status = 10 then 1 else 0 end) as rejetes,"   
		   + "sum(case when status = 4 then 1 else 0 end) as delivrees"
		   + " from demande_aut_fra where demande_aut_fra.owner =:owner", nativeQuery=true)    
   List<Object[]> statistiquesChefAntenne(@Param("owner") Long owner);
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission)"
           + " WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END)"
           + " AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END)"
           + " AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END)"
           + " AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END)"
           + " AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END)"
           + " AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM demande_aut_fra,motifrejetourenvoi WHERE \n"
           + "  date_soumission BETWEEN (SELECT  MIN(date_soumission) \n"
           + "FROM demande_aut_fra) AND (SELECT  MAX(date_soumission) \n"
           + "FROM demande_aut_fra ) AND motifrejetourenvoi.po_owner=:powner AND motifrejetourenvoi.id_link = demande_aut_fra.id", nativeQuery = true)
   public List<Object[]> nbrDemandeAutFraByDate(@Param("powner") Long powner);
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission)"
           + " WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END)"
           + " AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END)"
           + " AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END)"
           + " AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END)"
           + " AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END)"
           + " AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December' FROM demande_aut_fra,motifrejetourenvoi, produits WHERE \n"
           + "  date_soumission BETWEEN (SELECT  MIN(date_soumission) \n"
           + "FROM demande_aut_fra) AND (SELECT  MAX(date_soumission) \n"
           + "FROM demande_aut_fra ) AND demande_aut_fra.status = 7 AND (motifrejetourenvoi.id_link = produits.id AND demande_aut_fra.id = produits.demandeautfra)", nativeQuery = true)
   public List<Object[]> nbDemandeTraitees();
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission)"
   		+ " WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END)"
   		+ " AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END)"
   		+ " AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END)"
   		+ " AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END)"
   		+ " AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END)"
   		+ " AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM demande_aut_fra WHERE "
   		+ "date_soumission BETWEEN (SELECT MIN(date_soumission)"
   		+ " FROM demande_aut_fra) AND (SELECT  MAX(date_soumission)"
   		+ " FROM demande_aut_fra ) AND demande_aut_fra.owner=:owner", nativeQuery = true)
   public List<Object[]> nbrDemandeAutFraByMois(@Param("owner") Long owner);
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM demande_aut_fra WHERE date_soumission BETWEEN (SELECT MIN(date_soumission) FROM demande_aut_fra) AND (SELECT  MAX(date_soumission) FROM demande_aut_fra ) AND (demande_aut_fra.status = 4 OR demande_aut_fra.status = 5 OR demande_aut_fra.status = 6 OR demande_aut_fra.status = 7 OR demande_aut_fra.status = 10)", nativeQuery = true)
   public List<Object[]> nbrDemandeAutFraByAnnee();
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM demande_aut_fra WHERE date_soumission BETWEEN (SELECT MIN(date_soumission) FROM demande_aut_fra) AND (SELECT  MAX(date_soumission) FROM demande_aut_fra ) AND demande_aut_fra.email_responsable =:email", nativeQuery = true)
   public List<Object[]> statistiqueDemandeur(@Param("email") String email);
   
   @Query(value = "SELECT SUM(CASE MONTH(date_soumission) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(date_soumission) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(date_soumission) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(date_soumission) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(date_soumission) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(date_soumission) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(date_soumission) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(date_soumission) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(date_soumission) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(date_soumission) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(date_soumission) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(date_soumission) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM demande_aut_fra WHERE date_soumission BETWEEN (SELECT MIN(date_soumission) FROM demande_aut_fra) AND (SELECT  MAX(date_soumission) FROM demande_aut_fra ) AND (demande_aut_fra.status = 6 OR demande_aut_fra.status = 7)", nativeQuery = true)
   public List<Object[]> statistiqueLanac();

   @Query(value = "SELECT COUNT(demande_aut_fra.status), tp_task_status.status_name FROM " +
           "demande_aut_fra, tp_task_status, motifrejetourenvoi where demande_aut_fra.status=tp_task_status.status_id AND motifrejetourenvoi.po_owner=:powner AND motifrejetourenvoi.id_link = demande_aut_fra.id GROUP BY demande_aut_fra.status", nativeQuery = true)
   public List<Map<String, Object>> circulaireChefBureauAndDivision(@Param("powner") Long powner);
   
   @Query(value = "SELECT COUNT(produits.status), tp_task_status.status_name FROM tp_task_status, produits where produits.status=tp_task_status.status_id GROUP BY produits.status", nativeQuery = true)
   public List<Map<String, Object>> circulaireChefDivision();
   
   @Query(value = "SELECT COUNT(demande_aut_fra.status), tp_task_status.status_name FROM demande_aut_fra, tp_task_status where demande_aut_fra.status = tp_task_status.status_id AND demande_aut_fra.owner =:owner GROUP BY demande_aut_fra.status", nativeQuery = true)
   public List<Map<String, Object>> circulaireDemandesAntenne(@Param("owner") Long owner);
   
   @Query(value = "SELECT COUNT(demande_aut_fra.status), tp_task_status.status_name FROM demande_aut_fra, tp_task_status where demande_aut_fra.status=tp_task_status.status_id AND demande_aut_fra.email_responsable =:email GROUP BY demande_aut_fra.status", nativeQuery = true)
   public List<Map<String, Object>> circulaireDemandeur(@Param("email") String email);
   
   @Query(value = "SELECT COUNT(demande_aut_fra.status), tp_task_status.status_name FROM demande_aut_fra, tp_task_status where demande_aut_fra.status = tp_task_status.status_id AND (demande_aut_fra.status = 4 OR demande_aut_fra.status = 5 OR demande_aut_fra.status = 6 OR demande_aut_fra.status = 7) GROUP BY demande_aut_fra.status", nativeQuery = true)
   public List<Map<String, Object>> circulaireLaborantin();
}