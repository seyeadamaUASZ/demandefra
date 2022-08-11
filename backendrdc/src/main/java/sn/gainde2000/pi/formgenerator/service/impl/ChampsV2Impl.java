package sn.gainde2000.pi.formgenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.repository.ChampsV2Repository;
import sn.gainde2000.pi.formgenerator.service.IChampsV2Service;

@Service
public class ChampsV2Impl implements IChampsV2Service{
	
	@Autowired ChampsV2Repository champsV2Repository;

	@Override
	public ChampsV2 save(ChampsV2 champsV2) {
		return champsV2Repository.save(champsV2);
	}

	@Override
	public void delete(ChampsV2 champsV2) {
		 champsV2Repository.delete(champsV2);
	}
}
