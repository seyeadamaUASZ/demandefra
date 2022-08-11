import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Champs } from '../model/champs';
import { User } from 'src/app/utilisateur/models/user';
import { environment } from 'src/environments/environment';
import { Formulaire } from '../model/formulaire';
import { Profile } from 'src/app/utilisateur/models/profile';
import { Menu } from 'src/app/utilisateur/models/menu';
import { Application } from 'src/app/application/models/application';
import { Module } from 'src/app/parametrage/models/module';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';

@Injectable({
  providedIn: 'root'
})
export class FormulaireServiceService {
  api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }
  getUsers() {
    return this.http.get<any>(this.api + 'utilisateur/list', { 'headers': this.reqHelper.getReqOptions("list_utilisateur")});
  }

  registreUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/create/', user, { 'headers': this.reqHelper.getReqOptions("create_utilisateur")});

  }


  chargerFile(formData: FormData) {
    return this.http.post<any>(this.api + 'utilisateur/upload/', formData, { 'headers': this.reqHelper.getReqOptions("uploadfile_utilisateur")});
  }

  listprofil() {
    return this.http.get<any>(this.api + 'profil/list', { 'headers': this.reqHelper.getReqOptions("list_profil")});
  }

  listItem() {
    return this.http.get<any>(this.api + 'menu', { 'headers': this.reqHelper.getReqOptions("list_menu")});
  }
  createItem(menu: Menu) {
    return this.http.post<any>(this.api + 'menu/create/', menu, { 'headers': this.reqHelper.getReqOptions("create_menu")});
  }

  createUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/create', user , { 'headers': this.reqHelper.getReqOptions("create_utilisateur") });
  }

  updateUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/update', user, { 'headers': this.reqHelper.getReqOptions("edit_utilisateur") });
  }
  updateProfil(profil: Profile) {
    return this.http.post<any>(this.api + 'profil/update', profil, { 'headers': this.reqHelper.getReqOptions("edit_profil") });
  }
  createProfil(profile: Profile) {
    return this.http.post<any>(this.api + 'profil/create', profile, { 'headers': this.reqHelper.getReqOptions("create_profil") });
  }
  deleteProfile(profil: Profile) {
    return this.http.post<any>(this.api + 'profil/delete', profil , { 'headers': this.reqHelper.getReqOptions("delete_profil") });
  }
  recovermdp(data) {
    return this.http.post<any>(this.api + 'utilisateur/recover', data , { 'headers': this.reqHelper.getReqOptions("recover_utilisateur") });
  }
  detailUser(username: string) {
    let formdata: FormData = new FormData();
    formdata.append('username', username);
    // const body = {username: username};
    return this.http.post<any>(this.api + 'utilisateur/detail', formdata, { 'headers': this.reqHelper.getReqOptions("view_utilisateur") });
  }

  deleteUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/delete', user , { 'headers': this.reqHelper.getReqOptions("delete_utilisateur") });
  }


  reinitUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/reinitialisation', user, { 'headers': this.reqHelper.getReqOptions("reinitialisation_utilisateur") });
  }

  listeUser() {
    return this.http.get<any>(this.api + 'utilisateur/list', { 'headers': this.reqHelper.getReqOptions("list_utilisateur") });
  }

  changepwd(data: any) {
    // tslint:disable-next-line:prefer-const
    let formData: FormData = new FormData();
    formData.append('username', data.username);
    formData.append('oldpwd', data.oldPwd);
    formData.append('newpwd', data.newPwd);
    return this.http.post<any>(this.api + 'utilisateur/changerpwd', formData , { 'headers': this.reqHelper.getReqOptions("changerpwd_utilisateur") });
  }



  //service activer compte utilisateur
  activer(id) {
    return this.http.get<any>(this.api + 'utilisateur/actived/' + id , { 'headers': this.reqHelper.getReqOptions("actived_utilisateur") });
  }
  //service desactiver compte utilisateur
  desactiver(id) {
    return this.http.get<any>(this.api + 'utilisateur/desactived/' + id , { 'headers': this.reqHelper.getReqOptions("desactived_utilisateur") });
  }
  desactiverByusername(username) {
    return this.http.get<any>(this.api + 'utilisateur/bloquer/' + username , { 'headers': this.reqHelper.getReqOptions("bloquer_utilisateur") });
  }
   nbrIntegrateur() {
    return this.http.get<any>(this.api + 'utilisateur/nombreIntegrateur', { 'headers': this.reqHelper.getReqOptions("nombreIntegrateur_utilisateur") });
  }
  nbrAdministrateur() {
    return this.http.get<any>(this.api + 'utilisateur/nombreAdministrateur', { 'headers': this.reqHelper.getReqOptions("nombreAdministrateur_utilisateur")});
  }
  nbrCommerciaux() {
    return this.http.get<any>(this.api + 'utilisateur/nombreCommerciaux', { 'headers': this.reqHelper.getReqOptions("nombreCommerciaux_utilisateur")});
  }
  nbrUtilisateur() {
    return this.http.get<any>(this.api + 'utilisateur/nombreUser', { 'headers': this.reqHelper.getReqOptions("nombreUser_utilisateur")});
  }
  nbrUserConnect() {
    return this.http.get<any>(this.api + 'utilisateur/nombreUserConnect', { 'headers': this.reqHelper.getReqOptions("nombreUserConnect_utilisateur")});
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
  // listeModuleByApp(idApp) {
  //   return this.http.get<any>(this.api + 'module/'+idApp);
  // }
  listeModuleByApp(id) {
    return this.http.get<any>(this.api + 'get/moduleByAppId/' + id , { 'headers': this.reqHelper.getReqOptions("moduleByAppId_module")});
  }
  //Formulaire
  addFormulaire(formulaire: Formulaire) {
    return this.http.post<any>(this.api + 'formulaire/create', formulaire , { 'headers': this.reqHelper.getReqOptions("create_formulaire")});
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
  listeFormulairegenerer() {
    return this.http.get<any>(this.api + 'formulaire/generateform' , { 'headers': this.reqHelper.getReqOptions("generateform_formulaire")});
  }
  validerFormulaire(formulaire: Formulaire) {
    return this.http.post<any>(this.api + 'formulaire/validerfrm', formulaire , { 'headers': this.reqHelper.getReqOptions("validerfrm_formulaire")});
  }
  modeliserFormulaire(formulaire: Formulaire) {
    return this.http.post<any>(this.api + 'formulaire/modeliserfrm', formulaire , { 'headers': this.reqHelper.getReqOptions("modeliserfrm_formulaire")});
  }
  deleteFormulaire(formulaire: Formulaire) {
    return this.http.post<any>(this.api + 'formulaire/delete', formulaire, { 'headers': this.reqHelper.getReqOptions("delete_formulaire")});
  }
  //Champs pour un formulaire
  addChamps(champs: Champs) {
    return this.http.post<any>(this.api + 'champs/create', champs , { 'headers': this.reqHelper.getReqOptions("create_champs")});
  }
  supprimerByForm(idFormulaire) {
    return this.http.get<any>(this.api + 'champs/delete/' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("deleteByIdfrm_champs")});
  }
  listeChamps() {
    return this.http.get<any>(this.api + 'champs/list', { 'headers': this.reqHelper.getReqOptions("list_champs")});
  }
  deleteChamps(champs) {
    return this.http.post<any>(this.api + 'champs/delete',champs, { 'headers': this.reqHelper.getReqOptions("delete_champs")});
  }
  genererFormulaire(nomprojet: any, idFormulaire: any) {
    return this.http.get<any>(this.api + 'composant/' + nomprojet + '/' + idFormulaire,{ 'headers': this.reqHelper.getReqOptions("generersource_formulaire")});
  }
  supprimerFormulaire(nomprojet: any, idFormulaire: any) {
    return this.http.get<any>(this.api + 'supprimer/' + nomprojet + '/' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("deletephysique_formulaire")});
  }
  champsByForm(idFormulaire) {
    return this.http.get<any>(this.api + 'champs/champsByForm/' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("champsByForm_champs")});
  }
  fieldChampsByForm(idFormulaire) {
    return this.http.get<any>(this.api + 'champs/fieldByForm/' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("fieldByForm_champs")});
  }
  fieldValueChamps(idChamps) {
    return this.http.get<any>(this.api + 'value/' + idChamps, { 'headers': this.reqHelper.getReqOptions("valueId_value")});
  }
  buttonChampsByForm(idFormulaire) {
    return this.http.get<any>(this.api + 'buttonByForm' + idFormulaire, { 'headers': this.reqHelper.getReqOptions("buttonByFrom_champs")});
  }

  // value pour les champs radio et select
  listeValue() {
    return this.http.get<any>(this.api + 'value/list', { 'headers': this.reqHelper.getReqOptions("list_value")});
  }
  addValue(data) {
    return this.http.post<any>(this.api + 'value/create', data, { 'headers': this.reqHelper.getReqOptions("create_value")});
  }
  valueByChamps(id) {
    return this.http.get<any>(this.api + 'valueByChamps/' + id, { 'headers': this.reqHelper.getReqOptions("valueByChamps_value")});
  }
  suppressionValueByIdCh(id){
    return this.http.get<any>(this.api + 'value/deletebyidch/' + id, { 'headers': this.reqHelper.getReqOptions("deletebyidch_value")});
  }
  getParamUser(username) {
    return this.http.get<any>(this.api + 'utilisateur/get/' + username, { 'headers': this.reqHelper.getReqOptions("get_utilisateur")});
  }
  updateLangueUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/updateLangue', user, { 'headers': this.reqHelper.getReqOptions("updateLangue_utilisateur")});
  }
  updateThemeUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/updateTheme', user, { 'headers': this.reqHelper.getReqOptions("updateTheme_utilisateur")});
  }
  getIcone() {
    return this.http.get<any>(this.api + 'icone/list', { 'headers': this.reqHelper.getReqOptions("list_icone")});
  }
 getTable(){
  return this.http.get<any>(this.api + 'gettable', { 'headers': this.reqHelper.getReqOptions("list_table")});
 }
 getColonne(table){
  return this.http.get<any>(this.api + 'getcolonne/'+table, { 'headers': this.reqHelper.getReqOptions("list_colonne")});
 }

}

