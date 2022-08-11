import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MesdemandeaccepteesService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getMesDemandesAcceptees(email) {
    return this.http.get(this.api + "demandeautfra/mesdemandesacceptees/"+email);
  }
}
