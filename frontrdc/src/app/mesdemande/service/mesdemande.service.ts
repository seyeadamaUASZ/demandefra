import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MesdemandeService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getMesDemandes(email) {
    return this.http.get(this.api + "demandeautfra/mesdemandes/"+email);
  }
}
