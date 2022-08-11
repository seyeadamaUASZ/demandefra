/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.tools;

/**
 *
 * @author indiaye
 */
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sn.gainde2000.pi.config.AppProperties;

@Component
public class Email {

    @Autowired
    AppProperties app;

    public static final String MAIL_HOST = "172.16.2.11";
    private static final String FROM = "no-reply@gainde2000.sn";
    private static final String PASSWORD = "";
    public void sendMail(String from, String to, String message, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "172.16.2.11");
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        if(from!=null){
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);    
        } 
    }
    
    public boolean sendMailContact(String from, String to, String message, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "172.16.2.11");
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        if (from != null) {
            try {
                msg.setFrom(new InternetAddress(from));
                msg.setRecipient(RecipientType.TO, new InternetAddress(to));
                msg.setSubject(subject);
                msg.setText(message);
                Transport.send(msg);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("----------------------Erreur envoi de message----------------------- " + message);
            return false;
        }
    }

    public boolean sendMessage2(String subject, String text, String destinataire) {
        boolean isSend = false;
        try {
            sendMail(FROM, destinataire, text, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }

    public void sendMessage(String subject, String text, String destinataire) {
        try {
            sendMail(FROM, destinataire, text, subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendHtmlMail(String password, String from, String to, String message, String subject) throws MessagingException {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "172.16.2.11");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setText(message, "utf-8", "html");        
        Transport.send(msg);
    }

    public void broadcastHtmlMail(String password, String from, List<String> to, String message, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", MAIL_HOST);

        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        for (int i = 0; i < to.size(); i++) {
            msg.addRecipients(RecipientType.TO, InternetAddress.parse(to.get(i)));
        }
        BodyPart messageBodyPart = new MimeBodyPart();
        msg.setSubject(subject);
        msg.setText(message, "utf-8", "html");
        Transport.send(msg);
    }

    public boolean sendHtmlMessage(String subject, String text, String destinataire, String link) {
        boolean isSend = false;
       
        String message = "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script><script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>"
        		+ "<div style=\"width:600px;height: 609px;border-radius: 5px;border: solid 1px #0f569d;background-color: #f6f6f6;\">\n"
                + "<div style=\"width: 100%;height: 68px;border-bottom: : solid 1px #979797;background-color: #0f569d; position: relative;top:0;;background: #0f569d;\">\n"
                + "	<div style=\"align:center;padding:5px;display:block;font-family: TrebuchetMS;font-size: 21px;font-weight: normal;font-style: normal;font-stretch: normal;line-height: normal;letter-spacing: normal;text-align: center;color: #ffffff;\">Overflight / landing and stopover authorization request platform</div>\n"
                + "	\n"
                + "	</div>\n"
                + "	<div style=\"margin:25px;width: 91%;height: 80%;border: solid 0.5px #979797;background-color: #ffffff;\">\n"
                + "		<div style=\"display: block; text-align: center; margin: 5px; font-family: TrebuchetMS;font-size: 21px;font-weight: bold;font-style: normal;font-stretch: normal;line-height: normal;letter-spacing: normal;color: #0f569d;\">\n"
                + subject + "\n"
                + "		</div>\n"
                + "		<div style=\"margin: 10px;margin-top:20px;  font-family: TrebuchetMS;font-size: 21px;font-weight: normal;font-style: normal;font-stretch: normal;line-height: 1.43;letter-spacing: normal;color: rgba(0, 0, 0, 0.85);\">\n"
                + text
                + "		</div><br/><br/><br/>\n\n\n\n"
                + "		<center><a href=\""+link+"\" style=\"text-decoration:none;display:block;width: 90%;height: 20px;border: solid 1px #979797;background-color: #0f569d; color:white;margin-top: 50px;cursor: pointer;padding: 20px;\">Login</a></center>\n"
                + "	</div>\n"
                + "</div>";
        try {
            sendHtmlMail(PASSWORD, FROM, destinataire, message, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }
    
    @Async
    public boolean sendHtmlMessageFRA(String subject, String prenom, String nom, String numero, String destinataire) {
        boolean isSend = false;
       
        String message = "<html>\r\n" + 
        		"	<head>\r\n" + 
        		"		<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<style type=\"text/css\">\r\n" + 
        		"			.mnav{\r\n" + 
        		"				background-color: #165E2E; height: 103px; border-radius: 10px;\r\n" + 
        		"			}\r\n" + 
        		"			img{\r\n" + 
        		"				width: 251px; height: 61px; margin-top: 10px; margin-left: 330px;\r\n" + 
        		"			}\r\n" + 
        		"			#intro-example{\r\n" + 
        		"				background-image: url('https://41.214.74.215:8081/demandefra/assets/mail/image.png'); border-radius: 10px; margin-left: auto; margin-right: auto; margin-top: 10px; height: 350px;\r\n" + 
        		"			}\r\n" + 
        		"			.body{\r\n" + 
        		"				text-align: center; font-size: 22px; margin-top: 15px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter{\r\n" + 
        		"				background-color: #f1f1f1; display: flex; justify-content: center; border-radius: 10px; height: 100px; margin-top: 20px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter h4{\r\n" + 
        		"				margin-top: 27px; text-align: center;\r\n" + 
        		"			}\r\n" + 
        		"		</style>\r\n" + 
        		"	</head>\r\n" + 
        		"	<body>\r\n" + 
        		"		<div class=\"container\"> \r\n" + 
        		"	 		<div class=\"row justify-content-md-center\">\r\n" + 
        		"	 			<div class=\"col col-lg-10\">\r\n" + 
        		"	 				<nav class=\"mnav\" >\r\n" + 
        		"					    <div class=\"collapse navbar-collapse\" id=\"navbarExample01\">\r\n" + 
        		"					        <img src=\"https://41.214.74.215:8081/demandefra/assets/mail/fra.png\" alt=\"illustration\" >\r\n" + 
        		"					    </div>\r\n" + 
        		"				  	</nav>\r\n" + 
        		"				</div>  \r\n" + 
        		"				<div class=\"col col-lg-8\">	\r\n" + 
        		"				  <div\r\n" + 
        		"				          id=\"intro-example\"\r\n" + 
        		"				          class=\"p-5 text-center bg-image\"\r\n" + 
        		"				          >\r\n" + 
        		"				    </div>			  \r\n" + 
        		"				    <div class=\"body\">\r\n" + 
        		"				    	<h3>Bonjour "
        		+  prenom+nom+",</h3>\r\n" + 
        		"				    	<p>Nous accusons réception de votre demande<br> d 'autorisation FRA.</p>\r\n" + 
        		"				    	<p style=\"margin-top: 3px;\">Pour pouvoir payer les frais d'analyse des produits,<br> veuillez suivre votre dossier en utilisant le numéro<br> suivant:</p>\r\n" + 
        		"				    	<div class=\"mfooter\">\r\n" + 
        		"				    		<h4>"
        		+ numero+"</h4>\r\n" + 
        		"				    	</div>\r\n" + 
        		"				    </div>\r\n" + 
        		"	 			</div>\r\n" + 
        		"	 		</div>	  \r\n" + 
        		"	</div>\r\n" + 
        		"	</body>	 \r\n" + 
        		"</html>";
        try {
            sendHtmlMail(PASSWORD, FROM, destinataire, message, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }
    
    @Async
    public boolean sendHtmlMessageInscription(String subject, String prenom, String nom, String login, String password, String destinataire) {
        boolean isSend = false;
       
        String message = "<html>\r\n" + 
        		"	<head>\r\n" + 
        		"		<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<style type=\"text/css\">\r\n" + 
        		"			.mnav{\r\n" + 
        		"				background-color: #165E2E; height: 103px; border-radius: 10px;\r\n" + 
        		"			}\r\n" + 
        		"			img{\r\n" + 
        		"				width: 251px; height: 61px; margin-top: 10px; margin-left: 330px;\r\n" + 
        		"			}\r\n" + 
        		"			#intro-example{\r\n" + 
        		"				background-image: url('https://41.214.74.215:8081/demandefra/assets/mail/image.png'); border-radius: 10px; margin-left: auto; margin-right: auto; margin-top: 10px; height: 350px;\r\n" + 
        		"			}\r\n" + 
        		"			.body{\r\n" + 
        		"				text-align: center; font-size: 22px; margin-top: 15px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter{\r\n" + 
        		"				background-color: #165E2E; display: flex; justify-content: center; border-radius: 10px; height: 100px; margin-top: 20px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter h4{\r\n" + 
        		"				margin-top: 27px; color: #ffff; text-align: center;\r\n" + 
        		"			}\r\n" + 
        		"		</style>\r\n" + 
        		"	</head>\r\n" + 
        		"	<body>	\r\n" + 
        		"		 <div class=\"container\"> \r\n" + 
        		"		 		<div class=\"row justify-content-md-center\">\r\n" + 
        		"		 			<div class=\"col col-lg-10\">\r\n" + 
        		"		 				<nav class=\"mnav\">\r\n" + 
        		"						    <div class=\"collapse navbar-collapse\" id=\"navbarExample01\">\r\n" + 
        		"						        <img src=\"https://41.214.74.215:8081/demandefra/assets/mail/fra.png\" alt=\"illustration\">\r\n" + 
        		"						    </div>\r\n" + 
        		"					  	</nav>\r\n" + 
        		"					</div>  \r\n" + 
        		"					<div class=\"col col-lg-8\">	\r\n" + 
        		"					  <div\r\n" + 
        		"					          id=\"intro-example\"\r\n" + 
        		"					          class=\"p-5 text-center bg-image\">\r\n" + 
        		"					    </div>			  \r\n" + 
        		"					    <div class=\"body\">\r\n" + 
        		"					    	<h3>Bonjour "
        		+  prenom+nom+",</h3>\r\n" +
        		"					    	<p>Votre compte utilisateur a été créé.</p>\r\n" + 
        		"					    	<p style=\"margin-top: 3px;\">Vos paramétres de connexion sont:<br> Nom d'utilisateur:"
        		+ login+"<br>Mot de passe:"
        		+ password+"</p>\r\n" + 
        		"					    	<div class=\"mfooter\">\r\n" + 
        		"					    		<h4>\r\n" + 
        		"					    			<a target=\"_blank\" href=\"http://192.168.2.153:8081/demandefra/login\">Cliquez ici pour vous connecter à l'application.</a>\r\n" + 
        		"					    		</h4>\r\n" + 
        		"					    	</div>\r\n" + 
        		"					    </div>\r\n" + 
        		"		 			</div>\r\n" + 
        		"		 		</div>	  \r\n" + 
        		"		</div>\r\n" + 
        		"	</body>\r\n" + 
        		"</html>";
        try {
            sendHtmlMail(PASSWORD, FROM, destinataire, message, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }
    
    @Async
    public boolean sendHtmlMessageDownloadFRA(String subject, String prenom, String nom, String numero, String destinataire) {
        boolean isSend = false;
       
        String message = "<html>\r\n" + 
        		"	<head>\r\n" + 
        		"		<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n" + 
        		"		<style type=\"text/css\">\r\n" + 
        		"			.mnav{\r\n" + 
        		"				background-color: #165E2E; height: 103px; border-radius: 10px;\r\n" + 
        		"			}\r\n" + 
        		"			img{\r\n" + 
        		"				width: 251px; height: 61px; margin-top: 10px; margin-left: 330px;\r\n" + 
        		"			}\r\n" + 
        		"			#intro-example{\r\n" + 
        		"				background-image: url('https://41.214.74.215:8081/demandefra/assets/mail/image.png'); border-radius: 10px; margin-left: auto; margin-right: auto; margin-top: 10px; height: 350px;\r\n" + 
        		"			}\r\n" + 
        		"			.body{\r\n" + 
        		"				text-align: center; font-size: 22px; margin-top: 15px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter{\r\n" + 
        		"				background-color: #165E2E; display: flex; justify-content: center; border-radius: 10px; height: 100px; margin-top: 20px;\r\n" + 
        		"			}\r\n" + 
        		"			.mfooter h4{\r\n" + 
        		"				margin-top: 27px; color: #ffff; text-align: center;\r\n" + 
        		"			}\r\n" + 
        		"		</style>\r\n" + 
        		"	</head>\r\n" + 
        		"	<body>	\r\n" + 
        		"		 <div class=\"container\"> \r\n" + 
        		"		 		<div class=\"row justify-content-md-center\">\r\n" + 
        		"		 			<div class=\"col col-lg-10\">\r\n" + 
        		"		 				<nav class=\"mnav\">\r\n" + 
        		"						    <div class=\"collapse navbar-collapse\" id=\"navbarExample01\">\r\n" + 
        		"						        <img src=\"https://41.214.74.215:8081/demandefra/assets/mail/fra.png\" alt=\"illustration\">\r\n" + 
        		"						    </div>\r\n" + 
        		"					  	</nav>\r\n" + 
        		"					</div>  \r\n" + 
        		"					<div class=\"col col-lg-8\">	\r\n" + 
        		"					  <div\r\n" + 
        		"					          id=\"intro-example\"\r\n" + 
        		"					          class=\"p-5 text-center bg-image\">\r\n" + 
        		"					    </div>			  \r\n" + 
        		"					    <div class=\"body\">\r\n" + 
        		"					    	<h3>Bonjour "
        		+  prenom+nom+",</h3>\r\n" +
        		"					    	<p>Aprés examination de votre dossier <strong>"
        		+ numero+"</strong>,<br> nous vous délivrons l'autorisation FRA provisoire.</p>\r\n" + 
        		"					    	<p style=\"margin-top: 3px;\">Veuillez cliquer sur le lien pour suivre et télécharger<br> le document.</	p>\r\n" + 
        		"					    	<div class=\"mfooter\">\r\n" + 
        		"					    		<h4>\r\n" + 
        		"					    			<a target=\"_blank\" href=\"http://192.168.2.153:8081/demandefra/\">Suivre et télécharger FRA</a>\r\n" + 
        		"					    		</h4>\r\n" + 
        		"					    	</div>\r\n" + 
        		"					    </div>\r\n" + 
        		"		 			</div>\r\n" + 
        		"		 		</div>	  \r\n" + 
        		"		</div>\r\n" + 
        		"	</body>\r\n" + 
        		"</html>";
        try {
            sendHtmlMail(PASSWORD, FROM, destinataire, message, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }

    public boolean broadcastHtmlMessage(String subject, String text, List<String> destinataire, String link) {
        boolean isSend = false;

        String message = "<div style=\"width:600px;height: 609px;border-radius: 5px;border: solid 1px #0f569d;background-color: #f6f6f6;\">\n"
                + "<div style=\"width: 100%;height: 68px;border-bottom: : solid 1px #979797;background-color: #0f569d; position: relative;top:0;;background: #0f569d;\">\n"
                + "	<div style=\"align:center;padding:5px;display:block;font-family: TrebuchetMS;font-size: 21px;font-weight: normal;font-style: normal;font-stretch: normal;line-height: normal;letter-spacing: normal;text-align: center;color: #ffffff;\">Overflight / landing and stopover authorization request platform</div>\n"
                + "	\n"
                + "	</div>\n"
                + "	<div style=\"margin:25px;width: 91%;height: 80%;border: solid 0.5px #979797;background-color: #ffffff;\">\n"
                + "		<div style=\"display: block; text-align: center; margin: 5px; font-family: TrebuchetMS;font-size: 21px;font-weight: bold;font-style: normal;font-stretch: normal;line-height: normal;letter-spacing: normal;color: #0f569d;\">\n"
                + subject + "\n"
                + "		</div>\n"
                + "		<div style=\"margin: 10px;margin-top:20px;  font-family: TrebuchetMS;font-size: 21px;font-weight: normal;font-style: normal;font-stretch: normal;line-height: 1.43;letter-spacing: normal;color: rgba(0, 0, 0, 0.85);\">\n"
                + text
                + "		</div>\n\n\n\n"
                + "		<center><a href=\""+link+"\" style=\"text-decoration:none;display:block;width: 90%;height: 20px;border: solid 1px #979797;background-color: #0f569d; color:white;margin-top: 50px;cursor: pointer;padding: 20px;\">Login</a></center>\n"
                + "	</div>\n"
                + "</div>";
        try {
            broadcastHtmlMail(PASSWORD, FROM, destinataire, message, subject);
            isSend = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSend;
    }
    
}
