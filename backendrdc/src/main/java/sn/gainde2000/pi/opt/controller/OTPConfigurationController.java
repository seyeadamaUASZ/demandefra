package sn.gainde2000.pi.opt.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.opt.entity.OTPConfiguration;
import sn.gainde2000.pi.opt.entity.ParametreOtp;
import sn.gainde2000.pi.opt.repository.OTPConfigurationRepository;
import sn.gainde2000.pi.opt.repository.ParametreOtpReposiroty;
import sn.gainde2000.pi.opt.service.IOTPConfiguration;
import sn.gainde2000.pi.opt.service.IParametreOtp;

@Api(value = "OTPConfigurationController", description = "API pour la génération des code otp ainsi que la vérification pour une validation!!!!")
@RestController
public class OTPConfigurationController {
	
	@Autowired
  private IOTPConfiguration configuration;
	@Autowired
  private IParametreOtp iParametreOtp;	
	
	@Autowired
  private ParametreOtpReposiroty repo;
	
	@Autowired
	private OTPConfigurationRepository repoConf;
	
	@ApiOperation(value = "Liste des codes OTP générés",hidden=true)
	@GetMapping("/listconfigotp")
	public ServeurResponse listOTPConfigurations() {
		ServeurResponse response = new ServeurResponse();
		List<OTPConfiguration> otps= configuration.listOTPConfigurations();
		if(otps.isEmpty()) {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("OTP conf empty !!!");
		}else {
			response.setData(otps);
			response.setStatut(true);
			response.setDescription("otp conf existing !!");
		}
		return response;
	}
	
	@ApiOperation(value = "Méthode de test pour générer un code OTP à partir du libellé",hidden=true)
	@GetMapping("/genotpconf/{libelle}")
	public ServeurResponse genOtpConfigByLibelle(@PathVariable("libelle") String libelle) {
		ParametreOtp parametreOtp = iParametreOtp.findParametreOtpByLibelle(libelle);
		ServeurResponse response = new ServeurResponse();
		if(parametreOtp!=null) {
			OTPConfiguration otpConfiguration = new OTPConfiguration();
			if(parametreOtp.getTypeOtp().equals("SMS")) {
				char[] otp= iParametreOtp.generateCodeOtp("SMS");
				String str = String.valueOf(otp);
				otpConfiguration.setValcode(str);
				otpConfiguration.setValDuree(new Double(parametreOtp.getDuree()).longValue());
				
				configuration.generateCodeWithOTPConfig(otpConfiguration);
				response.setData(otpConfiguration);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}else {
				char[] otp= iParametreOtp.generateCodeOtp("Email");
				String str = String.valueOf(otp);
				otpConfiguration.setValcode(str);
				otpConfiguration.setValDuree(new Double(parametreOtp.getDuree()).longValue());
				
				configuration.generateCodeWithOTPConfig(otpConfiguration);
				response.setData(otpConfiguration);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}	
		}else {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("parametre otp not found !!");	
		}
		
		return response;
	}
	
	@ApiOperation(value = "Méthode utilisée pour générer un code OTP à partir du l'id,le type (sms ou email), le type de durée(minute ou heure)",hidden=true)
	@GetMapping("/genotp/{id}/{type}/{dureee}")
	public ServeurResponse genotpCodeConfigByDureee(@PathVariable("id")Long id,@PathVariable("type")String type,@PathVariable("dureee") String dureee) {
		ServeurResponse response = new ServeurResponse();
		ParametreOtp otp = repo.findById(id).get();
		OTPConfiguration conf=new OTPConfiguration();
		if(otp!=null) {
			if(otp.getTypeOtp().equals(type)) {
				char[] codeotp= iParametreOtp.generateCodeOtp(type);
				String str = String.valueOf(codeotp);
				conf.setValcode(str);
				conf.setValDuree(new Double(otp.getDuree()).longValue());
				conf.setParametreOtp(otp);
				configuration.generateCodeWithOTPConfig(conf);
				
				response.setData(conf);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			
			}else {
				response.setData(null);
				response.setStatut(false);
				response.setDescription("type code not found !!");
			}
		}else {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("parametreotp not found !!");
		}
		return response;
	}
	
