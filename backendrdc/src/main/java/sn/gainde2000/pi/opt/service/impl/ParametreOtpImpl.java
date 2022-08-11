package sn.gainde2000.pi.opt.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.opt.entity.ParametreOtp;
import sn.gainde2000.pi.opt.repository.ParametreOtpReposiroty;
import sn.gainde2000.pi.opt.service.IParametreOtp;


@Service
public class ParametreOtpImpl implements IParametreOtp{
	
	@Autowired
	private ParametreOtpReposiroty repo;

	@Override
	public ParametreOtp addParametreOtp(ParametreOtp opt) {
		// TODO Auto-generated method stub
		return repo.save(opt);
	}

	@Override
	public List<ParametreOtp> listParametreOpt() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ParametreOtp> getParametreOpt(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void supprimerParametreOpt(Long id) {
		// TODO Auto-generated method stub
		ParametreOtp otp=repo.findById(id).get();
		if(otp!=null) {
			repo.delete(otp);
		}
	}

	@Override
	public void modifierParametreOtp(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char[] generateCodeOtp(String type) {
		// TODO Auto-generated method stub
		if(type.equals("SMS")) {
			return generateOtpNumberSMS(6);
		}else {
			return generateOtpNumberEmail(6);
		}
		
	}

	//fonction de génération des codes soit chiffre ou alphanumerique
	
	public char[] generateOtpNumberSMS(int len) {
		String numbers="0123456789";
		Random rnd = new Random();
		char[] otp = new char[len];
		for(int i=0;i<len;i++) {
			otp[i]=numbers.charAt(rnd.nextInt(numbers.length()));
		}
		return otp;
	}
	
	public char[] generateOtpNumberEmail(int len) {
		String numbers="0123456789";
		Random rnd = new Random();
		char[] otp = new char[len];
		for(int i=0;i<len;i++) {
			otp[i]=numbers.charAt(rnd.nextInt(numbers.length()));
		}
		return otp;
	}
	
	public char[] generateOtpAlphaNumerique(int len) {
		    String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
	        String numbers = "0123456789";
	        String symbols = "!@#$%^&*_=+-/.?<>)";
	        String values = Capital_chars + Small_chars +
	                        numbers + symbols;
	        
	     // Using random method
	        Random rndm_method = new Random();
	  
	        char[] otp = new char[len];
	  
	        for (int i = 0; i < len; i++)
	        {
	            // Use of charAt() method : to get character value
	            // Use of nextInt() as it is scanning the value as int
	            otp[i] =
	              values.charAt(rndm_method.nextInt(values.length()));
	  
	        }
	        return otp;
	}

	@Override
	public ParametreOtp findParametreOtpByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return repo.findParametreOtpByLibelle(libelle);
	}

	@Override
	public ParametreOtp findParametreByDureee(String dureee) {
		// TODO Auto-generated method stub
		return repo.findParametreOtpByDureee(dureee);
	}

	@Override
	public ParametreOtp findParametreByEvenement(String evenement) {
		// TODO Auto-generated method stub
		return repo.findParametreOtpByEvenement(evenement);
	}

	@Override
	public List<ParametreOtp> listParametreOtpByType(String type) {
		// TODO Auto-generated method stub
		return repo.listParametrebyType(type);
	}

	@Override
	public List<ParametreOtp> listParametreOtpByDureee(String dureee) {
		// TODO Auto-generated method stub
		return repo.listParametreOtpByDureee(dureee);
	}

	@Override
	public void deleteParametreByLibelle(String libelle) {
		ParametreOtp otp=repo.findParametreOtpByLibelle(libelle);
		if(otp!=null) {
			repo.delete(otp);
		}
		
	}

}
