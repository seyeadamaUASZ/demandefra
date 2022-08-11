/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.ParametrageWidgetAttr;

/**
 *
 * @author jsarr
 */
@Repository
public interface ParametrageWidgetAttrRepository extends JpaRepository<ParametrageWidgetAttr,Long>{
    public ParametrageWidgetAttr save(ParametrageWidgetAttr pw);
}
