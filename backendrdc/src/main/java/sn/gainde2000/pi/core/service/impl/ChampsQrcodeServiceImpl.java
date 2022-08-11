/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.ChampsQrcode;
import sn.gainde2000.pi.core.repository.ChampsQrcodeRepository;
import sn.gainde2000.pi.core.service.IChampsQrcodeService;

/**
 *
 * @author asow
 */
@Service
public class ChampsQrcodeServiceImpl implements IChampsQrcodeService{
    @Autowired
    ChampsQrcodeRepository champsQrcodeRepository;

    @Override
    public List<ChampsQrcode> listBycqdId(Long id) {
        return champsQrcodeRepository.listByQrcodeId(id);
    }

    @Override
    public List<ChampsQrcode> listByQrcodeId(Long id) {
        return champsQrcodeRepository.listByQrcodeId(id);
    }

    @Override
    public ChampsQrcode save(ChampsQrcode champs) {
        return champsQrcodeRepository.save(champs);
    }

    @Override
    public void delete(Long id) {
       champsQrcodeRepository.deleteById(id);
    }

    @Override
    public void supprimerByQrcode(Long id) {
       champsQrcodeRepository.supprimer(id);
    }



 
}
