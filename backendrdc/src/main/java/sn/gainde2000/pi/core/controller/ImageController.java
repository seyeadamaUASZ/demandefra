package sn.gainde2000.pi.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Image;
import sn.gainde2000.pi.core.repository.ImageRepository;
import sn.gainde2000.pi.core.service.IImageService;

/**
 *
 * @author asow
 */
@RestController
public class ImageController {

    @Autowired
    IImageService imageRepository;

    @PostMapping("upload")
    public ServeurResponse uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("la taille orginal de l'image - " + file.getBytes().length);
        Image logo = new Image(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        ServeurResponse response = new ServeurResponse();
        try{
        imageRepository.saveImage(logo);
        response.setStatut(true);
        response.setDescription("Enregistrement reussi!");
        response.setData(logo);
        }
        catch(Exception e ){
        response.setStatut(false);
        response.setDescription("erreur serveur");
        response.setData(logo);
        }
        return response;

    }

    @GetMapping(path = {"/get/{imageId}"})
    public ServeurResponse getImage(@PathVariable("imageId") long imageid) throws IOException, DataFormatException {
        ServeurResponse response = new ServeurResponse();
        final Optional<Image> retrievedImage = imageRepository.findByImgId(imageid);
        Image img = new Image(retrievedImage.get().getImgName(), retrievedImage.get().getImgType(),
                decompressBytes(retrievedImage.get().getImgLogoByte()));
        response.setStatut(true);
        response.setDescription("++++Affichage reussi!!++++");
        response.setData(img);
        return response;
    }
    // compress  l'image avant de le stocker dans la base de donnée

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

    // decompressé la taille de l'image avant de le retourner sur application angular 
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

}
