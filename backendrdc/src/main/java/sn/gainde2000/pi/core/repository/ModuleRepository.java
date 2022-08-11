package sn.gainde2000.pi.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    /*List<Module> findBymodAppId(Long id);*/

    @Query("SELECT COUNT(*) FROM Module m")
    int nombreModule();   
    
     @Query(value="SELECT distinct m.* FROM tp_module m left join tp_fonctionnalite f on m.mod_id=f.fon_mod_id left join tr_app_fonc t on f.fon_id=t.appli_fonc_fon_id where t.appli_fonc_app_id=:id and t.appli_fon_is_active=1 ",nativeQuery = true)
     public List<Module> findByModuleAppId(@Param("id") Long id);
}
