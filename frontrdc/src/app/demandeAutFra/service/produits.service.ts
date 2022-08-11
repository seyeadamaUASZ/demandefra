import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProduitsService {
  api = environment.apii;
  constructor(private http: HttpClient) { }

  createProduits(data, etiquetteouemballage: any) {
    const formData = new FormData();
    formData.append('produits', JSON.stringify(data));
    formData.append('etiquetteouemballage', etiquetteouemballage)
    return this.http.post(this.api + "produits/create", formData);
  }
  getProduitsAll(owner) {
    return this.http.get(this.api + "produits/list/" + owner)
  }
  getByIdLink(id) {
    return this.http.get(this.api + "produits/list/" + id);
  }
  deleteProduits(data) {
    return this.http.post(this.api + "produits/delete", data)
  }
  getTask(poowner) {
    return this.http.get(this.api + "produits/task/" + poowner)
  }
  getStatus(taskId) {
    return this.http.get(this.api + "transition/statusAfterExecution/" + taskId)
  }
  updateTaskProduits(id, status) {
    return this.http.get(this.api + "produits/status/" + id + "/" + status)
  }

  getAllTask() {
    return this.http.get(this.api + "task/list")
  }
  getTaskTraite(poowner, profil) {
    return this.http.get(this.api + "produits/task/traite/" + poowner + "/" + profil);
  }
}
