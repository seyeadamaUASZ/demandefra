package sn.gainde2000.pi.formgenerator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.formgenerator.entity.ValueChampsV2;
import sn.gainde2000.pi.formgenerator.repository.ValueChampsV2Repository;
import sn.gainde2000.pi.formgenerator.service.IValueChampsV2Service;

@Service
public class ValueChampsV2Impl implements IValueChampsV2Service{

	@Autowired ValueChampsV2Repository valueChampsV2Repository;
	@Override
	public ValueChampsV2 save(ValueChampsV2 v2) {
		return valueChampsV2Repository.save(v2);
	}

	@Override
	public void delete(ValueChampsV2 v2) {
		valueChampsV2Repository.delete(v2);
	}

}
