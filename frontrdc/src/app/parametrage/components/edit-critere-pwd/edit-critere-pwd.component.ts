import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
// import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog, MatSnackBar, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CriterePwd } from '../../models/critere-pwd';
import { CriterePwdService } from '../../services/critere-pwd.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { parse } from 'querystring';

@Component({
  selector: 'app-edit-cretere-pwd',
  templateUrl: './edit-critere-pwd.component.html',
  styleUrls: ['./edit-critere-pwd.component.scss']
})
export class EditCriterePwdComponent implements OnInit {
  criterePwd: CriterePwd = null;
  criterePwdForm = this.formbuild.group({
    pwdId: ['', Validators.required],
    pwdCarMin: ['', Validators.required],
    pwdDigMin: ['', Validators.required],
    pwdMajMin: ['', Validators.required],
    pwdSpcMin: ['', Validators.required],
    pwdDure: ['', Validators.required],
  });

  constructor(private formbuild: FormBuilder,private dialogRef: MatDialog,private route: ActivatedRoute, private translate: TranslateService,
     private criterePwdService: CriterePwdService, private notification: NotificationService,
     @Inject(MAT_DIALOG_DATA) public donnee: any) { }

  ngOnInit() {
    this.detail();
  }

  onSubmit() {
    let messageSuccess;
    let messageError;
		this.translate.get('criterePwd.success').subscribe((res: string) => {
			messageSuccess = res;
		});
		this.translate.get('criterePwd.error').subscribe((res: string) => {
			messageError = res;
    });
     var somme = parseInt(this.criterePwdForm.value.pwdMajMin)+parseInt(this.criterePwdForm.value.pwdSpcMin)+parseInt(this.criterePwdForm.value.pwdDigMin)
     var min=parseInt(this.criterePwdForm.value.pwdCarMin)

     if(this.verification(somme,min)){
      this.criterePwdService.updateCriterePwd(this.criterePwdForm.value).subscribe(data => {
        if (data.statut) {
          this.translate.get(messageSuccess).subscribe((res: string) => {
            this.notification.success(res);
            });
            this.closeDialog();
        } else {
          this.translate.get(messageError).subscribe((res: string) => {
            this.notification.warn(res);
            });

        }

      });
     }
     else{
      this.translate.get('criterePwd.error').subscribe((res: string) => {
        this.notification.warn(res);
        });
     }

  }

  verification(a,b):boolean{
     return a<b
  }

  detail() {
    this.criterePwd = this.donnee;
    this.criterePwdForm.setValue({
      pwdId: this.criterePwd.pwdId,
      pwdCarMin: this.criterePwd.pwdCarMin ? this.criterePwd.pwdCarMin : null,
      pwdDigMin: this.criterePwd.pwdDigMin ? this.criterePwd.pwdDigMin : null,
      pwdMajMin: this.criterePwd.pwdMajMin ? this.criterePwd.pwdMajMin : null,
      pwdSpcMin: this.criterePwd.pwdSpcMin ? this.criterePwd.pwdSpcMin : null,
      pwdDure: this.criterePwd.pwdDure ? this.criterePwd.pwdDure : null,
    });

  }


  closeDialog() {
    this.dialogRef.closeAll();
  }

}
