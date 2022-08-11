package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.MenuRapport;
import sn.gainde2000.pi.core.entity.Rapport;

/**
*
* @author sagueye
*/
public interface IMenuRapportService {
public List<MenuRapport> getListMenuRapports();
    
    public List<MenuRapport> getListMenuRapportParMenus(Menu menu);
    
    public List<Menu> getListMenuParRapport(Rapport rapport);
    
    public MenuRapport save(MenuRapport mr);

    public Optional<MenuRapport> findById(Integer id);  

    public void delete(MenuRapport mr);
    
    public void removedMenuRapport(Rapport rapport, Menu action);
    
    public  Iterable<MenuRapport> menuRapportAccord(Long IdRapport);
}
