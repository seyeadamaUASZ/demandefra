package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;

/**
*
* @author Serigne Asse Gueye
*/

public interface IWidgetService {
    public List<Widget> getListWidget();
    public List<Widget> getListWidgetProfil(Profil profil);
    public List<Widget> getListWidgetProfilNoAttr(Profil profil);
    public List<Widget> getListWidgetProfilId(Long id);
    public Optional<Widget>  findById(Long id);
    public Widget save(Widget widget);
    public void delete(Widget Widget);
}
