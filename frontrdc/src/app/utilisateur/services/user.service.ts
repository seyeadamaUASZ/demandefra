import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import {HttpRequestHelper} from '../../shared/helpers/httprequest.helper';
import { User } from '../models/user';
import { Menu } from '../models/menu';
import { environment } from 'src/environments/environment';
import { Profile } from '../models/profile';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }
  advancedSearch(data){
    return this.http.post<any>(this.api + 'utilisateur/search/',data);
  }
  getUsers() {
    return this.http.get<any>(this.api + 'utilisateur/list', { 'headers': this.reqHelper.getReqOptions("list_utilisateur")});
  }
  getListUsersConnect() {
    return this.http.get<any>(this.api + 'utilisateur/listuserconnect', { 'headers': this.reqHelper.getReqOptions("list_utilisateur_conn")});
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
  listprofilsInscri() {
    return this.http.get<any>(this.api + 'profil/listInscri', { 'headers': this.reqHelper.getReqOptions("list_profil")});
  }

  listItem() {
    return this.http.get<any>(this.api + 'menu', { 'headers': this.reqHelper.getReqOptions("list_menu")});
  }
  createItem(menu: Menu) {
    return this.http.post<any>(this.api + 'menu/create/', menu, { 'headers': this.reqHelper.getReqOptions("create_menu")});
  }

  createUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/create', user , { 'headers': this.reqHelper.getReqOptions("create_user") });
  }

  updateUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/update', user, { 'headers': this.reqHelper.getReqOptions("edit_user") });
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
    return this.http.post<any>(this.api + 'utilisateur/recover', data , { 'headers': this.reqHelper.getReqOptions("recover_user") });
  }
  detailUser(username: string) {
    let formdata: FormData = new FormData();
    formdata.append('username', username);
    return this.http.post<any>(this.api + 'utilisateur/detail', formdata, { 'headers': this.reqHelper.getReqOptions("view_user") });
  }

  deleteUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/delete', user , { 'headers': this.reqHelper.getReqOptions("delete_user") });
  }

  reinitUser(user: User) {     
    return this.http.post<any>(this.api + 'utilisateur/reinitialisation', user, { 'headers': this.reqHelper.getReqOptions("reinitialisation_user") });
  }

  listeUser() {
    return this.http.get<any>(this.api + 'utilisateur/list', { 'headers': this.reqHelper.getReqOptions("list_user") });
  }

  changepwd(data: any, token: any) {  
    let formData: FormData = new FormData();
    formData.append('username', data.username);
    formData.append('oldpwd', data.oldPwd);
    formData.append('newpwd', data.newPwd);   
    return this.http.post<any>(this.api + 'utilisateur/changerpwd', formData , { 'headers':  this.reqHelper.getReqOptionsWithToken("changerpwd_user",token)});
  }

  activer(id) {
    return this.http.get<any>(this.api + 'utilisateur/actived/' + id , { 'headers': this.reqHelper.getReqOptions("actived_user") });
  }

  desactiver(id) {
    return this.http.get<any>(this.api + 'utilisateur/desactived/' + id , { 'headers': this.reqHelper.getReqOptions("desactived_user") });
  }
  desactiverByusername(username) {
    return this.http.get<any>(this.api + 'utilisateur/bloquer/' + username , { 'headers': this.reqHelper.getReqOptions("bloquer_user") });
  }
 
  nbrUtilisateur() {
    return this.http.get<any>(this.api + 'utilisateur/nombreUser', { 'headers': this.reqHelper.getReqOptions("nombreUser_utilisateur")});
  }
  nbrUserConnect() {
    return this.http.get<any>(this.api + 'utilisateur/nombreUserConnect', { 'headers': this.reqHelper.getReqOptions("nombreUserConnect_utilisateur")});
  }
  getParamUser(username) {
    return this.http.get<any>(this.api + 'utilisateur/get/' + username, { 'headers': this.reqHelper.getReqOptions("get_utilisateur")});
  }
  updateLangueUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/updateLangue', user, { 'headers': this.reqHelper.getReqOptions("updateLangue_utilisateur")});
  }notificationPush
  updateThemeUser(user: User) {
    return this.http.post<any>(this.api + 'utilisateur/updateTheme', user, { 'headers': this.reqHelper.getReqOptions("updateTheme_utilisateur")});
  }
  
  getUserByEmail(email) {
    return this.http.get(this.api + 'utilisateur/email/' + email);
  }

  getUserByUsername(username) {
    return this.http.get(this.api + 'utilisateur/username/' + username);
  }
}
