import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { NotificationProfil } from 'src/app/parametrage/models/notification-profil';

@Component({
  selector: 'app-view-notification',
  templateUrl: './view-notification.component.html',
  styleUrls: ['./view-notification.component.scss']
})
export class ViewNotificationComponent implements OnInit {
  public NotificationForm = this.formbuild.group({
    ntfObjet: ['', Validators.required],
    ntfSignature: ['', Validators.required],
    ntfText: [''],
    ntfAudio: ['']
});
  notification:NotificationProfil=null
  constructor(private formbuild: FormBuilder, private router: Router,
    public dialogRef: MatDialogRef<ViewNotificationComponent>, @Inject(MAT_DIALOG_DATA) public notificationprofil: any) { }

  ngOnInit() {
    this.initView()

  }

  initView() {
   this.notification=this.notificationprofil;
  }

  closeDialog() {
    this.dialogRef.close();
  }


}
