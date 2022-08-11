/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Session;
import sn.gainde2000.pi.core.entity.Utilisateur;

/**
 *
 * @author yougadieng
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByUtiId(String userid);

    Utilisateur findByUtiId(Long id);

    Utilisateur findByUtiUsername(String username);

    Utilisateur findByUtiEmail(String email);

    @Query("SELECT COUNT(*) FROM Utilisateur p where p.utiEmail=:email")
    int findUsersByUtiEmail(String email);

    @Query("SELECT COUNT(*) FROM Utilisateur p where p.utiUsername=:username")
    int findUsersByUtiUsername(String username);

    @Query("SELECT COUNT(p.utiId) FROM Utilisateur p")
    int nombreUtilisateur();

    @Query("select u from Utilisateur u where u.utiId =:id")
    public Utilisateur listByUtid(@Param("id") Long id);

    @Query("SELECT COUNT(*) FROM Utilisateur p,Profil pro where p.uti_pro_id=pro.proId and pro.proLibelle like %:libelle%")
    int nombreUtilisateurByProfil(@Param("libelle") String libelle);

    @Query(value = "select * FROM td_utilisateur where uti_username=:param_uti_username", nativeQuery = true)
    Utilisateur verifyLangueEtTheme(@Param("param_uti_username") String param_uti_username);

    @Query("SELECT u FROM Utilisateur u  WHERE u.uti_pro_id =:profil")
    List<Utilisateur> findUsersByUtiProfil(@Param("profil") Profil profil);
    
    @Query("SELECT u,s FROM Utilisateur u,Session s WHERE s.sesUtiId = u.utiId")
    List<Utilisateur> listUserConnect();
    
    @Query(value = "SELECT ROUND(( UNIX_TIMESTAMP(uti_temps_session) - (UNIX_TIMESTAMP())) / 60)  FROM td_utilisateur where uti_username=:username ", nativeQuery = true)
    public int verifSession(@Param("username") String username);

    //@Query(value ="SELECT MONTH(uti_date_creation) MONTH, COUNT(*) COUNT FROM td_utilisateur WHERE YEAR(uti_date_creation)= (SELECT EXTRACT(YEAR FROM uti_date_creation)) GROUP BY MONTH(uti_date_creation) ORDER BY uti_date_creation ASC",nativeQuery = true)
    @Query(value = "SELECT SUM(CASE MONTH(uti_date_creation) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(uti_date_creation) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(uti_date_creation) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(uti_date_creation) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(uti_date_creation) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(uti_date_creation) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(uti_date_creation) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(uti_date_creation) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(uti_date_creation) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(uti_date_creation) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(uti_date_creation) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(uti_date_creation) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM td_utilisateur WHERE \n"
            + "  uti_date_creation BETWEEN (SELECT  MIN(uti_date_creation) \n"
            + "FROM td_utilisateur) AND (SELECT  MAX(uti_date_creation) \n"
            + "FROM td_utilisateur ) ", nativeQuery = true)
    public List<Object[]> nbrUtiIndusByDate();
@Query(value = "SELECT SUM(CASE MONTH(uti_date_creation) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(uti_date_creation) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(uti_date_creation) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(uti_date_creation) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(uti_date_creation) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(uti_date_creation) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(uti_date_creation) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(uti_date_creation) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(uti_date_creation) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(uti_date_creation) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(uti_date_creation) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(uti_date_creation) WHEN 12 THEN 1 ELSE 0 END) AS 'December'  FROM td_utilisateur WHERE \n"
            + "  uti_date_creation BETWEEN (SELECT  MIN(uti_date_creation) \n"
            + "FROM td_utilisateur) AND (SELECT  MAX(uti_date_creation) \n"
            + "FROM td_utilisateur ) ", nativeQuery = true)
    public List<Map<String, Object>> nbrUtiIndusByDateExport();
    @Query(value = "SELECT COUNT(td_utilisateur.uti_id), tp_profil.pro_libelle FROM td_utilisateur JOIN tp_profil ON td_utilisateur.uti_pro_id=tp_profil.pro_id GROUP BY td_utilisateur.uti_pro_id", nativeQuery = true)
    public List<Map<String, Object>> nbrUtiIndusByProfil();

    @Query(value = "SELECT SUM(CASE MONTH(uti_date_creation) WHEN 1 THEN 1 ELSE 0 END) AS 'Janvier', SUM(CASE MONTH(uti_date_creation) WHEN 2 THEN 1 ELSE 0 END) AS 'Février', SUM(CASE MONTH(uti_date_creation) WHEN 3 THEN 1 ELSE 0 END) AS 'Mars', SUM(CASE MONTH(uti_date_creation) WHEN 4 THEN 1 ELSE 0 END) AS 'Avril', SUM(CASE MONTH(uti_date_creation) WHEN 5 THEN 1 ELSE 0 END) AS 'Mai', SUM(CASE MONTH(uti_date_creation) WHEN 6 THEN 1 ELSE 0 END) AS 'Juin', SUM(CASE MONTH(uti_date_creation) WHEN 7 THEN 1 ELSE 0 END) AS 'Juillet', SUM(CASE MONTH(uti_date_creation) WHEN 8 THEN 1 ELSE 0 END) AS 'Aout', SUM(CASE MONTH(uti_date_creation) WHEN 9 THEN 1 ELSE 0 END) AS 'September', SUM(CASE MONTH(uti_date_creation) WHEN 10 THEN 1 ELSE 0 END) AS 'October', SUM(CASE MONTH(uti_date_creation) WHEN 11 THEN 1 ELSE 0 END) AS 'November', SUM(CASE MONTH(uti_date_creation) WHEN 12 THEN 1 ELSE 0 END) AS 'December',\n"
            + "tp_profil.pro_libelle\n"
            + "FROM td_utilisateur JOIN tp_profil ON td_utilisateur.uti_pro_id=tp_profil.pro_id \n"
            + "WHERE  uti_date_creation BETWEEN (SELECT  MIN(uti_date_creation) FROM td_utilisateur) AND (SELECT  MAX(uti_date_creation)  FROM td_utilisateur )\n"
            + "GROUP BY td_utilisateur.uti_pro_id\n"
            + "ORDER BY FIELD(uti_date_creation,'Janvier', 'Février', 'Mars', 'Avril', 'Mai','Juin','Juillet','Aout','Septembre','Octobre','Novembre','Décembre') ", nativeQuery = true)
    public List<Map<String, Object>> nbrCourbeUtiIndusByProfil();

    @Query(value = "SELECT year(uti_date_creation) as annee , count(uti_id) as CountOfNewUsers FROM td_utilisateur GROUP BY year(uti_date_creation)", nativeQuery = true)
    public List<Map<String, Object>> nbrUtiIndusByAnnee();

    @Query(value = "SELECT year(td_utilisateur.uti_date_creation) as annee , count(td_utilisateur.uti_id) as CountOfNewUsers, tp_profil.pro_libelle FROM td_utilisateur JOIN tp_profil ON td_utilisateur.uti_pro_id=tp_profil.pro_id GROUP BY td_utilisateur.uti_pro_id", nativeQuery = true)
    public List<Map<String, Object>> nbrUtiIndusByProfilParAnnee();

    @Query(value = "select * from td_utilisateur where td_utilisateur.uti_idempreinte=:id ORDER BY td_utilisateur.uti_id DESC LIMIT 1", nativeQuery = true)
    Utilisateur findByIdEmpriente(@Param("id") String id);

}
