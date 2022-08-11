package sn.gainde2000.pi.core.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.AntenneRegionaleDepartementale;
import sn.gainde2000.pi.core.entity.Categorie;

public interface IAntenneRegionaleDepartementaleService {
	List<AntenneRegionaleDepartementale> findAll();
	AntenneRegionaleDepartementale save(AntenneRegionaleDepartementale antenneRegionaleDepartementale);
	List<AntenneRegionaleDepartementale> listeAntennesParRegion(Long id);
	public void updateAntenneRegionaleDepartementale(Long id, AntenneRegionaleDepartementale antenneRegionaleDepartementale);
	public void supprimerAntenneRegionaleDepartementale(Long id);
	public AntenneRegionaleDepartementale getOneAntenne(Long id);
}
