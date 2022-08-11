/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.entity.LandingPage;
import sn.gainde2000.pi.core.repository.LandingPageRepository;
import sn.gainde2000.pi.core.service.ILandingPage;

/**
 *
 * @author yougadieng
 */
@Service
public class LandingPageServiceImpl implements ILandingPage {

    private final Path root = Paths.get("uploads");

    @Autowired
    public LandingPageRepository landingRepo;

    @Override
    public void create(LandingPage landing) {
        landingRepo.save(landing);
    }

    @Override
    public List<LandingPage> getAll() {
        return landingRepo.findAll();
    }

    @Override
    public LandingPage finbyUsername(String username) {
        return landingRepo.findByUsername(username);
    }

    @Override
    public int nmbreDenregistrement() {
        return landingRepo.nombreEnregistrement();
    }

    @Override
    public void supprimer(LandingPage landing) {
        landingRepo.delete(landing);
    }

  

   

}
