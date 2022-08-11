import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { MonCompte } from '../models/mon-compte';
import { User } from 'src/app/utilisateur/models/user';

@Injectable({
  providedIn: 'root'
})
export class MonCompteService {
  api = environment.apii;
  constructor(private http: HttpClient) { }
  
  infoCompte() {
    return this.http.get<any>(this.api + 'utilisateur/infos');
  }

  updateCompte(compte: MonCompte) {
    return this.http.post<any>(this.api + 'utilisateur/updateinfos', compte);
  }

  // updateLogoUser(logo: any) {
    // const formData = new FormData();
    // formData.append('logo', logo);
  //   return this.http.post<any>(this.api + 'users/updatelogo', logo);
  // }

  updateLogoUser(formData:FormData){
    return this.http.post<any>(this.api+ 'utilisateur/updatelogo', formData)
  }
}
