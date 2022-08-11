package sn.gainde2000.pi.core.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import static io.jsonwebtoken.Jwts.header;
import static io.jsonwebtoken.Jwts.parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.*;
import sn.gainde2000.pi.core.service.JbpmApiService;
import sn.gainde2000.pi.core.service.JbpmFilesStorageService;
import sn.gainde2000.pi.core.service.impl.JbpmUserServiceImpl;
import sn.gainde2000.pi.core.service.impl.WorkflowServiceImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import sn.gainde2000.pi.core.service.JbpmMediaTypeUtils;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import static org.bouncycastle.asn1.cms.CMSAttributes.contentType;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import sn.gainde2000.pi.core.service.IJbpmUserService;
import sn.gainde2000.pi.core.service.IWorkflowService; 

@RestController
@CrossOrigin("*")
public class ProceduresController {
    @Autowired
    IWorkflowService workflowService;
    @Autowired
    JbpmApiService jbpmApiService;
    @Autowired
    JbpmMediaTypeUtils jbpmMediaTypeUtils;
    @Autowired
    IJbpmUserService jbpmUserService;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private AppProperties app;
    @Autowired
    JbpmFilesStorageService jbpmstorageService;
@Autowired
private ApplicationContext applicationContext;
 

    /**
     * Recuperation de la liste des secteurs (espaces de travail crées dans jbpm)
     * @return
     * @throws SAXException
     * @throws TransformerException
     * @throws IOException
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws JSONException
     */
    @GetMapping("getspacejbpm")
    public Object GetWorkflowSpace() throws SAXException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, JSONException {
        Object spacedata = jbpmApiService.RecupSpace();
        return jbpmApiService.RecupSpace();
    }
    
   
     
      
    
