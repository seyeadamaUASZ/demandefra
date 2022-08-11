import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Profil } from '../models/profil';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';


@Injectable({
  providedIn: 'root'
})
export class PrivilegeService {
  api = environment.apii;
  constructor(private http: HttpClient , private reqHelper: HttpRequestHelper) { }

  listPriveleges(p:Profil) {
    return this.http.post<any>(this.api + 'privilege/privilegebyprofil', p, { 'headers': this.reqHelper.getReqOptions("privilegebyprofil_privilege")});
  }

  allocatePriveleges(p:Profil, removed:any[], added:any[]) {
    //let formData = new FormData();
    //formData.append("removed", removed);
    //formData.append("added", added);
    let body = {
      "profil":p,
      "removed": removed,
      "added": added
    }
    let profil = {
      "proId":p.proId
    }
    let formData = new FormData();
    formData.append("profil", JSON.stringify(profil));
    formData.append("removed", JSON.stringify(removed));
    formData.append("added", JSON.stringify(added));
    return this.http.post<any>(this.api + 'privilege/allocateprivilege', formData, { 'headers': this.reqHelper.getReqOptions("allocateprivilege_privilege")});
  }

}
