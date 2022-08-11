import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class ProfilService{
  api = environment.apii;
  constructor(private http: HttpClient) { }

  listeProfils () {
    return this.http.get<any>(this.api + 'profil/list');
  }

}