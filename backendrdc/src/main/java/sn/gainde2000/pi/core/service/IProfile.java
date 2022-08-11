package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Profil;

public interface IProfile {

    public void addProfile(Profil profil);

    public void supprimerProfile(Profil profil);

    public Profil findByProLibelle(String profile);

    public Profil findByProId(String proId);

    public List<Profil> getAllProfile();
    
    public Profil findByNouvelleInscri();

    public Optional<Profil> findById(Long id);

    public Iterable<Profil> getListStatProfil();
    
    public Profil findByProId(Long proId);
}
