/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Theme;

/**
 *
 * @author yougadieng
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>{
    Theme findByThmId(String thm_id);
}
