import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeatraiterService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getDemandeATraiter() {
    return this.http.get(this.api + "demandeautfra/atraiterchefdivision");
  }
}
