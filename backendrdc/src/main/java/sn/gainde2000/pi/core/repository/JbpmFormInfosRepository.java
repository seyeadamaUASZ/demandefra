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
import sn.gainde2000.pi.core.entity.JbpmFormInfos;

/**
 *
 * @author jsarr
 */
@Repository
public interface JbpmFormInfosRepository extends JpaRepository<JbpmFormInfos, Long> {
    
    public JbpmFormInfos findByJfrmWorkflow(String jfrmWorkflow);
    public JbpmFormInfos findByJfrmFormulaire(String jfrmFormulaire);
    public JbpmFormInfos findByJfrmId(Long jfrmId);
    @Query("select jf from Jbpmformulaire jf ")
    public List<JbpmFormInfos> getListJbpmFormInfos();
    
    @Query("select jf from Jbpmformulaire jf where jf.jfrmWorkflow =:containerId AND jf.statusFrmWorkflow =1 ")
    public List<JbpmFormInfos> getListJbpmFormInfosBycontainer(@Param("containerId") String containerId);
    
     @Query("select jf from Jbpmformulaire jf where jf.jfrmId =:jfrmId")
    public List<JbpmFormInfos> getListJbpmFormInfosById(@Param("jfrmId") Long jfrmId);
     
     @Query("select jf from Jbpmformulaire jf where jf.jfrmWorkflow =:containerId AND jf.statusFrmWorkflow =0 ")
     public List<JbpmFormInfos> getListJbpmFormInfosGenerer(@Param("containerId") String containerId);
     @Query("select jf from Jbpmformulaire jf where jf.statusFrmWorkflow =1")
     public List<JbpmFormInfos> getListJbpmFormInfosNoGenerer();
     
     @Query("select jf from Jbpmformulaire jf where jf.jfrmWorkflow =:containerId")
     public List<JbpmFormInfos> getListJbpmFormInfos(@Param("containerId") String containerId);
}
