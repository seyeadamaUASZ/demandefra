/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;
import sn.gainde2000.pi.core.entity.LandingPage;

/**
 *
 * @author yougadieng
 */
public interface ILandingPage {  
    public void create(LandingPage landing);
    public void supprimer(LandingPage landing);
    public List<LandingPage> getAll();
    public LandingPage finbyUsername (String username);
    public int nmbreDenregistrement();
    // public void save(MultipartFile file);
   


}
