/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.QrCode;
import sn.gainde2000.pi.core.repository.QrCodeRepository;
import sn.gainde2000.pi.core.service.IQrCodeService;

/**
 *
 * @author asow
 */
@Service
public class QrCodeServiceImpl implements IQrCodeService{
    
   @Autowired
   QrCodeRepository qrcodeRepository;

    @Override
    public void saveQrCode(QrCode qrcode) {
        qrcodeRepository.save(qrcode);
    }

    @Override
    public Optional<QrCode> findById(Long id) {
        return qrcodeRepository.findById(id);
    }

    @Override
    public void supprimer(QrCode qrcode) {
        qrcodeRepository.delete(qrcode);
    }

	@Override
	public QrCode findByQrcId(Long qrcodeid) {
		return qrcodeRepository.findByQrcId(qrcodeid);
	}

    @Override
    public List<QrCode> listQrcodeNotModeliser() {
        return qrcodeRepository.listQrcodeNotModeliser();
    }

    @Override
    public List<QrCode> listQrcodeModeliser() {
        return qrcodeRepository.listQrcodeModeliser();
    }

    @Override
    public List<Map<String, Object>>  listQrcodeGenere() {
       return qrcodeRepository.listQrcodeGenere();
    }

    @Override
    public QrCode findbyQrcNom(String nom) {
        return qrcodeRepository.findByQrcNom(nom);
    }


    
}
