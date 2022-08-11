/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

import sn.gainde2000.pi.core.entity.LandingPage;

import sn.gainde2000.pi.core.service.ILandingPage;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("landing")
public class LandingPageController {

    @Autowired
    ILandingPage landingService;

    /* Récupère l'ensemble des images contenues sur la landing page*/
    @GetMapping("get")
    public ServeurResponse getAllImage() {
        List<LandingPage> landing = landingService.getAll();
        ServeurResponse response = new ServeurResponse();
        LandingPage l = new LandingPage();
        System.out.println("------------ouf----------" + landing);
        try {
            l.setLndId(landing.get(0).getLndId());
            l.setUsername(landing.get(0).getUsername());
            if (landing.get(0).getLndIm1() != null) {
                l.setLndIm1(decompressBytes(landing.get(0).getLndIm1()));
            }
            if (landing.get(0).getLndIm2() != null) {
                l.setLndIm2(decompressBytes(landing.get(0).getLndIm2()));
            }
            if (landing.get(0).getLndIm3() != null) {
                l.setLndIm3(decompressBytes(landing.get(0).getLndIm3()));
            }
            if (landing.get(0).getLndIm4() != null) {
                l.setLndIm4(decompressBytes(landing.get(0).getLndIm4()));
            }
            if (landing.get(0).getLndIm5() != null) {
                l.setLndIm5(decompressBytes(landing.get(0).getLndIm5()));
            }
            if (landing.get(0).getLndIm6() != null) {
                l.setLndIm6(decompressBytes(landing.get(0).getLndIm6()));
            }
            if (landing.get(0).getLndIm7() != null) {
                l.setLndIm7(decompressBytes(landing.get(0).getLndIm7()));
            }
            if (landing.get(0).getLndIm8() != null) {
                l.setLndIm8(decompressBytes(landing.get(0).getLndIm8()));
            }
            if (landing.get(0).getLndIm9() != null) {
                l.setLndIm9(decompressBytes(landing.get(0).getLndIm9()));
            }
            if (landing.get(0).getLndIm10() != null) {
                l.setLndIm10(decompressBytes(landing.get(0).getLndIm10()));
            }
            response.setStatut(true);
            response.setDescription("Recuperation reussi!");
            response.setData(l);
        } catch (DataFormatException e) {
            response.setStatut(false);
            response.setDescription("erreur serveur");
            response.setData(null);
        } catch (IndexOutOfBoundsException e) {
            response.setStatut(false);
            response.setDescription("erreur serveur");
            response.setData(null);
        }

        return response;
    }

