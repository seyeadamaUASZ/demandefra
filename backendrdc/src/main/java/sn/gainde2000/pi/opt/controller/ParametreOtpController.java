package sn.gainde2000.pi.opt.controller;


import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.tools.Email;
import sn.gainde2000.pi.core.tools.Sms;
import sn.gainde2000.pi.opt.entity.OTPConfiguration;
import sn.gainde2000.pi.opt.entity.ParametreOtp;
import sn.gainde2000.pi.opt.repository.OTPConfigurationRepository;
import sn.gainde2000.pi.opt.repository.ParametreOtpReposiroty;

import sn.gainde2000.pi.opt.service.IParametreOtp;

@Api(value = "ParametreOtpController", description = "API pour les paramétrage de code OTP! Ici vous ne ferez que le paramétrage.\n"
		+ " La génération et la vérification se feront au niveau du controller OTPConfiguration")
@RestController
public class ParametreOtpController {
	@Autowired
	private IParametreOtp iParametreOtp;
	
	@Autowired
	private ParametreOtpReposiroty repo;
	
	@Autowired
	private OTPConfigurationRepository repoConf;
	
	 @Autowired
	 Email email;
	
	@ApiOperation(value = "Liste des paramétrages de code OTP")
	@GetMapping("otp/list")
	public ServeurResponse getAllParamatreOtp() {
		List<ParametreOtp> parametres = iParametreOtp.listParametreOpt();
		ServeurResponse response = new ServeurResponse();
		if(parametres.isEmpty()) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucun element trouvé!!!");
		}else {
			response.setStatut(true);
			response.setData(parametres);
			response.setDescription("Parametres trouvés!!!");
		}
		return response;
	}
	
	@ApiOperation(value = "Cette méthode est utilisée pour ajouter un nouveau paramétrage")
	@PostMapping("otp/addParametre")
	public ServeurResponse addParametreOtp(@RequestBody ParametreOtp otp) {
	     ServeurResponse response = new ServeurResponse();
	     ParametreOtp otp2 = iParametreOtp.addParametreOtp(otp);
         if(otp2!=null) {
        	 response.setStatut(true);
        	 response.setData(otp2);
        	 response.setDescription("Parametre Otp ajouté avec succés!!");
         }else {
        	 response.setStatut(false);
        	 response.setData(null);
        	 response.setDescription("Parametre Otp non ajouté!!");
         }
         return response;
	}
	
	@ApiOperation(value = "Cette méthode est utilisée pour récupérer un élément")
	@GetMapping("otp/checkotp/{id}")
	public ServeurResponse checkParametreOtp(@PathVariable(name="id")Long id) {
		ServeurResponse response = new ServeurResponse();
		Optional<ParametreOtp> otp = iParametreOtp.getParametreOpt(id);
		if(otp!=null) {
			response.setStatut(true);
			response.setData(otp);
			response.setDescription("otp récupéré avec succés!!!");
		}else {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("otp non récupéré !!");
		}
		return response;
	}
	
	@ApiOperation(value = "Méthode pour supprimer un paramétrage")
	//@DeleteMapping("")
	@RequestMapping(value = "otp/deleteparametre/{id}", method = RequestMethod.GET)
	public ServeurResponse deleteParametreOtp(@PathVariable Long id) {
		ServeurResponse response = new ServeurResponse();
		iParametreOtp.supprimerParametreOpt(id);
		response.setStatut(true);
        response.setDescription("Parametre otp supprimé!!");
        return response;
		
	}
	
	@ApiOperation(value = "Méthode pour supprimer un paramétrage")
	@GetMapping("otp/deleteparametrebyLibelle/{libelle}")
	public ServeurResponse deleteParametreOtpByLibelle(@PathVariable("libelle")String libelle) {
		ServeurResponse response = new ServeurResponse();
		iParametreOtp.deleteParametreByLibelle(libelle);
		response.setStatut(true);
        response.setDescription("Parametre otp supprimé!!");
        return response;
		
	}
	
	@ApiOperation(value = "Cette méthode est utilisée pour générer un code OTP pour des tests")
	@GetMapping("otp/generateotp/{type}")
	public ServeurResponse generateOtp(@PathVariable(name="type")String type) {
		ServeurResponse response= new ServeurResponse();
		if(type.equals("SMS")) {
			char[] otp= iParametreOtp.generateCodeOtp("SMS");
			response.setStatut(true);
			response.setData(otp);
			response.setDescription("otp généré");
			return response;
			
		}else {
			char[] otp= iParametreOtp.generateCodeOtp("Email");
			response.setStatut(true);
			response.setData(otp);
			response.setDescription("otp généré");
			return response;
		}
		
	}
	
	//modifier
	@ApiOperation(value = "Méthode pour update un paramétre")
	 @PostMapping("otp/update/{id}")
	public ServeurResponse updateParametreOtp(@PathVariable(name="id")Long id, @RequestBody ParametreOtp otp) {
		ServeurResponse response=new ServeurResponse();
		ParametreOtp otp1= repo.findById(id).get();
		otp1.setLibelle(otp.getLibelle());
		otp1.setCaractere(otp.getCaractere());
		otp1.setDuree(otp.getDuree());
		otp1.setDureee(otp.getDureee());
		otp1.setEvenement(otp.getEvenement());
		otp1.setTypeOtp(otp.getTypeOtp());
		iParametreOtp.addParametreOtp(otp1);
		response.setStatut(true);
		response.setData(otp1);
		response.setDescription("Parametre otp modifié avec succés!!");
		return response;
	}
	 
	@ApiOperation(value = "Cette méthode est utilisée pour test de génération à partir du type sms ou email",hidden=true)
	 @GetMapping("/generatecodeotp/{type}")
	 public ServeurResponse generateCodeOtpByType(@PathVariable(name="type") String type) {
		 ServeurResponse response= new ServeurResponse();
			if(type.equals("SMS")) {
				char[] otp= iParametreOtp.generateCodeOtp("SMS");
				response.setStatut(true);
				response.setData(otp);
				response.setDescription("otp généré");
				 //Sms s = new Sms();
			     //s.sendSms("778094925","code de Validation "+ Arrays.toString(otp));
				return response;
				
			}else {
				char[] otp= iParametreOtp.generateCodeOtp("Email");
				response.setStatut(true);
				response.setData(otp);
				response.setDescription("otp généré");
				return response;
			}

	 }
	 
	 @ApiOperation(value = "Cette méthode est utilisée pour générer un code OTP à partir du type sms ou email")
	 @GetMapping("/envoyercodeotp/{type}/{numeroemail}")
	 public ServeurResponse envoyerCodeOtpByTypeAndNumero(@PathVariable(name="type")String type,@PathVariable(name="numeroemail")String numero) {
		 ServeurResponse response= new ServeurResponse();
		 List<ParametreOtp> parametreotps = iParametreOtp.listParametreOtpByType(type);
		 if(!parametreotps.isEmpty()) {
			 ParametreOtp parametreOtp =parametreotps.get(0);
			 OTPConfiguration otpConf=new OTPConfiguration();
			 otpConf.setParametreOtp(parametreOtp);
			 
			 if(type.equals("SMS")) {
					char[] otp= iParametreOtp.generateCodeOtp("SMS");
					 otpConf.setValcode(String.valueOf(otp));					
					
				}else {
					char[] otp= iParametreOtp.generateCodeOtp("Email");	
					otpConf.setValcode(String.valueOf(otp));
				}
			 otpConf.setValDuree(new Double(parametreOtp.getDuree()).longValue());
			 otpConf.setDejavalide(false);
			 //envoyer le sms
			 repoConf.save(otpConf);
			 
			 if(type.equals("SMS")) {
				 Sms s = new Sms();
			     s.sendSms(numero,"code de Validation :"+otpConf.getValcode());  
			 }else {
				 String from = "no-reply@gainde2000.sn";
                 try {
					email.sendMail(from,numero,"code de validation est \n"+otpConf.getValcode(),"validation code OTP");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 response.setStatut(true);
		     response.setDescription("code envoyé par "+parametreOtp.getTypeOtp()+"!!!");
		     response.setData(otpConf);
			   	 
		 }else {
			 response.setStatut(false);
		     response.setDescription("ce type de parametrage n'existe pas");
		     response.setData(null);
		 }
		 return response;
			
	 }
	  
	 //envoyer code otp avec otp configuration
	 @ApiOperation(value = "Cette méthode est utilisée pour des tests de génération de code OTP à partir de id parametreOTP et le numéro de téléphone ou email")
	 @GetMapping("/envoyercode/{id}/{numeroemail}")
	 public ServeurResponse envoyerCodeOtpByIdAndNumero(@PathVariable(name="id")Long id,@PathVariable(name="numeroemail")String numero) {
		 ServeurResponse response =new ServeurResponse();
		 ParametreOtp parametre = repo.findById(id).get();
		 if(parametre!=null) {
			 OTPConfiguration otpConf=new OTPConfiguration();
			 otpConf.setParametreOtp(parametre);
			 if(parametre.getTypeOtp().equals("SMS")) {
				 char[] code= iParametreOtp.generateCodeOtp("SMS");
				 otpConf.setValcode(String.valueOf(code));
				 
			 }else {
				 char[] code= iParametreOtp.generateCodeOtp("Email");
				 otpConf.setValcode(String.valueOf(code));
			 }
			 otpConf.setValDuree(new Double(parametre.getDuree()).longValue());
			 otpConf.setDejavalide(false);
			 //envoyer le sms
			 repoConf.save(otpConf);
			 
			 //pour l'envoi du code otp soit par mail ou sms
			 if(parametre.getTypeOtp().equals("SMS")) {
				 Sms s = new Sms();
			     s.sendSms(numero,"code de Validation :"+otpConf.getValcode()); 
			 }else {
				 String from = "no-reply@gainde2000.sn";
                 try {
					email.sendMail(from,numero,"code de validation est :\n"+otpConf.getValcode(),"validation code OTP");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 
		     response.setStatut(true);
		     response.setDescription("code envoyé par "+parametre.getTypeOtp()+"!!!");
		     response.setData(otpConf);
		 }else {
			 response.setStatut(false);
			 response.setDescription("Code non envoyé !!");
			 response.setData(null);
		 }
		 return response;
	 }
	 
	 //envoyer code depuis le libellé
	 
	 @ApiOperation(value = "Cette méthode est utilisée pour générer un code OTP \n "
	 		+ "à partir du libellé avec comme parametre numéro tel ou email")
	 @GetMapping("/envoyercodeByLibelle/{libelle}/{numeroemail}")
	 public ServeurResponse envoyerCodeOtpByLibelleAndNumero(@PathVariable(name="libelle")String libelle,@PathVariable(name="numeroemail")String numero) {
		 ServeurResponse response =new ServeurResponse();
		 ParametreOtp parametre = iParametreOtp.findParametreOtpByLibelle(libelle);
		 if(parametre!=null) {
			 OTPConfiguration otpConf=new OTPConfiguration();
			 otpConf.setParametreOtp(parametre);
			 if(parametre.getTypeOtp().equals("SMS")) {
				 char[] code= iParametreOtp.generateCodeOtp("SMS");
				 otpConf.setValcode(String.valueOf(code));
				 
			 }else {
				 char[] code= iParametreOtp.generateCodeOtp("Email");
				 otpConf.setValcode(String.valueOf(code));
			 }
			 otpConf.setValDuree(new Double(parametre.getDuree()).longValue());
			 //otpConf.setDejavalide(false);
			 repoConf.save(otpConf);
			 //envoyer le sms
			 if(parametre.getTypeOtp().equals("SMS")) {
				 Sms s = new Sms();
			     s.sendSms(numero,"code de Validation :"+otpConf.getValcode());
			 }else {
				 String from = "no-reply@gainde2000.sn";
                 try {
					email.sendMail(from,numero,"code de validation est \n"+otpConf.getValcode(),"validation code OTP");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 
		     response.setStatut(true);
		     response.setDescription("code envoyé par "+parametre.getTypeOtp()+"!!");
		     response.setData(otpConf);
		 }else {
			 response.setStatut(false);
			 response.setDescription("Code non envoyé !!");
			 response.setData(null);
		 }
		 return response;
	 }
	
	//envoyercode otp selon le type de durée (minute ou heure)
	 
	 @ApiOperation(value = "Cette méthode est utilisée pour générer un code OTP à partir du type de durée(minute ou heure) de validation \n "
	 		+ "avec l'ajout d'un numéro tel ou email")
	 @GetMapping("/envoyercode/{dureee}/{numeroemail}")
	 public ServeurResponse envoyerCodeOtpByEvenementAndNumero(@PathVariable(name="dureee")String dureee,@PathVariable(name="numeroemail")String numero) {
		 ServeurResponse response =new ServeurResponse();
		 List<ParametreOtp> otps=iParametreOtp.listParametreOtpByDureee(dureee);
		 	 
		 if(!otps.isEmpty()) {
			 ParametreOtp parametre = otps.get(0);
			 OTPConfiguration otpConf=new OTPConfiguration();
			 otpConf.setParametreOtp(parametre);
			 if(parametre.getTypeOtp().equals("SMS")) {
				 char[] code= iParametreOtp.generateCodeOtp("SMS");
				 otpConf.setValcode(String.valueOf(code));
				 
			 }else {
				 char[] code= iParametreOtp.generateCodeOtp("Email");
				 otpConf.setValcode(String.valueOf(code));
			 }
			 otpConf.setValDuree(new Double(parametre.getDuree()).longValue());
			 otpConf.setDejavalide(false);
			 repoConf.save(otpConf);
			 //envoyer le sms
			 
			 if(parametre.getTypeOtp().equals("SMS")) {
				 Sms s = new Sms();
			     s.sendSms(numero,"code de Validation :"+otpConf.getValcode());
			 }else {
				 String from = "no-reply@gainde2000.sn";
                 try {
					email.sendMail(from,numero,"code de validation est \n"+otpConf.getValcode(),"validation code OTP");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 
		     response.setStatut(true);
		     response.setDescription("code envoyé par "+parametre.getTypeOtp());
		     response.setData(otpConf);
		 }else {
			 response.setStatut(false);
			 response.setDescription("Code non envoyé  erreur!!");
			 response.setData(null);
		 }
		 return response;
	 }
	 
	
}
