import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestHelper {
    //public header: Headers;
    public getReqOptions(action:string){                   
     const headers= new HttpHeaders().set('action', action);
     return headers;
    }

    public getReqOptionsWithToken(action:string,token:any){                   
      const headers= new HttpHeaders().set('action', action).set('Authorization', 'Bearer ' + token);         
      return headers;
    }
}