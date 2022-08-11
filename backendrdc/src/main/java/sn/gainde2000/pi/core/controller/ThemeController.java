/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Theme;
import sn.gainde2000.pi.core.service.IThemeService;
import sn.gainde2000.pi.core.service.impl.ThemeServiceImpl;

/**
 *
 * @author hp
 */
@RestController
@CrossOrigin("*")
@RequestMapping("theme")
public class ThemeController {

     @Autowired
     IThemeService themeService;

     @GetMapping("list")
     public ServeurResponse getAllTheme() {
          ServeurResponse response = new ServeurResponse();
          Iterable<Theme> theme = themeService.getListTheme();
          if (theme == null) {
               response.setData(null);
               response.setStatut(false);
               response.setDescription("aucun élément trouvé");
          } else {
               response.setStatut(false);
               response.setData(theme);
               response.setDescription("La liste des themes");
          }
          return response;
     }
}
