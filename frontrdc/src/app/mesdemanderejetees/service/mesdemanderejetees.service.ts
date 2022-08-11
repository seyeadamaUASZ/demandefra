import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MesdemanderejeteesService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getMesDemandesRejetees(email) {
    return this.http.get(this.api + "demandeautfra/mesdemandesrejetees/"+email);
  }
}
