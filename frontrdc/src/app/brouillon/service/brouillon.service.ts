import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BrouillonService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getDemandeEnBrouillon(owner) {
    return this.http.get(this.api+"demandeautfra/brouillon/"+owner);
  }
}
