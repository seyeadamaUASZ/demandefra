import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Payeur } from '../models/payeur';

@Injectable({
  providedIn: 'root'
})
export class PaiementService {

  api = environment.apii;
  apiOrbus = environment.Orbus;
  constructor(private http: HttpClient) { }
  getListPaiement() {
    return this.http.get<any>(this.api + 'paiement/list');
  }
  getListPaiementByUser(username:any) {
    return this.http.get<any>(this.api + 'paiement/list/'+username);
  }
  getPaiementByReferencePaiement(reference) {
    return this.http.get<any>(this.api + 'paiement/referencePaiement/' + reference);
  }
//operateur de paiement
  getOperateurPaiement(){
    return this.http.get<any>(this.api + 'operateurPaiement/list');
  }
  CreateOperateurPaiement(operateurPaiement:any){
    return this.http.post<any>(this.api + 'operateurPaiement/create',operateurPaiement);
  }
   payer(objet:Payeur) {
    //const objet = {"idFacture":"500","montantFacture":"200","nomPayeur":"youga","prenomPayeur":"Dieng"};
     return this.http.post<any>(this.api + 'moyenPaiement/crypter',objet );
   }
   getNotification(objet:any) {
     return this.http.post<any>(this.api + 'paiement/notification',objet);
   }

   getMessageSucces(idFacture: number) {
    return this.http.get(this.api + "success/" + idFacture);
  }
}
