import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Qrcodes } from '../models/qrcodes';
import { Observable, from } from 'rxjs';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { FieldBase } from 'src/app/sharedcomponent/dynamicform/models/field-base';
import { ChampsQrcode } from '../models/champsQrcode';

@Injectable({
  providedIn: 'root'
})
export class QrcodeService {
  api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }


  getAllChamps() {
    return this.http.get<any>(this.api + 'champsQrcode/list');
  }
  //listes fields champsQrcode
  listFieldChampsQrcode(id) {
    return this.http.get<any>(this.api + 'champsQrcode/fieldByQrcode/' + id);
  }

  //listes bouttons champsQrcode
  listBouttonChampsQrcode(id) {
    return this.http.get<any>(this.api + 'champsQrcode/buttonByQrcode/' + id);
  }

  listChampsQrcode(id) {
    return this.http.get<any>(this.api + 'champsQrcode/champsByQrcode/' + id);
  }

  //créer un qr code
  saveQrcode(qr:Qrcodes) {
    return this.http.post<any>(this.api + 'barcodes/qrcode', qr);
  }

  qrcodeGenere(file:any,donnees:any, qr:Qrcodes) {
    let formData= new FormData();
    formData.append('file1',file);
    formData.append('data',donnees);
    formData.append('qrcode', JSON.stringify(qr));
    return this.http.post<any>(this.api + 'barcodes/qrcodeGennere', formData);
  }
  //créer un champs qr code
  saveChampsQrcode(champsqrcode: ChampsQrcode) {
    return this.http.post<any>(this.api + 'champsQrcode/create', champsqrcode);
  }
  deleteChampsQrcode(champs: ChampsQrcode) {
    return this.http.post<any>(this.api + 'champsQrcode/delete', champs);
  }

  deleteQrcode(qrcode: Qrcodes) {
    return this.http.post<any>(this.api + 'barcodes/qrcode/delete', qrcode);
  }

  supprimerQrcode(id) {
    return this.http.post<any>(this.api + 'barcodes/qrcode/supprime', id, { 'headers': this.reqHelper.getReqOptions("supprimerqrcode_qrcode") });
  }

  generateQrcode(contenue: any): Observable<Blob> {
    return this.http.post<any>(this.api + 'barcodes/zxing/qrcode', contenue);
  }
  getallQrcode() {
    return this.http.get<any>(this.api + 'barcodes/qrcode/listQrcodeNotModeliser');
  }

  getallQrcodeModeliser() {
    return this.http.get<any>(this.api + 'barcodes/qrcode/QrcodeModeliser');
  }

  fieldChampsByQrcode(id) {
    return this.http.get<any>(this.api + 'champsbyqrcode/' + id, { 'headers': this.reqHelper.getReqOptions("champsbyqrcode_qrcode") });
  }

  addChamps(champs: any) {
    return this.http.post<any>(this.api + "champsqrcode/create", champs, { 'headers': this.reqHelper.getReqOptions("champsrqrcode_qrcode") });
  }
  supprimerChamps(id: any) {
    return this.http.post<any>(this.api + 'champsqrcode/delete', id, { 'headers': this.reqHelper.getReqOptions("delete_champsqrcode_qrcode") });
  }



  getimage(id) {
    return this.http.get<any>(this.api + 'barcodes/' + id);
  }

  download(id) {
    return this.http.get(this.api + 'barcodes/qrcode/downloadFile/' + id,
      {
        observe: 'response',
        responseType: 'arraybuffer'
      });
  }

  lierFichierQrcode(data) {
    let formdata: FormData = new FormData();
    formdata.append('data', JSON.stringify(data))
    return this.http.post<any>(this.api + 'rapport/qrcode', formdata)

  }
  convertChampsToField(ch: ChampsQrcode): FieldBase<string> {
    return new FieldBase<string>({
      key: ch.cqdNom,
      label: ch.cqdLabel,
      type: ch.cqdType,
      required: ch.cqdObligatoire
    });
  }
  modeliserQrcode(qrcode: Qrcodes) {
    return this.http.post<any>(this.api + 'barcodes/qrcode/modeliser', qrcode, { 'headers': this.reqHelper.getReqOptions("modeliserqrcode_qrcode") });
  }

  validerQrcode(qrcode:Qrcodes) {
    return this.http.post<any>(this.api + 'barcodes/qrcode/valider', qrcode, { 'headers': this.reqHelper.getReqOptions("validerqrcode_qrcode") });
  }


  getallQrcodeGenerer() {
    return this.http.get<any>(this.api + 'barcodes/qrcode/QrcodeGenerer');
  }

}
