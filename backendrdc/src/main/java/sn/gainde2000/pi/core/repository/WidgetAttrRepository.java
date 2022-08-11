package sn.gainde2000.pi.core.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;

import java.util.List;

public interface WidgetAttrRepository extends JpaRepository<WidgetAttribution, Long> {

    @Query("SELECT w FROM Widget w LEFT JOIN WidgetAttribution wa ON (w.wdgId=wa.widget.wdgId) WHERE wa.profile =:profil ")
    public  List<Widget> findByProfiles(@Param("profil") Profil profil);
    @Transactional
    @Modifying
    @Query("DELETE FROM WidgetAttribution wa WHERE wa.profile =:profil AND wa.widget =:widget")
    public void removedWidgetAttribution(@Param("profil") Profil profil, @Param("widget") Widget widget);
    public WidgetAttribution findByWaId(String widgetAttribution);

    @Query("SELECT wdg.wdgNom FROM Widget wdg, WidgetAttribution wa where wdg.wdgId = wa.widget  AND wa.profile.proId = :Idprofile")
    Iterable<WidgetAttribution> attributionWidget(@Param("Idprofile") Long Idprofile);
    
    @Query("SELECT wa FROM WidgetAttribution wa WHERE wa.profile =:profil AND wa.widget =:widget")
    public  WidgetAttribution findByProfileWidget(@Param("profil") Profil profil, @Param("widget") Widget widget);
}