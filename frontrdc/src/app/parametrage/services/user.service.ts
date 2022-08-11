import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { Menu } from '../models/menu';
import { environment } from 'src/environments/environment';
import { Profil } from '../models/profil';
import { Application } from '../models/application';
import { Module } from '../models/module';
import { Formulaire } from '../models/formulaire';
import { Champs } from '../models/champs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  api = environment.apii;
  constructor(private http: HttpClient) { }
  getUsers() {
    return this.http.get<any>(this.api + 'utilisateur/list');
  }
  registreUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/create/', user);

  }
  nbrFichier() {
    return this.http.get<any>(this.api + 'fichier/nombreFichier');
  }
  nbrFormulaire() {
    return this.http.get<any>(this.api + 'formulaire/nombreFormulaire');
  }
  nbrWorkflow() {
    return this.http.get<any>(this.api + 'workflow/nombreWorkflow');
  }


  chargerFile(formData: FormData){
  return this.http.post<any>(this.api + 'utilisateur/upload/', formData);
}

  listprofil() {
    return this.http.get<any>(this.api + 'profil/list');
  }

  listItem() {
    return this.http.get<any>(this.api + 'menu');
  }
  createItem(menu: Menu) {
    return this.http.post<any>(this.api + 'menu/create/', menu);
  }

  createUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur', user);
  }

  updateUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/update', user);
  }
  updateProfil(profil: Profil) {
    return this.http.post<any>(this.api + 'profil/update', profil);
  }
  createProfil(profil: Profil) {
    return this.http.post<any>(this.api + 'profil/create', profil);

  }
  recovermdp(data){
    return this.http.post<any>(this.api + 'utilisateur/recover', data);
  }
  detailUser(username: string) {
    let formdata: FormData = new FormData();
    formdata.append('username', username);
    // const body = {username: username};
    return this.http.post<any>(this.api + 'utilisateur/detail', formdata);
  }

  deleteUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/delete', user);
  }
  deleteProfile(profil: Profil) {
    return this.http.post<any>(this.api + 'profil/delete', profil);
  }

  reinitUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/reinitialisation', user);
  }
  listeUser() {
    return this.http.get<any>(this.api + 'utilisateur/list');
    //  return this.http.get<any>(this.api_base + 'assets/users.json');

  }

  changepwd(data: any) {
    // tslint:disable-next-line:prefer-const
    let formData: FormData = new FormData();
    formData.append('username', data.username);
    formData.append('oldpwd', data.oldPwd);
    formData.append('newpwd', data.newPwd);
    return this.http.post<any>(this.api + 'utilisateur/changerpwd', formData);
  }



  //service activer compte utilisateur
  activer(id){
    return this.http.get<any>(this.api + 'utilisateur/actived/'+id);
  }
  //service desactiver compte utilisateur
  desactiver(id){
    return this.http.get<any>(this.api + 'utilisateur/desactived/'+id);
  }  nbrIntegrateur(){
    return this.http.get<any>(this.api + 'utilisateur/nombreIntegrateur');
  }
  nbrAdministrateur(){
    return this.http.get<any>(this.api + 'utilisateur/nombreAdministrateur');
  }
  nbrCommerciaux(){
    return this.http.get<any>(this.api + 'utilisateur/nombreCommerciaux');
  }
  nbrUtilisateur(){
    return this.http.get<any>(this.api + 'utilisateur/nombreUser');
  }
  nbrUserConnect(){
    return this.http.get<any>(this.api + 'utilisateur/nombreUserConnect');
  }
  nbrApplication(){
    return this.http.get<any>(this.api + 'utilisateur/nombreApplication');
  }
  nbrModule(){
    return this.http.get<any>(this.api + 'utilisateur/nombreModule');
  }
  //application
  addApplication(application:Application) {
    return this.http.post<any>(this.api + 'application', application);
  }
  listeApplication() {
    return this.http.get<any>(this.api + 'applications');
  }
  deleteApplication(application: Application) {
    return this.http.post<any>(this.api + 'application/delete', application);
  }
  
  



  
  
  getParamUser(username){
    return this.http.get<any>(this.api + 'utilisateur/get/'+username);
  }
}
