/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetTemplate;
/**
 *
 * @author jsarr
 */
public interface WidgetTemplateRepository extends JpaRepository<WidgetTemplate,Long> {
    
    public WidgetTemplate findByWtId(Long wtId);
    
    
}
