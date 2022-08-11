import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Fichier } from '../models/fichier';
import { Rapport } from '../models/rapport';
import { Champs } from '../models/champs';
import { FieldBase } from '../../sharedcomponent/dynamicform/models/field-base';
import { Menu } from 'src/app/utilisateur/models/menu';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { Signature } from 'src/app/demandeAutFra/models/signature';

@Injectable({
  providedIn: 'root'
})
export class FichierService {

  constructor(private http: HttpClient,private reqHelper: HttpRequestHelper) { }
  api = environment.apii;

  listFichier() {
    return this.http.get<any>(this.api + 'fichier', { 'headers': this.reqHelper.getReqOptions("list_fichier") });
  }

  deleteFichier(fichier: Fichier) {
    return this.http.post<any>(this.api + 'fichier/delete', fichier, { 'headers': this.reqHelper.getReqOptions("delete_fichier") });
  }

  listFichierByApp(id) {
    return this.http.get<any>(this.api + 'fichierByAppId/' + id, { 'headers': this.reqHelper.getReqOptions("fichierByAppId_fichier") });
  }

  addRapport(fichierjrxml: any, rapport:Rapport) {
    const formData = new FormData();
    formData.append('jrxmlfile', fichierjrxml);
    formData.append('rapport', JSON.stringify(rapport));
    return this.http.post<any>(this.api + 'rapport', formData, { 'headers': this.reqHelper.getReqOptions("create_rapport") });
  }

  editRapport(rapport:Rapport) {
    const formData = new FormData();
    formData.append('rapport', JSON.stringify(rapport));
    return this.http.post<any>(this.api + 'updaterapport', formData, { 'headers': this.reqHelper.getReqOptions("update_rapport") });
  }

  editRapportFile(fichierjrxml: any, rapport:Rapport) {
    const formData = new FormData();
    formData.append('jrxmlfile', fichierjrxml);
    formData.append('rapport', JSON.stringify(rapport));
    return this.http.post<any>(this.api + 'updaterapportfile', formData, { 'headers': this.reqHelper.getReqOptions("update_rapport") });
  }

  listeRapportNotgenerer() {
    return this.http.get<any>(this.api+ 'notgeneraterapport', { 'headers': this.reqHelper.getReqOptions("notgeneraterapport_rapport") });
  }

  addChamps(champs:any) {
    return this.http.post<any>(this.api+ "champsrapport", champs, { 'headers': this.reqHelper.getReqOptions("champsrapport_rapport") });
  }

  validerRapport(rapport:Rapport) {
    return this.http.post<any>(this.api + 'validerrapport', rapport, { 'headers': this.reqHelper.getReqOptions("validerrapport_rapport") });
  }

  fieldChampsByRapport(idRapport){
    return this.http.get<any>(this.api + 'champsbyrapport/' +idRapport, { 'headers': this.reqHelper.getReqOptions("champsbyrapport_rapport") });
  }

  listeRapportGenerer() {
    return this.http.get<any>(this.api + 'generaterapport', { 'headers': this.reqHelper.getReqOptions("generaterapport_rapport") });
  }

  listeRapportParMenu(menu: Menu) {
    return this.http.post<any>(this.api + 'rapportparmenu', menu, { 'headers': this.reqHelper.getReqOptions("rapportparmenu_rapport") });
  }

  modeliserRapport(rapport: Rapport) {
    return this.http.post<any>(this.api + 'modeliserrapport', rapport, { 'headers': this.reqHelper.getReqOptions("modeliserrapport_rapport") });
  }

  supprimerChamps(champsId:any) {
    return this.http.post<any>(this.api + 'champsrapport/delete', champsId, { 'headers': this.reqHelper.getReqOptions("delete_champsrapport_rapport") });
  }

  supprimerChampsByRapport(idRapport) {
    return this.http.get<any>(this.api + 'deletebyrapport/' +idRapport, { 'headers': this.reqHelper.getReqOptions("deletebyrapport_rapport") });
  }

