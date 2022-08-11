import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificationServiceMessage } from 'src/app/parametrage/services/notification.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-edit-type-notif',
  templateUrl: './edit-type-notif.component.html',
  styleUrls: ['./edit-type-notif.component.scss']
})
export class EditTypeNotifComponent implements OnInit {
  addForm = this.formbuild.group({
    tdnId: ['', Validators.required],
    tdnType: ['', Validators.required],
    tdnContenu: ['', Validators.required],
    tdnAnId: ['', Validators.required],
    tdnTypeNotif:['',Validators.required]

  });
  type: any;
  constructor(private formbuild: FormBuilder, private router: Router,
    private notification: NotificationService, 
    private translate: TranslateService, 
    private notificationService: NotificationServiceMessage,
    @Inject(MAT_DIALOG_DATA) public typeNotification ,
    public dialogRef: MatDialogRef<EditTypeNotifComponent>, ) { }

  ngOnInit() {
    this.notificationService.listeTypeDeNotificationAction().subscribe(data => {
      if (data.statut) {
        this.type = data.data;
      }
    })
    this.initView();
  }

  initView() {
    this.addForm.setValue({
      tdnId: this.typeNotification.tdnId?this.typeNotification.tdnId:null,
      tdnType: this.typeNotification.tdnType ? this.typeNotification.tdnType:null,
      tdnContenu: this.typeNotification.tdnContenu ?this.typeNotification.tdnContenu :null,
      tdnTypeNotif:this.typeNotification.tdnTypeNotif ?this.typeNotification.tdnTypeNotif :null,
      tdnAnId: this.typeNotification.tdnAnId ? this.typeNotification.tdnAnId : null,
    });
  }

  compareActionType(d1, d2): boolean {
    return d1 && d2 ? d1.AnId === d2.AnId : false;
  }
  
  onSubmit() {
    this.notificationService.updateTypeNotification(this.addForm.value).subscribe(data => {
      if (data.statut) {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.success(res);
        });
        console.log(data);
        this.addForm.reset();
        this.closeDialog();
      }else{
        this.translate.get('fonctionnalite.erreure').subscribe((res: string) => {
          this.notification.warn(res);
        });
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
        this.notification.error(res);
      });
    });
 
}
get f() { return this.addForm.controls; }

closeDialog() {
  this.dialogRef.close();
}


}
