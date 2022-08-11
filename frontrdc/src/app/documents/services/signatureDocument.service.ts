import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SignatureDocumentService {
  api = environment.apii;
  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }


listeDocumentSignerByIdUser(id) {
  return this.http.get<any>(this.api + 'signatureDocument/listdocument/'+id);
}

DetailDocumentSignerByIdDoc(id) {
  return this.http.get<any>(this.api + 'signatureDocument/listutilisateur/'+id);
}



}
