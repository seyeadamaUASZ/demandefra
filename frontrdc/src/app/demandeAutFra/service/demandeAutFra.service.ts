import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DemandeAutFraService {
  api = environment.apii;
  constructor(private http: HttpClient) { }

  createDemandeAutFra(data, certificatanalysesproduit: any, registrecommerceetcreditmobilier: any, juridique: any, processusfabrication: any, copieninea: any, cnipasseport: any) {
    const formData = new FormData();

    formData.append('demandeAutFra', JSON.stringify(data));

    formData.append('certificatanalysesproduit', certificatanalysesproduit)
    formData.append('registrecommerceetcreditmobilier', registrecommerceetcreditmobilier)
    formData.append('juridique', juridique)
    formData.append('processusfabrication', processusfabrication)
    formData.append('copieninea', copieninea)
    formData.append('cnipasseport', cnipasseport)
    return this.http.post(this.api + "demandeautfra/create", formData);
  }

  paiementManuel(idDemande,typeRecu,file) {
    const formData = new FormData();


    formData.append('recuPaiement', file)
    formData.append('id', idDemande)
   
    formData.append('typeFileRecu', typeRecu)
    return this.http.post<any>(this.api + "demandeautfra/paiementmanuel", formData);
  }

  rejetRecu(idDemande) {
    const formData = new FormData();


    formData.append('id', idDemande)
   
    return this.http.post<any>(this.api + "demandeautfra/rejetpaiement", formData);
  }

  deleteProduits(produit:any){
    return this.http.post(this.api+"produits/delete", produit);
  }

  updateTaskTraitementChefBureauForm(oldtask:any,traitement:any){
    let formData:FormData = new FormData();
    formData.append('id',oldtask.id);
    formData.append('commentaire',traitement.commentaire);
    formData.append('motif',traitement.motif);
    formData.append('powner',localStorage.getItem('profil'));
    formData.append('owner',localStorage.getItem('id'));
    return this.http.post<any>(this.api+'updateDemandeAutFraChefDeBureau',formData);
}

renvoiDemandeAuFra(oldtask:any,traitement:any){
  let formData:FormData = new FormData();
    formData.append('id',oldtask.id);
    formData.append('motif',traitement.motif);
    formData.append('powner',localStorage.getItem('profil'));
    formData.append('owner',localStorage.getItem('id'));
    return this.http.post<any>(this.api+'rejetDemandeAutFraChefDeBureau',formData);
}

  getDemandeAutFraAll(owner) {
    return this.http.get(this.api + "demandeautfra/list/" + owner)
  }

  getByIdLink(idlink) {
    return this.http.get(this.api + "demandeAutFra/" + idlink);
  }

  getDemandeById(id) {
    return this.http.get(this.api + "demandeautfra/" + id);
  }

  deleteDemandeAutFra(data) {
    return this.http.post(this.api + "demandeautfra/delete", data)
  }

  getTask(poowner) {
    return this.http.get(this.api + "demandeautfra/task/" + poowner)
  }

  getStatus(taskId) {
    return this.http.get(this.api + "transition/statusAfterExecution/" + taskId)
  }

  updateTaskDemandeAutFra(id, status) {
    return this.http.get(this.api + "demandeautfra/status/" + id + "/" + status)
  }

  getAllTask() {
    return this.http.get(this.api + "task/list")
  }

  getTaskTraite(poowner, profil) {
    return this.http.get(this.api + "demandeautfra/task/traite/" + poowner + "/" + profil);
  }

  suivreDemandeAutFra(traitement:any){
    let formData:FormData = new FormData();
    formData.append('emailEntreprise', traitement.emaildemandeur);
    formData.append('numdemande', traitement.numerodemandeautfra)
    return this.http.post(this.api + "demandeautfra/suivredemande", formData);
  }

  getAntennesByRegion(id) {
    return this.http.get(this.api + "antenneRegionaleDepartementale/listeAntenneParRegion/" + id);
  }  
}
