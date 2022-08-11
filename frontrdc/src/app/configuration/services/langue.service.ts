import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LangueService {
  readonly api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }

  getLangue(){
    return this.http.get<any>(this.api + 'langue');
  }

  addLangue(langue){
    return this.http.post<any>(this.api + 'langue',langue);
  }

  deleteLangue(langue){
    return this.http.post<any>(this.api + 'langue/delete',langue);
  }

  updateLangue(langue){
    return this.http.post<any>(this.api + 'langue/update', langue);
  }
  
}
