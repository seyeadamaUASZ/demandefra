import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient,private reqHelper: HttpRequestHelper) { }
  api = environment.apii;
  public isAuthenticated() {
    const token = localStorage.getItem('token');
    // Check whether the token is expired and return
    // true or false
    return !this.jwtHelper.isTokenExpired(token);
  }

  login(username: string, password: string):Observable<any>{
    const body = { username: username, password: password };
    const bearer = btoa(username + ':' + password);
    const headers = new HttpHeaders({ 'Authorization': 'Basic ' + bearer });
    return this.http.post<any>(this.api + 'auth', body);
  }
  VerifierConnexion(username:string){
    return this.http.get<any>(this.api + 'users/verifySession/' + username,{ 'headers': this.reqHelper.getReqOptions("verifySession_utilisateur")});
  }

  getMenus() {
    return this.http.get<any>(this.api + 'menu',{ 'headers': this.reqHelper.getReqOptions("getmenu_menu")});
  }

  getMenusProfil(p) {
    //alert("-------------getMenusProfil--------------"+JSON.stringify(p));
    return this.http.get<any>(this.api + 'menu/profil/'+p,{ 'headers': this.reqHelper.getReqOptions("menuprofil_menu")});
  }

  deconnecter(id: string) {
    return this.http.get<any>(this.api + 'disconnect/logout/' + id,{ 'headers': this.reqHelper.getReqOptions("logout_utilisateur")});

  }

}

