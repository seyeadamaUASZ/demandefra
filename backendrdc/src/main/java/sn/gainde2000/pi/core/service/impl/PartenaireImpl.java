/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Partenaire;
import sn.gainde2000.pi.core.repository.PartenaireRepository;
import sn.gainde2000.pi.core.service.IPartenaireService;

/**
 *
 * @author yougadieng
 */
@Service
public class PartenaireImpl implements IPartenaireService {

    @Autowired
    PartenaireRepository partRepo;

    @Override
    public Partenaire save(Partenaire part) {
        return partRepo.save(part);
    }

    @Override
    public List<Partenaire> getAll() {
        return partRepo.findAll();
    }

    @Override
    public void delete(Partenaire part) {
        partRepo.delete(part);
    }

}
