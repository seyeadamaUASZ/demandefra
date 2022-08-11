/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import sn.gainde2000.pi.core.entity.QrCode;

/**
 *
 * @author asow
 */
public interface IQrCodeService {

    public void saveQrCode(QrCode qrcode);

    public List<QrCode> listQrcodeNotModeliser();

    public List<QrCode> listQrcodeModeliser();

    public Optional<QrCode> findById(Long id);

    public QrCode findByQrcId(Long qrcodeid);

    public void supprimer(QrCode qrcode);

    public List<Map<String, Object>>  listQrcodeGenere();
    
    QrCode findbyQrcNom(String nom);
    

}
