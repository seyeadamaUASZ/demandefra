/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.gainde2000.pi.core.entity.Profil;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yougadieng
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profil, Long> {

    public Profil findByProLibelle(String profile);
   
    

    public Profil findByProId(String proId);
    
    public Profil findByProId(Long proId);

    @Query("SELECT p.proLibelle ,count(*) from Profil p left join Utilisateur u on p.proId=u.uti_pro_id where p.proId=u.uti_pro_id GROUP BY p.proLibelle")
    Iterable<Profil> listStatProfile();
    
     @Query("select p from Profil p  where p.nouvelleInscri=true")
     public Profil findByNouvelleInscri();
}
