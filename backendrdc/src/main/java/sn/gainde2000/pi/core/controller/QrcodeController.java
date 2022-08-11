package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.QrCode;
import sn.gainde2000.pi.core.service.IChampsQrcodeService;
import sn.gainde2000.pi.core.service.IQrCodeService;
import sn.gainde2000.pi.core.tools.Qrcode;
//import org.tensorflow.TensorFlow;

@RestController
@RequestMapping("/barcodes")
public class QrcodeController {

    @Autowired
    IQrCodeService qrcodeService;
    Qrcode qrcode = new Qrcode();

    @Autowired
    IChampsQrcodeService champsQrcodeService;

    @Autowired
    private AppProperties app;

    /**
     * Créer un QRcode
     *
     * @param qrcode
     * @return ServeurResponse
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @PostMapping("/qrcode")
    public ServeurResponse createQrocde(@RequestBody QrCode qrcode) throws Exception {
        ServeurResponse response = new ServeurResponse();
        if (qrcodeService.findbyQrcNom(qrcode.getQrcNom()) == null) {
            qrcode.setQrcValider("Modeliser");
            Qrcode.generateQRCodeImage1(qrcode.getQrcNom());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(Qrcode.generateQRCodeImage1(qrcode.getQrcNom()), "png", os);
            qrcode.setQrcLogo(os.toByteArray());
            qrcodeService.saveQrCode(qrcode);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
        } else {

            response.setStatut(false);
            response.setDescription("Ce qrcode existe deja");
        }

        return response;
    }

    @PostMapping("/qrcodeGennere")
    public ServeurResponse createQrocdeGenerer(HttpServletRequest request, @RequestParam(value = "data") String donnéesQrcode, @RequestParam("file1") MultipartFile file) {
        ServeurResponse response = new ServeurResponse();

        QrCode qrcod;
        try {
            qrcod = new ObjectMapper().readValue(
                    request.getParameter("qrcode"), new TypeReference<QrCode>() {
            }
            );
            qrcod.setQrcImg(file.getBytes());
            if (qrcod.getQrcImg() != null && qrcod.getQrcCouleur() != 0) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
                JSONObject obj = new JSONObject(donnéesQrcode);
                obj.keys().forEachRemaining(key -> {
                    Object value = obj.get(key);
                });
                Qrcode.generateQRCodeImage(donnéesQrcode + "\n", qrcod.getQrcCouleur(), qrcod.getQrcImg());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(Qrcode.generateQRCodeImage(donnéesQrcode + "\n", qrcod.getQrcCouleur(), qrcod.getQrcImg()), "png", os);
                qrcod.setQrcLogo(os.toByteArray());
                qrcodeService.saveQrCode(qrcod);
                response.setStatut(true);
                response.setDescription("Enregistrement réussi");
            } else {
                Qrcode.generateQRCodeImage1(donnéesQrcode);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(Qrcode.generateQRCodeImage1(donnéesQrcode), "png", os);
                qrcod.setQrcLogo(os.toByteArray());
                qrcodeService.saveQrCode(qrcod);
                response.setStatut(true);
                response.setDescription("Enregistrement réussi");

            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * listes des qrcodes
     *
     * @return ServeurResponse
     */
    @GetMapping("qrcode/listQrcodeNotModeliser")
    public ServeurResponse getAllQrCode() {
        Iterable<QrCode> qr = qrcodeService.listQrcodeNotModeliser();

        ServeurResponse response = new ServeurResponse();
        if (qr == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(qr);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("qrcode/QrcodeModeliser")
    public ServeurResponse getAllQrCodeModeliser() {
        Iterable<QrCode> qr = qrcodeService.listQrcodeModeliser();

        ServeurResponse response = new ServeurResponse();
        if (qr == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(qr);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("qrcode/QrcodeGenerer")
    public ServeurResponse getAllQrCodeGenerer() {
        List<Map<String, Object>> qr = qrcodeService.listQrcodeGenere();
        ServeurResponse response = new ServeurResponse();
        if (qr == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(qr);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @PostMapping("qrcode/valider")
    public ServeurResponse valider(@RequestBody QrCode qrcode) {
        ServeurResponse response = new ServeurResponse();
        qrcode.setQrcStatus(false);
        qrcode.setQrcValider("Valider");
        qrcodeService.saveQrCode(qrcode);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(qrcode);

        return response;
    }

    @PostMapping("qrcode/modeliser")
    public ServeurResponse modeliser(@RequestBody QrCode qrcode) {
        ServeurResponse response = new ServeurResponse();
        QrCode q = qrcodeService.findByQrcId(qrcode.getQrcId());
        q.setQrcValider("Modeliser");
        qrcodeService.saveQrCode(q);
        response.setStatut(true);
        response.setDescription("Rapport valider");
        response.setData(q);

        return response;
    }

    /**
     * Décompresse l'image
     *
     * @param data
     * @return static byte[]
     * @throws DataFormatException
     */
    public static byte[] decompressBytes(byte[] data) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        }
        return outputStream.toByteArray();
    }

    /**
     * Compresse l'image ud QRcode
     *
     * @param data
     * @return static byte[]
     */
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressé la taille de l'image- " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    /**
     * Télécharge l'image du QRcode
     *
     * @param qrcodeid
     * @param response
     * @return ResponseEntity<ByteArrayResource>
     */
    @GetMapping("qrcode/downloadFile/{qrcodeid}")
    public ResponseEntity<ByteArrayResource> downloadQrCode(@PathVariable Long qrcodeid, HttpServletResponse response) {
        QrCode qr = qrcodeService.findByQrcId(qrcodeid);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + qr.getQrcNom() + "\"")
                .body(new ByteArrayResource(qr.getQrcLogo()));

    }

    /**
     * Supprime le QRcode
     *
     * @param qrcode
     * @return ServeurResponse
     */
    @PostMapping("qrcode/delete")
    public ServeurResponse deleteQrcode(@RequestBody QrCode qrcode) {
        ServeurResponse response = new ServeurResponse();
        qrcodeService.supprimer(qrcode);
        response.setStatut(true);
        return response;
    }

    @PostMapping("qrcode/supprime")
    public ServeurResponse deleteRapport(@RequestBody QrCode qrcod) {
        ServeurResponse response = new ServeurResponse();
        champsQrcodeService.supprimerByQrcode(qrcod.getQrcId());
        qrcodeService.supprimer(qrcod);
        response.setStatut(true);
        return response;
    }

}
