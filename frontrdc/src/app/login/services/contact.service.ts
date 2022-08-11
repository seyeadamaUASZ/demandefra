import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class ContactService {
 api = environment.apii;
  
 constructor(private http: HttpClient) { }

 messageContact(formData: FormData) {
  return this.http.post(this.api + 'demandeautfra/contact', formData)
  }
}