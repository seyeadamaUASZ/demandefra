import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { CriterePwd } from '../models/critere-pwd';

@Injectable({
    providedIn: 'root'
})

export class CriterePwdService {
    api = environment.apii;
    constructor(private http: HttpClient) { }

    infoCriterePwd() {
        return this.http.get<any>(this.api + 'pwdcriteria/details');
    }

    updateCriterePwd(criterePwd: CriterePwd) {
        return this.http.post<any>(this.api + 'pwdcriteria/update', criterePwd);
    }
}