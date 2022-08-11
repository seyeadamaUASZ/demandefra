package sn.gainde2000.pi.formgenerator.generator;

import java.util.ArrayList;
import java.util.List;

import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.formgenerator.entity.Step;

public class Generator {
	
	
	public static final boolean genererWorkflow(FormulaireV2 formV2, List<FormulaireV2> forms) {
		boolean process = FrontGenerator.generateWorkflowModule(formV2, forms);
		BackGenerator backgen = new BackGenerator();
		List<ChampsV2> listChamps = new ArrayList<>();
		List<ChampsV2> listChampsfile = new ArrayList<>();
		if(formV2.getSteps().size()>0) {
			for(Step step: formV2.getSteps()) {
				if(step.getChamps().size()>0) {
					for(ChampsV2 champs: step.getChamps()) {
						if(champs.getChpType().equalsIgnoreCase("file")) {
							listChampsfile.add(champs);
						}else {
							listChamps.add(champs);
						}
					}
				}
			}
		}
		//forms principal
		process = backgen.generateAllBack(formV2.getFrmNom(), listChamps, listChampsfile, true,true,forms);
		
		if(forms.size()>0) {
			for(FormulaireV2 f : forms) {
				listChamps = new ArrayList<>();
				listChampsfile = new ArrayList<>();
				if(f.getSteps().size()>0) {
					for(Step step: f.getSteps()) {
						if(step.getChamps().size()>0) {
							for(ChampsV2 champs: step.getChamps()) {
								if(champs.getChpType().equalsIgnoreCase("file")) {
									listChampsfile.add(champs);
								}else {
									listChamps.add(champs);
								}
							}
						}
					}
				}
				//forms principal
				process =backgen.generateAllBack(f.getFrmNom(), listChamps, listChampsfile, true,false,null);
			}
		}
		return process;
	}
	
	public static boolean genererFormulaireGestion(FormulaireV2 formV2) {
		boolean process = FrontGenerator.generateFormulaire(formV2);
		BackGenerator backgen = new BackGenerator();
		List<ChampsV2> listChamps = new ArrayList<>();
		List<ChampsV2> listChampsfile = new ArrayList<>();
		if(formV2.getSteps().size()>0) {
			for(Step step: formV2.getSteps()) {
				if(step.getChamps().size()>0) {
					for(ChampsV2 champs: step.getChamps()) {
						if(champs.getChpType().equalsIgnoreCase("file")) {
							listChampsfile.add(champs);
						}else {
							listChamps.add(champs);
						}
					}
				}
			}
		}
		//forms principal
		process = backgen.generateAllBack(formV2.getFrmNom(), listChamps, listChampsfile, false,false,null);
		return process;
	}
}
