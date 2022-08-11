import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpRequestHelper } from 'src/app/shared/helpers/httprequest.helper';
import { environment } from 'src/environments/environment';
import { Document } from '../model/document';

@Injectable({
  providedIn: 'root'
})
export class PrivilegeSignerService {

  api = environment.apii;

  constructor(private http: HttpClient, private reqHelper: HttpRequestHelper) { }


  savePrivilegeSigner(id?:any,doc?:any) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('document', doc);
    return this.http.post<any>(this.api + 'privilegeSigner/create', formData);
  }
  savePrivilegeCertifier(id?:any,doc?:any) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('document', doc);
    return this.http.post<any>(this.api + 'privilegeCertifier/create', formData);
  }
  savePrivilegeConsuler(id?:any,doc?:any) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('document', doc);
    return this.http.post<any>(this.api + 'privilegeConsulter/create', formData);
  }

  savePrivilegeApprouver(id?:any,doc?:any) {
    const formData = new FormData();
    formData.append('id', id);
    formData.append('document', doc);
    return this.http.post<any>(this.api + 'privilegeApprouver/create', formData);
  }

  listDocumentSignerByIdUser(id) {
    return this.http.get<any>(this.api + 'privilegeSigner/list/'+id);
  }
  listDocumentConsulterByIdUser(id) {
    return this.http.get<any>(this.api + 'privilegeConsulter/list/'+id);
  }
  listDocumentCertifierByIdUser(id) {
    return this.http.get<any>(this.api + 'privilegeCertifier/list/'+id);
  }

  listDocumentApprouverByIdUser(id) {
    return this.http.get<any>(this.api + 'privilegeApprouver/list/'+id);
  }



}
