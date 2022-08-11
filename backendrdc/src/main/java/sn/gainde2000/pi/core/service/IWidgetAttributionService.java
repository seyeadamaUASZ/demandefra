package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;

/**
*
* @author Serigne Asse Gueye
*/
public interface IWidgetAttributionService {
    public List<Widget> getListWidgetProfil(Profil profil);
    public WidgetAttribution getWidgetProfil(Profil profil, Widget Widget);
    public Iterable<WidgetAttribution>  attributionWidget(Long id);
    public WidgetAttribution save(WidgetAttribution wa);
    public void removedWidgetAttribution(Profil profil, Widget Widget);
}
