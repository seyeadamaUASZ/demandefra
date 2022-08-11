import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChefdebureauService {
  api=environment.apii;
  constructor(private http:HttpClient) { }


  updateTaskTraitementChefBureauForm(oldtask:any,traitement:any){
    let formData:FormData = new FormData();
    formData.append('id',oldtask.id);
    //formData.append('agentsa',traitement.agentsaisie);
    formData.append('commentaire',traitement.commentaire);
    formData.append('motif',traitement.motif);
    //formData.append('traiter',traitement.traiter);
    formData.append('powner',localStorage.getItem('profil'));
    //formData.append('owner',traitement.owner);
    return this.http.post<any>(this.api+'updateDemandeAutFraChefDeBureau',formData);
}
}
