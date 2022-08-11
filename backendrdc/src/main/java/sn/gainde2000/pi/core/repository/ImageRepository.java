/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Image;

/**
 *
 * @author yougadieng
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImgId(Long img_id);    
    
}