    @GetMapping("downloadjbpmfile/{jbpmfileid}")
   public ResponseEntity<Resource> Downloadjbpmfile(HttpServletResponse response,@PathVariable String jbpmfileid) throws IOException {
                String jbpmfilename = "";
                //System.out.println(app.getJbpmDirFile()+jbpmfileid+"/");
        File file = new File(app.getJbpmDirFile()+jbpmfileid+"/");
        File[] files = file.listFiles();
        for(File f: files){
            jbpmfilename = f.getName();
            //System.out.println("++++++++++++++++++++++++++++++"+f.getName());
        }
        String extension = ""; 
        int i = jbpmfilename.lastIndexOf('.');
            if (i > 0) {
              extension = jbpmfilename.substring(i+1);
            }
      
            File file2 = new File(app.getJbpmDirFile()+jbpmfileid+"/"+jbpmfilename+"");
             HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+jbpmfilename);
             headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            //System.out.print(file2.getName());
            Path path = Paths.get(app.getJbpmDirFile()+jbpmfileid+"/"+jbpmfilename+"");
          //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
          InputStreamResource resource = new InputStreamResource(new FileInputStream(app.getJbpmDirFile()+jbpmfileid+"/"+jbpmfilename+""));
          
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/"+extension+""))
                    .body(resource);
                
	
    }
 
    
    

    /**
     * Recuperation des champs de formulaired'execution via processInstanceId
     * @param processInstanceId
     * @return
     * @throws SAXException
     * @throws TransformerException
     * @throws IOException
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws JSONException
     */
    @GetMapping("getoutputbpm/{processInstanceId}")
    public String GetWorkflowOutput(@PathVariable String processInstanceId) throws SAXException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, JSONException {
        return jbpmApiService.RecupOutput(processInstanceId);
    }

    /**
     * Recuperation du bpmn en format svg pour affichae du diagramme
     * @param containerId
     * @param processInstanceId
     * @return
     * @throws SAXException
     * @throws TransformerException
     * @throws IOException
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws JSONException
     */
    @GetMapping("displaybpm/{containerId}/{processInstanceId}")
    public ServeurResponse GetWorkflowBpm(@PathVariable String containerId,@PathVariable String processInstanceId) throws SAXException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, JSONException {
        ServeurResponse response = new ServeurResponse();
        Object bpmdata = jbpmApiService.RecupBpm(containerId,processInstanceId);
        response.setData(bpmdata);
        response.setStatut(true);
        response.setDescription("Nom du workflow");
        return response;
    }

    /**
     * Recuperation du processId via le containerId
     * @param containerId
     * @return
     * @throws SAXException
     * @throws TransformerException
     * @throws IOException
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws JSONException
     */
    @GetMapping("processinfo/{containerId}")
    public ServeurResponse GetCurrentProcessInstInd(@PathVariable String containerId) throws SAXException, TransformerException, IOException, XPathExpressionException, ParserConfigurationException, JSONException {
        ServeurResponse response = new ServeurResponse();
        Object CurrentInstInd = jbpmApiService.ListOfprocess(containerId);
        response.setData(CurrentInstInd);
        response.setStatut(true);
        response.setDescription("Process instance Id");
        return response;
    }

    @GetMapping("taskinfos/{containerId}/{processId}")
    @ResponseBody
    public Object getAllTaskinfos(@PathVariable String containerId, @PathVariable String processId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        return jbpmApiService.ExtractAllTaskInfo(containerId, processId);
    }

    @GetMapping("extractProcessDefUser/{containerId}/{processId}")
    @ResponseBody
    public Object getExtractProcessDefUser(@PathVariable String containerId, @PathVariable String processId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        return jbpmApiService.ExtractProcessDefUser(containerId, processId);
    }
    @GetMapping("containerlist/")
    @ResponseBody
    public Object getAllContainerId() throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        Object containerdata = jbpmApiService.ExtractAllContainerId();
        return containerdata;
    }

    @GetMapping("listofcontainer")
    @ResponseBody
    public Object listOfContainer() throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        Object containerdata = jbpmApiService.ListOfContainer();
        return containerdata;
    }

    @GetMapping("allrecuptask")
    public Object taskrecupall() throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        ServeurResponse response = new ServeurResponse();
        Object alltask =jbpmApiService.RecupTaskAll();
        response.setStatut(true);
        response.setDescription("Enregistrement réussi!");
        response.setData(alltask);
        return jbpmApiService.RecupTaskAll();
    }
    @GetMapping("recupgroup/{username}")
    public Object grouprecup(@PathVariable String username) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        ServeurResponse response = new ServeurResponse();
        JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(username);
        String groupe = "";
        if (findjbpmuser != null) {
            groupe = findjbpmuser.getJbpmGroupe();
            response.setStatut(true);
            response.setDescription("Récuperation groupe réussi!");
            response.setData(groupe);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement!");
        }
        return groupe;

    }
    @GetMapping("recuptask/{username}/{containerId}")
    public Object taskrecup(@PathVariable String username,@PathVariable String containerId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        ServeurResponse response = new ServeurResponse();
        JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(username);
        String user = "";
        String pass = "";
        String groupe = "";
        Object donnetask = "";
        if (findjbpmuser != null) {
            user = findjbpmuser.getIndusUser();
            pass = findjbpmuser.getJbpmPass();
            groupe = findjbpmuser.getJbpmGroupe();
            donnetask = jbpmApiService.RecupTask(user, pass,groupe); 
            //System.out.println("----------"+donnetask); 
            response.setStatut(true);
            response.setDescription("Enregistrement réussi!");
            response.setData(findjbpmuser);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement!");
        }
        return donnetask;

    }

    @GetMapping("recupformprocess/{containerId}/{processId}")
    @ResponseBody
    public Object recupformprocess(@PathVariable String containerId, @PathVariable String processId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        return jbpmApiService.generateurFormProcess(containerId, processId);
    }

    @GetMapping("recupformtask/{containerId}/{taskId}")
    @ResponseBody
    public Object recupformtask(@PathVariable String containerId, @PathVariable Long taskId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        return jbpmApiService.generateurFormTask(containerId, taskId);
    }
    
     @GetMapping("recupnextformtask/{containerId}/{taskId}")
    @ResponseBody
    public Object recupnextformtask(@PathVariable String containerId, @PathVariable Long taskId) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, JSONException {
        return jbpmApiService.generateurStartFormTask(containerId, taskId);
    }
    


    /* @PostMapping(value = "executeworkflow")
    public Object executeWorkflows(@RequestParam("workflow") String workflow ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        ServeurResponse response = new ServeurResponse();

            JSONObject obj = new JSONObject(workflow);

            String container;
            container = (String) obj.get("wkfConteneur").toString();
            String process;
         String username;
            process = (String) obj.get("wkfProcess_id").toString();
            Workflow findWorkflow = workflowService.findByWkfConteneur(container);

            Long idprocess = 1L;
            if (findWorkflow != null) {
                findWorkflow.setWkfEtat("3");
                this.workflowService.save(findWorkflow);
                idprocess = jbpmApiService.ExecuteOneWorkflow(container, process);
                response.setStatut(true);
                response.setDescription("Enregistrement réussi!");
                response.setData(findWorkflow);
            } else {
                response.setStatut(false);
                response.setDescription("Echec d'enregistrement!");
            }

            return idprocess;

    }*/

    @PostMapping(value = "executeworkflow")
    public Object executeWorkflows(@RequestParam("workflow") String workflow,@RequestParam("utilisateur") String utilisateur ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        ServeurResponse response = new ServeurResponse();
        String usertask = utilisateur.replaceAll("\\[", "").replaceAll("\\]","");
       // System.out.println(taskId+" ----- "+usertask.replace("\"", ""));
        JSONObject obj = new JSONObject(workflow);
        String container;
        container = (String) obj.get("wkfConteneur").toString();
        String process;
        String pass = "";
        String groupe = "";
        Long taskProcInstId= 1L;
        process = (String) obj.get("wkfProcess_id").toString();

        JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(usertask.replace("\"", ""));

        Workflow findWorkflow = workflowService.findByWkfConteneur(container);
        Long processInstId = 1L;
        Object reclam;
        Object champstask = "";
        Object donneestask = "";
        //System.out.println(process+" ----- "+container+" ----- "+usertask);
        Object var_tache = "";
       if (findWorkflow != null && findjbpmuser !=null) {
            pass = findjbpmuser.getJbpmPass();
           groupe = findjbpmuser.getJbpmGroupe();
            //System.out.println(pass+" ----- ");
            findWorkflow.setWkfEtat("3");
            this.workflowService.save(findWorkflow);
            processInstId = jbpmApiService.startOneWorkflow(container, process);
           // System.out.println(idprocess+" ----- ");
           donneestask= jbpmApiService.RecupTaskInstIdByProcessInstId(usertask.replace("\"", ""), pass,processInstId);
           JSONObject obj0 = new JSONObject(String.valueOf(donneestask));
           JSONArray jsonDataset1 = obj0.getJSONArray("task-summary"); 
           for (int i = 0; i < jsonDataset1.length(); i++) {
               JSONObject forid = jsonDataset1.getJSONObject(i);  
               Integer id = forid.getInt("task-id"); 
               taskProcInstId = Long.valueOf(id);
              // System.out.println("------------task id----"+taskProcInstId);
           }
           // System.out.println("------------task id verif----"+taskProcInstId);
            reclam = jbpmApiService.ReclameOneTask(container, usertask.replace("\"", ""), pass, taskProcInstId);
            
            
           champstask =  jbpmApiService.generateurStartFormTask(container, taskProcInstId);
         // champstask= jbpmApiService.RecupTask(usertask.replace("\"", ""), pass,groupe);

            response.setStatut(true);
            response.setDescription("Enregistrement réussi!");
           response.setData(champstask);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement!");
        }


        var_tache = "{\n" +
                "            \"tache\": {\n" +
                "            \"forms\": "+champstask+" ,\n" +
                "            \"taskid\": "+"\""+taskProcInstId+"\",\n" +
                "            \"containerid\": "+"\""+container+"\",\n" +
                "            \"processinstid\": "+"\""+processInstId+"\",\n" +
                "            \"processid\": "+"\""+process+"\",\n" +
                "            \"usertask\": "+usertask+"\n" +
                "        }\n" +
                "        }";
       // System.out.println(var_tache+" ----- ");
       return  var_tache;
        //return "[{\"taskid:\""+"\""+taskProcInstId+"\""+"}],"+""+champstask;

    }

    //@PostMapping("executetaskwithdoc")
    @RequestMapping(value = "executetaskwithdoc", method = RequestMethod.POST)
    @ResponseBody
    public Object executeTaskWithDocJbpm(@RequestParam("workflow") String workflow,@RequestParam("docname") String docname,@RequestPart("doc") MultipartFile doc ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        ServeurResponse response = new ServeurResponse();
        Object repextask = "";
            try {

                UUID uuigen = Generators.timeBasedGenerator().generate();
                String uuid1 = app.getJbpmDirFile()+""+uuigen;

                Path path = Paths.get(uuid1);

                if (!Files.exists(path)) {

                    Files.createDirectory(path);

                   // System.out.println("Directory created"+path);
                } else {

                   // System.out.println("Directory already exists");
                }


                String donneedocu = "{" +
                        "\""+docname.replace("\"","")+"\" : {\n" +
                        "        \"org.jbpm.document.service.impl.DocumentImpl\":{\n" +
                        "            \"identifier\":\""+uuigen+"\",\n" +
                        "            \"name\":\""+doc.getOriginalFilename()+"\",\n" +
                        "            \"link\":\""+uuigen+"\",\n" +
                        "            \"size\": \""+doc.getSize()+"\",\n" +
                        "            \"lastModified\": \""+uuigen.timestamp()+"\",\n" +
                        "            \"content\":\"\"\n" +
                        "    }\n" +
                        "}\n" +

                        "}";

                //System.out.println(donneedocu);
                Files.copy(doc.getInputStream(), Paths.get(path+"/" + doc.getOriginalFilename()));
            JSONObject obj = new JSONObject(workflow);
                String owner;
                String usertsk;
                Long idprocessInst = 1L;
                owner = (String) obj.get("taskactualowner").toString();
                usertsk =  owner.replaceAll("\\[", "").replaceAll("\\]","");

                JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(usertsk.replace("\"",""));
            String pass = "";
                String container;
                container = (String) obj.get("wkfConteneur").toString();
                String taskid;
                taskid = (String) obj.get("wkfTaskId").toString();
                String processid;
                processid = (String) obj.get("wkfProcess_id").toString();
                String instprocessid;
                instprocessid = (String) obj.get("wkfProcess_inst_id").toString();
                Integer processInsIdCast=Integer.parseInt(instprocessid);  
                idprocessInst = Long.valueOf(processInsIdCast);
                Object docprocess = "";
                docprocess = donneedocu;
                Object champrocess= "";
                champrocess = "";

            if (findjbpmuser != null) {
                pass = findjbpmuser.getJbpmPass();
                //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+pass);
               // System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+container+" "+usertsk.replace("\"","")+" "+pass+" "+taskid+" "+champrocess);
                jbpmApiService.AddDocProcess(container,idprocessInst,docprocess);
                
                repextask = jbpmApiService.ExecuteOneTaskWithDocu(container, usertsk.replace("\"",""), pass, processid, champrocess);
                //System.out.println("++++++++++++++++++++++++with doc++++++++++++++++++++++++++++"+repextask);

                response.setStatut(true);
                response.setDescription("Enregistrement réussi!");
                response.setData(findjbpmuser);
            } else {
                response.setStatut(false);
                response.setDescription("Echec d'enregistrement!");
            }

            } catch (Exception e) {
                response.setStatut(false);
                response.setDescription("Echec d'enregistrement!");
            }
        return repextask;
    }

    //@PostMapping("executetask")
     @RequestMapping(value = "executetask", method = RequestMethod.POST)
    @ResponseBody
    public Object executeTask(@RequestParam("workflow") String workflow, @RequestParam("form") String form ) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {

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

            //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+champrocess);

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



   @GetMapping("taskreclame/{containerId}/{taskId}/{username}")
    @ResponseBody
    public Object reclameTask(@PathVariable  String containerId,@PathVariable  Long taskId,@PathVariable  String username) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
        ServeurResponse response = new ServeurResponse();
        JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(username);
        Object reTask = "";
        String pass = "";
        if (findjbpmuser != null) {
            pass = findjbpmuser.getJbpmPass();
             reTask = jbpmApiService.ReclameOneTask(containerId, username, pass, taskId);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi!");
            response.setData(reTask);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement!");
        }

        return reTask;

    }
    @GetMapping("tasksuspendre/{containerId}/{taskId}/{username}")
    @ResponseBody
    public Object suspendreTask(@PathVariable  String containerId,@PathVariable  Integer taskId,@PathVariable  String username) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
        ServeurResponse response = new ServeurResponse();
        JbpmUserMapping findjbpmuser = jbpmUserService.findByIndusUser(username);
        Object reTask = "";
        String pass = "";
        if (findjbpmuser != null) {
            pass = findjbpmuser.getJbpmPass();
            reTask = jbpmApiService.SuspendreOneTask(containerId, username, pass, taskId);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi!");
            response.setData(reTask);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement!");
        }

        return reTask;

    }
    
    
    
    



}
