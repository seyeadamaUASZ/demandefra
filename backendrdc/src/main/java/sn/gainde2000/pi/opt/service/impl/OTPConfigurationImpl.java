package sn.gainde2000.pi.opt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.opt.entity.OTPConfiguration;
import sn.gainde2000.pi.opt.entity.ParametreOtp;
import sn.gainde2000.pi.opt.repository.OTPConfigurationRepository;
import sn.gainde2000.pi.opt.service.IOTPConfiguration;
import sn.gainde2000.pi.opt.service.IParametreOtp;

@Service
public class OTPConfigurationImpl implements IOTPConfiguration{
   
	@Autowired
	private OTPConfigurationRepository repo;
	
	@Autowired
	private IParametreOtp iparam;
	
	@Override
	public List<OTPConfiguration> listOTPConfigurations() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public OTPConfiguration generateCodeWithOTPConfig(OTPConfiguration otpConfiguration) {
		// TODO Auto-generated method stub
		return repo.save(otpConfiguration);
	}

	@Override
	public OTPConfiguration getOTPConfiguration(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public OTPConfiguration findOTPConfigurationByValcode(String code) {
		// TODO Auto-generated method stub
		return repo.findOTPConfigurationByValcode(code);
	}

	@Override
	public OTPConfiguration generateOtpByEvenement(String evenement) {
		// TODO Auto-generated method stub
		ParametreOtp otp= iparam.findParametreByEvenement(evenement);
		if(otp!=null) {		
			OTPConfiguration configuration=new OTPConfiguration();
			if(otp.getTypeOtp().equals("SMS")) {
				char[] codeotp=iparam.generateCodeOtp("SMS");
				String str = String.valueOf(codeotp);
				configuration.setValcode(str);
				//configuration.setValDuree(new Double(otp.getDuree()).longValue());
				//configuration.setParametreOtp(otp);
				//repo.save(configuration);
			}else {
				char[] codeotp=iparam.generateCodeOtp("Email");
				String str = String.valueOf(codeotp);
				configuration.setValcode(str);
			}
			configuration.setValDuree(new Double(otp.getDuree()).longValue());
			configuration.setParametreOtp(otp);
			return repo.save(configuration);
		}else {
			return null;
		}
		
	}

	@Override
	public OTPConfiguration generateOtpByLibelle(String libelle) {
		ParametreOtp otp= iparam.findParametreOtpByLibelle(libelle);
		if(otp!=null) {		
			OTPConfiguration configuration=new OTPConfiguration();
			if(otp.getTypeOtp().equals("SMS")) {
				char[] codeotp=iparam.generateCodeOtp("SMS");
				String str = String.valueOf(codeotp);
				configuration.setValcode(str);
				//configuration.setValDuree(new Double(otp.getDuree()).longValue());
				//configuration.setParametreOtp(otp);
				//repo.save(configuration);
			}else {
				char[] codeotp=iparam.generateCodeOtp("Email");
				String str = String.valueOf(codeotp);
				configuration.setValcode(str);
			}
			configuration.setValDuree(new Double(otp.getDuree()).longValue());
			configuration.setParametreOtp(otp);
			return repo.save(configuration);
		}else {
			return null;
		}
	}

}
