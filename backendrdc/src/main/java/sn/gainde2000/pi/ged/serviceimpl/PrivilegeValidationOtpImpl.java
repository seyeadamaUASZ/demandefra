package sn.gainde2000.pi.ged.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.entity.PrivilegeValidationOtp;
import sn.gainde2000.pi.ged.repository.PrivilegeValidationOtpRepository;
import sn.gainde2000.pi.ged.service.IPrivilegeValidationOtp;

@Service
public class PrivilegeValidationOtpImpl implements IPrivilegeValidationOtp {

	@Autowired PrivilegeValidationOtpRepository privilegeValidationOtpRepository;
	

	@Override
	public PrivilegeValidationOtp update(PrivilegeValidationOtp privilegeValidationOtp) {
		return privilegeValidationOtpRepository.save(privilegeValidationOtp);
	}

	@Override
	public void delete(PrivilegeValidationOtp privilegeValidationOtp) {
		privilegeValidationOtpRepository.delete(privilegeValidationOtp);
	}

	@Override
	public PrivilegeValidationOtp add(PrivilegeValidationOtp privilegeValidationOtp) {
		return privilegeValidationOtpRepository.save(privilegeValidationOtp);
	}

	@Override
	public PrivilegeValidationOtp findByPriviligeSignerAndPrisValCode(PrivilegeSigner privilegeSigner, String code) {
		return privilegeValidationOtpRepository.findByPrivilegeSignerAndPrisValCode(privilegeSigner.getPrisId(), code);
	}

}
