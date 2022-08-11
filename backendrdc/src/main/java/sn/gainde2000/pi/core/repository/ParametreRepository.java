/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Parametre;

/**
 *
 * @author yougadieng
 */
@Repository
public interface ParametreRepository extends JpaRepository<Parametre, Long> {

    Parametre findByParamUtiUsername(String param_uti_username);

    @Query("SELECT COUNT(*) FROM Parametre p where p.paramUtiUsername=:param_uti_username")
    int verifyParametre(@Param("param_uti_username") String param_uti_username);

    @Query("SELECT p FROM Parametre p ORDER BY p.paramId DESC")
    //Parametre selectParam(Pageable page);
    public Page<Parametre> selectParam(Pageable pageable);

    
   

}
