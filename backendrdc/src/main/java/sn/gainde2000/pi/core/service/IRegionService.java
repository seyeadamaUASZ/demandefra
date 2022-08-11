package sn.gainde2000.pi.core.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.Region;

public interface IRegionService {
	List<Region> findAll();
	Region save(Region region);
	public void updateRegion(Long id, Region region);
	public void supprimerRegion(Long id);
	public Region getOneRegion(Long id);
}
