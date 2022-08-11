import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class GenerateurSService {
  api = environment.apii;
  constructor(private http:HttpClient) { }


  getAllGenerateurOtp(){
    return this.http.get<any>(this.api+'otp/list')
  }

  addParametre(data:any){
    return this.http.post<any>(this.api+'otp/addParametre',data)
  }


  deleteParametre(id){
    return this.http.get(this.api+'otp/deleteparametre/'+id)
  }

  modifierParametre(id,data){
    return this.http.post<any>(this.api+'otp/update/'+id,data)
  }

  generatecodeOtp(type){
    return this.http.get<any>(this.api+'generatecodeotp/'+type);
  }

  envoyercodeOtp(type,numero){
    return this.http.get<any>(this.api+'envoyercodeotp/'+type+'/'+numero);
  }

  envoyerCodeOtp1(libelle,numero){
    return this.http.get<any>(this.api+'envoyercodeByLibelle/'+libelle+'/'+numero);
  }


  verifiercode(code){
    return this.http.get<any>(this.api+'verificationCode/'+code);
  }

  deleteParametreByLibelle(libelle){
    return this.http.get<any>(this.api+'otp/deleteparametrebyLibelle/'+libelle);
  }
}
