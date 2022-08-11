package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.TypeNotification;

/**
*
* @author sagueye
*/
public interface ITypeNotificationService {
	public List<TypeNotification> getListTypeNotification();

    public TypeNotification save(TypeNotification typeNotification);

    public Optional<TypeNotification> findById(Integer id);

    public TypeNotification findByName(String nom);
}
