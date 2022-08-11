package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Image;


public interface IImageService {
	  public void saveImage(Image image);
	    public List<Image> getListImage();
	    public Optional<Image>  findById(Long id);
	    public void supprimer(Image image);
	    Optional<Image> findByImgId(Long img_id);    
}
