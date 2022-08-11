/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.repository.MenuRepository;
import sn.gainde2000.pi.core.repository.ActionRepository;
import sn.gainde2000.pi.core.service.IMenuService;

/**
 *
 * @author indiaye
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ActionRepository actionRepository;

    @Override
    public List<Menu> getListMenu() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> getListMenuRacine() {
        return menuRepository.getListMenuPere();
    }

    @Override
    public List<Menu> getListMenuProfil(Long p) {
        //this.getListMenuAccessProfil(p);
        //System.out.println("-------------------getListMenuAccessProfil-------------------");
        //return menuRepository.getListMenuProfil(p);    
        return this.getListMenuAccessProfil(p);
    }

    @Override
    public Menu findByMenNom(String nom) {
        return menuRepository.findByMenNom(nom);
    }

    @Override
    public Menu save(Menu m) {
        return menuRepository.save(m);
    }

    @Override
    public void delete(Menu m) {
        menuRepository.delete(m);
    }
    Menu mAdd = new Menu();

    @Override
    public List<Menu> getListMenuAccessProfil(Long p) {
        List<Menu> menusAccess = new ArrayList<Menu>();
        List<Action> actionsRights = actionRepository.getListActionsProfil(p);
        List<Menu> menus = menuRepository.getListMenuPere();

        for (Menu m : menus) {
            //System.out.println("------Nom menu------ "+m.getMenNom());
            mAdd = this.getMenusAccess(m, actionsRights);
            if ((mAdd != null) && (((mAdd.getActions() != null) && (!mAdd.getActions().isEmpty()))
                    || ((mAdd.getSousMenus() != null) && (!mAdd.getSousMenus().isEmpty())))) {
                menusAccess.add(mAdd);
            }
        }

        //System.out.println("-----------------------------Affichage des menus avec droits --------------------------- ");
        /* for(Menu m : menusAccess) {
             System.out.println("--------Nom menu------ "+m.getMenNom());
            m.getSousMenus().forEach(sm -> {
                System.out.println("--------------Nom Sous menu------ "+sm.getMenNom());
            });
        }        
         */
        return menusAccess;
    }

    @Override
    public Menu getListMenuAccess(Menu m, List<Action> actionsRights) {
        Menu menuAccess = new Menu();
        menuAccess.setActions(this.getActionsAccess(m.getActions(), actionsRights));
        return menuAccess;
    }

    public Menu getMenusAccess(Menu menu, List<Action> actionsRights) {
        Menu menuAccess = menu;
        if (menu == null) {
            return menu;
        }
        menuAccess.setActions(this.getActionsAccess(menu.getActions(), actionsRights));
        if (menuAccess.getActions().isEmpty() && (menu.getSousMenus().isEmpty())) {
            return null;
        }
        menuAccess.setSousMenus(this.getSousMenusAccess(menu.getSousMenus(), actionsRights));
        return menuAccess;
    }

    public Collection<Action> getActionsAccess(Collection<Action> actions, List<Action> actionsRights) {
        List<Action> collect = actions.stream()
                .filter(a1 -> actionsRights.stream().anyMatch(a2 -> Objects.equals(a2.getActId(), a1.getActId())))
                .collect(Collectors.toList());
        return collect;
    }

    public Collection<Menu> getSousMenusAccess(Collection<Menu> menus, List<Action> actionsRights) {
        if (menus.isEmpty()) {
            return menus;
        }
        for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext();) {
            Menu m = iterator.next();
            m.setActions(this.getActionsAccess(m.getActions(), actionsRights));
            m.setSousMenus(this.getSousMenusAccess(m.getSousMenus(), actionsRights));
            if (((m.getActions() == null) || m.getActions().isEmpty()) && ((m.getSousMenus() == null) || (m.getSousMenus().isEmpty()))) {
                iterator.remove();
            }
        }
        return menus;

    }

    @Override
    public List<Menu> getListSousMenuRapport(Long menId) {
        return menuRepository.getListSousMenuRapport(menId);
    }

    @Override
    public Menu getMenuByPath(String menPath) {
        return menuRepository.getMenuByPath(menPath);
    }

    @Override
    public List<Menu> menu(String keyword) {
        return menuRepository.seach(keyword);
    }

}
