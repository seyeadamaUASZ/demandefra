import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { NotificationMessage } from '../models/notification';
import { NotificationProfil } from '../models/notification-profil';
import { TypeDeNotification } from 'src/app/configuration/models/typeDeNotification';

@Injectable({
    providedIn: 'root'
})

export class NotificationServiceMessage {
    api = environment.apii;
    constructor(private http: HttpClient) { }

    listeNotifications() {
        return this.http.get<any>(this.api + 'notification');
    }

    listeNotificationProfils() {
        return this.http.get<any>(this.api + "notification/listnotificationprofil");
    }

    listeEvenements(notification: any) {
        return this.http.post<any>(this.api + 'evenement/evenementparnotification', notification);
    }

    allocateEvenements(notification: NotificationMessage, removed: any[], added: any[]) {
        let formData = new FormData();
        formData.append("notification", JSON.stringify(notification));
        formData.append("removed", JSON.stringify(removed));
        formData.append("added", JSON.stringify(added));
        return this.http.post<any>(this.api + 'evenement/allocateevenement', formData);
    }

    listeProfils() {
        return this.http.get<any>(this.api + 'profil/list');
    }

    listeDestinataireParProfils(profil: any) {
        return this.http.post<any>(this.api + 'utilisateur/listUsersbyprofil', profil);
    }

    listeDestinataires() {
        return this.http.get<any>(this.api + 'utilisateur/list');
    }

    allocateDestinataires(notification: NotificationMessage, removed: any[], added: any[]) {
        let formData = new FormData();
        formData.append("notification", JSON.stringify(notification));
        formData.append("removed", JSON.stringify(removed));
        formData.append("added", JSON.stringify(added));
        return this.http.post<any>(this.api + 'notificationdestionataire/allocateDestinataire', formData);
    }

    listeTypeNotification() {
        return this.http.get<any>(this.api + 'typenotification');
    }

    addNotificationParProfil(profil, notification: NotificationMessage) {
        let formatData = new FormData;
        formatData.append("profil", JSON.stringify(profil));
        formatData.append("notification", JSON.stringify(notification));
        return this.http.post<any>(this.api + 'notification/addparprofil', formatData);
    }

    addNotification(removed: any[], added: any[], profil, notification: NotificationMessage) {
        let formatData = new FormData;
        formatData.append("profil", JSON.stringify(profil));
        formatData.append("removed", JSON.stringify(removed));
        formatData.append("added", JSON.stringify(added));
        formatData.append("notification", JSON.stringify(notification));
        return this.http.post<any>(this.api + 'notification/create', formatData);
    }

    updateNotification(notificationProfil: NotificationProfil) {
        return this.http.post<any>(this.api + 'notification/update', notificationProfil);
    }


    deleteNotification(notification: Notification) {
        return this.http.post<any>(this.api + 'notification/delete', notification);
    }

    addNotificationAudioParProfil(profil, notification: NotificationMessage, audio) {
        const formData = new FormData();
        formData.append('profil', JSON.stringify(profil));
        formData.append('notification', JSON.stringify(notification));
        formData.append('audio', audio);
        return this.http.post<any>(this.api + 'notification/addaudioparprofil', formData);
    }

    getListProfil(notification: any) {
        let formatData = new FormData;
        formatData.append("notification", JSON.stringify(notification));
        return this.http.post<any>(this.api + 'notification/listprofilparnotification', formatData);
    }

    listeTypeDeNotification() {
        return this.http.get<any>(this.api + 'typeDeNotification/list');
    }

    search(search:string) {
        return this.http.get<any>(this.api + 'typeDeNotification/' + search);
    }

    ajouterMessage(typeDeNotification: TypeDeNotification) {
        return this.http.post<any>(this.api + 'typeDeNotification/create', typeDeNotification);
    }

    listeTypeDeNotificationAction() {
        return this.http.get<any>(this.api + 'actionNotification/list');
    }

    deleteTypeDeNotif(typeDeNotification: TypeDeNotification) {
        return this.http.post<any>(this.api + 'typeDeNotification/delete', typeDeNotification);
    }

    updateTypeNotification(notification: TypeDeNotification) {
        return this.http.post<any>(this.api + 'typeDeNotification/update', notification);
    }

    searchNotification(search:string) {
        return this.http.get<any>(this.api + 'notification/notifprofil/'+ search);
    }

}


