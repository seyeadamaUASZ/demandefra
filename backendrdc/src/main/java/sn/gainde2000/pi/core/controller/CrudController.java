package sn.gainde2000.pi.core.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.ApplicationStatus;
import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.ChampsRapport;

import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.entity.Langue;
import sn.gainde2000.pi.core.entity.Module;
import sn.gainde2000.pi.core.entity.Parametre;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.entity.RegleGestion;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.TaskStatus;
import sn.gainde2000.pi.core.entity.Theme;
import sn.gainde2000.pi.core.entity.TimerTask;
import sn.gainde2000.pi.core.entity.Transition;
import sn.gainde2000.pi.core.entity.ValueChamps;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.entity.WorkflowFormConfig;
import sn.gainde2000.pi.core.entity.WorkflowSecteur;
import sn.gainde2000.pi.core.repository.ChampsRapportRipository;
import sn.gainde2000.pi.core.repository.ChampsRepository;
import sn.gainde2000.pi.core.repository.FormulaireRepository;
import sn.gainde2000.pi.core.service.Crud;
import sn.gainde2000.pi.core.service.IApplicationService;
import sn.gainde2000.pi.core.service.IFormulaireService;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;
import sn.gainde2000.pi.core.service.IRapportService;
import sn.gainde2000.pi.core.service.IRegleGestionService;
import sn.gainde2000.pi.core.service.ITaskService;
import sn.gainde2000.pi.core.service.ITaskStatusService;
import sn.gainde2000.pi.core.service.IThemeService;
import sn.gainde2000.pi.core.service.ITimerTaskService;
import sn.gainde2000.pi.core.service.ITransition;
import sn.gainde2000.pi.core.service.IValue;
import sn.gainde2000.pi.core.service.IWidgetService;
import sn.gainde2000.pi.core.service.IWorkflowService;
import sn.gainde2000.pi.core.service.IlangueService;
import sn.gainde2000.pi.core.service.ImoduleService;
import sn.gainde2000.pi.core.service.IparametreService;
import sn.gainde2000.pi.core.service.IworkflowSecteur;
import sn.gainde2000.pi.core.service.impl.ApplicationServiceImpl;
import sn.gainde2000.pi.core.service.impl.ValueServiceImpl;
import sn.gainde2000.pi.core.service.impl.WorkflowFormConfigImpl;
import sn.gainde2000.pi.core.tools.StringProcess;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.formgenerator.generator.Generator;
import sn.gainde2000.pi.formgenerator.service.IFormulaireV2Service;

@RestController
@CrossOrigin("*")
public class CrudController {
	
	@Autowired WorkflowFormConfigImpl workflowFormConfigService;
	@Autowired IFormulaireV2Service iFormulaireV2Service;

    @Autowired
    private ChampsRepository champsRepository;
    @Autowired
    IFormulaireService formulaireRepository;
    @Autowired
    IApplicationService applicationServiceImpl;
    @Autowired
    IThemeService iThemeService;
    @Autowired
    IWorkflowService iworkflow;
    @Autowired
    IFormulaireService iformulaire;
    
    @Autowired
    IValue iValue;
    @Autowired
    @Qualifier("dataSourceJasper")
    DataSource dataSourceJasper;
    @Autowired
    IWidgetService iWidgetService;
    @Autowired
    IlangueService ilangueService;
    @Autowired
    IPwdCriteriaService iPwdCriteriaService;
    @Autowired
    IRapportService iRapportService;
    @Autowired
    IparametreService iparametreService;
    @Autowired
    ImoduleService iModuleService;
    @Autowired
    ITaskService iTaskService;
    @Autowired
    ITaskStatusService iTaskStatusService; 
    @Autowired
    ITransition iTransitionService;
    @Autowired
    private ChampsRapportRipository champsRapportService;
    @Autowired
    private IworkflowSecteur iworkflowSecteur;
    @Autowired
    private IRegleGestionService iRegleGestionService;
    @Autowired
    private ITimerTaskService iTimerTaskService;

