package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Icone;



public interface IIconeService {
	  public void saveIcone(Icone icone);
	    public List<Icone> getListIcone();
	    public Optional<Icone>  findById(Long id);
	    public void supprimer(Icone icone);
}
