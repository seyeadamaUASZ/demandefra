/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.Theme;
import sn.gainde2000.pi.core.repository.ThemeRepository;
import sn.gainde2000.pi.core.service.IThemeService;

/**
 *
 * @author asow
 */
@Service
public class ThemeServiceImpl implements IThemeService{
    @Autowired
    ThemeRepository themeRepository;

    @Override
    public List<Theme> getListTheme() {
        return themeRepository.findAll();
    }
    
    
}
