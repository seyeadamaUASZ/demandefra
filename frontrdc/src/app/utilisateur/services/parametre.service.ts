import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { Parametre } from '../models/parametre';
import { Observable } from 'rxjs';
import { from } from 'rxjs';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';

@Injectable({
  providedIn: 'root'
})
export class ParametreService {
  api_base = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }
  getTheme() {
    return this.http.get<any>(this.api_base + 'theme/list');
  }
  getLangue() {
    return this.http.get<any>(this.api_base + 'langue');
  }
  updateLangueUser(user: User) {
    return this.http.post<any>(this.api_base + 'langue/update', user);
  }
  updateParametre(parametre: Parametre) {
    //alert("---------------updateParametre----------"+JSON.stringify(parametre));
    return this.http.post<any>(this.api_base + 'parametre/update', parametre);
  }
  updateParametreSansfile(parametre: Parametre) {
    //alert("---------------updateParametre----------"+JSON.stringify(parametre));
    return this.http.post<any>(this.api_base + 'parametre/updatee', parametre);
  }
  getDefautParametre() {
    return this.http.get<any>(this.api_base + 'parametre');
  }

  uplaodImage(formData: FormData) {
    //alert(this.api_base+ 'upload');
    return this.http.post<any>(this.api_base + 'upload/', formData)
  }
  getImage(imageName) {
    return this.http.get<any>(this.api_base + 'get/' + imageName)
  }
  getImageLanding() {
    //alert(this.api_base+ 'upload');
    return this.http.get<any>(this.api_base + 'landing/get')
  }
  getTypeConnexion(){
    return this.http.get<any>(this.api_base + 'typeConnexion/list' )

  }
  getPartenaire() {
    let username;
     //alert(this.api_base+ 'upload');
     return this.http.get<any>(this.api_base + 'partenaire/get')
   }
}
