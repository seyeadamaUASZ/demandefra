package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Image;
import sn.gainde2000.pi.core.repository.ImageRepository;
import sn.gainde2000.pi.core.service.IImageService;
@Service
public class ImageServiceImpl implements IImageService {
	
	@Autowired
	 ImageRepository imageRepository;

	@Override
	public void saveImage(Image image) {
		
		imageRepository.save(image);
	}

	@Override
	public List<Image> getListImage() {
		// TODO Auto-generated method stub
		return imageRepository.findAll();
	}

	@Override
	public Optional<Image> findById(Long id) {
		// TODO Auto-generated method stub
		return imageRepository.findById(id);
	}

	@Override
	public void supprimer(Image image) {
		imageRepository.delete(image);
		
	}

	@Override
	public Optional<Image> findByImgId(Long img_id) {
		// TODO Auto-generated method stub
		return imageRepository.findByImgId(img_id);
	}

}
