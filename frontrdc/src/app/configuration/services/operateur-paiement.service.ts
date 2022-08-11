import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class OperateurPaiementService {
  readonly api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }

  getOperateurPaiement(){
    return this.http.get<any>(this.api + 'operateurPaiement/list');
  }
  CreateOperateurPaiement(operateurPaiement:any){
    return this.http.post<any>(this.api + 'operateurPaiement/create',operateurPaiement);
  }
  ActiverOperateur(idOpa){
    return this.http.get<any>(this.api + 'operateurPaiement/activer/'+idOpa);
  }
  DesactiverOperateur(idOpa){
    return this.http.get<any>(this.api + 'operateurPaiement/desactiver/'+idOpa);
  }
 
}
