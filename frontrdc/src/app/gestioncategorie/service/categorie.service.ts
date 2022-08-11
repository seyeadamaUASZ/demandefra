import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategorieService {
  api = environment.apii;
  constructor(private http:HttpClient) { }
  
  allCategories(){
    return this.http.get(this.api+'categorie/list')
  }

  addCategorie(data){
    return this.http.post(this.api+'categorie/create',data)
  }

  updateCategorie(id,data){
    return this.http.post(this.api+'categorie/update/'+id,data)
  }

  deleteCategorie(element){
    return this.http.post(this.api+'categorie/delete',element)
  }
}
