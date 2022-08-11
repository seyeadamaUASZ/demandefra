import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeacorrigerService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getDemandeACorriger(owner) {
    return this.http.get(this.api+"demandeautfra/demandeacorriger/"+owner);
  }
}
