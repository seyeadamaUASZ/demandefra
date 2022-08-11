import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { Parametre } from '../models/parametre';
import { LandingPage } from '../models/LandingPage';

@Injectable({
  providedIn: 'root'
})
export class ParametreService {
  api_base = environment.apii;
  constructor(private http: HttpClient) { }


  getTheme() {
    return this.http.get<any>(this.api_base + 'theme');
  }
  getLangue() {
    return this.http.get<any>(this.api_base + 'langue');
  }
  getListSecteur() {
    return this.http.get<any>(this.api_base + 'get/secteur');
  }
  deleteSecteur(secteur: any) {
    return this.http.post<any>(this.api_base + 'delete/secteur', secteur);
  }
  updateLangueUser(user: User) {
    return this.http.post<any>(this.api_base + 'langue/update', user);
  }
  updateParametre(parametre: Parametre) {
    //alert("---------------updateParametre----------"+JSON.stringify(parametre));
    return this.http.post<any>(this.api_base + 'parametre/update', parametre);
  }
  getDefautParametre() {
    return this.http.get<any>(this.api_base + 'parametre');
  }

  uplaodImage(formData: FormData) {
    //alert(this.api_base+ 'upload');
    return this.http.post<any>(this.api_base + 'upload', formData)
  }
  // chargerImageLanding(landing: LandingPage) {
  //   //alert(this.api_base+ 'upload');
  //   return this.http.post<any>(this.api_base + 'landing/create', landing)
  // }
  chargerImageLanding(formData: FormData) {
    // const formData = new FormData()
    // Object.keys(data).map((key) => {
    //   formData.append(key, data[key][0])
    // });
    return this.http.post(this.api_base + 'landing/create', formData)
  }

  
  getImageLanding() {
   let username;
    //alert(this.api_base+ 'upload');
    return this.http.get<any>(this.api_base + 'landing/get')
  }
  getImage(imageName) {
    return this.http.get<any>(this.api_base + 'get/' + imageName)
  }
  getTypeConnexion(){
    return this.http.get<any>(this.api_base + 'typeConnexion/list' )

  }
  updateTypeConnexion(typeConnexion){
    return this.http.post<any>(this.api_base + 'typeConnexion/update',typeConnexion )

  }
  getPartenaire() {
    let username;
     //alert(this.api_base+ 'upload');
     return this.http.get<any>(this.api_base + 'partenaire/get')
   }
   chargerPartenaire(formData: FormData) {
     
    return this.http.post(this.api_base + 'partenaire/create', formData)
  }
  deletePartenaire(partenaire: any) {
    // const formData = new FormData()
    // Object.keys(data).map((key) => {
    //   formData.append(key, data[key][0])
    // });
    return this.http.post(this.api_base + 'partenaire/delete', partenaire)
  }

}