	@ApiOperation(value = "Méthode utilisée pour générer un code OTP à partir de l'évenement de validation",hidden=true)
	@GetMapping("/genotpEvenement/{evenement}")
	public ServeurResponse genotpCodeEvenement(@PathVariable("evenement")String evenement) {
		ParametreOtp parametreOtp = iParametreOtp.findParametreByEvenement(evenement);
		ServeurResponse response = new ServeurResponse();
		if(parametreOtp!=null) {
			OTPConfiguration otpConfiguration = new OTPConfiguration();
			if(parametreOtp.getTypeOtp().equals("SMS")) {
				char[] otp= iParametreOtp.generateCodeOtp("SMS");
				String str = String.valueOf(otp);
				otpConfiguration.setValcode(str);
				otpConfiguration.setValDuree(new Double(parametreOtp.getDuree()).longValue());
				otpConfiguration.setParametreOtp(parametreOtp);
				configuration.generateCodeWithOTPConfig(otpConfiguration);
				response.setData(otpConfiguration);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}else {
				char[] otp= iParametreOtp.generateCodeOtp("Email");
				String str = String.valueOf(otp);
				otpConfiguration.setValcode(str);
				otpConfiguration.setValDuree(new Double(parametreOtp.getDuree()).longValue());
				
				configuration.generateCodeWithOTPConfig(otpConfiguration);
				response.setData(otpConfiguration);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}
			
			
		}else {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("parametre otp not found !!");	
		}
		
		return response;
	}

	//gen by type duree
	@ApiOperation(value = "Méthode utilisée pour générer un code OTP à partir de l'id du paramétre et le type de durée (minute ou heure)",hidden=true)
	@GetMapping("/genotpBydureee/{id}/{dureee}")
	public ServeurResponse genOtpByDureee(@PathVariable("id")Long id,@PathVariable("dureee")String dureee) {
		ServeurResponse response = new ServeurResponse();
		ParametreOtp otp = repo.findById(id).get();
		OTPConfiguration conf=new OTPConfiguration();
		if(otp!=null) {
			if(otp.getTypeOtp().equals("SMS")) {
				char[] codeotp= iParametreOtp.generateCodeOtp("SMS");
				String str = String.valueOf(codeotp);
				conf.setValcode(str);
				conf.setValDuree(new Double(otp.getDuree()).longValue());
				conf.setParametreOtp(otp);
				configuration.generateCodeWithOTPConfig(conf);
				response.setData(conf);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}else {
				char[] codeotp= iParametreOtp.generateCodeOtp("Email");
				String str = String.valueOf(codeotp);
				conf.setValcode(str);
				conf.setValDuree(new Double(otp.getDuree()).longValue());
				
				configuration.generateCodeWithOTPConfig(conf);
				response.setData(conf);
				response.setStatut(true);
				response.setDescription("otpconfiguration added !!");
			}
		}else {
			response.setData(null);
			response.setStatut(false);
			response.setDescription("Parametre otp not found !!");
		}
		return response;
	}
	
	//verifier le code depuis l'entité
	@ApiOperation(value = "Méthode utilisée pour la vérification d'un code déjà généré")
	@GetMapping("/verificationCode/{code}")
	public ServeurResponse verifCodeOtp(@PathVariable("code") String code) {
		ServeurResponse response=new ServeurResponse();
		OTPConfiguration otpconfiguration=configuration.findOTPConfigurationByValcode(code);
		if(otpconfiguration!=null) {
			Date d = otpconfiguration.getOtpValDateCreation();
			//System.out.println(d.getTime());
			d.setTime(d.getTime() + otpconfiguration.getValDuree()*60000);
			
			if(code.equals(otpconfiguration.getValcode()) && ((new Date()).getTime() - d.getTime()) < 0L) {
				if(otpconfiguration.isDejavalide()==false) {
					response.setStatut(true);
					response.setDescription("Code Validé !!!");
					response.setData(null);
				    otpconfiguration.setDejavalide(true);
				    repoConf.save(otpconfiguration);
				}else {
					response.setStatut(false);
					response.setData(null);
					response.setDescription("Code dejà validé!!");
				}		
			}else {
				response.setStatut(false);
				response.setData(null);
				response.setDescription("Code expiré !!");
			}
		}else {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Code inexistant  !!");
		}
		return response;
	}
}
