import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificationServiceMessage } from 'src/app/parametrage/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { MatDialog, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-ajout-type-notif',
  templateUrl: './ajout-type-notif.component.html',
  styleUrls: ['./ajout-type-notif.component.scss']
})
export class AjoutTypeNotifComponent implements OnInit {
  type: any;
  addForm = this.formbuild.group({
    tdnType: ['', Validators.required],
    tdnContenu: ['', Validators.required],
    tdnAnId: ['', Validators.required],
    tdnTypeNotif:['',Validators.required]

  });

  constructor(private formbuild: FormBuilder, private router: Router,
    private notificationService: NotificationServiceMessage,
    public dialogRef: MatDialogRef<AjoutTypeNotifComponent>,
    private notification: NotificationService, private translate: TranslateService) { }


  ngOnInit() {
    this.listActionNotif();
  }

  get f() { return this.addForm.controls; }

  onSubmit() {
    if (this.addForm.value.invalid) {
      this.translate.get('formulaire invalide').subscribe((res: string) => {
        this.notification.warn(res);
      });
    } 
    else {
      this.notificationService.ajouterMessage(this.addForm.value).subscribe(data => {
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          console.log(data);
          this.addForm.reset();
          this.closeDialog();
        } 
        else {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.warn(res);
          });
        }
      }, error => {
        this.translate.get('Error.internalservererror').subscribe((res: string) => {
          this.notification.error(res);
        });
      });
    }

  }
  closeDialog() {
    this.dialogRef.close();
  }

  listActionNotif() {
    this.notificationService.listeTypeDeNotificationAction().subscribe(data => {
      if (data.statut) {
        this.type = data.data;
      }
    })
  }

}
