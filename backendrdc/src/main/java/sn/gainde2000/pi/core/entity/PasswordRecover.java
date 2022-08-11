package sn.gainde2000.pi.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yougadieng
 */
@Entity
@Table(name = "td_password_recover",  schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PasswordRecover.findAll", query = "SELECT p FROM PasswordRecover p")})
public class PasswordRecover implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pwr_id")
    private Long pwrId;

    @Size(max = 255)
    @Column(name = "pwr_reset_token")
    private String pwrResetToken;

    @Column(name = "pwr_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwrDate;
    private Date pwdDateExpiration;

    @Size(max = 255)
    @Column(name = "pwr_email")
    private String pwrEmail;

    public PasswordRecover() {
    }

    public PasswordRecover(Long pwrId) {
        this.pwrId = pwrId;
    }

    public Long getPwrId() {
        return pwrId;
    }

    public void setPwrId(Long proId) {
        this.pwrId = pwrId;
    }

    public String getPwrResetToken() {
        return pwrResetToken;
    }

    public void setPwrResetToken(String pwrResetToken) {
        this.pwrResetToken = pwrResetToken;
    }

    public Date getPwrDate() {
        return pwrDate;
    }

    public void setPwrDateCreation(Date pwrDate) {
        this.pwrDate = pwrDate;
    }

    public String getPwrEmail() {
        return pwrEmail;
    }

    public void setPwrEmail(String pwrEmail) {

        this.pwrEmail = pwrEmail;
    }


    public void setPwrDateCreation(String format) {

    }

    /**
     * @return the pwdDateExpiration
     */
    public Date getPwdDateExpiration() {
        return pwdDateExpiration;
    }

    /**
     * @param pwdDateExpiration the pwdDateExpiration to set
     */
    public void setPwdDateExpiration(Date pwdDateExpiration) {
        this.pwdDateExpiration = pwdDateExpiration;
    }
}

