package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Icone;
import sn.gainde2000.pi.core.repository.IconeRepository;
import sn.gainde2000.pi.core.service.IIconeService;
@Service
public class IconeServiceImpl implements IIconeService {
	
	@Autowired
	IconeRepository iconeRepo;

	@Override
	public void saveIcone(Icone icone) {
		iconeRepo.save(icone);
		
	}

	@Override
	public List<Icone> getListIcone() {
		// TODO Auto-generated method stub
		return iconeRepo.findAll();
	}

	@Override
	public Optional<Icone> findById(Long id) {
		// TODO Auto-generated method stub
		return iconeRepo.findById(id);
	}

	@Override
	public void supprimer(Icone icone) {
		// TODO Auto-generated method stub
		iconeRepo.delete(icone);
	}

}
