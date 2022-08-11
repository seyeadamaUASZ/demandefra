import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Workflow } from '../models/workflow';
import { Task } from '../models/task';
import { WorkflowItem } from '../models/workflowItem';
import { ProcessInfo } from '../models/processinfo';
import { SecteurItem } from '../models/secteurItem';
import { Observable } from 'rxjs';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { JbpmTaskEntree } from '../models/jbpmTaskEntree';
@Injectable({
    providedIn: 'root'
})
export class WorkflowService {
    //api = "http://10.3.20.62:9090/";
    constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }
    api = environment.apii;

    recupGroupe(username:any) {
        return this.http.get<any>(this.api + 'recupgroup/'+username);
    }
   /* recupDocJbpm(username:any) {
        return this.http.get<any>(this.api + 'downloadjbpmfile/'+username);
    }*/
    listWorkflows() {
        return this.http.get<any>(this.api + 'workflows');
    }
    listjbpmdonne() {
        return this.http.get<any>(this.api + 'jbpmtasktntreelist', );

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

    listOftask(username: any,containerId:any) {

        return this.http.get<any>(this.api + 'recuptask/' + username+'/'+containerId);
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
    listNextChampsTask(data: any, data1: any) {

        return this.http.get<any>(this.api + 'recupnextformtask/' + data + '/' + data1);
    }
    displayBpm(data:any,data1:any){

        return this.http.get<any>(this.api + 'displaybpm/'+data+'/'+data1);
    }

    listContainerIdByProcess(data) {

        return this.http.get<any>(this.api + 'processinfo/' + data);
    }

     /*startProjet(workflow, varprocess,doc:File) { 
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(workflow));
        formData.append("form", JSON.stringify(varprocess));  
        return this.http.post<any>(this.api + 'executeworkflow', formData,doc[0]);
    } */

     startProjet(formData:FormData) { 
        return this.http.post<any>(this.api + 'executeworkflow', formData);
    } 


    startTask(formData:FormData) { 
        return this.http.post<any>(this.api + 'executetask', formData);
    }
    startTaskWithDoc(formData:FormData) { 
        
        return this.http.post<any>(this.api + 'executetaskwithdoc', formData);
    }
    
    reclamerTask(containerId: any,TaskId:any,username:any) {   
        
        return this.http.get<any>(this.api + 'taskreclame/'+containerId+'/'+TaskId+'/'+username);
    }
    suspendreTask(containerId: any,TaskId:any,username:any) {   
        
        return this.http.get<any>(this.api + 'tasksuspendre/'+containerId+'/'+TaskId+'/'+username);
    }
    modeliser(workflow: Workflow) {
        return this.http.post<any>(this.api + 'modeliser', workflow);

    }

    listtaskjbpm() {
        return this.http.get<any>(this.api + 'jbpmtasklist');

    }
    
    insertjbpmdonne(formData) {
        return this.http.post<any>(this.api + 'jbpmtasktntree', formData);

    }


    
    downloadFile(id): Observable<HttpResponse<any>>{

        return this.http.get(this.api + 'downloadjbpmfile/'+id, {
          observe: 'response',
          responseType: 'blob' as 'json'
        });
    
       }

}  