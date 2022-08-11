package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.MenuRapport;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.repository.MenuRapportRepository;
import sn.gainde2000.pi.core.service.IMenuRapportService;

/**
*
* @author sagueye
*/
@Service
public class MenuRapportServiceImpl implements IMenuRapportService{
	
	@Autowired
	MenuRapportRepository menuRapportRepository;

	@Override
	public List<MenuRapport> getListMenuRapports() {
		return menuRapportRepository.findAll();
	}

	@Override
	public List<MenuRapport> getListMenuRapportParMenus(Menu menu) {
		return menuRapportRepository.findByMenus(menu);
	}

	@Override
	public List<Menu> getListMenuParRapport(Rapport rapport) {
		return menuRapportRepository.findByRapports(rapport);
	}

	@Override
	public MenuRapport save(MenuRapport mr) {
		return menuRapportRepository.save(mr);
	}

	@Override
	public Optional<MenuRapport> findById(Integer id) {
		return menuRapportRepository.findById(id);
	}

	@Override
	public void delete(MenuRapport mr) {
		menuRapportRepository.delete(mr);
	}

	@Override
	public void removedMenuRapport(Rapport rapport, Menu menu) {
		menuRapportRepository.removedMenuRapport(rapport, menu);
	}

	@Override
	public Iterable<MenuRapport> menuRapportAccord(Long IdRapport) {
		return menuRapportRepository.menuRapportAccord(IdRapport);
	}

}
