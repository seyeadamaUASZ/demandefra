package sn.gainde2000.pi.formgenerator.controller;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.formgenerator.entity.Step;
import sn.gainde2000.pi.formgenerator.entity.ValueChampsV2;
import sn.gainde2000.pi.formgenerator.generator.FrontGenerator;
import sn.gainde2000.pi.formgenerator.service.IChampsV2Service;
import sn.gainde2000.pi.formgenerator.service.IFormulaireV2Service;
import sn.gainde2000.pi.formgenerator.service.IStepService;
import sn.gainde2000.pi.formgenerator.service.IValueChampsV2Service;

@RestController
@CrossOrigin
@RequestMapping("formulairev2")
public class FormulaireV2Controller {

	@Autowired IFormulaireV2Service iFormulaireV2Service;
	@Autowired IStepService iStepService;
	@Autowired IChampsV2Service iChampsV2Service;
	@Autowired IValueChampsV2Service iValueChampsV2Service;
	
	@PostMapping(value = "create")
	public ServeurResponse create(@RequestBody FormulaireV2 formulaireV2) {
		ServeurResponse response = new ServeurResponse();
		
		
		formulaireV2.setFrmValider("Valider");

		FormulaireV2 f = iFormulaireV2Service.save(formulaireV2);
		
		
		try {
		if(f != null) {
			for (Step step : formulaireV2.getSteps()) {
				step.setFormulaire(formulaireV2);
				Step step2 = iStepService.save(step);
				if(step2 !=null) {
					if(step.getChamps()!=null) {
					for (ChampsV2 champsV2 : step.getChamps()) {
						champsV2.setStep(step);
						ChampsV2 champsV2cp = iChampsV2Service.save(champsV2);

						if(champsV2cp !=null) {
							if(champsV2.getValues()!=null) {
							for (ValueChampsV2 valueChampsV2 : champsV2.getValues()) {

								valueChampsV2.setValueChp(champsV2);
								valueChampsV2 = iValueChampsV2Service.save(valueChampsV2);
							}
							}
						}
					}
					}
				}
			}
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		response.setStatut(true);
		formulaireV2 = iFormulaireV2Service.findByFrmId(formulaireV2.getFrmId());
		response.setData(formulaireV2);
		return response;
	}
	
	@PostMapping("delete/form")
	public ServeurResponse deleteForm(@RequestBody FormulaireV2 formulaireV2) {
		ServeurResponse response = new ServeurResponse();
		
		iFormulaireV2Service.delete(formulaireV2);
		response.setStatut(true);
		return response;
	}
	
	@PostMapping("delete/step")
	public ServeurResponse deleteStep(@RequestBody Step step) {
		ServeurResponse response = new ServeurResponse();
		
		iStepService.delete(step);
		response.setStatut(true);
		return response;
	}
	
	@PostMapping("delete/champs")
	public ServeurResponse deleteChamps(@RequestBody ChampsV2 champsV2) {
		ServeurResponse response = new ServeurResponse();
		
		iChampsV2Service.delete(champsV2);
		response.setStatut(true);
		return response;
	}
	
	@PostMapping("delete/champsvalue")
	public ServeurResponse deleteChampsValue(@RequestBody ValueChampsV2 v2) {
		ServeurResponse response = new ServeurResponse();
		
		iValueChampsV2Service.delete(v2);
		response.setStatut(true);
		return response;
	}
	
	@GetMapping(value="get/{id}")
	public ServeurResponse get(@PathVariable Long id) {
		FormulaireV2 f = iFormulaireV2Service.findByFrmId(id);
		ServeurResponse response = new ServeurResponse();
		response.setData(f);
		response.setStatut(true);
		return response;
	}
	
	@GetMapping("liste")
	public ServeurResponse getAll() {
		ServeurResponse response = new ServeurResponse();
		Iterable<FormulaireV2> f = iFormulaireV2Service.findAll();
		response.setData(f);
		response.setStatut(true);
		response.setDescription("list");
		return response;
	}
	
	@GetMapping("generate/{id}")
	public ServeurResponse generateForm(@PathVariable Long id) {
		ServeurResponse response = new ServeurResponse();

		FormulaireV2 f = iFormulaireV2Service.findByFrmId(id);
		
//		FrontGenerator.generateFormulaire(f);
//		FrontGenerator.generateWorkflowModule(f);
		
		response.setStatut(true);
	
		
		return response;
	}
	
	@GetMapping("formulaireByApp")
    public ServeurResponse getFormulaireByApp() {
         ServeurResponse response = new ServeurResponse();
         List<FormulaireV2> f = iFormulaireV2Service.getAllFormulaireLibreValider();
         response.setData(f);
         response.setStatut(true);
         return response;
    }
	
	   @GetMapping("formulaireByAppOuLibre/{id}")
	     public ServeurResponse getFormulaireLibreOuSpecifique(@PathVariable Long id){
	          ServeurResponse response = new ServeurResponse();
	          List<FormulaireV2> f = iFormulaireV2Service.listFormByAppIdOulibre(id);
	          response.setData(f);
	          response.setStatut(true);
	          return response;
	     } 
	   
	 
	   @RequestMapping(value = "formulaireLierApp", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	     public ServeurResponse LierFormulaireEtApp(HttpServletRequest request) {
	          ServeurResponse response = new ServeurResponse();
	          String idApp = (String) request.getParameter("idApp");
	          String idFormulaire = (String) request.getParameter("idFormulaire");
	          iFormulaireV2Service.lierFormulaire(idApp, idFormulaire);
	          response.setStatut(true);
	          response.setDescription("liaison effectué avec succès");

	          return response;
	     }

	     @GetMapping("formulaireLierApp/enlever/{idFormulaire}")
	     public ServeurResponse EnleverLiaisonFormulaireEtApp(@PathVariable String idFormulaire) {
	          ServeurResponse response = new ServeurResponse();
	          System.out.println("formulaire id:" + idFormulaire);
	          iFormulaireV2Service.enleverLiasonFormulaire(idFormulaire);
	          response.setStatut(true);
	          response.setDescription("liaison enlevé avec succès");

	          return response;
	     }
}
