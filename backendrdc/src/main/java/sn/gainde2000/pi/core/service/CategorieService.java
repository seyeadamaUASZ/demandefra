package sn.gainde2000.pi.core.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.Categorie;

public interface CategorieService {
	public Categorie addCategorie(Categorie categorie);
	public List<Categorie> listCategories();
	public void updateCategorie(Long id,Categorie categorie);
	public void supprimerCategorie(Long id);
}
