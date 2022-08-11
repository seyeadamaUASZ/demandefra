/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.entity.Image;
import sn.gainde2000.pi.core.repository.ImageRepository;

/**
 *
 * @author asow
 */
@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image storeFile(MultipartFile file) {
         // Normalize file name
        String par_fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Image image = null;
        try {
            image = new Image(par_fileName, file.getContentType(), file.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }

         return imageRepository.save(image);
    }
    
    }

