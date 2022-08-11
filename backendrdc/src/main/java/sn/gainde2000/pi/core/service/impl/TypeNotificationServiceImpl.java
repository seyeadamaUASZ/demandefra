package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.TypeNotification;
import sn.gainde2000.pi.core.repository.TypeNotificationRepository;
import sn.gainde2000.pi.core.service.ITypeNotificationService;

@Service
public class TypeNotificationServiceImpl implements ITypeNotificationService {
	
	@Autowired
	public TypeNotificationRepository typeNotificationRepository;
	
	@Override
	public List<TypeNotification> getListTypeNotification() {
		return typeNotificationRepository.findAll();
	}

	@Override
	public TypeNotification save(TypeNotification typeNotification) {
		return typeNotificationRepository.save(typeNotification);
	}


	@Override
	public TypeNotification findByName(String tntNom) {
		return typeNotificationRepository.findByTntNom(tntNom);
	}

	@Override
	public Optional<TypeNotification> findById(Integer id) {
		return typeNotificationRepository.findById(id);
	}

}
