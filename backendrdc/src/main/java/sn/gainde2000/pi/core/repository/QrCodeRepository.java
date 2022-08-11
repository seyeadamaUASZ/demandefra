/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.gainde2000.pi.core.entity.QrCode;

/**
 *
 * @author asow
 */
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

    public QrCode findByQrcId(Long qrcodeid);
    QrCode findByQrcNom(String nom);

    @Query("SELECT q FROM QrCode q where q.qrcValider= 'Modeliser'")
    List<QrCode> listQrcodeNotModeliser();

    @Query("SELECT q FROM QrCode q where q.qrcValider = 'Valider'")
    List<QrCode> listQrcodeModeliser();
    //@Query("select q from QrCode q ORDER BY q.qrcId DESC")
    @Query(value="SELECT *FROM tp_qrcode order by tp_qrcode.qrc_id DESC LIMIT 1",nativeQuery=true)
    public List<Map<String, Object>> listQrcodeGenere();

}
