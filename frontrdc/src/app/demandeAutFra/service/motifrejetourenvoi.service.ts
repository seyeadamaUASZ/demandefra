import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MotifrejetourenvoiService {
  api = environment.apii;
  constructor(private http: HttpClient) { }

  createMotifrejetourenvoi(data) {
    return this.http.post(this.api + "motifrejetourenvoi/create", data);
  }

createRenvoiProduit(data) {
    return this.http.post(this.api + "motifrejetourenvoi/rejetproduit", data);
  }
  getMotifrejetourenvoiAll(owner) {
    return this.http.get(this.api + "motifrejetourenvoi/list/" + owner)
  } 

  getByIdLink(idlink) {
    return this.http.get(this.api + "motifrejetourenvoi/" + idlink);
  }

  deleteMotifrejetourenvoi(data) {
    return this.http.post(this.api + "motifrejetourenvoi/delete", data)
  }

  getTask(poowner) {
    return this.http.get(this.api + "motifrejetourenvoi/task/" + poowner)
  }

  getStatus(taskId) {
    return this.http.get(this.api + "transition/statusAfterExecution/" + taskId)
  }

  updateTaskMotifrejetourenvoi(id, status) {
    return this.http.get(this.api + "motifrejetourenvoi/status/" + id + "/" + status)
  }

  getAllTask() {
    return this.http.get(this.api + "task/list")
  }
  
  getTaskTraite(poowner, profil) {
    return this.http.get(this.api + "motifrejetourenvoi/task/traite/" + poowner + "/" + profil);
  }

  getHistoriqueCommentaire(idLink, status) {
    return this.http.get(this.api + 'motifrejetourenvoi/historique/'+idLink+"/"+status);
  }

  getMotifRejet(idLink, status) {
    return this.http.get(this.api + 'motifrejetourenvoi/motifrejet/'+idLink+"/"+status);
  }

  getMotifRenvoiChefDivision(idLink) {
    return this.http.get(this.api + 'motifrejetourenvoi/renvoichefdivision/' + idLink);
  }
}
