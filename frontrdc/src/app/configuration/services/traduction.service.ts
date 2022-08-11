import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TranslateLoader } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TraductionService {

  constructor(private http: HttpClient) { }
  
  readonly api = environment.apii;
  
  addTraduction(traduction){
    return this.http.post<any>(this.api+'traduction/create',traduction);
  }

  updateTraduction(traduction){
    return this.http.post<any>(this.api+'traduction/update',traduction);
  }

  getTraduction(langue){
    return this.http.post<any>(this.api+'traduction/liste',langue);
  }

  deleteTraduction(traduction){
    return this.http.post<any>(this.api+'traduction/delete',traduction);
  }
  
}
