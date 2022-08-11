package sn.gainde2000.pi.ged.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.ged.entity.TypeDocuments;



public interface ITypeDocumentService {
	
    public void saveTypeDocuments(TypeDocuments typeDocuments);

    public List<TypeDocuments> getListTypeDocument();

    public Optional<TypeDocuments> findById(Long id);

    public TypeDocuments findByTydId(Long id);

    public void supprimer(TypeDocuments typeDocuments);
    public List<TypeDocuments> getListTypeDocumentProfil(Long profil);
}
