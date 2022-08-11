import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeService {
  apii = environment.apii
  constructor(private http:HttpClient) { }

  listDemandeValidees(owner){
    return this.http.get(this.apii+'motifrejetvalide/valide/'+owner)
  }
}
