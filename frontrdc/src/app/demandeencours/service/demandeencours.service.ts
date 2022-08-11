import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class DemandeencoursService {
    api = environment.apii;

    constructor(private http: HttpClient) { }

    getDemandeEnCours() {
        return this.http.get(this.api + "demandeautfra/atraiterchefbureau");
    }
  } 