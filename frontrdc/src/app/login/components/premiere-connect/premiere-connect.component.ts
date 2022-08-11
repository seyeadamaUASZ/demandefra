import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
  selector: 'app-premiere-connect',
  templateUrl: './premiere-connect.component.html',
  styleUrls: ['./premiere-connect.component.scss']
})
export class PremiereConnectComponent implements OnInit {
  appName;
  appLogo;
  constructor(private userService: UserService,
    private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private translate: TranslateService,
    private notification: NotificationService) { }
  user;
  token: any;
  changeForm = this.formBuilder.group({
    username: [localStorage.getItem('username'), Validators.required],
    oldPwd: ['', Validators.required],
    newPwd: ['', Validators.required],
    confirmPwd: ['', Validators.required],
  });
  imageAccueil;
  ngOnInit() {
    this.imageAccueil = localStorage.getItem('imageConnexion');
    this.user = localStorage.getItem('username');
    this.token = localStorage.getItem('token');
    let appLogo = localStorage.getItem('logo');

    //To avoid relogin
    localStorage.removeItem('token');
  }

  onSubmit() {
    if (!this.changeForm.invalid) {
      if (this.changeForm.controls['newPwd'].value === this.changeForm.controls['oldPwd'].value) {
        this.translate.get('ancienEtNouveauCorrespondent').subscribe((res: string) => {
          this.notification.warn(res);
        });
      }
      else if (this.changeForm.controls['newPwd'].value === this.changeForm.controls['confirmPwd'].value) {
        this.userService.changepwd(this.changeForm.value, this.token).subscribe(data => {
          if (data.statut) {
            this.router.navigate(['/home']);
            this.translate.get(data.description).subscribe((res: string) => {
              this.notification.success(res);
            });

          } else {
            this.translate.get(data.description).subscribe((res: string) => {
              this.notification.warn(res);
            });

          }
        });
      } else {
        this.translate.get('La confirmation du nouveau mot de passe est incorrecte').subscribe((res: string) => {
          this.notification.warn(res);
        });
      }
    } else {
      this.translate.get('Formulaire invalide').subscribe((res: string) => {
        this.notification.warn(res);
      });
    }
  }

}
