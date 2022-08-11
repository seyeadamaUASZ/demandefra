import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Formulairev2Service {
  readonly api = environment.apii;
  constructor(private http: HttpClient,private reqHelper:HttpRequestHelper) { }

  getIcone() {
    return this.http.get<any>(this.api + 'icone/list', { 'headers': this.reqHelper.getReqOptions("list_icone")});
  }
 getTable(){
  return this.http.get<any>(this.api + 'gettable', { 'headers': this.reqHelper.getReqOptions("list_table")});
 }
 getColonne(table){
  return this.http.get<any>(this.api + 'getcolonne/'+table, { 'headers': this.reqHelper.getReqOptions("list_colonne")});
 }

 saveForm(form){
   return this.http.post<any>(this.api + 'formulairev2/create',form);
 }

 getById(id){
   return this.http.get<any>(this.api + 'formulairev2/get/'+id);
 }

 getList(){
   return this.http.get<any>(this.api+'formulairev2/liste');
 }

 deleteForm(form){
   return this.http.post<any>(this.api + 'formulairev2/delete/form',form);
 }
 deleteSteps(step){
   return this.http.post<any>(this.api + 'formulairev2/delete/step',step);
 }
 deleteChamps(champs){
   return this.http.post<any>(this.api + 'formulairev2/delete/champs',champs);
 }

 deleteChampsValues(champsvalues){
   return this.http.post<any>(this.api+'formulairev2/delete/champsvalue',champsvalues);
 }



}
