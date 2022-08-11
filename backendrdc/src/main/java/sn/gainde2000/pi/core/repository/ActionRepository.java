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
import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;

/**
 *
 * @author asow
 */
@Repository
public interface ActionRepository extends JpaRepository<Action, Long>{
    public Action findByActId(String actionid);
    public Action findByActNom(String actionnom);
    public Action findByActCode(String actioncode);
    public Action findByActDescription(String description);
    @Query("SELECT a FROM Action a LEFT JOIN Privilege p ON a.actId=p.action WHERE p.profile.proId = :profil")
    public List<Action> getListActionsProfil(@Param("profil") Long profil);
    public List<Action> findByactMenId(Menu men);

}
