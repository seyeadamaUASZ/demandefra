import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Workflow } from '../../procedures/models/workflow';
import { Task } from '../../procedures/models/task';
import { WorkflowItem } from '../../procedures/models/workflowItem';
import { ProcessInfo } from '../../procedures/models/processinfo';
import { SecteurItem } from '../../procedures/models/secteurItem';
@Injectable({
    providedIn: 'root'
})
export class WorkflowService {
    //api = "http://10.3.20.62:9090/";
    api = environment.apii;
    constructor(private http: HttpClient) { }

    listWorkflows() {
        return this.http.get<any>(this.api + 'workflows');
    }
    listWorkflowsbysector(secteur:any) {
        return this.http.get<any>(this.api + 'workflowsbysector/'+secteur);
    }
    WorkflowsListSecteur() {
        return this.http.get<any>(this.api + 'workflowlistsecteur');
    }
    
    listSpace() {
        return this.http.get<any>(this.api + 'getspacejbpm');
    }
    nomDuWorkflows(containerId:any) {
        return this.http.get<any>(this.api + 'workflowname/'+containerId);
    }
    listWorkflowsByApp(id) {
        return this.http.get<any>(this.api + 'workflowsByAppId/' + id);
    }

  /*  getJbpmUrl(){
        return environment.jbpm;
}*/

    createSecteur(secteurItem: SecteurItem) {
        return this.http.post<any>(this.api + 'workflowsecteur', secteurItem);

    }

    creatWorkflow(workflow: Workflow, workflowItem: any) {
        return this.http.post<any>(this.api + 'workflow', workflow, workflowItem);

    }

    deleteWorkflow(workflow: Workflow) {

        return this.http.post<any>(this.api + 'workflow/delete', workflow);


    }

    updateWorkflow(workflow: Workflow) {
        return this.http.post<any>(this.api + 'update', workflow);

    }
    listOfContainer() {

        return this.http.get<any>(this.api + 'listofcontainer');
    }

    listOftask(username: any) {

        return this.http.get<any>(this.api + 'recuptask/' + username);
    }
    listOfAlltask() {

        return this.http.get<any>(this.api + 'allrecuptask/');
    }
    
    listOfOutputTask(processInstanceId: any) {

        return this.http.get<any>(this.api + 'getoutputbpm/' + processInstanceId);
    }
    listChampsProcess(data: any, data1: any) {

        return this.http.get<any>(this.api + 'recupformprocess/' + data + '/' + data1);
    }

    listChampsTask(data: any, data1: any) {

        return this.http.get<any>(this.api + 'recupformtask/' + data + '/' + data1);
    }
    displayBpm(data:any,data1:any){

        return this.http.get<any>(this.api + 'displaybpm/'+data+'/'+data1);
    }

    listContainerIdByProcess(data) {

        return this.http.get<any>(this.api + 'processinfo/' + data);
    }

    startProjet(workflow, varprocess) {
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(workflow));
        formData.append("form", JSON.stringify(varprocess)); 
        return this.http.post<any>(this.api + 'executeworkflow', formData);
    }
    startTask(workflow, varprocess) {
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(workflow));
        formData.append("form", JSON.stringify(varprocess));   
        return this.http.post<any>(this.api + 'executetask', formData);
    }
    reclamerTask(containerId: any,TaskId:any,username:any) {   
        
        return this.http.get<any>(this.api + 'taskreclame/'+containerId+'/'+TaskId+'/'+username);
    }
    modeliser(workflow: Workflow) {
        return this.http.post<any>(this.api + 'modeliser', workflow);

    }


}  