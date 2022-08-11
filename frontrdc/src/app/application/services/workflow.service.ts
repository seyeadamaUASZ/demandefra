import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Workflow } from '../../procedures/models/workflow';
import { Task } from '../../procedures/models/task';
import { WorkflowItem } from '../../procedures/models/workflowItem';
import { ProcessInfo } from '../../procedures/models/processinfo';
import { SecteurItem } from '../../procedures/models/secteurItem';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
@Injectable({
    providedIn: 'root'
})
export class WorkflowService {
    //api = "http://10.3.20.62:9090/";
    api = environment.apii;
    constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }

    listWorkflows() {
        return this.http.get<any>(this.api + 'workflows',{ 'headers': this.reqHelper.getReqOptions("list_workflow") });
    }
    listWorkflowsbysector(secteur:any) {
        return this.http.get<any>(this.api + 'workflowsbysector/'+secteur,{ 'headers': this.reqHelper.getReqOptions("workflowsbysector_workflow") });
    }
    WorkflowsListSecteur() {
        return this.http.get<any>(this.api + 'workflowlistsecteur',{ 'headers': this.reqHelper.getReqOptions("workflowlistsecteur_workflow") });
    }
    
    listSpace() {
        return this.http.get<any>(this.api + 'getspacejbpm',{ 'headers': this.reqHelper.getReqOptions("getspacejbpm_workflow") });
    }
    nomDuWorkflows(containerId:any) {
        return this.http.get<any>(this.api + 'workflowname/'+containerId,{ 'headers': this.reqHelper.getReqOptions("workflowname_workflow") });
    }
    listWorkflowsByApp(id) {
        return this.http.get<any>(this.api + 'workflowsByAppId/' + id,{ 'headers': this.reqHelper.getReqOptions("workflowsByAppId_workflow") });
    }

  /*  getJbpmUrl(){
        return environment.jbpm;
}*/

    createSecteur(secteurItem: SecteurItem) {
        return this.http.post<any>(this.api + 'workflowsecteur', secteurItem,{ 'headers': this.reqHelper.getReqOptions("create_workflow") });

    }

    creatWorkflow(workflow: Workflow, workflowItem: any) {
        return this.http.post<any>(this.api + 'workflow', workflow, workflowItem),{ 'headers': this.reqHelper.getReqOptions("workflowsecteur_workflow") };

    }

    deleteWorkflow(workflow: Workflow) {

        return this.http.post<any>(this.api + 'workflow/delete', workflow,{ 'headers': this.reqHelper.getReqOptions("delete_workflow") });


    }

    updateWorkflow(workflow: Workflow) {
        return this.http.post<any>(this.api + 'update', workflow,{ 'headers': this.reqHelper.getReqOptions("update_workflow") });

    }
    listOfContainer() {

        return this.http.get<any>(this.api + 'listofcontainer',{ 'headers': this.reqHelper.getReqOptions("listofcontainer_workflow") });
    }

    listOftask(username: any) {

        return this.http.get<any>(this.api + 'recuptask/' + username,{ 'headers': this.reqHelper.getReqOptions("recuptask_workflow") });
    }
    listOfAlltask() {

        return this.http.get<any>(this.api + 'allrecuptask/',{ 'headers': this.reqHelper.getReqOptions("allrecuptask_workflow") });
    }
    
    listOfOutputTask(processInstanceId: any) {

        return this.http.get<any>(this.api + 'getoutputbpm/' + processInstanceId,{ 'headers': this.reqHelper.getReqOptions("getoutputbpm_workflow") });
    }
    listChampsProcess(data: any, data1: any) {

        return this.http.get<any>(this.api + 'recupformprocess/' + data + '/' + data1,{ 'headers': this.reqHelper.getReqOptions("recupformprocess_workflow") });
    }

    listChampsTask(data: any, data1: any) {

        return this.http.get<any>(this.api + 'recupformtask/' + data + '/' + data1,{ 'headers': this.reqHelper.getReqOptions("recupformprocess_workflow") });
    }
    displayBpm(data:any,data1:any){

        return this.http.get<any>(this.api + 'displaybpm/'+data+'/'+data1,{ 'headers': this.reqHelper.getReqOptions("displaybpm_workflow") });
    }

    listContainerIdByProcess(data) {

        return this.http.get<any>(this.api + 'processinfo/' + data,{ 'headers': this.reqHelper.getReqOptions("processinfo_workflow") });
    }

    startProjet(workflow, varprocess) {
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(workflow));
        formData.append("form", JSON.stringify(varprocess)); 
        return this.http.post<any>(this.api + 'executeworkflow', formData,{ 'headers': this.reqHelper.getReqOptions("executeworkflow_workflow") });
    }
    startTask(workflow, varprocess) {
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(workflow));
        formData.append("form", JSON.stringify(varprocess));   
        return this.http.post<any>(this.api + 'executetask', formData,{ 'headers': this.reqHelper.getReqOptions("executetask_workflow") });
    }
    reclamerTask(containerId: any,TaskId:any,username:any) {   
        
        return this.http.get<any>(this.api + 'taskreclame/'+containerId+'/'+TaskId+'/'+username,{ 'headers': this.reqHelper.getReqOptions("taskreclame_workflow") });
    }
    modeliser(workflow: Workflow) {
        return this.http.post<any>(this.api + 'modeliser', workflow,{ 'headers': this.reqHelper.getReqOptions("modeliser_workflow") });

    }


}  