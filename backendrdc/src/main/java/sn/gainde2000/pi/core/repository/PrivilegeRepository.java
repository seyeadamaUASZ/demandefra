/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Privilege;
import sn.gainde2000.pi.core.entity.Profil;
import java.util.List;


/**
 *
 * @author asow
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query("SELECT a FROM Action a INNER JOIN Privilege p ON (a.actId=p.action.actId) WHERE p.profile =:profil ")
    public  List<Action> findByProfiles(@Param("profil") Profil profil);
    @Transactional
    @Modifying
    @Query("DELETE FROM Privilege p WHERE p.profile =:profil AND p.action =:action")
    public void removedPrivilege(@Param("profil") Profil profil, @Param("action") Action action);    
    public Privilege findByPriId(String priviliges);

    @Query("SELECT act.actNom FROM Action act, Privilege pri where act.actId = pri.action  AND pri.profile.proId = :Idprofile")
    Iterable<Privilege> Privilegeaccord(@Param("Idprofile") Long Idprofile);
}
