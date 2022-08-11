package sn.gainde2000.pi.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.service.ITaskService;

@RestController
public class TaskController {

	 @Autowired
     ITaskService taskService;
	 
	    @GetMapping("task/list")
	     public ServeurResponse getAllTask() {
	          ServeurResponse response = new ServeurResponse();
	           Iterable<Task> task = taskService.getAllTask();
	          // System.out.println(jbpmFormInfos);
	          response.setData(task);
	          response.setStatut(true);
	          response.setDescription("Liste des tasks"); 
	          return response;
	     }

             @GetMapping("listtaskstatususer/{tskId}/{profilId}")
	     public ServeurResponse getAllTask(@PathVariable Long tskId,@PathVariable Long profilId) {
	          ServeurResponse response = new ServeurResponse();
	           Iterable<Task> task = taskService.getAllTaskStatusUser(tskId,profilId); 
	          response.setData(task);
	          response.setStatut(true);
	          response.setDescription("Liste des status tasks de l'utilisateur"); 
	          return response;
	     }             
}
