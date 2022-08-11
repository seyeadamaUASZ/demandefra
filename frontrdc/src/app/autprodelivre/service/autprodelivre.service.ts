import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AutprodelivreService {
  api = environment.apii;

  constructor(private http:HttpClient) { }

  getAutProDelivres(owner) {
    return this.http.get(this.api+"demandeautfra/autfraprodelivre/"+owner);
  }
}
