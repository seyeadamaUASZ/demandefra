package sn.gainde2000.pi.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.DemandeAutFra;
import sn.gainde2000.pi.core.entity.Signature;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.service.ISignatureService;
import sn.gainde2000.pi.ged.entity.Document;

@RestController
public class SignatureController {
	@Autowired
	ISignatureService signatureService;

	@PostMapping("signature/create")
    public ServeurResponse createSignature(@RequestBody Signature signature) {
		ServeurResponse response = new ServeurResponse();
		Signature signature2 = signatureService.save(signature);
		if(signature2 !=null) {
			response.setData(signature2);
			response.setDescription("signature created !!");
			response.setStatut(true);
		}else {
			response.setData(null);
			response.setDescription("signature not created !!");
			response.setStatut(false);
		}
		return response;
	}
	
	@PostMapping("signature/verification")
    public ServeurResponse verificationCleSignature(HttpServletRequest request) {
    	ServeurResponse response = new ServeurResponse();
        String codePin = request.getParameter("codePin");
        Long owner = Long.parseLong(request.getParameter("owner"));
            
    	Signature signature = signatureService.verification(owner, codePin);
    	if (signature !=null) {
    		response.setData(signature);
    		response.setStatut(true);
    		response.setDescription("signature !!!");
    	}else {
    		response.setData(null);
    		response.setDescription("Code pin non conforme !!");
    		response.setStatut(false);
    	}
    	return response;
    }
}
