package sn.gainde2000.pi.core.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.MenuRapport;
import sn.gainde2000.pi.core.entity.Rapport;

@Repository
public interface MenuRapportRepository extends JpaRepository<MenuRapport, Integer> {
	@Query("SELECT m FROM Menu m INNER JOIN MenuRapport mr ON (m.menId=mr.menu.menId) WHERE mr.rapport =:rapport ")
    public  List<Menu> findByRapports(@Param("rapport") Rapport rapport);
    @Transactional
    @Modifying
    @Query("DELETE FROM MenuRapport mr WHERE mr.rapport =:rapport AND mr.menu =:menu")
    public void removedMenuRapport(@Param("rapport") Rapport rapport, @Param("menu") Menu menu);

    @Query("SELECT men FROM Menu men, MenuRapport mr where men.menId = mr.menu.menId  AND mr.rapport.rptId = :IdRapport")
    Iterable<MenuRapport> menuRapportAccord(@Param("IdRapport") Long IdRapport);
    
    @Query("SELECT mr FROM MenuRapport mr WHERE mr.menu =:menu ")
    public  List<MenuRapport> findByMenus(@Param("menu") Menu menu);
}
