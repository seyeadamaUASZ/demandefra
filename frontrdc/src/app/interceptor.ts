
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
// import * as CryptoJS from 'crypto-js';

@Injectable()
export class Interceptor implements HttpInterceptor {

    constructor(private router: Router) { }
    encryptSecretKey ="Klasdj#8ufsdj";

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        

        const token = localStorage.getItem('token');
        if (token) {
            
            req = req.clone({
                headers: req.headers
                    .set('Access-Control-Allow-Origin', '*')
                    // .set('Content-Type', 'application/json')
                    .set('Access-Control-Allow-Headers', 'Content-Type, Accept, Authorization, X-Requested-With')
                    .set('Access-Control-Allow-Methods', 'POST, GET, OPTIONS, PUT, DELETE')
                    .set('X-Requested-With', 'XMLHttpRequest')
                    .set('Authorization', 'Bearer ' + token)
            });
            return next.handle(req);
        } else {
            req = req.clone({
                headers: req.headers
                    .set('Access-Control-Allow-Origin', '*')
                    //.set('Content-Type', 'application/json')
                    .set('Access-Control-Allow-Headers', 'Content-Type, Accept, Authorization, X-Requested-With')
                    .set('Access-Control-Allow-Methods', 'POST, GET, OPTIONS, PUT, DELETE')
                    .set('X-Requested-With', 'XMLHttpRequest')
            });      
            return next.handle(req);
        }
        

    }
    
}

