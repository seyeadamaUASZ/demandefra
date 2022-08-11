/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.Partenaire;

/**
 *
 * @author yougadieng
 */
public interface IPartenaireService {
    public Partenaire save(Partenaire part);
    public List<Partenaire> getAll();
    public void delete(Partenaire part);

    
}
