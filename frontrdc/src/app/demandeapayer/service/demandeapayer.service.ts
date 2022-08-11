import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeapayerService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getDemandeAPayer(owner) {
    return this.http.get(this.api+"demandeautfra/apayer/"+owner);
  }
}
