import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SuivredemandeService {
  api = environment.apii;
  constructor(private http: HttpClient) { }

  createSuivredemande(data) {
    const formData = new FormData();
    formData.append('suivredemande', JSON.stringify(data));
    return this.http.post(this.api + "suivredemande/create", formData);
  }
  getSuivredemandeAll(owner) {
    return this.http.get(this.api + "suivredemande/list/" + owner)
  }
  getByIdLink(idlink) {
    return this.http.get(this.api + "suivredemande/" + idlink);
  }
  deleteSuivredemande(data) {
    return this.http.post(this.api + "suivredemande/delete", data)
  }
  getTask(poowner) {
    return this.http.get(this.api + "suivredemande/task/" + poowner)
  }
  getStatus(taskId) {
    return this.http.get(this.api + "transition/statusAfterExecution/" + taskId)
  }
  updateTaskSuivredemande(id, status) {
    return this.http.get(this.api + "suivredemande/status/" + id + "/" + status)
  }

  getAllTask() {
    return this.http.get(this.api + "task/list")
  }
  getTaskTraite(poowner, profil) {
    return this.http.get(this.api + "suivredemande/task/traite/" + poowner + "/" + profil);
  }
}
