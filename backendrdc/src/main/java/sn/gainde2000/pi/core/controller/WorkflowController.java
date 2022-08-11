package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.pi.core.entity.JbpmFormInfos;
import sn.gainde2000.pi.core.entity.Task; 
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.*;
import sn.gainde2000.pi.core.service.IJbpmUserService;
import sn.gainde2000.pi.core.service.IWorkflowService;
import sn.gainde2000.pi.core.service.IworkflowSecteur;
import sn.gainde2000.pi.core.service.JbpmApiService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.core.service.impl.JbpmFormInfosImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException; 
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import sn.gainde2000.pi.core.service.impl.TaskServiceImpl; 
import sn.gainde2000.pi.core.service.ITaskService; 
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.IRegleGestionService;
import sn.gainde2000.pi.core.service.ITaskStatusService;
import sn.gainde2000.pi.core.service.ITimerTaskService;
import sn.gainde2000.pi.core.service.impl.WorkflowFormConfigImpl;

@RestController

public class WorkflowController {
    @Autowired
     ITaskService taskService;
    @Autowired
    ITimerTaskService timerTaskService;
    @Autowired
    IRegleGestionService RegleGestionService;
    @Autowired
     ITaskStatusService taskStatusService;
     @Autowired
     IWorkflowService workflowService;
     @Autowired
     JbpmFormInfosImpl jbpmFormInfosService; 
     @Autowired
     WorkflowFormConfigImpl workflowFormConfigService; 
     @Autowired
     WorkflowFormConfigImpl workflowFormConfigImpl;
     @Autowired
     TaskServiceImpl taskservice;    
     @Autowired
     IworkflowSecteur workflowSecteurService;
     @Autowired
     JbpmApiService jbpmApiService;
     @Autowired
     IJbpmUserService jbpmUserService;
     @Autowired
    IProfile profileService;
    @Autowired
    private AppProperties app;
    
    
    @GetMapping("workflowtaskprofil/{containerId}")
     public ServeurResponse getWorkflowTaskProfil(@PathVariable String containerId) {
          ServeurResponse response = new ServeurResponse();
           Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosBycontainer(containerId);
          response.setData(jbpmFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     
    @GetMapping("listentitities/{containerId}/{processId}")
     public ServeurResponse getEntitiesWorkflow(@PathVariable String containerId) {
          ServeurResponse response = new ServeurResponse();
           Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosBycontainer(containerId);
          response.setData(jbpmFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
      @GetMapping("formulaireworkflow/{containerId}")
     public ServeurResponse getFormulaireWorkflow(@PathVariable String containerId) {
          ServeurResponse response = new ServeurResponse();
           Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosBycontainer(containerId);
          response.setData(jbpmFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     
      @GetMapping("formulaireworkflow2/{idwrkf}")
     public ServeurResponse getFormulaireWorkflow2(@PathVariable Long idwrkf) {
          ServeurResponse response = new ServeurResponse(); 
           Iterable<WorkflowFormConfig> wrkfFormInfos =  workflowFormConfigService.getListWfcWorkflowFormConfigByWorkflow(idwrkf); 
          response.setData(wrkfFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
       @GetMapping("primaryformulaireworkflow2/{idwrkf}")
     public ServeurResponse getPrimaryFormulaireWorkflow2(@PathVariable Long idwrkf) {
          ServeurResponse response = new ServeurResponse(); 
           Iterable<WorkflowFormConfig> wrkfFormInfos =  workflowFormConfigService.getListWfcPrimaryFormConfigByWorkflow(idwrkf); 
          response.setData(wrkfFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
      @GetMapping("formulaireworkflowparid/{jfrmId}")
     public ServeurResponse getFormulaireWorkflowbyId(@PathVariable Long jfrmId) {
          ServeurResponse response = new ServeurResponse();
           Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosById(jfrmId);
          response.setData(jbpmFormInfos);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     @GetMapping("downloadfrmfile/{frmfileid}")
   public ResponseEntity<Resource> downloadFrmFile(HttpServletResponse response,@PathVariable String frmfileid) throws IOException {
            String frmfilename = frmfileid.replace(app.getJbpmDirForm(),"");
            File file2 = new File(app.getJbpmDirForm()+frmfileid);
            
            String extension = ""; 
        int i = frmfilename.lastIndexOf('.');
            if (i > 0) {
              extension = frmfilename.substring(i+1);
            }
             HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+frmfilename);
             headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            Path path = Paths.get(frmfileid);
          InputStreamResource resource = new InputStreamResource(new FileInputStream(app.getJbpmDirForm()+frmfileid));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file2.length())
                    .contentType(MediaType.parseMediaType("application/"+extension+""))
                    .body(resource);
    }
   
      @GetMapping("deletewrkflform/{jfrmId}")
     public ServeurResponse deleteWrkflForm(@PathVariable Long jfrmId) {
          ServeurResponse response = new ServeurResponse();
             jbpmFormInfosService.delete(jfrmId);
          response.setStatut(true);
        return response;
     }
     
     @RequestMapping(value = "chargerjbpmform", method = RequestMethod.POST)
    @ResponseBody
    public Object chargerJbpmForm(@RequestParam("workflowform") String workflowform,@RequestParam("containerId") String containerId, @RequestParam("idwrkf") Long idwrkf, @RequestPart("fichierform") MultipartFile fichierform ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        
       Random rand = new Random();  
      int upperbound = 99999; 
      int int_random = rand.nextInt(upperbound);  
                 Files.copy(fichierform.getInputStream(), Paths.get(app.getJbpmDirForm()+ int_random+"_"+fichierform.getOriginalFilename()));
 
            JSONObject obj = new JSONObject(workflowform);
            
            String urlform = app.getJbpmDirForm()+""+int_random+"_"+fichierform.getOriginalFilename();
            String nomform = "";
             nomform = (String) obj.get("nomform").toString();
             boolean positionform ;
             positionform = (boolean) obj.get("positionform");
          JbpmFormInfos FormInfos = new JbpmFormInfos();
          FormInfos.setJfrmFormulaire(nomform);
          FormInfos.setJfrmWorkflow(containerId);
          FormInfos.setJfrmUrlfile(urlform);
          FormInfos.setJfrmJfrmPrimaire(positionform);
          FormInfos.setStatusFrmWorkflow(StatusFrmWorkflow.NOGENERER);

          FormInfos.setJfrmIdworkflow(idwrkf);

          response.setData(jbpmFormInfosService.save(FormInfos));
          response.setStatut(true);
          response.setDescription("Nom du formulaire");
         
        return FormInfos;
    }
    
     @RequestMapping(value = "chargerjbpmformv2", method = RequestMethod.POST)
    @ResponseBody
    public Object chargerJbpmFormV2(@RequestParam("workflowform") String workflowform,@RequestParam("idwrkf") Long idwrkf) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        JSONObject obj = new JSONObject(workflowform);
         
             Long formId = 1L;
        String cformId = "";
        cformId = (String) obj.get("idform").toString(); 
        Integer formIdcast=Integer.parseInt(cformId);  
        formId = Long.valueOf(formIdcast);
        Workflow workflowname;
        workflowname = new Workflow(idwrkf);
             boolean positionform ;
             positionform = (boolean) obj.get("positionform");
          WorkflowFormConfig workflowFormConfig = new WorkflowFormConfig();
          workflowFormConfig.setWfcIdFormulaire(formId);
          workflowFormConfig.setWorkflowname(workflowname);
          workflowFormConfig.setWfcPrimaire(positionform); 
          workflowFormConfig.setStatusWcfWorkflow(StatusWcfWorkflow.GENERER);
           
          response.setData(workflowFormConfigImpl.save(workflowFormConfig));
          response.setStatut(true);
          response.setDescription("Nom du formulaire");
         
        return workflowFormConfig;
    }
     
    /**
     * recuperation des profils concernant toutes les taches
     * @return 
     */
    @GetMapping("listetaskprofil")
     public ServeurResponse getListeTaskProfil() {
          ServeurResponse response = new ServeurResponse(); 
           Iterable<Profil> Profil = profileService.getAllProfile();
          response.setData(Profil);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     /**
      * liste des taches par workflow id
      * @param idwrkf
      * @return 
      */
     @GetMapping("listetaskparid/{tskWkfId}")
     public ServeurResponse getListeTaskbyId(@PathVariable Long tskWkfId) {
          ServeurResponse response = new ServeurResponse();
           Iterable<Task> task =  taskservice.getListTaskByTskWkfId(tskWkfId); 
          response.setData(task);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     /**
      * focntions de creation de taches dans un workflow données
      * @param workflowform
      * @param idwrkf
      * @return
      * @throws ParserConfigurationException
      * @throws TransformerException
      * @throws SAXException
      * @throws XPathExpressionException
      * @throws IOException
      * @throws URISyntaxException
      * @throws JSONException 
      */
     @RequestMapping(value = "createtask", method = RequestMethod.POST)
    @ResponseBody
    public Object createTask(@RequestParam("taskgenform") String workflowform,@RequestParam("idwrkf") Long  idwrkf ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        JSONObject obj = new JSONObject(workflowform);
        String taskname = "";
        taskname = (String) obj.get("taskname").toString();
        String tasknamesuivant = "";
        tasknamesuivant = (String) obj.get("tasknamesuivant").toString(); 
        String taskdescription = "";
        taskdescription = (String) obj.get("taskdescription").toString();
        Long statusId = 1L;
        String pstatusId = "";
        pstatusId = (String) obj.get("statusId").toString(); 
        Integer statusIdcast=Integer.parseInt(pstatusId);  
        statusId = Long.valueOf(statusIdcast);
        Workflow worklowid;
        Profil owner;
        Long ownername = 1L;
        String poowner = "";
        poowner = (String) obj.get("ownername").toString(); 
        Integer ownernamecast=Integer.parseInt(poowner);  
        ownername = Long.valueOf(ownernamecast);
        owner = new Profil(ownername);
        worklowid = new Workflow(idwrkf);
        boolean positionform ;
        positionform = (boolean) obj.get("positionform");
        boolean endtask ;
        endtask = (boolean) obj.get("endtask");
           Task task  = new Task(); 
           task.setPoOwner(owner);
           task.setTskIsFirst(positionform);
           task.setTskName(taskname); 
           task.setTskFormNameSuiv(tasknamesuivant);
           task.setTskFormName(taskname);
           task.setTskDescription(taskdescription);  
           task.setTskStatusId(statusId); 
           task.setTskWkfId(worklowid);
           task.setTskIsLast(endtask);
           taskservice.save(task);
           response.setData(task);
           response.setStatut(true);
           response.setDescription("Nom du formulaire");
         
        return task;
    }
    
    /**
     * fonction de mise à jour des informations du formulaires
     * @param workflowform
     * @param containerId
     * @param idwrkf
     * @param fichierform
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws IOException
     * @throws URISyntaxException
     * @throws JSONException 
     */
    @RequestMapping(value = "updatejbpmform", method = RequestMethod.POST)
    @ResponseBody
    public ServeurResponse updateJbpmForm(@RequestParam("workflowform") String workflowform,@RequestParam("containerId") String containerId, @RequestParam("idwrkf") Long idwrkf, @RequestPart("fichierform") MultipartFile fichierform ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
     JSONObject obj = new JSONObject(workflowform);
     Long jfrmId = 1L;
            jfrmId = (Long) obj.get("jfrmId");
        JbpmFormInfos jbpmFormInfos = jbpmFormInfosService.findByJfrmId(jfrmId);
        if (jbpmFormInfos == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else { 
               
             String nomform = ""; 
             nomform = (String) obj.get("nomform").toString();
             boolean positionform ;
             positionform = (boolean) obj.get("positionform");
             Random rand = new Random();
             int upperbound = 99999;
             int int_random = rand.nextInt(upperbound); 
             Files.copy(fichierform.getInputStream(), Paths.get(app.getJbpmDirForm()+ int_random+"_"+fichierform.getOriginalFilename()));
  
          String urlform = app.getJbpmDirForm()+""+int_random+"_"+fichierform.getOriginalFilename(); 
          jbpmFormInfos.setJfrmFormulaire(nomform);
          jbpmFormInfos.setJfrmWorkflow(containerId);
          jbpmFormInfos.setJfrmUrlfile(urlform);
          jbpmFormInfos.setJfrmJfrmPrimaire(positionform);
          jbpmFormInfos.setStatusFrmWorkflow(StatusFrmWorkflow.NOGENERER); 
          jbpmFormInfos.setJfrmIdworkflow(idwrkf); 
          response.setData(jbpmFormInfosService.save(jbpmFormInfos));
            response.setStatut(true);
            response.setData(jbpmFormInfos);
            response.setDescription("Données récupérées.");
        }
      
         
        return response;
    }
    
    
    /**
     * fonction de mise jour des informations d'une taches données
     * @param taskgenform
     * @param idtsk
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws IOException
     * @throws URISyntaxException
     * @throws JSONException 
     */
    @RequestMapping(value = "updateworkflowtask", method = RequestMethod.POST)
    @ResponseBody
    public ServeurResponse updateWorkflowTask(@RequestParam("taskgenform") String taskgenform,@RequestParam("idtsk") Long  idtsk ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
     JSONObject obj = new JSONObject(taskgenform); 
          Task  taskupdate =  taskservice.findByTskId(idtsk);
        if (taskupdate != null) {
            String taskname = "";
            taskname = (String) obj.get("taskname").toString();
            String taskdescription = "";
            taskdescription = (String) obj.get("taskdescription").toString(); 
            Profil owner;
            Long ownername = 1L;
            String poowner = "";
            poowner = (String) obj.get("ownername").toString(); 
            Integer ownernamecast=Integer.parseInt(poowner);  
            ownername = Long.valueOf(ownernamecast);
            owner = new Profil(ownername);
            Long statusId = 1L;
            String pstatusId = "";
            pstatusId = (String) obj.get("statusId").toString(); 
            Integer statusIdcast=Integer.parseInt(pstatusId);  
            statusId = Long.valueOf(statusIdcast);
        
            String tasknamesuivant = "";
        tasknamesuivant = (String) obj.get("tasknamesuivant").toString(); 
            boolean positionform ;
            positionform = (boolean) obj.get("positionform");  
            taskupdate.setPoOwner(owner);
            taskupdate.setTskIsFirst(positionform);
            taskupdate.setTskName(taskname);  
            taskupdate.setTskDescription(taskdescription); 
            taskupdate.setTskStatusId(statusId);    
            taskupdate.setTskFormNameSuiv(tasknamesuivant);
               this.taskservice.updateWrkTask(taskupdate);
              response.setData(taskupdate);
              response.setStatut(true);
              response.setDescription("task mise à jour");
            } else { 
                   response.setStatut(false);
                response.setData(null);
                response.setDescription("Aucune élèment trouvé.");
            }

         
        return response;
    }
     
    /**
     * fonction de suppression d'une tache conernant un workflow données
     * @param idtsk
     * @return 
     */
      @GetMapping("deletewrkfltask/{idtsk}")
     public ServeurResponse deleteWrkflTask(@PathVariable Long idtsk) {
          ServeurResponse response = new ServeurResponse();
             taskservice.delete(idtsk); 
          response.setStatut(true);
        return response;
     }
     
     /**
      * fonction de recuperation de  la liste des status
      * @return 
      */
     @GetMapping("listetaskstatus")
     public ServeurResponse getlisteTaskStatus() {
          ServeurResponse response = new ServeurResponse(); 
           Object taskStatus =  taskStatusService.getAllTaskStatus();
          response.setData(taskStatus);
          response.setStatut(true);
          response.setDescription("Nom du formulaire"); 
          return response;
     }
     
     /**
      * fonction de creation de status
      * @param taskstatusform
      * @return
      * @throws ParserConfigurationException
      * @throws TransformerException
      * @throws SAXException
      * @throws XPathExpressionException
      * @throws IOException
      * @throws URISyntaxException
      * @throws JSONException 
      */
     @RequestMapping(value = "createtaskstatus", method = RequestMethod.POST)
    @ResponseBody
    public Object createTaskStatus(@RequestParam("taskstatusform") String taskstatusform) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        JSONObject obj = new JSONObject(taskstatusform);
        String taskstatusname = "";
        taskstatusname = (String) obj.get("taskstatusname").toString();
        TaskStatus taskstatus  = new TaskStatus();  
        taskstatus.setStsName(taskstatusname);
        taskStatusService.save(taskstatus);
        response.setData(taskstatus);
        response.setStatut(true);
        response.setDescription("Nom du formulaire");
        return taskstatus;
    }
     
    
    @RequestMapping(value = "createconfigtimer", method = RequestMethod.POST)
    @ResponseBody
    public Object createConfigTimer(@RequestParam("configtmform") String configtmform,@RequestParam("selectedtypevar") String selectedtypevar) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
            JSONObject obj = new JSONObject(configtmform);
            JSONObject obj1 = new JSONObject(selectedtypevar);
            String titretimer = "";
            titretimer = (String) obj.get("titretimer").toString();
             
            Long tmTskActuel = 1L;
            String tmTskActuelId = "";
             tmTskActuelId = (String) obj.get("tmTskActuel").toString(); 
        Integer tmTskActuelIdcast=Integer.parseInt(tmTskActuelId);  
        tmTskActuel = Long.valueOf(tmTskActuelIdcast);
        
         Long tmWorkflowid = 1L;
            String tmWorkflowidId = "";
             tmWorkflowidId = (String) obj.get("workflowid").toString(); 
        Integer tmtmWorkflowidIdcast=Integer.parseInt(tmWorkflowidId);  
        tmWorkflowid = Long.valueOf(tmtmWorkflowidIdcast);
        
        Long tmTskSuiv = 1L;
            String tmTskSuivId = "";
             tmTskSuivId = (String) obj.get("tmTskSuiv").toString(); 
        Integer tmTskSuivIdcast=Integer.parseInt(tmTskSuivId);  
        tmTskSuiv = Long.valueOf(tmTskSuivIdcast);
         
            String valuetimer = "";
            valuetimer = (String) obj.get("valuetimer").toString();
            String selectedtypetimer = "";
            selectedtypetimer = (String) obj1.get("typetimercontent").toString();
             
          TimerTask timerTask  = new TimerTask();  
           timerTask.setTmTitre(titretimer);
           timerTask.setTmTaskId(tmTskActuel);
           timerTask.setTmTaskSuivId(tmTskSuiv);
           timerTask.setTmTimerData(valuetimer);
           timerTask.setTmTimerType(selectedtypetimer);
           timerTask.setTmWorkflowId(tmWorkflowidId);
           timerTaskService.save(timerTask);
          response.setData(timerTask);
          response.setStatut(true);
          response.setDescription("Nom du formulaire");
         
        return timerTask;
    }
    
    
     @GetMapping("listeconfigtimer")
     public ServeurResponse getAllTimerTaskConfig() {
          Iterable<TimerTask> timerTask = timerTaskService.getAllTimerTask();
          ServeurResponse response = new ServeurResponse();
          if (timerTask == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(timerTask);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     
     @GetMapping("donneetasktimer/{tmId}")
     public ServeurResponse getDonneetaskTimer(@PathVariable Long tmId) {
          List<Map<String, Object>> donneeTimerTask = timerTaskService.fetchdonneetaskandtimer(tmId);
          ServeurResponse response = new ServeurResponse();
          if (donneeTimerTask == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(donneeTimerTask);
               response.setDescription("Données récupérées.");
          }
          return response;
     }
     
     
     @RequestMapping(value = "createreglegestion", method = RequestMethod.POST)
    @ResponseBody
    public Object createRegleGestion(@RequestParam("reglegestionform") String reglegestionform) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
            JSONObject obj = new JSONObject(reglegestionform);
            String titrereglegestion = "";
            titrereglegestion = (String) obj.get("titrereglegestion").toString();
             
            String condition = "";
            condition = (String) obj.get("condition").toString();
             String donneecondition = "";
            donneecondition = (String) obj.get("donneecondition").toString();
            
             Long variablecondition = 1L;
            String variableconditionId = "";
             variableconditionId = (String) obj.get("variablecondition").toString(); 
        Integer variableconditionIdcast=Integer.parseInt(variableconditionId);  
        variablecondition = Long.valueOf(variableconditionIdcast);
        
            Long idwrkfvar = 1L;
            String idwrkfId = "";
             idwrkfId = (String) obj.get("workflowid").toString(); 
        Integer idwrkfIdcast=Integer.parseInt(idwrkfId);  
        idwrkfvar = Long.valueOf(idwrkfIdcast);
        
        Long tskIdvar = 1L;
            String tskIdId = "";
             tskIdId = (String) obj.get("taskid").toString(); 
        Integer tskIdIdcast=Integer.parseInt(tskIdId);  
        tskIdvar = Long.valueOf(tskIdIdcast);
         
           RegleGestion regleGestion = new RegleGestion(); 
           regleGestion.setRgTitre(titrereglegestion);
           regleGestion.setRgVarFomrId(variablecondition);
           regleGestion.setRgDonneeCondition(donneecondition);
           regleGestion.setRgCondition(condition);
           regleGestion.setRgTskId(tskIdvar);
           regleGestion.setRgwrflId(idwrkfvar);
           RegleGestionService.save(regleGestion);
          response.setData(regleGestion);
          response.setStatut(true);
          response.setDescription("Nom du formulaire");
         
        return regleGestion;
    }
    /**
     * fonction de recuperation de d'information (titre) d'un workflow par container Id
     * @param containerId
     * @return 
     */
     @GetMapping("workflowname/{containerId}")
     public ServeurResponse getWorkflowName(@PathVariable String containerId) {
          ServeurResponse response = new ServeurResponse();
          response.setData(workflowService.findByWkfConteneur(containerId));
          response.setStatut(true);
          response.setDescription("Nom du workflow");

          return response;
     }
     
    

    /**
     * Récuperation de la liste des workflows
     * @return liste des workflows
     */
     @GetMapping("workflows")
     public ServeurResponse getAllWorkflow() {
          Iterable<Workflow> workflow = workflowService.getListWorkflow();
          ServeurResponse response = new ServeurResponse();
          if (workflow == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(workflow);
               response.setDescription("Données récupérées.");
          }
          return response;
     }

    /**
     * Recuperation des workflows par secteur
     * @param secteur
     * @return
     */
    @GetMapping("workflowsbysector/{secteur}")
    public ServeurResponse getWorkflowDisplayBySector(@PathVariable String secteur) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        ServeurResponse response = new ServeurResponse();
        
        Iterable<Workflow> workflow = workflowService.getWorkflowBySector(secteur);
         
        if (workflow == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else { 
            System.out.println("++++++++"+workflow);
            response.setStatut(true);
            response.setData(workflow);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Recuperation de la liste des secteur
     * @return
     */
     @GetMapping("workflowlistsecteur")
     public ServeurResponse getAllSecteurList() {
          Iterable<WorkflowSecteur> workflowSecteurs = workflowSecteurService.getListWorkflowSecteur();
          ServeurResponse response = new ServeurResponse();
          if (workflowSecteurs == null) {
               response.setStatut(false);
               response.setData(null);
               response.setDescription("Aucune élèment trouvé.");

          } else {

               response.setStatut(true);
               response.setData(workflowSecteurs);
               response.setDescription("Données récupérées.");
          }
          return response;
     }

    /**
     * Creation de secteur
     * @param workflowSecteur
     * @return
     */
     @PostMapping("workflowsecteur")
     public ServeurResponse createSecteur(@RequestBody WorkflowSecteur workflowSecteur) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
         ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); 
        JSONObject obj = new JSONObject(workflowSecteur);
         ServeurResponse response = new ServeurResponse();
         String creator = "wbadmin";
         String codeSecteur;
        codeSecteur = (String) obj.get("codeSecteur").toString(); 
         workflowSecteurService.save(workflowSecteur);
           //Object spacecreate = jbpmApiService.CreateOneSpace(codeSecteur,creator);
          response.setStatut(true);
          response.setDescription("Enregistrement réussi");
          response.setData("workflowSecteurService");

          return response;
     }

    /**
     * Creation de workflow
     * @param workflow
     * @return
     */
     @PostMapping("workflow")
     public ServeurResponse create(@RequestBody Workflow workflow) {
          ServeurResponse response = new ServeurResponse();
          Object respbody = "";
          if (workflowService.findByName(workflow.getName()) != null) {
               response.setStatut(false);
               response.setDescription("Ce workflow exist deja");
          }else{
               workflow.setWkfEtat("3");
                workflowService.save(workflow);
               /*try {
                   //respbody = this.createWorkflows(new WorkflowItem(workflow.getName(), workflow.getGroupId(), workflow.getVersion(), workflow.getDescription()),workflow.getWkfSecteur());
                    
                   if (respbody !=null && respbody !=""){
                       workflowService.save(workflow);
                   }else{
                       response.setStatut(false);
                       response.setDescription("Echec creation workflow");
                       response.setData(workflow);
                   }

               } catch (ParserConfigurationException e) {
                    e.printStackTrace();
               } catch (TransformerException e) {
                    e.printStackTrace();
               } catch (SAXException e) {
                    e.printStackTrace();
               } catch (XPathExpressionException e) {
                    e.printStackTrace();
               } catch (IOException e) {
                    e.printStackTrace();
               }*/
               response.setStatut(true);
               response.setDescription("Enregistrement réussi");
               response.setData(workflow);
          }
          return response;
     }

    /**
     * Compilation de workflow modeliser
     * @param workflow
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
     @PostMapping("modeliser")
     public ServeurResponse modeliser(@RequestBody Workflow workflow) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException, JSONException {
          ServeurResponse response = new ServeurResponse();
          Workflow findWorkflow = workflowService.findByName(workflow.getName());
          if (findWorkflow != null) {
               findWorkflow.setWkfEtat("2");
               Object containerid = this.getOneContainer(workflow.getName());
               Object processid = this.getProcessinfos((String) containerid);
               findWorkflow.setWkfConteneur((String) containerid);
               findWorkflow.setWkfProcess_id((String) processid);
               this.workflowService.save(findWorkflow);
               response.setStatut(true);
               response.setDescription("Enregistrement réussi!");
               response.setData(findWorkflow);
          } else {
               response.setStatut(false);
               response.setDescription("Echec d'enregistrement!");
          }
          return response;
     }

    /**
     * Recuperation de workflow par Id
     * @param id
     * @return
     */
     @GetMapping("workflow/{id}")
     public Workflow findById(@PathVariable Long id) {
          return workflowService.findByWkfId(id);
     }

    /**
     * Supprimer un workflow
     * @param workflow
     * @return
     */
     @PostMapping("workflow/delete")
     public ServeurResponse deleteWorkflow(@RequestBody Workflow workflow) {
          ServeurResponse response = new ServeurResponse();
          workflowService.delete(workflow);
          response.setStatut(true);
          return response;
     }

    /**
     * Mettre à jour un workflow
     * @param workflow
     * @return
     */
     @PostMapping("update")
     public ServeurResponse update(@RequestBody Workflow workflow) {
          ServeurResponse response = new ServeurResponse();
          Workflow findWorkflow = workflowService.findByWkfId(workflow.getWkfId());
          if (findWorkflow != null) {
               findWorkflow.setName(workflow.getName());
               findWorkflow.setDescription(workflow.getDescription());
               findWorkflow.setGroupId(workflow.getGroupId());
               findWorkflow.setVersion(workflow.getVersion());
              findWorkflow.setWkfCalltoaction(workflow.getWkfCalltoaction());
              findWorkflow.setWkfLabelwdgt(workflow.getWkfLabelwdgt());
              findWorkflow.setWkfSecteur(workflow.getWkfSecteur());
               this.workflowService.save(findWorkflow);
               response.setStatut(true);
               response.setDescription("Enregistrement réussi!");
          } else {
               response.setStatut(false);
               response.setDescription("Echec d'enregistrement!");
          }
          return response;
     }

    
    private Object createWorkflows(WorkflowItem workflowItem, String space) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        return jbpmApiService.CreateOneWorkflow(workflowItem, space);
    }

    private Object getOneContainer(String artifactId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        return jbpmApiService.RecupContainer(artifactId);
    }

    public Object getProcessinfos(String containerid) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        return jbpmApiService.ListOfprocess(containerid);
    }

      @GetMapping("workflow/nombreWorkflow")
     public ServeurResponse getNombreWorkflow(){
          ServeurResponse response = new ServeurResponse();
          response.setData(workflowService.nombreWorkflow());
          response.setStatut(true);
          return response;
     }
      
       
      @GetMapping("formInfo/generer/{containerId}")
      public ServeurResponse getAllGenerer(@PathVariable String containerId) {
          Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosGenerer(containerId);
          ServeurResponse response = new ServeurResponse();
          if (jbpmFormInfos == null) {
              response.setStatut(false);
              response.setData(null);
              response.setDescription("Aucune élèment trouvé.");

          } else {
              
              response.setStatut(true);
              response.setData(jbpmFormInfos);
              response.setDescription("Données récupérées.");
          }
          return response;
      }
      @GetMapping("formInfo/nogenerer")
      public ServeurResponse getAllNoGenerer() {
          Iterable<JbpmFormInfos> jbpmFormInfos = jbpmFormInfosService.getListJbpmFormInfosNoGenerer();
          ServeurResponse response = new ServeurResponse();
          if (jbpmFormInfos == null) {
              response.setStatut(false);
              response.setData(null);
              response.setDescription("Aucune élèment trouvé.");

          } else {
              
              response.setStatut(true);
              response.setData(jbpmFormInfos);
              response.setDescription("Données récupérées.");
          }
          return response;
      }
       
      private Task getTask(Long taskid) {
        return taskService.findByTskId(taskid);          
      }
      
      @GetMapping("recuptask/{workflowId}/{taskid}/{profil}")
      public ServeurResponse getAllTask(@PathVariable Long workflowId,@PathVariable Long profil, @PathVariable Long taskId) {
        Task task =  this.getTask(taskId);
        String form = task.getTskFormName();
        Iterable <?> allTask = taskService.getListTaskByTaskIdAndForm(taskId, form);
          ServeurResponse response = new ServeurResponse();
          if (allTask == null) {
              response.setStatut(false);
              response.setData(null);
              response.setDescription("Aucune élèment trouvé.");

          } else { 
              response.setStatut(true);
              response.setData(allTask);
              response.setDescription("Données récupérées.");
          }
          return response;
      }
      
      @GetMapping("executer/{workflowId}/{poOwner}/{containerId}/{processId}/{processInstId}")
      public ServeurResponse executeTask(@PathVariable("workflowId") Long workflowId, @PathVariable("poOwner") Long poOwner, @PathVariable("containerId") String containerId, @PathVariable("processId") String processId, @PathVariable("processInstId") Long processInstId) {
    	  
    	  //si premier tache startprocess
          Task task = taskService.findByWorkflowAndProfil(workflowId, poOwner);
          Object extask = null;
          if(task!=null) {
        	  try {
            if(task.getTskIsFirst()) {
               
				processInstId = jbpmApiService.startOneWorkflow(containerId, processId);
            }
           
				extask= executeTask(workflowId+"", task.getTskFormName());
			
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
            

          }

        

    	  return new ServeurResponse(extask!=null?true:false, "", extask,null);
      }
      
      
		public Object executeTask(
				@RequestParam("workflow") String workflow, @RequestParam("form") String form ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {

          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
          ServeurResponse response = new ServeurResponse();
          JSONObject obj = new JSONObject(workflow);
          String owner;
          String usertsk;
          Long idfortask = 1L;
           owner = (String) obj.get("taskactualowner").toString();
           usertsk =  owner.replaceAll("\\[", "").replaceAll("\\]","");

          JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(usertsk.replace("\"",""));
          String container;
          container = (String) obj.get("wkfConteneur").toString();
          String taskid;
          taskid = (String) obj.get("wkfTaskId").toString();
          Integer taskidcast=Integer.parseInt(taskid);  
          idfortask = Long.valueOf(taskidcast);
          String processid;
          processid = (String) obj.get("wkfProcess_id").toString();
          Object champrocess = "";
          champrocess = form;
          String pass = "";
          Object extask = "";
          if (findjbpmuser != null) { 
              pass = findjbpmuser.getJbpmPass(); 
 
              extask= jbpmApiService.ExecuteOneTask(container, usertsk.replace("\"",""), pass, idfortask, champrocess);
              
              response.setStatut(true);
              response.setDescription("Enregistrement réussi!");
              response.setData(extask);
          } else {
              response.setStatut(false);
              response.setDescription("Echec d'enregistrement!");
          }

          return extask;

      }

}