  supprimerRapport(idRapport) {
    return this.http.post<any>(this.api + 'supprimerrapport', idRapport, { 'headers': this.reqHelper.getReqOptions("supprimerrapport_rapport") });
  }

  convertChampsToField(ch:Champs):FieldBase<string>{
      return new FieldBase<string>({
        key: ch.crtNom,
        label: ch.crtLabel,
        type: ch.crtType,
        required: ch.crtObligatoire
     });
  }

  genererRapportPdf(fichier: any,data:any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
       'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichier/pdf`, formData, httpOptions);
  }

  genererRapportPdfQrcode(fichier: any,data:any, data1:any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    formData.append('data1', data1);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
       'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichierQrcode/pdf`, formData, httpOptions);
  }

  genererCertificatPdf(fichier: any,data:any,id,numero) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    formData.append('id', id);
    formData.append('numero', numero);

    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
       'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichier/pdf`, formData, httpOptions);
  }

  genererRapportExcel(fichier: any,data:any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      'headers': this.reqHelper.getReqOptions("genererRapportExcel_rapport")

    };
    return this.http.post<any>(`${this.api}fichier/excel`, formData, httpOptions);
  }

  listeMenuRapports (rapport: any) {
    return this.http.post<any>(this.api + 'menurapport/menurapportparrapport', rapport, { 'headers': this.reqHelper.getReqOptions("listeMenuRapport_rapport") });
  }

  allocateMenuRapports(rapport:Rapport, removed:any[], added:any[]) {
    let formData = new FormData();
    formData.append("rapport", JSON.stringify(rapport));
    formData.append("removed", JSON.stringify(removed));
    formData.append("added", JSON.stringify(added));
    return this.http.post<any>(this.api + 'menurapport/allocatemenurapport', formData, { 'headers': this.reqHelper.getReqOptions("allocateMenurapport_rapport") });
  }

  getMenusProfil(p) {
    return this.http.get<any>(this.api + 'menu/profil/'+p);
  }

  createSousMenu (sousMenu: any) {
    return this.http.post<any>(this.api + 'menu/createsousmenurapport', sousMenu, { 'headers': this.reqHelper.getReqOptions("createSousMenu_menu") });
  }

  ListeSousMenuRapport () {
    return this.http.get<any>(this.api + 'menu/sousmenurapport');
  }

  getMenuByPath(menu: any) {
    return this.http.post<any>(this.api+'menu/path', menu);
  }

  genererAutFraProPdf(fichier: any, data: any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichierVisualiser/pdf`, formData, httpOptions);
  }

  signature(fichier:any, data:any, iddemande:any, result:any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    formData.append('iddemande', iddemande);
    formData.append('signature',result);   
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
   
    return this.http.post<any>(`${this.api}fichier/pdf`, formData, httpOptions);
  }

  genererAutFraDefPdf(fichier: any, data: any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichierAutFraDefinitif/pdf`, formData, httpOptions);
  }

  signerFraDefinitive(fichier:any,data:any,idproduit:any,result: any) {
    let formData: FormData = new FormData();
    formData.append('fichier', fichier);
    formData.append('data', data);
    formData.append('id', idproduit);
    formData.append('signature', result);
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      'headers': this.reqHelper.getReqOptions("genererRapportPdf_rapport")
    };
    return this.http.post<any>(`${this.api}fichierSigner/pdf`, formData, httpOptions);
  }

  downloadAutFraPro(id) {
    let headers = new HttpHeaders();
    return this.http.get(this.api+'demandeautfra/downloadAutFraPro/'+id,
      {
        headers: headers,
        observe: 'response',
        responseType: 'arraybuffer'
      });
  }

  downloadAutFraDef(id) {
    let headers = new HttpHeaders();
    return this.http.get(this.api+'demandeautfra/downloadAutFraDef/'+id,
      {
        headers: headers,
        observe: 'response',
        responseType: 'arraybuffer'
      });
  }
}
