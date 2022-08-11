import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Profile } from '../models/profile';
import { environment } from 'src/environments/environment';
import { Action } from '../models/action';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';


@Injectable({
  providedIn: 'root'
})
export class RoleService {
 api = environment.apii;
 public profilId:number;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }

  listprofils() {
    return this.http.get<any>(this.api + 'profil/list', { 'headers': this.reqHelper.getReqOptions("list_profil")});
  }

  updateProfil(profil: Profile) {
    return this.http.post<any>(this.api + 'profil/update', profil,{ 'headers': this.reqHelper.getReqOptions("edit_profil")});
  }
  createProfil(profile: Profile) {
    return this.http.post<any>(this.api + 'profil/create', profile,{ 'headers': this.reqHelper.getReqOptions("create_profil")});
  }

  deleteProfile(profil: Profile) {
    return this.http.post<any>(this.api + 'profil/delete', profil,{ 'headers': this.reqHelper.getReqOptions("delete_profil")});
  }
  //action
  listAction() {
    return this.http.get<any>(this.api + 'action/list',{ 'headers': this.reqHelper.getReqOptions("list_action")});
  }

  updateAction(action: Action) {
    return this.http.post<any>(this.api + 'action/update', action,{ 'headers': this.reqHelper.getReqOptions("edit_action")});
  }
  createAction(action: Action) {
    return this.http.post<any>(this.api + 'action/create', action,{ 'headers': this.reqHelper.getReqOptions("create_action")});
  }

  deleteAction(action: Action) {
    return this.http.post<any>(this.api + 'action/delete', action,{ 'headers': this.reqHelper.getReqOptions("delete_action")});
  }
}
