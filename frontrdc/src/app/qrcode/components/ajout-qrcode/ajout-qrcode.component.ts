import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { QrcodeService } from '../../services/qrcode.service';
@Component({
  selector: 'app-ajout-qrcode',
  templateUrl: './ajout-qrcode.component.html',
  styleUrls: ['./ajout-qrcode.component.scss']
})
export class AjoutQrcodeComponent implements OnInit {
  @ViewChild('file1', { static: true }) file1;

  color:any;
  loading: any;
  statut:any;
  QrcodeForm = this.formbuild.group({
    qrcNom: ['', Validators.required],
    qrcDescription: ['', Validators.required],
  });
  result: any
  constructor(private formbuild: FormBuilder, private router: Router,
    private qrcodeService: QrcodeService,
    public dialogRef: MatDialog, private _snackBar: MatSnackBar,
    private route: ActivatedRoute, private notification: NotificationService,
     private translate: TranslateService, ) {

  }

  ngOnInit() {
  }
  ffile1:File;

  uploadActeNaissance() {
    this.ffile1 = this.file1.nativeElement.files[0];
    const extension = this.ffile1.name.split('.')[1].toLowerCase();
       if ( "png" != extension ) {
        this.translate.get("demandevehicule.fileExtenxionNotif").subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.ffile1 =null;
        return;
       }
      if (this.ffile1.size >3000000){
        this.translate.get("demandevehicule.fileSizeNotif").subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.ffile1 = null;
        return;
      }
  }
  addFiles1() {
    this.file1.nativeElement.click();
  }

  closeDialog() {
    this.dialogRef.closeAll();
  }

  onSubmit() {
    if (this.QrcodeForm.valid) {
      this.qrcodeService.saveQrcode(this.QrcodeForm.value).subscribe(data => {
        this.statut = data.statut
        if (this.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          this.QrcodeForm.reset();
          this.closeDialog();

        }else{
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.info(res);
          });

        }

      }, error => {
        this.translate.get(error).subscribe((res: string) => {
          this.notification.error(res);
        });
      });



    }
    else {
      this.translate.get('formulaire invalide').subscribe((res: string) => {
        this.notification.error(res);
      });
    }
  }
}