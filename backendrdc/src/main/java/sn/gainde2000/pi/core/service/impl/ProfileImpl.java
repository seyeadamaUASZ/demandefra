package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.repository.ProfileRepository;
import sn.gainde2000.pi.core.service.IProfile;

@Service
public class ProfileImpl implements IProfile {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public void addProfile(Profil profil) {

        profileRepository.save(profil);
    }

    @Override
    public void supprimerProfile(Profil profil) {
        profileRepository.delete(profil);

    }

    @Override
    public Profil findByProLibelle(String profile) {
        // TODO Auto-generated method stub
        return profileRepository.findByProLibelle(profile);
    }

    @Override
    public Profil findByProId(String proId) {
        // TODO Auto-generated method stub
        return profileRepository.findByProId(proId);
    }

    @Override
    public List<Profil> getAllProfile() {
        // TODO Auto-generated method stub
        return this.profileRepository.findAll();
    }

    @Override
    public Optional<Profil> findById(Long id) {
        // TODO Auto-generated method stub
        return this.profileRepository.findById(id);
    }

    @Override
    public Iterable<Profil> getListStatProfil() {
       return this.profileRepository.listStatProfile();
    }

    @Override
    public Profil findByNouvelleInscri() {
       return profileRepository.findByNouvelleInscri();
    }

    @Override
    public Profil findByProId(Long proId) {
      return profileRepository.findByProId(proId);
    }




    

}
