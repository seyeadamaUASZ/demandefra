package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Region;
import sn.gainde2000.pi.core.repository.RegionRepository;
import sn.gainde2000.pi.core.service.IRegionService;

@Service
public class RegionImpl implements IRegionService {
	@Autowired
	RegionRepository regionRepository;

	@Override
	public List<Region> findAll() {
		return regionRepository.findAll();
	}

	@Override
	public Region save(Region region) {
		return regionRepository.save(region);
	}

	@Override
	public void updateRegion(Long id, Region region) {
		region.setId(id);
		regionRepository.save(region);
	}

	@Override
	public void supprimerRegion(Long id) {
		regionRepository.deleteById(id);
	}

	@Override
	public Region getOneRegion(Long id) {
		return regionRepository.findById(id).get();
	}
}
