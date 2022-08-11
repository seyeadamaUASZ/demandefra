import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, Validators } from '@angular/forms';
import { QrcodeService } from '../../services/qrcode.service';

@Component({
  selector: 'app-edit-qrcode',
  templateUrl: './edit-qrcode.component.html',
  styleUrls: ['./edit-qrcode.component.scss']
})
export class EditQrcodeComponent implements OnInit {
  qrcode:any
  QrcodeForm = this.formbuild.group({
    qrcId: ['', Validators.required],
    qrcNom: ['', Validators.required],
    qrcDescription: ['', Validators.required],
  });
  constructor(private route: ActivatedRoute,private formbuild: FormBuilder,
    private dialog: MatDialog,
    private dialogRef: MatDialog,
    private translate: TranslateService,
    private notification: NotificationService,private qrcodeService:QrcodeService,@Inject(MAT_DIALOG_DATA) public donnee: any) { }

  ngOnInit() {
    this.detail()
  }
  detail() {
    this.qrcode = this.donnee;
console.log(this.donnee)
    this.QrcodeForm.setValue({
      qrcId: this.qrcode.qrcId,
      qrcNom: this.qrcode.qrcNom ? this.qrcode.qrcNom : null,
      qrcDescription: this.qrcode. qrcDescription ? this.qrcode. qrcDescription : null,


    });

  }

  closeDialog() {
    this.dialogRef.closeAll();
  }
 onUpdate(){
   this.qrcodeService.saveQrcode(this.QrcodeForm.value).subscribe(resp=>{
    if (resp.statut) {
      this.translate.get(resp.description).subscribe((res: string) => {
         this.notification.success(res);
      });
     console.log(resp);
     this.QrcodeForm.reset();
       this.closeDialog();

  }
 }, error => {
 this.translate.get(error).subscribe((res: string) => {
  this.notification.error(res);
   });
  });

 }
}
