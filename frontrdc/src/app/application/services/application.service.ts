import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Application } from 'src/app/application/models/application';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { Module } from 'src/app/utilisateur/models/module';


@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }
  api = environment.apii;

  updateEtapeCreationApp(data: any) {
    var formData3: FormData = new FormData();
    formData3.append('etape', data.etape);
    formData3.append('idApp', data.idApp);
    return this.http.post<any>(this.api + 'application/updateEtape', formData3, { 'headers': this.reqHelper.getReqOptions("update_application") });
  }
  getDetailApp(id) {
    return this.http.get<any>(this.api + 'application/' + id, { 'headers': this.reqHelper.getReqOptions("view_application") });
  }
  // getFichierLibre() {
  //   return this.http.get<any>(this.api + 'fichierByApp');
  // }
  getFichierLibre() {
    return this.http.get<any>(this.api + 'generateRapportLibre', { 'headers': this.reqHelper.getReqOptions("generateRapportLibre_rapport") });
  }

  getFichierLibreOuSpecifique(id) {
    return this.http.get<any>(this.api + 'fichierByAppOuLibre/' + id, { 'headers': this.reqHelper.getReqOptions("fichierByAppOuLibre_rapport") });
  }
  getFormulaireLibre() {
    return this.http.get<any>(this.api + 'formulairev2/formulaireByApp', { 'headers': this.reqHelper.getReqOptions("formulaireByApp_formulaire") });

  }
  getFormulaireLibreOuSpecifique(id) {
    return this.http.get<any>(this.api + 'formulairev2/formulaireByAppOuLibre/' + id, { 'headers': this.reqHelper.getReqOptions("formulaireByAppOuLibre_formulaire") });

  }
  getWorkflowLibre() {
    return this.http.get<any>(this.api + 'WorkflowByApp', { 'headers': this.reqHelper.getReqOptions("WorkflowByApp_workflow") });

  }
  getWorkflowLibreOuSpecifique(id) {
    return this.http.get<any>(this.api + 'WorkflowByAppOuLibre/' + id, { 'headers': this.reqHelper.getReqOptions("WorkflowByAppOuLibre_workflow") });

  }
  LierWorkflowEtApp(data: any) {
    var formData1: FormData = new FormData();
    formData1.append('idWorkflow', data.idWorkflow);
    formData1.append('idApp', data.idApp);
    return this.http.post<any>(this.api + 'workflowLierApp', formData1, { 'headers': this.reqHelper.getReqOptions("workflowLierApp_workflow") });

  }
  EnleverliaisonWorkflowEtApp(idWorkflow) {
    return this.http.get<any>(this.api + 'workflowLierApp/enlever/' + idWorkflow, { 'headers': this.reqHelper.getReqOptions("workflowLierApp_enlever_workflow") });

  }

  LierFormulaireEtApp(data: any) {
    var formData2: FormData = new FormData();
    formData2.append('idFormulaire', data.idFormulaire);
    formData2.append('idApp', data.idApp);
    return this.http.post<any>(this.api + 'formulairev2/formulaireLierApp', formData2, { 'headers': this.reqHelper.getReqOptions("formulaireLierApp_formulaire") });

  }
  EnleverliaisonFormulaireEtApp(idFormulaire) {
    return this.http.get<any>(this.api + 'formulairev2/formulaireLierApp/enlever/' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("formulaireLierApp_enlever_formulaire") });

  }

  LierFichierEtApp(data: any) {
    var formData3: FormData = new FormData();
    formData3.append('idFichier', data.idFichier);
    formData3.append('idApp', data.idApp);
    return this.http.post<any>(this.api + 'fichierLierApp', formData3, { 'headers': this.reqHelper.getReqOptions("fichierLierApp_fichier") });
  }

  EnleverliaisonFichierEtApp(idFichier) {
    return this.http.get<any>(this.api + 'fichierLierApp/enlever/' + idFichier, { 'headers': this.reqHelper.getReqOptions("fichierLierApp_enlever_fichier") });

  }
  EnleverliaisonTableLiaison(id) {
    return this.http.get<any>(this.api + 'enleverLiaisonApp/enlever/' + id, { 'headers': this.reqHelper.getReqOptions("enleverLiaisonApp_enlever_fichier") });

  }



  zipper(id) {
    return this.http.get(this.api + 'zipper/' + id, { 'headers': this.reqHelper.getReqOptions("zipper_application") });
  }
  /*downloadFile(id): any{
    return this.http.get(this.api + 'zipper/'+id, {responseType: 'blob'});
   }*/
  downloadFile(id): Observable<HttpResponse<any>> {
    return this.http.get(this.api + 'download/' + id, {
      observe: 'response',
      responseType: 'blob',
      'headers': this.reqHelper.getReqOptions("download_application")
    });

  }
   //application
   addApplication(application: Application) {
    return this.http.post<any>(this.api + 'application/create', application , { 'headers': this.reqHelper.getReqOptions("create_application")});
  }
  listeApplication() {
    return this.http.get<any>(this.api + 'application/list', { 'headers': this.reqHelper.getReqOptions("list_application")});
  }
  deleteApplication(application: Application) {
    return this.http.post<any>(this.api + 'application/delete', application, { 'headers': this.reqHelper.getReqOptions("delete_application")});
  }
  //module
  addModule(module: Module) {
    return this.http.post<any>(this.api + 'module', module, { 'headers': this.reqHelper.getReqOptions("create_module")});
  }
  listeModule() {
    return this.http.get<any>(this.api + 'module', { 'headers': this.reqHelper.getReqOptions("list_module")});
  }
  deleteModule(module: Module){
    return this.http.post<any>(this.api + 'module/delete',module,{ 'headers': this.reqHelper.getReqOptions("delete_module")});
  }
  // listeModuleByApp(idApp) {
  //   return this.http.get<any>(this.api + 'module/'+idApp);
  // }
  listeModuleByApp(id) {
    return this.http.get<any>(this.api + 'get/moduleByAppId/' + id , { 'headers': this.reqHelper.getReqOptions("moduleByAppId_module")});
  }
  listeFormulaire() {
    return this.http.get<any>(this.api + 'formulaire/list', { 'headers': this.reqHelper.getReqOptions("list_formulaire")});
  }
  listFormulaireByApp(id) {
    return this.http.get<any>(this.api + 'formulaire/formulaireByAppId/' + id, { 'headers': this.reqHelper.getReqOptions("formulaireByAppId_formulaire")});
  }
  listeFormulaireNotgenerer() {
    return this.http.get<any>(this.api + 'formulaire/notgenerateform', { 'headers': this.reqHelper.getReqOptions("notgenerateform_formulaire")});
  }
  nbrApplication() {
    return this.http.get<any>(this.api + 'utilisateur/nombreApplication', { 'headers': this.reqHelper.getReqOptions("nombreApplication_utilisateur")});
  }
  nbrModule() {
    return this.http.get<any>(this.api + 'utilisateur/nombreModule', { 'headers': this.reqHelper.getReqOptions("nombreModule_utilisateur")});
  }
  nbrFichier() {
    return this.http.get<any>(this.api + 'fichier/nombreFichier', { 'headers': this.reqHelper.getReqOptions("nombreFichier_fichier")});
  }
  nbrFormulaire() {
    return this.http.get<any>(this.api + 'formulaire/nombreFormulaire', { 'headers': this.reqHelper.getReqOptions("nombreFormulaire_formulaire")});
  }
  nbrWorkflow() {
    return this.http.get<any>(this.api + 'workflow/nombreWorkflow', { 'headers': this.reqHelper.getReqOptions("nombreWorkflow_workflow")});
  }


  //publication application
   publierApp(appId,appUrl,file){
    const formData = new FormData();
    formData.append('appId', appId);
    formData.append('appUrl', appUrl);
    formData.append('appImg',file);
    return this.http.post<any>(this.api + 'application/publier', formData);
   }

   //application depublier
   depublierApp(app){
     return this.http.post<any>(this.api + 'application/depublier',app );
   }

   // liste application publier
   listeAppPubliee(){
     return this.http.get<any>(this.api + "application/publicationliste");
   }
   listeAppPubliee2(){
    return this.http.get<any>(this.api + "application/publicationliste2");
  }

}
