import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class DemanderenvoyeesService {
    api = environment.apii;

    constructor(private http: HttpClient) { }

    getDemandeRejetees() {
      return this.http.get(this.api + "demandeautfra/rejetees");
    }

    getDemandeRenvoyees() {
      return this.http.get(this.api + "demandeautfra/renvoyees");
    }

    listDemandeRenvoyees(owner){
      return this.http.get(this.api+'motifrejetourenvoi/rejetee/'+owner)
    }
  } 