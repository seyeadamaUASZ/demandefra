/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.pi.core.entity.Menu;

/**
 *
 * @author yougadieng
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Menu findByMenNom(String nom);
    
    @Query("SELECT m FROM Menu m LEFT JOIN Action a ON m.menId=a.actMenId LEFT JOIN Privilege p ON a.actId=p.action WHERE p.profile.proId = :profil GROUP BY m.menId")
     List<Menu> getListMenuProfil(@Param("profil") Long profil);
     @Query("SELECT m FROM Menu m WHERE m.menIdParent is null")
     List<Menu> getListMenuPere();
     @Query("SELECT m FROM Menu m LEFT JOIN Menu mn ON m.menIdParent=mn.menId WHERE mn.menId = :menId")
     List<Menu> getListSousMenuRapport(@Param("menId") Long menId);
     @Query("SELECT m FROM Menu m WHERE m.menPath=:menPath")
     Menu getMenuByPath(@Param("menPath") String menPath);
     
     @Query("SELECT m FROM Menu m WHERE m.menNom LIKE %:Keyword%")
    public List<Menu> seach(@Param("Keyword") String Keyword);
     
     
     
}