    public String repo = "/opt/industrialisation/codesource";
    public String cheminZip = "/opt/industrialisation/depotzip";

    public CrudController() {
        String SE = System.getProperty("os.name").toLowerCase();
        if (SE.indexOf("nux") >= 0) {
            this.repo = "/opt/industrialisation/codesource";
            this.cheminZip = "/opt/industrialisation/depotzip";

        } else {
            this.repo = System.getProperty("user.dir") + "/opt/industrialisation/codesource";
            this.cheminZip = System.getProperty("user.dir") + "/opt/industrialisation/depotzip";
        }

    }

    @GetMapping("download/{idApp}")
    public ResponseEntity<?> download(@PathVariable("idApp") Long idApp) throws IOException {
        Application app = applicationServiceImpl.findByappId(idApp);
        HttpHeaders header = new HttpHeaders();
        Crud crud = new Crud();
        File comp = new File(this.cheminZip);
        String depot = app.getAppNom().replace(' ', '_');
        if (comp.isDirectory()) {
            System.out.println("Debut suppression");
            crud.deleteZip();
            System.out.println("+++++++++++++fin suppression des zip");
        }
        if (crud.zipperProjet(depot.toLowerCase())) {
            System.out.print("projet ziper");
            File file = new File(this.cheminZip + "/" + depot.toLowerCase() + ".zip");
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            System.out.print(file.getName());
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(resource);
        } else {
            return null;
        }
    }

    @GetMapping("zipper/{idApp}")
    public ServeurResponse zipperProjet(@PathVariable("idApp") Long idApp) throws IOException {
        Application app = applicationServiceImpl.findByappId(idApp);
        //app.setAppLien("C:\\Users\\asow\\Desktop\\Projet_industrialisation\\Alysow");
        Crud crud = new Crud();
        ServeurResponse response = new ServeurResponse();
        if (crud.zipperProjet(app.getAppNom())) {
            response.setStatut(true);
            response.setDescription("Le projet a ete bien Zipper");
        } else {
            response.setStatut(false);
            response.setDescription("Probleme sur la partie zipper");
        }
        return response;

    }

    //configuration base de donnees by id application
    public void showTables() throws Exception {

        DatabaseMetaData metaData = dataSourceJasper.getConnection().getMetaData();

        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

        while (tables.next()) {

            String tableName = tables.getString("TABLE_NAME");
            ResultSet columns = metaData.getColumns(null, null, "td_application", "%");

            while (columns.next()) {

                String columnName = columns.getString("COLUMN_NAME");

                System.out.println("\t" + columnName);

            }

        }

    }

    @GetMapping("gettable")
    public ServeurResponse getTable() throws SQLException {
        ArrayList<String> listeTable = new ArrayList<>();
        DatabaseMetaData metaData = this.dataSourceJasper.getConnection().getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        while (tables.next()) {

            String tableName = tables.getString("TABLE_NAME");
            listeTable.add(tableName);
        }
        
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setData(listeTable);
        response.setDescription("Liste des tables de la base de donnée");

        return response;
    }

    @GetMapping("getcolonne/{table}")
    public ServeurResponse getColonne(@PathVariable("table") String table) throws SQLException {
        ArrayList<String> listeColonne = new ArrayList<>();
        DatabaseMetaData metaData = this.dataSourceJasper.getConnection().getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

        ResultSet columns = metaData.getColumns(null, null, table, "%");

        while (columns.next()) {

            String columnName = columns.getString("COLUMN_NAME");

            listeColonne.add(columnName);

        }

        ServeurResponse response = new ServeurResponse();

        response.setStatut(true);
        response.setData(listeColonne);
        response.setDescription("Liste des colonne de la table " + table);

        return response;
    }

    @GetMapping("front/{nomProjet}")
    public ServeurResponse generedCrud(@PathVariable("nomProjet") String nomprojet) {
        Crud crud = new Crud();
        ServeurResponse response = new ServeurResponse();
        if (crud.createProjet(nomprojet)) {
            response.setStatut(true);
            response.setDescription("Le projet a été bien crée avec succès");

        } else {

            response.setStatut(false);
            response.setDescription("Probleme sur la creation du projet");
        }
        return response;
    }
    //configuration base de donnees by id application

