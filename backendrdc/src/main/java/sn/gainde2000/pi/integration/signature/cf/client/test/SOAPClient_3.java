package sn.gainde2000.pi.integration.signature.cf.client.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.ws.security.SecurityConstants;

import sn.gainde2000.pi.integration.signature.cf.client.callback.GaindeClientCallback;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.IRegistSignPort;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.RASign;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.SignatureResult;

public class SOAPClient_3 {
	//private final String serviceURL = "http://rasign.gainde2000.sn:8080/ETAFI/RA_Sign/services";
	private final String serviceURL = "http://rasign.gainde2000.sn:8080/RA_TIERS1/RA_Sign/services";
	private IRegistSignPort port;

	public SOAPClient_3() {
		try {

			URL wsdlLocation = new URL(serviceURL + "?wsdl");
			RASign service = new RASign(wsdlLocation);
			port = (IRegistSignPort) service.getPort(IRegistSignPort.class);
			((BindingProvider) port).getRequestContext().put(SecurityConstants.CALLBACK_HANDLER,
					new GaindeClientCallback());
			((BindingProvider) port).getRequestContext().put(SecurityConstants.SIGNATURE_PROPERTIES,
					wss4jInProperties());
			((BindingProvider) port).getRequestContext().put(SecurityConstants.ENCRYPT_PROPERTIES, wss4jInProperties());
			((BindingProvider) port).getRequestContext().put(SecurityConstants.SIGNATURE_USERNAME, "clientkey");
			((BindingProvider) port).getRequestContext().put(SecurityConstants.ENCRYPT_USERNAME, "serverkey");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Properties wss4jInProperties() {
		Properties properties = new Properties();
		properties.put("org.apache.wss4j.crypto.merlin.provider", "org.apache.wss4j.common.crypto.Merlin");
		properties.put("org.apache.wss4j.crypto.merlin.keystore.type", "jks");
		properties.put("org.apache.wss4j.crypto.merlin.keystore.password", "VERITAS");
		properties.put("org.apache.wss4j.crypto.merlin.keystore.file", "certs/test/client_new2.keystore");
		return properties;
	}

	public SignatureResult callMethd_Signer(long userID, byte[] document) throws RemoteException {
		byte[] byteArrayDocument = null;
		try {
			byteArrayDocument = document;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return port.signatureBeneficiaire(userID, byteArrayDocument);
	}
	
	public SignatureResult callMethd_Signer_fra(long IDSignataire, String Cle_Signataire, String Code_PIN, byte[] Contenu_A_Signer, long IDApplication) throws RemoteException {
		byte[] byteArrayDocument = null;
		try {
			byteArrayDocument = Contenu_A_Signer;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return port.signatureLTV(IDSignataire, Cle_Signataire, Code_PIN, Contenu_A_Signer, IDApplication);
	}

	public String callMethd_Enregistrer() throws RemoteException {

		return port.enregistrerBeneficiaire("Maimounata DERA", "104160", "CNI2755198502292", 2384759);
	}

	public String callMethd_EnregistrerEyone() throws RemoteException {
		byte[] byteArrayScan = null;
		try {
			byteArrayScan = Files.readAllBytes(Paths.get("C:\\Users\\mmbaye\\Documents\\Moi\\CI_NEW_3_MM.pdf"));
			System.out.println("byteArrayDocument.length: " + byteArrayScan.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return port.enroleeyone("DEPOT ETAFI DGID", "122019", "15042020", byteArrayScan, 2384759);
	}

	public void writeBytesToFile(String fileOutput, byte[] bytes) throws IOException {

		try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
			fos.write(bytes);
		}

	}
}
