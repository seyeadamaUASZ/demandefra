package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Categorie;
import sn.gainde2000.pi.core.repository.CategoryRepository;
import sn.gainde2000.pi.core.service.CategorieService;

@Service
public class CategorieServiceImpl implements CategorieService {
	@Autowired
   CategoryRepository repos;

	@Override
	public Categorie addCategorie(Categorie categorie) {
		return repos.save(categorie);
	}

	@Override
	public List<Categorie> listCategories() {
		return repos.findAll();
	}

	@Override
	public void supprimerCategorie(Long id) {
		repos.deleteById(id);
	}

	@Override
	public void updateCategorie(Long id, Categorie categorie) {
		categorie.setId(id);
		repos.saveAndFlush(categorie);
	}
}