    @GetMapping("configapp/{id}")
    public ServeurResponse congigappli(@PathVariable("id") Long id) {
        Crud crud = new Crud();
        Application app = applicationServiceImpl.findByappId(id);
        List<Theme> listeTheme = iThemeService.getListTheme();
        List<Workflow> listWorkflow = iworkflow.findBywkfAppId(id);
        List<FormulaireV2> listFormulaire = iFormulaireV2Service.findByFrmAppId(id);
        List<Widget> listeWidget = iWidgetService.getListWidget();
        List<Langue> listeLangue = ilangueService.getListLang();
        List<PwdCriteria> listePwdCritaire = iPwdCriteriaService.getListPwd();
        List<Rapport> listeRapport = iRapportService.getRapportByAppId(id);
        List<Parametre> listeParametre = iparametreService.getListParam();
        List<WorkflowSecteur> listWorkflowSecteur = iworkflowSecteur.getListWorkflowSecteur();
        List<ChampsRapport> listChampsRapport = champsRapportService.findAll();
        List<Module> listModule=iModuleService.findBymodAppId(id);
        List<Task> listTask = iTaskService.findByWorkflowTaskByAppId(id);
        List<TaskStatus> listTaskStatus=iTaskStatusService.getAllTaskStatus();
       List<Transition> listTransition=iTransitionService.findByWorkflowTransByAppId(id);
       List<RegleGestion> listRegleGestion=iRegleGestionService.findByRegleGestionWrkId(id);
       List<TimerTask> listTimerTask=iTimerTaskService.findByTimerTaskWrkId(id);
       
        ServeurResponse response = new ServeurResponse();
        try {
           
            crud.initfichierstatique();
            crud.initialiserApp(app, listeTheme, listWorkflow, listFormulaire, listeWidget, listeLangue,
            listePwdCritaire, listeRapport, listeParametre, listWorkflowSecteur, listChampsRapport,listModule,listTask,listTaskStatus,listTransition,listRegleGestion,listTimerTask);
            response.setStatut(true);
            response.setDescription("fichier de configuration crée"+listTask);

        } catch (IOException e) {

            e.printStackTrace();
            response.setStatut(false);
            response.setDescription("fichier de configuaration non cree");

        }

        return response;
    }
    //suppression code sources formulaire by id formulaire 

    @GetMapping("supprimer/{nomProjet}/{idFormulaire}")
    public ServeurResponse supprimerComposant(@PathVariable("nomProjet") String nomprojet, @PathVariable("idFormulaire") Long idFormulaire) throws IOException {
        Crud crud = new Crud();
        List<Champs> listChamps = this.champsRepository.listFieldByFrmId(idFormulaire);
        Formulaire form = formulaireRepository.findByfrmId(idFormulaire);
        for (int i = 0; i < listChamps.size(); i++) {
            List<ValueChamps> valeurs = iValue.listByChampId(listChamps.get(i).getChpId());
            for (int j = 0; j < valeurs.size(); j++) {
                iValue.delete(valeurs.get(j));
            }
            this.champsRepository.delete(listChamps.get(i));

        }
        String composant = form.getFrmNom();
        ServeurResponse response = new ServeurResponse();
        formulaireRepository.delete(form);
        File f = new File(this.repo + "/" + nomprojet);
        File comp = new File(this.repo + "/" + nomprojet + "/src/app/" + composant);
        File model = new File(this.repo + "/" + nomprojet + "/src/app/model/" + composant);
        File service = new File(this.repo + "/" + nomprojet + "/src/app/service/" + composant);
        if (comp.isDirectory() || model.isDirectory() || service.isDirectory()) {
            crud.deleteComposant(nomprojet, composant);
            crud.deleteModel(composant, nomprojet);
            crud.deleteService(composant, nomprojet);
            response.setStatut(true);
            response.setDescription("Le composant a ete bien supprimer" + composant);
        } else {
            response.setStatut(false);
            response.setDescription("Les composant n'est pas encore generer");
        }
        return response;
    }

