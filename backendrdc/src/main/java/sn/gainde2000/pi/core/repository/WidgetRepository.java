package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long> {

    public Widget findByWdgId(String widgetid);

    public Widget findByWdgNom(String widgetnom);

    public Widget findByWdgDescription(String description);

    @Query("SELECT wdg FROM Widget wdg LEFT JOIN WidgetAttribution wa ON (wdg.wdgId=wa.widget.wdgId) WHERE wa.profile =:profil ")
    public List<Widget> findByProfiles(@Param("profil") Profil profil);

    @Query("SELECT w FROM Widget w LEFT JOIN WidgetAttribution wa ON w.wdgId=wa.widget.wdgId WHERE wa.profile.proId =:profil ORDER BY wa.waOrdre")
    public List<Widget> findAllWidget(@Param("profil") Long profil);

    @Query("SELECT w FROM Widget w LEFT JOIN WidgetAttribution wa ON w.wdgId=wa.widget.wdgId WHERE wa.profile.proId !=:profil")
    public List<Widget> findAllWidgetNoAttr(@Param("profil") Long profil);

   

}
