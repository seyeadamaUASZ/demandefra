package sn.gainde2000.pi.ged.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.ged.entity.TypeDocuments;
import sn.gainde2000.pi.ged.repository.TypeDocumentsRepository;
import sn.gainde2000.pi.ged.service.ITypeDocumentService;
@Service
public class TypeDocumentsServiceImpl implements ITypeDocumentService{
	
	@Autowired
	TypeDocumentsRepository typeDocumentRepo;

	@Override
	public void saveTypeDocuments(TypeDocuments typeDocuments) {
		
		typeDocumentRepo.save(typeDocuments);
		
	}

	@Override
	public List<TypeDocuments> getListTypeDocument() {
		// TODO Auto-generated method stub
		return typeDocumentRepo.findAll();
	}

	@Override
	public Optional<TypeDocuments> findById(Long id) {
		// TODO Auto-generated method stub
		return typeDocumentRepo.findById(id);
	}

	@Override
	public TypeDocuments findByTydId(Long id) {
		// TODO Auto-generated method stub
		return typeDocumentRepo.getOne(id);
	}

	@Override
	public void supprimer(TypeDocuments typeDocuments) {
		// TODO Auto-generated method stub
		typeDocumentRepo.delete(typeDocuments);
	}

	@Override
	public List<TypeDocuments> getListTypeDocumentProfil(Long profil) {
		// TODO Auto-generated method stub
		return typeDocumentRepo.getListTypeDocumentProfil(profil);
	}

}
