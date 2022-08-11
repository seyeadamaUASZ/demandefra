/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;
import org.xml.sax.SAXException;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.Transition;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.service.impl.TaskServiceImpl;
import sn.gainde2000.pi.core.service.impl.TransitionServiceImpl;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("transition")
public class TransitionController {

    @Autowired
    TransitionServiceImpl transitionService;
    @Autowired
    TaskServiceImpl taskService;

   /* @PostMapping("create")
    public ServeurResponse createTransition(@RequestBody Transition transition) {
        System.out.println("------------transition-----" + transition.toString());
        ServeurResponse response = new ServeurResponse();
        transitionService.create(transition);
        return response;
    }*/
 @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Object createTransition(@RequestParam("transitionform") String workflowform) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException, URISyntaxException, JSONException {
       ServeurResponse response = new ServeurResponse(); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        JSONObject obj = new JSONObject(workflowform);
        String trnAction = "";
        trnAction = (String) obj.get("trnAction").toString(); 
        
        Long trnTskActuel = 1L;
        String trnTskActuelId = "";
        trnTskActuelId = (String) obj.get("trnTskActuel").toString(); 
        Integer trnTskActuelIdcast=Integer.parseInt(trnTskActuelId);  
        trnTskActuel = Long.valueOf(trnTskActuelIdcast);
        
        Long trnTskSuiv = 1L;
        String trnTskSuivId = "";
        trnTskSuivId = (String) obj.get("trnTskSuiv").toString(); 
        Integer trnTskSuivIdcast=Integer.parseInt(trnTskSuivId);  
        trnTskSuiv = Long.valueOf(trnTskSuivIdcast);
        
        Long trnWkfId = 1L;
        String trnWkfIdId = "";
        trnWkfIdId = (String) obj.get("trnWkfId").toString(); 
        Integer trnWkfIdIdcast=Integer.parseInt(trnWkfIdId);  
        trnWkfId = Long.valueOf(trnWkfIdIdcast);
        
        Workflow worklowid; 
        worklowid = new Workflow(trnWkfId); 
           Transition transition  = new Transition();
           transition.setTrnAction(trnAction);
           transition.setTrnTskActuel(trnTskActuel);
           transition.setTrnTskSuiv(trnTskSuiv);
           transition.setTrnWkfId(worklowid);
           transitionService.save(transition);
           response.setData(transition);
           response.setStatut(true);
           response.setDescription("Nom du formulaire");
         
        return transition;
    }
    @GetMapping("statusAfterExecution/{taskId}")
    public ServeurResponse getStatusTask(@PathVariable Long taskId) {
        ServeurResponse response = new ServeurResponse();
        Transition t = transitionService.getTransitionSuiv(taskId);
        if (t != null) {
            Task taskStatus = taskService.findByTskId(t.getTrnTskSuiv());
            response.setData(taskStatus.getTskStatusId());
            response.setStatut(true);
            response.setDescription("statut recupere avec succès");
        } else {
            response.setStatut(false);
            response.setDescription("statut non recupere");
        }

        return response;
    }

}
