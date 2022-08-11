/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.repository.ProfileRepository;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.impl.ProfileImpl;
import sn.gainde2000.pi.core.entity.Profil;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author yougadieng
 */
@RestController
public class ProfilController {

    @Autowired
    IProfile profileService;

    @PostMapping("profil/create")
    public ServeurResponse addProfile(@RequestBody Profil profile) {

        ServeurResponse response = new ServeurResponse();
        if (profileService.findByProLibelle(profile.getProLibelle()) == null) {
            profileService.addProfile(profile);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(profile.getProId()).toUri();
            ResponseEntity.created(location).build();
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(profile);

        } else {
            response.setStatut(false);
            response.setDescription("role.exist-role");
            response.setData(null);
        }
        return response;

    }

    @GetMapping("profil/list")
    public ServeurResponse getProfile() {
        ServeurResponse response = new ServeurResponse();

        Iterable<Profil> profils = profileService.getAllProfile();
        if (profils == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune profil trouvé.");

        } else {
            //System.out.println("-------------------getProfile------------------------");
            response.setStatut(true);
            response.setData(profils);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @PostMapping("profil/update")
    public ServeurResponse updateProf(@RequestBody Profil profil) {
        ServeurResponse response = new ServeurResponse();
        profileService.addProfile(profil);
        response.setStatut(true);
        response.setDescription("role.success-update");
        return response;
    }

    @GetMapping("profil/{id}")
    public Optional<Profil> findById(@PathVariable Long id) {
        return profileService.findById(id);
    }

    @PostMapping("profil/delete")
    public ServeurResponse deleteProf(@RequestBody Profil profil) {
        ServeurResponse response = new ServeurResponse();
        profileService.supprimerProfile(profil);
        response.setStatut(true);
        return response;
    }

    @GetMapping("widget/getStatProfil")
    public ServeurResponse statDesProfils() {
        ServeurResponse response = new ServeurResponse();
        Iterable<Profil> stats = profileService.getListStatProfil();
        response.setStatut(true);
        response.setData(stats);
        return response;
    }
    
    
}
