import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Workflow } from '../models/workflow';
import { Task } from '../models/task';
import { WorkflowItem } from '../models/workflowItem';
import { ProcessInfo } from '../../procedures/models/processinfo';
import { SecteurItem } from '../models/secteurItem';
import { Transition } from '../models/Transition';
import { Observable } from 'rxjs';
@Injectable({
    providedIn: 'root'
})
export class WorkflowService {
    //api = "http://10.3.20.62:9090/";
    api = environment.apii;
    constructor(private http: HttpClient) { }

     createTransition(formData:FormData){
        return this.http.post<any>(this.api + 'transition/create',formData);
     }

    taskInfosAll(containerId:any,processId:any) {
        return this.http.get<any>(this.api + 'taskinfos/'+containerId+'/'+processId);
    }
    taskStatusAll() {
        return this.http.get<any>(this.api + 'listetaskstatus');
    } 
    listtaskstatususer(tskId:any,profilId:any) {
        return this.http.get<any>(this.api + 'listtaskstatususer/'+tskId+'/'+profilId);
    }

    listetaskparid(idwrkf:any) {
        return this.http.get<any>(this.api + 'listetaskparid/'+idwrkf);
    }
    extractProcessDefUser(containerId:any,processId:any) {
        return this.http.get<any>(this.api + 'extractProcessDefUser/'+containerId+'/'+processId);
    }
    deleteWrkflForm(jfrmId:any) {
        return this.http.get<any>(this.api + 'deletewrkflform/'+jfrmId);
    }
    listFormulaireworkflow(containerId:any) {
        return this.http.get<any>(this.api + 'formulaireworkflow/'+containerId);
    }
    listFormulaireworkflow2(idwrkf:any) {
        return this.http.get<any>(this.api + 'formulaireworkflow2/'+idwrkf);
    }
    listFormAjouterv2() {
        return this.http.get<any>(this.api + 'formulairev2/liste');
    }
     
    formulaireworkflowparid(jfrmId:any) {
        return this.http.get<any>(this.api + 'formulaireworkflowparid/'+jfrmId);
    }
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
    genererFrm(nameprojet,idFrm) {
      return this.http.get<any>(this.api + 'champForm/create/'+nameprojet+'/'+idFrm);
   }
   listFormulaireGenerer(containerId) {
    return this.http.get<any>(this.api + 'formInfo/generer/'+containerId);
  }
  listFormulaireNoGenerer() {
    return this.http.get<any>(this.api + 'formInfo/nogenerer');
  }
  listChampsFormulaire(id) {
    return this.http.get<any>(this.api + 'champForm/listbyForm/'+id);
  }


   /* getJbpmUrl(){
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

    chargerjbpmform(formData:FormData) {
        return this.http.post<any>(this.api + 'chargerjbpmform', formData);
    }
    chargerjbpmform2(formData:FormData) {
        return this.http.post<any>(this.api + 'chargerjbpmformv2', formData);
    }
    createtask(formData:FormData) {
        return this.http.post<any>(this.api + 'createtask', formData);
    }

    createtaskstatus(formData:FormData) {
        return this.http.post<any>(this.api + 'createtaskstatus', formData);
    }

    updateworkflowtask(formData:FormData) {
        return this.http.post<any>(this.api + 'updateworkflowtask', formData);
    }
    deletewrkfltask(idtsk:any) {
        return this.http.get<any>(this.api + 'deletewrkflform/'+idtsk);
    }

    reclamerTask(containerId: any,TaskId:any,username:any) {

        return this.http.get<any>(this.api + 'taskreclame/'+containerId+'/'+TaskId+'/'+username);
    }
    modeliser(workflow: Workflow) {
        return this.http.post<any>(this.api + 'modeliser', workflow);

    }
    downloadFile(id:any): Observable<HttpResponse<any>>{

        return this.http.get(this.api + 'downloadfrmfile/'+id, {
          observe: 'response',
          responseType: 'blob' as 'json'
        });
    
       }

       listeWidget() {
        return this.http.get<any>(this.api + 'listwidget');
    }
    listeTemplateWidget() {
        return this.http.get<any>(this.api + 'listwidgettemplate');
    }

    createparamwidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'createparamwidget', formData);

    }
    listeParamwidget(idwrkf:any) { 
        return this.http.get<any>(this.api + 'listparamwidget/'+idwrkf);

    }

    widgetTemplatesAttr(id:any){
        return this.http.get<any>(this.api + 'attribuertemplwidget/widgettemplatesAttr/'+id);
    }

    recupChampsFormParam(idwrkf:any) { 
        return this.http.get<any>(this.api + 'recupchampsformparam/'+idwrkf);

    }
    
    attributionConfigWidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'attributionconfigwidget', formData);

    }
    updtateAttributionConfigWidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'updateattributionconfigwidget', formData);

    }

    listeConfigWidget() {
        return this.http.get<any>(this.api + 'listconfigwidget');
    }
    deleteAttributionConfigWidget(formData:FormData) { 
        return this.http.post<any>(this.api + 'deleteattributionconfigwidget', formData);

    }
    createConfigTimer(formData:FormData ) { 
        return this.http.post<any>(this.api + 'createconfigtimer', formData);

    }

    listeConfigTimer(){ 
        return this.http.get<any>(this.api + 'listeconfigtimer/');

    }
    listeConfigTimerparid(tskId:any){ 
        return this.http.get<any>(this.api + 'listeconfigtimerparid/'+tskId);

    }
    
    recupTaskAndTimer(tmId:any) { 
        return this.http.get<any>(this.api + 'donneetasktimer/'+tmId);

    }

    recupParTaskId(tskId:any) { 
        return this.http.get<any>(this.api + 'recuptaskparid/'+tskId);

    }

    addreglegestion(formData:FormData ) { 
        return this.http.post<any>(this.api + 'createreglegestion', formData);

    }

    getById(id){
        return this.http.get<any>(this.api + 'formulairev2/get/'+id);
      }
     
      getList(){
        return this.http.get<any>(this.api+'formulairev2/liste');
      }
}
