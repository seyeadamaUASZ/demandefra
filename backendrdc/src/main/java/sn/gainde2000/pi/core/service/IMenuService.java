/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;


import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;

/**
 *
 * @author indiaye
 */
public interface IMenuService {

    public List<Menu> getListMenu();

    public List<Menu> getListMenuRacine();

    public List<Menu> getListMenuProfil(Long p);

    public Menu findByMenNom(String nom);

    public Menu save(Menu m);

    public void delete(Menu m);

    public List<Menu> getListMenuAccessProfil(Long p);

    public Menu getListMenuAccess(Menu m, List<Action> actionsRights);

    public List<Menu> getListSousMenuRapport(Long menId);

    public Menu getMenuByPath(String menPath);
    
    public List<Menu> menu(String keyword);
}
