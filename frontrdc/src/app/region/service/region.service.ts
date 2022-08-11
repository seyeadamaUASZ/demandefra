import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
  })
  export class RegionService {
    api = environment.apii;

    constructor(private http: HttpClient) { }

    createRegion(data) {
      return this.http.post(this.api+"region/create",data);
    }

    getAllRegion(){
      return this.http.get(this.api+"region/list")
    }

    updateRegion(id,data){
      return this.http.post(this.api+'region/update/'+id,data)
    }
  
    deleteRegion(element){
      return this.http.post(this.api+'region/delete',element)
    }
  } 