
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PasswordRecover } from '../../utilisateur/models/passwordRecover';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { Inscription } from '../model/inscription';

@Injectable({
  providedIn: 'root'
})
export class InscriptionService {
  api = environment.apii;

  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }


  inscriptionUtilisateur(inscription: Inscription) {   
    return this.http.post<any>(this.api + 'inscription/create', inscription, 
    { 'headers': this.reqHelper.getReqOptions("create_inscription")});

  }

  desinscrire(data: any, token: any) {  
    let formData: FormData = new FormData();
    formData.append('username', data.username);
    formData.append('oldpwd', data.oldPwd);  
    return this.http.post<any>(this.api + 'inscription/desinscrire', formData , { 'headers':  this.reqHelper.getReqOptionsWithToken("desinscrire_user",token)});
  }
}
