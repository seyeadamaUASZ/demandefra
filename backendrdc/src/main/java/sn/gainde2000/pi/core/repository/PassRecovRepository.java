package sn.gainde2000.pi.core.repository;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.pi.core.entity.PasswordRecover;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.pi.core.entity.Privilege;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Repository()
public interface PassRecovRepository extends JpaRepository<PasswordRecover, Integer> {
    PasswordRecover findPassBypwrEmail(String pwrEmail);
    PasswordRecover findPassBypwrResetToken(String pwrResetToken);

    //@Query("SELECT p.pwrResetToken FROM PasswordRecover p where p.pwrResetToken = :veriftoken AND p.pwrDate > NOW() - INTERVAL 120 MINUTE")
    //@Query("SELECT p.pwrResetToken FROM PasswordRecover p where p.pwrResetToken = :veriftoken AND p.pwrDate >:verifdate")
    @Query("SELECT p.pwrResetToken FROM PasswordRecover p where p.pwrResetToken = :veriftoken AND p.pwdDateExpiration > :verifdate")
    String verifTkn(@Param("veriftoken") String veriftoken, @Param("verifdate") Date verifdate);

    @Query("SELECT p.pwrEmail FROM PasswordRecover p where p.pwrResetToken = :veriftoken")
    String recupEmail(@Param("veriftoken") String veriftoken);
}
