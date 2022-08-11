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
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.TypeDeNotification;

/**
 *
 * @author asow
 */
@Repository
public interface TypeDeNotificationRepository extends JpaRepository<TypeDeNotification, Long> {

    @Query("select t from TypeDeNotification t where t.tdnAnId.anNom='inscription' or t.tdnAnId.anNom='Inscription'")
    public TypeDeNotification findByTdnContenu();
    
          @Query("SELECT t FROM TypeDeNotification t WHERE t.tdnType LIKE %:Keyword%")
      public List<TypeDeNotification> seach(@Param("Keyword") String Keyword);
      
      
    

}