    /* Création et mis à jour des images de la landing page */
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServeurResponse uplaodLogo(
            @RequestParam(name = "imageFile1", required = false) MultipartFile file1,
            @RequestParam(name = "imageFile2", required = false) MultipartFile file2,
            @RequestParam(name = "imageFile3", required = false) MultipartFile file3,
            @RequestParam(name = "imageFile4", required = false) MultipartFile file4,
            @RequestParam(name = "imageFile5", required = false) MultipartFile file5,
            @RequestParam(name = "imageFile6", required = false) MultipartFile file6,
            @RequestParam(name = "imageFile7", required = false) MultipartFile file7,
            @RequestParam(name = "imageFile8", required = false) MultipartFile file8,
            @RequestParam(name = "imageFile9", required = false) MultipartFile file9,
            @RequestParam(name = "imageFile10", required = false) MultipartFile file10,
            @RequestParam(name = "username", required = false) String username) throws IOException {
        ServeurResponse response = new ServeurResponse();
        int n = landingService.nmbreDenregistrement();
        LandingPage l = new LandingPage();
        byte[] logo1 = null;
        byte[] logo2 = null;
        byte[] logo3 = null;
        byte[] logo4 = null;
        byte[] logo5 = null;
        byte[] logo6 = null;
        byte[] logo7 = null;
        byte[] logo8 = null;
        byte[] logo9 = null;
        byte[] logo10 = null;

        if (file1 != null) {
            System.out.println("file 1");
            logo1 = compressBytes(file1.getBytes());
        }

        if (file2 != null) {
            System.out.println("file 2");
            logo2 = compressBytes(file2.getBytes());
        }
        if (file3 != null) {
            System.out.println("file 3");

            logo3 = compressBytes(file3.getBytes());
        }
        if (file4 != null) {
            System.out.println("file 4");

            logo4 = compressBytes(file4.getBytes());
        }
        if (file5 != null) {
            System.out.println("file 5");
            logo5 = compressBytes(file5.getBytes());
        }
        if (file6 != null) {
            System.out.println("file 6");
            logo6 = compressBytes(file6.getBytes());
        }
        if (file7 != null) {
            System.out.println("file 7");
            logo7 = compressBytes(file7.getBytes());
        }
        if (file8 != null) {
            System.out.println("file 8");
            logo8 = compressBytes(file8.getBytes());
        }
        if (file9 != null) {
            System.out.println("file 9");
            logo9 = compressBytes(file9.getBytes());
        }
        if (file10 != null) {
            System.out.println("file 10");
            logo10 = compressBytes(file10.getBytes());
        }

        // System.out.println("---------------------je compte---------" + n);
        if (n == 0) {
            //System.out.println("---------------------if---------");

            try {
                l.setLndIm1(logo1);
                l.setLndIm2(logo2);
                l.setLndIm3(logo3);
                l.setLndIm4(logo4);
                l.setLndIm5(logo5);
                l.setLndIm6(logo6);
                l.setLndIm7(logo7);
                l.setLndIm8(logo8);
                l.setLndIm9(logo9);
                l.setLndIm10(logo10);

                l.setUsername(username);
                landingService.create(l);
                response.setStatut(true);
                response.setDescription("Enregistrement reussi!");
                response.setData(l);
            } catch (Exception e) {
                response.setStatut(false);
                response.setDescription("erreur serveur");
                response.setData(l);
            }
        } else {
            //System.out.println("---------------------else---------");
            LandingPage p = new LandingPage();
            List<LandingPage> landin = landingService.getAll();
            //landingService.supprimer(landin.get(0));
            //System.out.println("---------------------username---------" + landin.get(0).getUsername());
            //System.out.println("---------------------Id---------" + landin.get(0).getLndId());
            // p.setLndId(landin.get(0).getLndId());
            if (file1 == null) {
                p.setLndIm1(landin.get(0).getLndIm1());
            } else {
                p.setLndIm1(logo1);
            }
            if (file2 == null) {
                p.setLndIm2(landin.get(0).getLndIm2());
            } else {
                p.setLndIm2(logo2);
            }
            if (file3 == null) {
                p.setLndIm3(landin.get(0).getLndIm3());
            } else {
                p.setLndIm3(logo3);
            }
            if (file4 == null) {
                p.setLndIm4(landin.get(0).getLndIm4());
            } else {
                p.setLndIm4(logo4);
            }
            if (file5 == null) {
                p.setLndIm5(landin.get(0).getLndIm5());
            } else {
                p.setLndIm5(logo5);
            }
            if (file6 == null) {
                p.setLndIm6(landin.get(0).getLndIm6());
            } else {
                p.setLndIm6(logo6);
            }
            if (file7 == null) {
                p.setLndIm7(landin.get(0).getLndIm7());
            } else {
                p.setLndIm7(logo7);
            }
            if (file8 == null) {
                p.setLndIm8(landin.get(0).getLndIm8());
            } else {
                p.setLndIm8(logo8);
            }
            if (file9 == null) {
                p.setLndIm9(landin.get(0).getLndIm9());
            } else {
                p.setLndIm9(logo9);
            }

            if (file10 == null) {
                p.setLndIm10(landin.get(0).getLndIm10());
            } else {
                p.setLndIm10(logo10);
            }
            p.setUsername(username);
            landingService.supprimer(landin.get(0));
            landingService.create(p);
            response.setStatut(true);
            response.setDescription("Mis à jour reussi!");
            response.setData(p);
        }

        return response;
    }

    /* Compression de la taille des images avant enregistrement*/
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
