import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RapportanalyseService {
    api = environment.apii;
    constructor(private http: HttpClient) { }

    createRapportanalyse(data, analyse) {
        const formData = new FormData();
        formData.append('rapportAnalyse', JSON.stringify(data));
        formData.append('analyse', analyse)
        return this.http.post(this.api + "rapportanalyse/create", formData);
    }
    
    getAllRapportanalyse() {
        return this.http.get(this.api + "rapportanalyse/list");
    }

    getOneRapportAnalyse(id) {
        return this.http.get(this.api+"rapportanalyse/"+id);
    }

    getRapportAnalayseParProduit(idProduit) {
        return this.http.get(this.api+"rapportanalyse/produit/"+idProduit);
    }

    getRapportAnalayseSuivi(idProduit) {
        return this.http.get(this.api+"rapportanalyse/produits/"+idProduit);
    }

    addCleSignature(data) {
        const formData = new FormData();
        formData.append('signature', JSON.stringify(data));
        return this.http.post(this.api + "signature/create", formData);
    }

    verificationCodePin(traitement:any){
        let formData:FormData = new FormData();
        formData.append('codePin', traitement.codePin);
        formData.append('owner', traitement.utilisateur)
        return this.http.post(this.api + "signature/verification", formData);
    }
}