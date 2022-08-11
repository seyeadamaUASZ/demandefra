import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatSnackBar, MatDialog } from '@angular/material';
import { ToolbarComponent } from 'src/app/sharedcomponent/toolbar/toolbar.component';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { InscriptionService } from '../../services/inscription.service';
import { AuthService } from 'src/app/utilisateur/services/auth.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-desinscrire',
  templateUrl: './desinscrire.component.html',
  styleUrls: ['./desinscrire.component.scss']
})
export class DesinscrireComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ToolbarComponent>,
    private dialog: MatDialog,
    private inscriptionService: InscriptionService,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private translate: TranslateService,
    private authService: AuthService,
    private router: Router,
    private notification: NotificationService) { }
  user;
  session: any;
  changeForm = this.formBuilder.group({
    username: [localStorage.getItem('username'), Validators.required],
    oldPwd: ['', Validators.required],
  });

  ngOnInit() {
    this.user = localStorage.getItem('username');
  }

  dialogClose() {
    this.dialogRef.close();
  }

  onSubmit() {
    if (!this.changeForm.invalid) {
      this.inscriptionService.desinscrire(this.changeForm.value, localStorage.getItem('token')).subscribe(data => {
        if (data.statut) {
          if (!this.session || this.session === 'undefined') {
            localStorage.removeItem('token');
            localStorage.removeItem('session');
            sessionStorage.removeItem('menus');
            sessionStorage.removeItem('accesmenus')
          } else {
            this.authService.deconnecter(this.session).subscribe(data => {
              if (data.statut) {
                localStorage.removeItem('prenom');
                localStorage.removeItem('nom');
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                localStorage.removeItem('id');
                localStorage.removeItem('session');
                sessionStorage.removeItem('menus');
                sessionStorage.removeItem('accesmenus')     
              }
            }, error => {
              console.log(error);
  
            });
          }
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          this.dialogClose();
          this.router.navigate(['/landing']);
        } else {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.warn(res);
          });
        }

      });

    } else {
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
    }
  }

}
