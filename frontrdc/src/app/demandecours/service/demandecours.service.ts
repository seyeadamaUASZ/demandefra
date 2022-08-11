import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandecoursService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getDemandeEnCours(owner) {
    return this.http.get(this.api+"demandeautfra/demandeencours/"+owner);
  }
}
