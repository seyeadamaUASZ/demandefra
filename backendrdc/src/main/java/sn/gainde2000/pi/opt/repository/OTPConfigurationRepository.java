package sn.gainde2000.pi.opt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.pi.opt.entity.OTPConfiguration;

public interface OTPConfigurationRepository extends JpaRepository<OTPConfiguration, Long>{
    
	@Query("select o from OTPConfiguration o where o.Valcode=:code")
	public OTPConfiguration findOTPConfigurationByValcode(@Param("code")String code);
}
