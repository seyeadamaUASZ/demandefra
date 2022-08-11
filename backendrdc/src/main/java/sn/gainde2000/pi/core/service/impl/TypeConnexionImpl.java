/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.TypeConnexion;
import sn.gainde2000.pi.core.repository.TypeConnexionRepository;
import sn.gainde2000.pi.core.service.ITypeConnexion;

/**
 *
 * @author yougadieng
 */
@Service
public class TypeConnexionImpl implements ITypeConnexion {

    @Autowired
    TypeConnexionRepository typeConnexionRepo;

    @Override
    public List<TypeConnexion> getAll() {
        return typeConnexionRepo.findAll();
    }

    @Override
    public TypeConnexion findById(Long Id) {
        return typeConnexionRepo.findByTypId(Id);
    }

    @Override
    public void update(TypeConnexion typeConnexion) {
        typeConnexionRepo.save(typeConnexion);
    }

   

}
