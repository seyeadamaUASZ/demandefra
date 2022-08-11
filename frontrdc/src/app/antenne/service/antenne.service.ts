import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class AntenneService {
    api = environment.apii;

    constructor(private http: HttpClient) { }

    createAntenne(data) {
      return this.http.post(this.api+"antenneRegionaleDepartementale/create",data);
    }

    getAllAntenne(){
      return this.http.get(this.api+"antenneRegionaleDepartementale/list")
    }
  
    updateAntenne(id,data){
      return this.http.post(this.api+'antenneRegionaleDepartementale/update/'+id,data)
    }
  
    deleteAntenne(element){
      return this.http.post(this.api+'antenneRegionaleDepartementale/delete',element)
    }
  } 