/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.LandingPage;

/**
 *
 * @author yougadieng
 */
@Repository
public interface LandingPageRepository extends JpaRepository<LandingPage,Long>{
    public LandingPage findByUsername(String username);
    @Query("SELECT COUNT(*) FROM LandingPage l")
    int nombreEnregistrement();
    
}
