package sn.gainde2000.pi.core.service;

import org.apache.tomcat.websocket.server.UriTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;
import sn.gainde2000.pi.core.entity.SecteurItem;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.entity.WorkflowItem;
//import sun.jvm.hotspot.utilities.Assert;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.core.entity.WorkflowSecteur;


@Service
public class JbpmApiService {
      
    @Autowired
    private AppProperties app;

    /**
     * Endpoint de recuperation des secteurs depuis jbpm
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object RecupSpace() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
         String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/business-central/rest/spaces/";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class);
        String json = response1.getBody();
        return json;
    }
    
 
    /**
     * Endpoint de recuperation des champs de formulaire de processus depuis jbpm
     * @param processInstanceId
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public String RecupOutput(String processInstanceId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/queries/processes/instances/{processInstanceId}/variables/instances";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class, processInstanceId);
        String json = response1.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation des diagrame bpmn en svg depuis jbpm
     * @param containerId
     * @param processInstanceId
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public String RecupBpm(String containerId, String processInstanceId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/images/processes/instances/{processInstanceId}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class, containerId,processInstanceId);
        String json = response1.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation des l'ensemble des tache d'un utilisateur depuis jbpm
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object RecupTaskAll() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/queries/tasks/instances/";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response0 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class);
        String json = response0.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation d'une tache specifique à l'utilisateur depuis jbpm
     * @param user
     * @param pass
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object RecupTask(String user, String pass, String groupe) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        //String url1 = app.getJbpmserver()+"kie-server/services/rest/server/queries/tasks/instances/owners?status=Reserved&status=InProgress&status=Completed&user={user}&page=0&pageSize=1000&sortOrder=true";
        String url2 = app.getJbpmserver()+"kie-server/services/rest/server/queries/tasks/instances/pot-owners?status=Created&status=Ready&status=Reserved&status=InProgress&status=Suspended&status=Completed&status=Failed&status=Error&status=Exited&status=Obsolete&groups="+groupe+"&user="+user+"&sortOrder=true";

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        //ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class,user);
        ResponseEntity<String> response2 = restTemplate.exchange(url2, HttpMethod.GET, request, String.class,user);
        //System.out.println("-------------------response2.getBody()------------------"+response2.getBody());
        String json = response2.getBody();
        return json;
    }

    public Object RecupTaskInstIdByProcessInstId(String user, String pass, long processInstId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        //String url1 = app.getJbpmserver()+"kie-server/services/rest/server/queries/tasks/instances/owners?status=Reserved&status=InProgress&status=Completed&user={user}&page=0&pageSize=1000&sortOrder=true";
        String url2 = app.getJbpmserver()+"kie-server/services/rest/server/queries/tasks/instances/process/"+processInstId;

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        //ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class,user);
        ResponseEntity<String> response2 = restTemplate.exchange(url2, HttpMethod.GET, request, String.class,user);
        //System.out.println("-------------------response2.getBody()------------------"+response2.getBody());
        String json = response2.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation de formulaire de processus par containerId et processId depuis jbpm
     * @param containerId
     * @param processId
     * @return
     * @throws JSONException
     */
    public Object  generateurFormProcess(String containerId,String processId) throws JSONException {
        RestTemplate restTemplate2 = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr2 = ""+user+":"+pass+"";
        String base64Credits2 = Base64.getEncoder().encodeToString(authStr2.getBytes());
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Basic " + base64Credits2);
        headers2.add("content-type","application/json");
        HttpEntity request2 = new HttpEntity(headers2);
        String url2 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/forms/processes/{processId}?lang=en&type=ANY&marshallContent=true";
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers2.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response2 = restTemplate2.exchange(url2, HttpMethod.GET, request2, String.class,containerId,processId);
        String data =response2.getBody();
        JSONArray jsonarray = new JSONArray(data);
        String champs = "";
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String name = jsonobject.getString("fields");
            champs = name;
        }
        return champs;
    }

    /**
     * Endpoint de recuperation de formulaire de tache par containerId et processId depuis jbpm
     * @param containerId
     * @return
     * @throws JSONException
     */
    public Object  generateurFormTask(String containerId,Long  taskId) throws JSONException {
        RestTemplate restTemplate2 = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr2 = ""+user+":"+pass+"";
        String base64Credits2 = Base64.getEncoder().encodeToString(authStr2.getBytes());
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Basic " + base64Credits2);
        headers2.add("content-type","application/json");
        HttpEntity request2 = new HttpEntity(headers2);
        String url2 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/forms/tasks/"+taskId+"?lang=en&filter=true&type=ANY&marshallContent=true";
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers2.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response2 = restTemplate2.exchange(url2, HttpMethod.GET, request2, String.class,containerId,taskId);
        String data =response2.getBody();
        /*JSONArray jsonarray = new JSONArray(data); 
        Object champs = "";
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            JSONArray recs = jsonobject.getJSONArray("fields"); 
            //String name = jsonobject.getString("fields"); 
            champs = recs; 
        }
        System.out.println("-------jsonformfield----"+champs); */
        return data;
    }
    
        public Object  generateurStartFormTask(String containerId,Long  taskId) throws JSONException {
        RestTemplate restTemplate2 = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr2 = ""+user+":"+pass+"";
        String base64Credits2 = Base64.getEncoder().encodeToString(authStr2.getBytes());
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Basic " + base64Credits2);
        headers2.add("content-type","application/json");
        HttpEntity request2 = new HttpEntity(headers2);
        String url2 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/forms/tasks/"+taskId+"?lang=en&filter=true&type=ANY&marshallContent=true";
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers2.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response2 = restTemplate2.exchange(url2, HttpMethod.GET, request2, String.class,containerId,taskId);
        String data =response2.getBody();
        JSONArray jsonarray = new JSONArray(data); 
        Object champs = "";
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            JSONArray recs = jsonobject.getJSONArray("fields");  
            champs = recs; 
        } 
        return champs;
    }

    /**
     * Endpoint de recuperation d'un containerId depuis jbpm
     * @param artifactId
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object RecupContainer(String artifactId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers?artifactId={artifactId}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response0 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class,artifactId);
        String json = response0.getBody();
        JSONObject req = new JSONObject(json);
        JSONObject locs1 = req.getJSONObject("result");
        JSONObject locs = locs1.getJSONObject("kie-containers");
        JSONArray recs = locs.getJSONArray("kie-container");
        String containerid = "";
        for (int j = 0; j < recs.length(); j++) {
            JSONObject data2 = recs.getJSONObject(j);
            String str = data2.getString("container-id");
            containerid  =str;
        }
        return containerid;
    }

    /**
     * Endpoint de recuperation le la liste des process par containerId depuis jbpm
     * @param containerid
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object ListOfprocess(String containerid) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerid}/processes?page=0&pageSize=10&sortOrder=true";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class,containerid);
        String json = response1.getBody();
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("processes");
        String processid = "";
        for (int i = 0; i < arr.length(); i++) {
            String process_id = arr.getJSONObject(i).getString("process-id");
            processid = process_id;
        }
        return processid;
    }

    /**
     * Endpoint de recuperation de la liste des containerId depuis jbpm
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public Object ListOfContainer() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class);
        String json = response1.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation de la liste des containerId depuis jbpm
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public Object ExtractAllContainerId() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/queries/processes/definitions?page=0&pageSize=10&sortOrder=true";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class);
        String json = response1.getBody();
        return json;
    }

    /**
     * Endpoint de recuperation des information d'un tache depuis jbpm
     * @param containerId
     * @param processId
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public Object ExtractAllTaskInfo(String containerId,String processId ) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
            RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
            String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Credits);
            HttpEntity request = new HttpEntity(headers);
            String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/processes/definitions/{processId}/entities";
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("X-Request-Source", "Desktop");
            ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class, containerId,processId);
            String json = response1.getBody();
            return json;
    }

     public Object ExtractProcessDefUser(String containerId,String processId ) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
            RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
            String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Credits);
            HttpEntity request = new HttpEntity(headers);
            String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/{containerId}/processes/definitions/{processId}/tasks/users";
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("X-Request-Source", "Desktop");
            ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, String.class, containerId,processId);
            String json = response1.getBody();
            return json;
    }
    /**
     * Endepoint d'execution d'un processus
     * @param container
     * @param process
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
        public Long startOneWorkflow(String container, String process) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
            String user = app.getJbpmLogin();
            String pass = app.getJbpmPass();
            String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/kie-server/services/rest/server/containers/"+container+"/processes/"+process+"/instances";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");

            Map<String, Object> map = new HashMap<>();
            map.put("containerId", container);
            map.put("processId", process);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<Workflow> response1 = restTemplate.postForEntity(url1, entity, Workflow.class);
            if (response1.getStatusCode() == HttpStatus.CREATED) {
                Long idp = response1.getBody().getWkfId();
                return idp;
            } else {
                return null;
            }
    }

    /**
     * Endpoint de prise en main d'un tache
     * @param container
     * @param owner
     * @param pass
     * @param taskId
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object ReclameOneTask(String container,String owner,String pass, Long taskId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String authStr = ""+owner+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/{container}/tasks/{taskId}/states/started?user={owner}").build()
                        .expand(container, taskId,owner)
                        .encode();
        URI uri = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        HttpEntity<String> entity = new HttpEntity<String>(owner.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Object SuspendreOneTask(String container,String owner,String pass, Integer taskId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String authStr = ""+owner+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/{container}/tasks/{taskId}/states/suspended?user={owner}").build()
                        .expand(container, taskId,owner)
                        .encode();
        URI uri = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        HttpEntity<String> entity = new HttpEntity<String>(owner.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    /**
     * Endpoint d'execution d'une tache
     * @param container
     * @param owner
     * @param pass
     * @param taskid
     * @param champrocess
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object ExecuteOneTask(String container,String owner,String pass, Long idfortask, Object champrocess) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String authStr = ""+owner+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/"+container+"/tasks/"+idfortask+"/states/completed?user="+owner).build()
                        .expand(container, idfortask,owner)
                        .encode();
        URI uri = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");      
        System.out.println("++++++++++++++++++ExecuteOneTask+++++++champrocess+++++++++++++++++++++++++++"+champrocess.toString());
        HttpEntity<String> entity = new HttpEntity<String>(champrocess.toString(), headers);
        //System.out.println("-----------------uri-------------------"+uri+"-------------------entity-----------------"+entity.toString());
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Object ExecuteOneTaskWithDocu(String container,String owner,String pass, String taskid, Object champrocess) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String authStr = ""+owner+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/"+container+"/tasks/"+taskid+"?user="+owner).build()
                        .expand(container, taskid,owner)
                        .encode();
        URI uri1 = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        System.out.println("++++++++++++++++++ExecuteOneTaskWithDocu+++++++champrocess+++++++++++++++++++++++++++"+champrocess.toString());
        HttpEntity<String> entity1 = new HttpEntity<String>(champrocess.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(uri1, HttpMethod.PUT, entity1, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {

            return response.getBody();
        } else {
            return null;
        }
    }

    /**
     * Endpoint d'insertion de données d'entrée
     * @param container
     * @param idprocess
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     * @throws JSONException
     */
    public Object AddInputProcess(String container, String idprocess,Object champrocess) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/{container}/processes/instances/{idprocess}/variables").build()
                        .expand(container, idprocess)
                        .encode();
        URI uri = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        HttpEntity<String> entity = new HttpEntity<String>(champrocess.toString(), headers);
        ResponseEntity<String> response =  restTemplate.postForEntity(uri, entity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Object AddDocProcess(String container, Long idprocessInst,Object docprocess) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(app.getJbpmserver()+"/kie-server/services/rest/server/containers/{container}/processes/instances/{idprocessInst}/variables").build()
                        .expand(container, idprocessInst)
                        .encode();
        URI uri = uriComponents.toUri();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        HttpEntity<String> entity = new HttpEntity<String>(docprocess.toString(), headers);
        ResponseEntity<String> response =  restTemplate.postForEntity(uri, entity, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }



    /**
     * Endpoint de creation de secteur
     * @param secteurItem
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public Object CreateOneSpace(String codeSecteur, String creator) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/business-central/rest/spaces/";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        SecteurItem wspace = new SecteurItem(codeSecteur,creator);
        HttpEntity<SecteurItem> entity = new HttpEntity<>(wspace, headers);
        ResponseEntity<SecteurItem> response =  restTemplate.postForEntity(url1, entity, SecteurItem.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    /**
     * Endpoint de creation de workflow
     * @param workflowItem
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public Object CreateOneWorkflow(WorkflowItem workflowItem, String space) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        RestTemplate restTemplate = new RestTemplate();
        String user = app.getJbpmLogin();
        String pass = app.getJbpmPass();
        String authStr = ""+user+":"+pass+"";
        String base64Credits = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credits);
        HttpEntity request = new HttpEntity(headers);
        String url1 = app.getJbpmserver()+"/business-central/rest/spaces/"+space+"/projects/";

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Request-Source", "Desktop");
        WorkflowItem wpost = new WorkflowItem(workflowItem.getName(), "sn.gainde2000.pi","1.0.0-SNAPSHOT",workflowItem.getDescription());
        HttpEntity<WorkflowItem> entity = new HttpEntity<>(wpost, headers);
        ResponseEntity<WorkflowItem> response =  restTemplate.postForEntity(url1, entity, WorkflowItem.class); 
        if ((response.getStatusCode() == HttpStatus.OK)||(response.getStatusCode() == HttpStatus.ACCEPTED)||(response.getStatusCode() == HttpStatus.CREATED)) {
            return response.getBody();
        } else {
            return null;
        }
    }


}
