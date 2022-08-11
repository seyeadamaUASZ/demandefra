/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;

import sn.gainde2000.pi.core.entity.ChampsRapport;

/**
 *
 * @author asow, sagueye
 */
public interface IChampsRapportService {

    public List<ChampsRapport> listBycrtId(Long id);

    public List<ChampsRapport> listByRapportId(Long id);
    
    public ChampsRapport save(ChampsRapport champRapport);
    
    public void delete(Long id);
    
    public void supprimerByRapport(Long id);
}
