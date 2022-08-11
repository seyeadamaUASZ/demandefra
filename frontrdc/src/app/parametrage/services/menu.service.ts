import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  api = environment.apii;
  private menuSource = new BehaviorSubject<any>("");
  currentMenu = this.menuSource.asObservable();
    constructor(private http: HttpClient) { }

    changemenu(menu:any){
      this.menuSource.next(menu);
    }

    listeMenu() {
        return this.http.get<any>(this.api + 'menu/list');
    }

    listeMenuHierarchique() {
        return this.http.get<any>(this.api + 'menu/listhierarchique');
    }
    

    infoMenu(id: any) {
        return this.http.get<any>(this.api + 'menu', id);
    }    

    updateMenu(menu: any) {
      return this.http.post<any>(this.api + 'menu/update', menu);
    }

    saveMenu(menu: any) {
      return this.http.post<any>(this.api + 'menu/create', menu);
    }

  search(search:string) {
    return this.http.get<any>(this.api + 'menu/recherche/'+ search);
}
}
