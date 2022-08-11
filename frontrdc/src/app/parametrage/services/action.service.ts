import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class ActionService {

  api = environment.apii;
  private actionSource = new BehaviorSubject<any>("");
  currentActions = this.actionSource.asObservable();
    constructor(private http: HttpClient) { }

    changeactions(actions:any[]){
      this.actionSource.next(actions);
    }

    listeActions() {
        return this.http.get<any>(this.api + 'listactions');
    }

    infoAction(id: any) {
        return this.http.get<any>(this.api + 'action', id);
    }    

    saveAction(action: any, menu:any) {
      let formData = new FormData();
      formData.append("action", JSON.stringify(action));     
      formData.append("menu", JSON.stringify(menu));     
      return this.http.post<any>(this.api + 'action/create', formData);
    }
}
