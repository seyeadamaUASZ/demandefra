package sn.gainde2000.pi.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.ApplicationStatus;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.service.IApplicationService;
import sn.gainde2000.pi.core.service.IWorkflowService;

@RestController
@CrossOrigin("*")
public class ApplicationController {

    @Autowired
    IApplicationService applicationRepository;
    @Autowired
    IWorkflowService workflowService;

    /**
     * Liste des applications
     *
     * @return ServeurResponse
     */
    @GetMapping("application/list")
    public ServeurResponse getAllApplication() {
        Iterable<Application> application = applicationRepository.getListApplication();
        ServeurResponse response = new ServeurResponse();
        if (application == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun élèment trouvé.");
        } else {
            response.setStatut(true);
            response.setData(application);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Ajouter une application
     *
     * @param application
     * @return ServeurResponse
     */
    @PostMapping("application/create")

    public ServeurResponse create(@RequestBody Application application) {
        ServeurResponse response = new ServeurResponse();
        Application app = applicationRepository.findByappNom(application.getAppNom());
        if (app == null) {
            application.setAppDateCreation(new Date());
            // application.setAppEtapeCreation(1);
            applicationRepository.save(application);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(application);
        } else {
            application.setAppId(app.getAppId());
            application.setAppDateCreation(new Date());
            applicationRepository.save(application);
            response.setStatut(false);
            response.setDescription("Cette nom application a été déja crée ");
            response.setData(application);
        }

        return response;
    }

    /**
     * recuperation de l'application par id
     *
     * @param id
     * @return ServeurResponse
     */
    @GetMapping("application/{id}")
    public ServeurResponse findById(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        Application app = applicationRepository.findByappId(id);
        if (app != null) {
            response.setStatut(true);
            response.setDescription("récuperation réussi");
            response.setData(app);
        } else {
            response.setStatut(false);
            response.setDescription("aucun donnee trouvee");
            response.setData(null);
        }
        return response;
    }

    /**
     * Supprime l'application
     *
     * @param application
     * @return ServeurResponse
     */
    @PostMapping("application/delete")
    public ServeurResponse deleteApplication(@RequestBody Application application) {
        ServeurResponse response = new ServeurResponse();
        try {
            applicationRepository.deleteJointureWorkflow(application);
            applicationRepository.deleteJointureFormulaire(application.getAppId());
            applicationRepository.deleteJointureFichier(application);
            //applicationRepository.deleteJointureTableRelationnel(application.getAppId());
        } catch (IllegalArgumentException e) {
        } finally {
            applicationRepository.delete(application);

        }

        response.setStatut(true);
        return response;
    }

    /**
     * Met à jour l'étape de création d'application
     *
     * @param request
     * @return ServeurResponse
     */
    @RequestMapping(value = "application/updateEtape", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServeurResponse UpdateEtapeApp(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String idApp = request.getParameter("idApp");
        String etape = request.getParameter("etape");
        applicationRepository.update(etape, idApp);
        response.setStatut(true);
        response.setDescription("Application update ok");
        return response;

    }

    /**
     * Liste les workflows de l'application
     *
     * @param request
     * @return ServeurResponse
     */
    @RequestMapping(value = "workflowLierApp", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServeurResponse lierWorkflowEtApp(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String idApp = (String) request.getParameter("idApp");
        String idWorkflow = (String) request.getParameter("idWorkflow");
        workflowService.lierWorkflow(idApp, idWorkflow);
        response.setStatut(true);
        response.setDescription("liaison effectué avec succès");

        return response;
    }

    /**
     * Enlève la liaison entre l'application et le workflow
     *
     * @param idWorkflow
     * @return ServeurResponse
     */
    @GetMapping("workflowLierApp/enlever/{idWorkflow}")
    public ServeurResponse enleverLiaisonWorkflowEtApp(@PathVariable String idWorkflow) {
        ServeurResponse response = new ServeurResponse();
        System.out.println("workflow id:" + idWorkflow);
        workflowService.enleverLiasonWorkflow(idWorkflow);
        response.setStatut(true);
        response.setDescription("liaison enlevé avec succès");

        return response;
    }

    /**
     * Liste les workflows libres (qui sont pas liés à une application)
     *
     * @return ServeurResponse
     */
    @GetMapping("WorkflowByApp")
    public ServeurResponse getWorkflowByApp() {
        ServeurResponse response = new ServeurResponse();
        List<Workflow> f = workflowService.getListWorkflowLibre();
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    /**
     *
     * @param id
     * @return ServeurResponse
     */
    @GetMapping("workflowsByAppId/{id}")
    public ServeurResponse getWorkflowByApp(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        List<Workflow> f = workflowService.getWorkflowByAppId(id);
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    /**
     *
     * @param id
     * @return ServeurResponse
     */
    @GetMapping("WorkflowByAppOuLibre/{id}")
    public ServeurResponse getWorkflowLibreOuSpecifique(@PathVariable Long id) {
        ServeurResponse response = new ServeurResponse();
        List<Workflow> f = workflowService.getWorkflowByAppIdOuLibre(id);
        response.setData(f);
        response.setStatut(true);
        return response;
    }

    /**
     *
     * @param request {appId,appUrl}
     * @param file
     * @return
     */
    @PostMapping("application/publier")
    public ServeurResponse publishingApp(HttpServletRequest request, @RequestParam("appImg") MultipartFile file) {

        Long applicationId;

        applicationId = Long.parseLong(request.getParameter("appId"));
        String applicationUrl = request.getParameter("appUrl");
        Application app = applicationRepository.findByappId(applicationId);
        if (app == null) {
            return new ServeurResponse(false, "application.publication.notification.notFound", null, null);
        } else {
            try {

                app.setAppImg(file.getBytes());
                app.setAppUrl(applicationUrl);
                app.setAppDatePub(new Date());
                app.setAppStatus(ApplicationStatus.PUBLICATION);
                applicationRepository.save(app);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return new ServeurResponse(false, "application.publication.notification.saveError", null, null);

            }
        }

        return new ServeurResponse(true, "application.publication.notification.publicationOK", app, null);

    }

    /**
     *
     * @param request {appId,appUrl}
     * @param file
     * @return
     */
    @GetMapping("application/publicationliste")
    public ServeurResponse getListAppPublished() {

        return new ServeurResponse(true, "", applicationRepository.listApplicationPubliee(), null);
    }

    @GetMapping("application/publicationliste2")
    public ServeurResponse list2LeastRecentApplicationPubliee() {

        return new ServeurResponse(true, "", applicationRepository.list2LeastRecentApplicationPubliee(), null);
    }

    /**
     *
     * @param application
     * @return
     */
    @PostMapping("application/depublier")
    public ServeurResponse unpublishingApp(@RequestBody Application application) {
        application.setAppStatus(ApplicationStatus.DEPUBLICATION);
        if (applicationRepository.findByappId(application.getAppId()) != null) {
            application = applicationRepository.save(application);
            if (application != null) {
                return new ServeurResponse(true, "application.publication.notification.depublierOK", application, null);
            } else {
                return new ServeurResponse(false, "application.publication.notification.depublierError", application, null);
            }

        } else {
            return new ServeurResponse(false, "application.publication.notification.depublierError", application, null);

        }
    }

}
