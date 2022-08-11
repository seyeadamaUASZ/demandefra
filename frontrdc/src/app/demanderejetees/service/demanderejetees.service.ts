import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemanderejeteesService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getDemandeRejetees(owner) {
    return this.http.get(this.api+"demandeautfra/demanderejetees/"+owner);
  }
}
