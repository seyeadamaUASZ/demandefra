/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.pi.core.entity.PwdCriteria;

/**
 *
 * @author asow
 */
public interface PwdCriteriaRepository extends JpaRepository<PwdCriteria, Long> {

    public PwdCriteria findByPwdId(Long pwdId);
}
