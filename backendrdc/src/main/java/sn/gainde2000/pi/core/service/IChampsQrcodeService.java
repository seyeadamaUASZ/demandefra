/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.ChampsQrcode;

/**
 *
 * @author asow
 */
public interface IChampsQrcodeService {

    public List<ChampsQrcode> listBycqdId(Long id);

    public List<ChampsQrcode> listByQrcodeId(Long id);
    
    public ChampsQrcode save(ChampsQrcode champs);
    
    public void delete(Long id);
    
    public void supprimerByQrcode(Long id);
    
}
