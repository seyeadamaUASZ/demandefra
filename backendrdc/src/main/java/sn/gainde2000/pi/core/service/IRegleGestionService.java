/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import sn.gainde2000.pi.core.entity.RegleGestion;

/**
 *
 * @author jsarr
 */
public interface IRegleGestionService {
     public RegleGestion findByRgwrflId(Long rgwrflId); 
      public List<RegleGestion> findByRegleGestionWrkId(Long id);
    public RegleGestion save(RegleGestion rg);
}
