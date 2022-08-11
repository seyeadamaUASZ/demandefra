import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AutdefdelivreService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getAutDefDelivres(owner) {
    return this.http.get(this.api+"demandeautfra/autfradefdelivre/"+owner);
  }
}
