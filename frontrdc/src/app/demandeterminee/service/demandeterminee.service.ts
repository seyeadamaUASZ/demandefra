import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandetermineeService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getDemandeTerminee() {
    return this.http.get(this.api + "demandeautfra/demandeautfraterminee");
  }
}
