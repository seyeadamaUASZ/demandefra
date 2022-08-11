import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeautfradelivreproService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getDemandeautfraprovisoire() {
    return this.http.get(this.api + "demandeautfra/demandeautfraprovisoire");
  }
}
