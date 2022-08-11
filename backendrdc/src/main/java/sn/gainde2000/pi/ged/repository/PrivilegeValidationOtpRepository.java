package sn.gainde2000.pi.ged.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.entity.PrivilegeValidationOtp;

public interface PrivilegeValidationOtpRepository extends JpaRepository<PrivilegeValidationOtp, Long> {
	PrivilegeValidationOtp findByPrivilegeSigner(PrivilegeSigner privilegeSigner);
	
	@Query(value="select p from PrivilegeValidationOtp p where p.privilegeSigner.prisId=:prisId and p.prisValCode=:code")
	PrivilegeValidationOtp findByPrivilegeSignerAndPrisValCode(@Param("prisId") Long prisId, @Param("code") String code);
}
