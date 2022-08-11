import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class NotificationPushService {

  api = environment.apii;

  constructor(private http: HttpClient) { }

  private eventSubject = new BehaviorSubject<any>(undefined);

  creerEvent(param: any) {
    this.eventSubject.next(param);
  }

  getEvent(): BehaviorSubject<any> {
    return this.eventSubject;
  }
  createNotifPush(notificationPush) {
    return this.http.post<any>(this.api + 'notificationPush/create', notificationPush);
  }
  deleteNotifPush(notificationPush) {
    return this.http.post<any>(this.api + 'notificationPush/delete', notificationPush);
  }
  getNotifPush() {
    return this.http.get<any>(this.api + 'notificationPush/list');
  }
}