    //endpoit pour la creation d'un component et modification
    @GetMapping("composant/{nomProjet}/{idApplication}")
    public ServeurResponse generedComposant(@PathVariable("nomProjet") String nomprojet, @PathVariable("idApplication") Long idApplication) throws IOException {
        Crud crud = new Crud();

        ServeurResponse response = new ServeurResponse();
//        List<Formulaire> listFormulaire = iformulaire.findByFrmAppId(idApplication);
        List<FormulaireV2> listFormulaire = iFormulaireV2Service.findByFrmAppId(idApplication);
        Application app = applicationServiceImpl.findByappId(idApplication);
        List<Theme> listeTheme = iThemeService.getListTheme();
        List<Workflow> listWorkflow = iworkflow.findBywkfAppId(idApplication);
        List<Widget> listeWidget = iWidgetService.getListWidget();
        List<Langue> listeLangue = ilangueService.getListLang();
        List<PwdCriteria> listePwdCritaire = iPwdCriteriaService.getListPwd();
        List<Rapport> listeRapport = iRapportService.getRapportByAppId(idApplication);
        List<WorkflowSecteur> listWorkflowSecteur = iworkflowSecteur.getListWorkflowSecteur();
        List<ChampsRapport> listChampsRapport = champsRapportService.findAll();
        List<Parametre> listeParametre = iparametreService.getListParam();
        List<Module> listModule=iModuleService.findBymodAppId(idApplication);
        List<Task> listTask = iTaskService.findByWorkflowTaskByAppId(idApplication);
        List<TaskStatus> listTaskStatus=iTaskStatusService.getAllTaskStatus();
        List<Transition> listTransition=iTransitionService.findByWorkflowTransByAppId(idApplication);
        List<RegleGestion> listRegleGestion=iRegleGestionService.findByRegleGestionWrkId(idApplication);
        List<TimerTask> listTimerTask=iTimerTaskService.findByTimerTaskWrkId(idApplication);
        crud.initfichierstatique();
        crud.initialiserApp(app, listeTheme, listWorkflow, listFormulaire, listeWidget, listeLangue, listePwdCritaire,
        listeRapport, listeParametre, listWorkflowSecteur, listChampsRapport,listModule,listTask,listTaskStatus,listTransition,listRegleGestion,listTimerTask);
        
        /////////// start gen v2
        boolean process = true;
        for(Workflow w: listWorkflow) {
            Iterable<WorkflowFormConfig> listeFormulairePrimaireWc = workflowFormConfigService.getListWfcPrimaryFormConfigByWorkflow(w.getWkfId());
            Iterable<WorkflowFormConfig> listeFormulaireSecondairesWc = workflowFormConfigService.getListWfcWorkflowFormConfigByWorkflow(w.getWkfId());
            FormulaireV2 formPrimaire = iFormulaireV2Service.findByFrmId(listeFormulairePrimaireWc.iterator().next().getWfcIdFormulaire());
            List<FormulaireV2> listeFormulaireSecondaire = new ArrayList<>();
        	System.out.print(formPrimaire.getFrmNom());

            for(WorkflowFormConfig wfc: listeFormulaireSecondairesWc) {
            	if(!wfc.getWfcPrimaire()) {
            		FormulaireV2 f2 = iFormulaireV2Service.findByFrmId(wfc.getWfcIdFormulaire());
            		listeFormulaireSecondaire.add(f2);
            	}
            }
            
            process = Generator.genererWorkflow(formPrimaire, listeFormulaireSecondaire);
            
        }
        if(listFormulaire.size()>0) {
        	for(FormulaireV2 f: listFormulaire) {
        		process = Generator.genererFormulaireGestion(f);
        	}
        }
        if(process) {
        	 response.setStatut(true);
           response.setDescription("Le composant a ete bien cree" );
           //set new app status to generated
           app.setAppStatus(ApplicationStatus.GENERATION);
           applicationServiceImpl.save(app);
        }else {
        	 response.setStatut(false);
        }
        
       
        //////////// end gen v2
//        for (int w = 0; w < listFormulaire.size(); w++) {
//            List<Champs> listChamps = this.champsRepository.listFieldByFrmId(listFormulaire.get(w).getFrmId());
//            List<Champs> listChampsfile = this.champsRepository.listByFrmIdFile(listFormulaire.get(w).getFrmId());
//            Formulaire form = formulaireRepository.findByfrmId(listFormulaire.get(w).getFrmId());
//            String composant = form.getFrmNom();
//
//            File f = new File(this.repo + "/" + nomprojet);
//            File comp = new File(this.repo + "/" + nomprojet + "/src/app/" + composant.toLowerCase());
//            StringProcess uppercasefunct = new StringProcess();
//            if (f.isDirectory()) {
//
//                if (comp.isDirectory()) {
//                    System.out.println("Debut suppression");
//                    crud.deleteComposant(nomprojet, composant.toLowerCase());
//                    System.out.println("+++++++++++++fin suppression");
//                }
                
                
//                if (crud.createComposant(nomprojet, composant)
//                        && crud.createComposantadd(nomprojet, uppercasefunct.capitalize(composant))
//                        && crud.createComposantview(nomprojet, uppercasefunct.capitalize(composant))
//                        && crud.createComposantEdit(nomprojet, uppercasefunct.capitalize(composant))
//                        && crud.createComposantList(nomprojet, uppercasefunct.capitalize(composant))
//                        && crud.createService(composant, nomprojet)
//                        && crud.createModel(composant, nomprojet)
//                        && crud.createRouting(nomprojet, composant)) {
//
//                    try {
//                        form.setFrmStatus(true);
//                        formulaireRepository.save(form);
//                        crud.generationFileModelBack(composant, listChamps);
//                        crud.generationFileControllerBack(composant, listChamps, listChampsfile);
//                        crud.generationFileServiceBack(composant);
//                        crud.generationFileRepositoryBack(composant);
//
//                        crud.updateComponentAddHtml(nomprojet, composant, listChamps, iValue, listChampsfile);
//                        crud.updateComponent(composant, nomprojet, listChamps, listChampsfile);
//                        crud.updateService(composant, nomprojet, listChamps);
//                        crud.updateComponentCss(composant, nomprojet);
//                        crud.updateComponentList(composant, nomprojet, listChamps);
//                        crud.updateComponentListCss(composant, nomprojet);
//                        crud.updateComponentListHtml(composant, nomprojet, listChamps);
//                        crud.updateComponentEditHtml(composant, nomprojet, listChamps, iValue, listChampsfile);
//                        crud.updateComponentEditTs(composant, nomprojet, listChamps, listChampsfile);
//                        crud.updateComponentEditCss(composant, nomprojet);
//                        crud.updateModel(composant, nomprojet, listChamps);
//                        crud.updateRoutingForm(nomprojet, composant);
//                        crud.updateComposantFormTs(nomprojet, composant);
//                        crud.updateRoutingApp(nomprojet, composant, listFormulaire);
//                        crud.updateComponentViewCss(composant, nomprojet);
//                        crud.updateComponentViewHtml(composant, nomprojet, listChamps);
//                        crud.updateComponentViewTs(composant, nomprojet, listChamps);
//
//                        System.out.println("Successfully wrote to the file.");
//                    } catch (IOException e) {
//                        System.out.println("An error occurred.");
//                        e.printStackTrace();
//                    }
//                    response.setStatut(true);
//                    response.setDescription("Le composant a ete bien cree" + composant);
//                    //set new app status to generated
//                    app.setAppStatus(ApplicationStatus.GENERATION);
//                    applicationServiceImpl.save(app);
//
//                } else {
//
//                    response.setStatut(false);
//                    response.setDescription("Le composant n'est pas ete cree");
//                }

//            } else {
//
//                response.setStatut(false);
//                response.setDescription("Probleme sur la creation du projet");
//            }
//
//        }
        return response;
    }

}
