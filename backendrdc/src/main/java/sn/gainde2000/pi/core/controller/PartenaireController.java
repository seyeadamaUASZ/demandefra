/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import static sn.gainde2000.pi.core.controller.LandingPageController.compressBytes;
import static sn.gainde2000.pi.core.controller.LandingPageController.decompressBytes;
import sn.gainde2000.pi.core.entity.Partenaire;
import sn.gainde2000.pi.core.service.IPartenaireService;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("partenaire")
public class PartenaireController {

    @Autowired
    IPartenaireService partService;

    @GetMapping("get")
    public ServeurResponse getAllImage() {
        List<Partenaire> partner = partService.getAll();
        byte[] logo = null;

        ServeurResponse response = new ServeurResponse();
        if (partner != null) {
            try {
                for (int i = 0; i < partner.size(); i++) {
                   logo= decompressBytes(partner.get(i).getPartIm());
                   partner.get(i).setPartIm(logo);
                }
                response.setData(partner);
                response.setStatut(true);
                response.setDescription("ok");
            } catch (DataFormatException e) {
            }

        }
        return response;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServeurResponse uplaodLogo(HttpServletRequest request,
            @RequestParam(name = "imageFile1", required = false) MultipartFile file1,
            @RequestParam(name = "nomPartner", required = false) String nomPartner) throws IOException {
        ServeurResponse response = new ServeurResponse();
        Partenaire p = new Partenaire();
        byte[] logo1 = null;
        if (file1 != null) {
            System.out.println("file 1");
            logo1 = compressBytes(file1.getBytes());
            p.setPartIm(logo1);
            p.setPartNom(nomPartner);
            partService.save(p);
            response.setData(p);
            response.setStatut(true);
            response.setDescription("ok");
        }

        return response;
    }

    @PostMapping("delete")
    public ServeurResponse deletepartner(@RequestBody Partenaire partner) {
        ServeurResponse response = new ServeurResponse();
        partService.delete(partner);
        response.setData(partner);
        response.setStatut(true);
        return response;
    }
}
