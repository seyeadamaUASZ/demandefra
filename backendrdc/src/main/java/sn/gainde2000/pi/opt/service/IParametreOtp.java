package sn.gainde2000.pi.opt.service;

import java.util.List;
import java.util.Optional;
import sn.gainde2000.pi.opt.entity.ParametreOtp;

public interface IParametreOtp {
	public ParametreOtp addParametreOtp(ParametreOtp opt);
	public List<ParametreOtp> listParametreOpt();
	public  Optional<ParametreOtp> getParametreOpt(Long id);
	public void supprimerParametreOpt(Long id);
	public void modifierParametreOtp(Long id);
	public char[] generateCodeOtp(String type);
	public ParametreOtp findParametreOtpByLibelle(String libelle);
	public ParametreOtp findParametreByDureee(String dureee);
	public ParametreOtp findParametreByEvenement(String evenement);
	
	public List<ParametreOtp> listParametreOtpByType(String type);
	
	public List<ParametreOtp> listParametreOtpByDureee(String dureee);
	
	public void deleteParametreByLibelle(String libelle);
	

}
