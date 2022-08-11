/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.core.entity.Champs;

/**
 *
 * @author yougadieng
 */
public interface IChampsService {

    public List<Champs> getListChamps();

    public List<Champs> listByFrmId(Long id);

    public List<Champs> listFieldByFrmId(Long id);

    public Iterable<Champs> listButtonByFrmId(Long id);

    public Champs save(Champs champs);

    public Optional<Champs> findById(Long id);

    public void delete(Champs champs);
    
    public void supprimerByFormulaire(Long id);
    public List<Champs> listByFrmIdFile(Long id);

}
