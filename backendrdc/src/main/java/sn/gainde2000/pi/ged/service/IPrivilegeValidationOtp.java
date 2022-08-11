package sn.gainde2000.pi.ged.service;

import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.entity.PrivilegeValidationOtp;

public interface IPrivilegeValidationOtp {
	PrivilegeValidationOtp findByPriviligeSignerAndPrisValCode(PrivilegeSigner privilegeSigner,String code);
	PrivilegeValidationOtp update(PrivilegeValidationOtp privilegeValidationOtp);
	void delete(PrivilegeValidationOtp privilegeValidationOtp);
	PrivilegeValidationOtp add(PrivilegeValidationOtp privilegeValidationOtp);
}
