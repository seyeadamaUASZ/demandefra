import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Fonctionnalite } from 'src/app/application/models/fonctionnalite';

import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { AppFonc } from 'src/app/application/models/appFonc';

@Injectable({
  providedIn: 'root'
})
export class FonctionnaliteService {
  api = environment.apii;
  constructor(private http: HttpClient,private reqHelper: HttpRequestHelper) { }


  //liste des fonctionnalités par module!!
  listFonctionnalite(id) {
    return this.http.get<any>(this.api + 'fonctionnaliteModule/' + id,{ 'headers': this.reqHelper.getReqOptions("fonctionnaliteModule_fonctionnalite")});
  }

  listFonctionnaliteAppFonc(idApp, idFon) {
    return this.http.get<any>(this.api + 'listeAppFonc/'+ idApp+'/'+idFon,{ 'headers': this.reqHelper.getReqOptions("listeAppFonc_fonctionnalite")});
  }

  IsActiveOuNon(idApp, idFon){

    return this.http.get<any>(this.api + 'testeSiActiveOuNon/' + idApp+'/'+idFon,{ 'headers': this.reqHelper.getReqOptions("testeSiActionOuNon_fonctionnalite")});
  }

  //creation d'une fonctionnalite
  creatFonctioonalite(fonctionnalite: Fonctionnalite) {
    return this.http.post<any>(this.api + 'fonctionnalite', fonctionnalite,{ 'headers': this.reqHelper.getReqOptions("create_fonctionnalite")});

  }

  creatAppFonc(appfonc:AppFonc) {
    return this.http.post<any>(this.api + 'ajoutAppFonc', appfonc,{ 'headers': this.reqHelper.getReqOptions("ajouterAppFonc_application")});

  }
  //update fonctionnalité
  updateFonctionnalite(fonctionnalite: Fonctionnalite) {
    return this.http.post<any>(this.api + 'updatefonctionnalite', fonctionnalite,{ 'headers': this.reqHelper.getReqOptions("edit_fonctionnalite")});

  }

  //suppression d'une fonctionnalite
  deleteFonctionnalite(fonctionnalite: Fonctionnalite) {
    return this.http.post<any>(this.api + 'fonctionnalite/delete', fonctionnalite,{ 'headers': this.reqHelper.getReqOptions("delete_fonctionnalite")});
  }

  activerAppFon(idApp, idFon) {
    return this.http.get<any>(this.api + 'ativeAppFonc/' + idApp+'/'+idFon,{ 'headers': this.reqHelper.getReqOptions("activeAppFonc_fonctionnalite")});
  }

  //service desactiver une fonctionnalité!
  desactiverAppFon(idApp, idFon) {
    return this.http.get<any>(this.api + 'desactiveAppFonc/' + idApp+'/'+idFon,{ 'headers': this.reqHelper.getReqOptions("desactveAppFonc_fonctionnalite")});
  }

}
