/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.pi.core.repository.UtilisateurRepository;
import sn.gainde2000.pi.core.entity.Utilisateur;

/**
 *
 * @author yougadieng
 */
@Service
@Repository
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    //ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUtiUsername(username);
        //ProfileRepository profile = profileRepository.findByLibelle(profile);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("le mot de passe ou le username est incorrect.");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getUti_pro_id().getProLibelle());
        UserDetails details = (UserDetails) new org.springframework.security.core.userdetails.User(utilisateur.getUtiUsername(),
                utilisateur.getUtiPassword(), Arrays.asList(authority));
        return details;
    }
}
