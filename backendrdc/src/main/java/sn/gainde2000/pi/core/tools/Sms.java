package sn.gainde2000.pi.core.tools;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
import java.net.URLEncoder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.config.AppProperties;


/**
 *
 * @author Ibou NDIAYE
 */
@Service
public class Sms {    
    @Autowired
    private AppProperties app;
    
    public static final String URL_BEGIN = "http://192.168.1.164/sms/traitersms.php?username=kannel&password=kannel&to=";
    public static final String URL_CENTER = "&text=";
    public static final String URL_END = "&sms_platform=4&signature=GAINDE2000";
   

    public boolean sendSms(String telephoneDestinataire, String sms) {        
        boolean isSmsSend = false;
        String url = "";
        //app = new AppProperties();
        //System.out.println("------------------------&sms_platform=4&signature----------------------------"+app.getUrlsms());
       // String URL_BEGIN = app.getUrlsms();
  
        //String URL_END = "&sms_platform=4&signature="+app.getSignaturesms();
        String telephoneDestinataireFormate = "";
        String telephoneDestinataireWithoutSpace = telephoneDestinataire.trim().replaceAll(" ", "");
        //System.out.println("telephoneDestinataire = [" + telephoneDestinataire + "]");
        //System.out.println("telephoneDestinataireWithoutSpace = [" + telephoneDestinataireWithoutSpace + "]");
        try{
            if (telephoneDestinataireWithoutSpace.length() <= 9) {
                //System.out.println("Numero bon pour envoi.");
                telephoneDestinataireFormate = "221" + telephoneDestinataireWithoutSpace;
                //System.out.println("telephoneDestinataireFormate = [" + telephoneDestinataireFormate + "]");
            } else {
                //Traitement numéro à faire
                 telephoneDestinataireFormate = telephoneDestinataireWithoutSpace;
            }
            if (sms.length() < 160) {
                    url = URL_BEGIN + telephoneDestinataireFormate + URL_CENTER + URLEncoder.encode(sms, java.nio.charset.StandardCharsets.UTF_8.toString()) + URL_END;
                    System.out.println("url = [" + url + "]");
                    /*
                    Client client = Client.create();
                    WebResource webResource = client.resource(url);
                    ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
                    */
                    Client client = ClientBuilder.newClient();
                    WebTarget target = client.target(url);
                    //String result = target.pathParam("param", "value").get(String.class);
                     Response response = target.request("application/json").get(Response.class);
                    if (response.getStatus() == 200) {                        
                        //String output = response.getEntity(String.class);                        
                        String output = response.getEntity().toString();                        
                        //System.out.println("Output response builder toString = " + output);
                        if (output.equalsIgnoreCase("1")) {
                            isSmsSend = true;
                        } else {
                            //Réponse différent de 1 donc erreur
                        }
                    } else {
                        //Error authentication invoke
                        System.out.println("Response status = " + response.getStatus());
                    }
                    
            } else {
                    //Très long à spliter
                    System.out.println("Très long à spliter " );
            }
                
                isSmsSend = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSmsSend;
    }
    
    //Constructor
    /*public Sms() {        
    }*/
   
    
    
}
