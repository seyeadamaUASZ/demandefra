package sn.gainde2000.pi.config;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author indiaye
 */
@ConfigurationProperties("app")
@Validated
public class AppProperties {                    
    private String jbpmserver;
    private String urlsms;
    private String signaturesms;
    public String hostmail;
    private String pwdmail;
    private String frommail;
    private String urlapp;
    private String hostserver;
    private String jbpmlogin;
    private String jbpmpass;
    private String jbpmdirfile;
    private String jbpmdirform;
    private String urlOpay;
    private String folderqrcode;
    private String folderqrcodelogo;
    private String urlcertificat;
    private String empriente;


    public String getJbpmserver() {
        return jbpmserver;
    }

    public void setJbpmserver(String jbpmserver) {
        this.jbpmserver = jbpmserver;
    }

    public String getUrlsms() {
        return urlsms;
    }

    public void setUrlsms(String urlsms) {
        this.urlsms = urlsms;
    }

    public String getSignaturesms() {
        return signaturesms;
    }

    public void setSignaturesms(String signaturesms) {
        this.signaturesms = signaturesms;
    }        

    public  String getHostmail() {
        return hostmail;
    }

    public void setHostmail(String hostmail) {
        this.hostmail = hostmail;
    }   

    public String getPwdmail() {
        return pwdmail;
    }

    public void setPwdmail(String pwdmail) {
        this.pwdmail = pwdmail;
    }

    public String getFrommail() {
        return frommail;
    }

    public void setFrommail(String frommail) {
        this.frommail = frommail;
    }

	public String getUrlapp() {
		return urlapp;
	}

	public void setUrlapp(String urlapp) {
		this.urlapp = urlapp;
	}

    public String getJbpmLogin() {
        return jbpmlogin;
    }

    public void setJbpmLogin(String jbpmlogin) {
        this.jbpmlogin = jbpmlogin;
    }

    public String getJbpmPass() {
        return jbpmpass;
    }

    public void setJbpmPass(String jbpmpass) {
        this.jbpmpass = jbpmpass;
    }

    public String getJbpmDirFile() {
        return jbpmdirfile;
    }
    public void setJbpmDirFile(String jbpmdirfile) {
        this.jbpmdirfile = jbpmdirfile;
    }
    
    public String getJbpmDirForm() {
        return jbpmdirform;
    }
    public void setJbpmDirForm(String jbpmdirform) {
        this.jbpmdirform = jbpmdirform;
    }


    /**
     * @return the hostserver
     */
    public String getHostserver() {
        return hostserver;
    }

    /**
     * @param hostserver the hostserver to set
     */
    public void setHostserver(String hostserver) {
        this.hostserver = hostserver;
    }

    /**
     * @return the urlOpay
     */
    public String getUrlOpay() {
        return urlOpay;
    }

    /**
     * @param urlOpay the urlOpay to set
     */
    public void setUrlOpay(String urlOpay) {
        this.urlOpay = urlOpay;
    }

    public String getFolderqrcode() {
        return folderqrcode;
    }

    public void setFolderqrcode(String folderqrcode) {
        this.folderqrcode = folderqrcode;
    }

    public String getFolderqrcodelogo() {
        return folderqrcodelogo;
    }

    public void setFolderqrcodelogo(String folderqrcodelogo) {
        this.folderqrcodelogo = folderqrcodelogo;
    }

    
      public String getEmpriente() {
        return empriente;
    }

    public void setEmpriente(String empriente) {
        this.empriente = empriente;
    }

    /**
     * @return the urlcertificat
     */
    public String getUrlcertificat() {
        return urlcertificat;
    }

    /**
     * @param urlcertificat the urlcertificat to set
     */
    public void setUrlcertificat(String urlcertificat) {
        this.urlcertificat = urlcertificat;
    }
     
       
}
