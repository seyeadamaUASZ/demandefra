import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PasswordRecover } from '../../utilisateur/models/passwordRecover';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
 

@Injectable({
  providedIn: 'root'
})
export class ResetpwdService {
 api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }


 sendToken(utiEmail: PasswordRecover) {
    return this.http.post<any>(this.api + 'utilisateur/recover', utiEmail);
  }
  resetPassword(resetpwd: PasswordRecover) {
    return this.http.post<any>(this.api + 'utilisateur/resetpwd', resetpwd, { 'headers': this.reqHelper.getReqOptions("resetpwd_utilisateur") });
  }
 verifToken(token) {
    return this.http.get<any>(this.api + 'utilisateur/veriftoken/'+token);
  }

}