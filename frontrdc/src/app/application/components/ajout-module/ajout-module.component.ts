import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { MatDialogRef, MatSnackBar } from '@angular/material';
import { AjoutAppComponent } from '../ajout-app/ajout-app.component';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { FonctionnaliteService } from '../../services/fonctionnalite.service';
import { ApplicationService } from '../../services/application.service';
import { NotificationPush } from 'src/app/parametrage/models/NotificationPush';
import { NotificationPushService } from 'src/app/parametrage/services/notification-push.service';
import { PaiementAppComponent } from 'src/app/paiement/paiement-app.component';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';
import { test } from 'src/app/sharedcomponent/test';
@Component({
  selector: 'app-ajout-module',
  templateUrl: './ajout-module.component.html',
  styleUrls: ['./ajout-module.component.scss']
})
export class AjoutModuleComponent implements OnInit {

  application: [];
  fonctionnalite: any[];
  notificationPush = new NotificationPush();
  iconRegistry;
  sanitizer;
  ModuleForm = this.formbuild.group({
    modNom: ['', Validators.required],
    modCode: ['', Validators.required],
    modDescription: [''],
    fonModId: [''],

  });
  NotifForm = this.formbuild.group({
    ntfId: [],
    ntfLibelle: ['Un nouveau module vient d\'être crée'],
    ntfTitre: ['Creation de module'],
    ntfDate: [new Date()],

  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private notifPush: NotificationPushService, private _snackBar: MatSnackBar,
    private fonctionnaliteService: FonctionnaliteService,
    private applicationService: ApplicationService,
    public dialogRef: MatDialogRef<AjoutAppComponent>, private translate: TranslateService,
    private notification: NotificationService) {

  }


  ngOnInit() {
    this.applicationService.listeApplication().subscribe(res => {
      this.application = res.data;
      let test = new PaiementAppComponent(this.iconRegistry, this.sanitizer);


    });
  }
  //get f() { return this.ModuleForm.controls; }


  onSubmit() {
    if (this.ModuleForm.valid) {
      this.applicationService.addModule(this.ModuleForm.value).subscribe(data => {
        console.log(data);
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          this.notifyPush();
          // this.notifPush.creerEvent('yup');
          test.myNotif++;
          this.ModuleForm.reset();
          this.closeDialog();
          
        }
      }, error => {
        this.translate.get(error).subscribe((res: string) => {
          this.notification.error(res);
        });
      });
    } else {
      let invalidForm;
      this.translate.get('application.invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
    }

  }
  closeDialog() {
    this.dialogRef.close();
  }
  notifyPush() {
    this.notifPush.createNotifPush(this.NotifForm.value).subscribe(data => { });
    this.notification.playAudio();
    setTimeout(() => {
      this.push();
    }, 3000);
  }
  push(){
    let title= 'Notification';
    let iconPush;
     if (window.Notification && Notification.permission !== "denied") {
       Notification.requestPermission(function(status) {
           var n = new Notification(title, {
               body: "You have new notifications",
               icon: iconPush
           });
       });
   }
   }

}
