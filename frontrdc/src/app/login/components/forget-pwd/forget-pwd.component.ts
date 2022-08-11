import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ResetpwdService } from '../../services/resetpwd.service';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';


@Component({
  selector: 'app-forget-pwd',
  templateUrl: './forget-pwd.component.html',
  styleUrls: ['./forget-pwd.component.scss']
})
export class ForgetPwdComponent implements OnInit {
   ForgetPwdForm = this.formbuild.group({
    pwrEmail: ['', Validators.required],
  });
  constructor(private formbuild: FormBuilder,
    private _snackBar: MatSnackBar, 
		private translate: TranslateService,
     private router: Router,private resetpwd: ResetpwdService,
     private notification:NotificationService,
     private paramService: ParametreService) {

  }
  appLogo;

  appName;
  imageAccueil;
  ngOnInit() {
    this.imageAccueil=localStorage.getItem('imageConnexion');
    this.appName = localStorage.getItem("appName");
    this.appLogo = localStorage.getItem("logo");

  }


  onSubmit() {
    if(this.ForgetPwdForm.valid){
      this.resetpwd.sendToken(this.ForgetPwdForm.value).subscribe(data => {
        //console.log(data);
        if (data.statut == true) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
         // alert(data.description);
          this.ForgetPwdForm.reset();
          this.router.navigate(['login/newPwd']);

        }else{
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
        //alert(data.description);
        }
      }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
          this.notification.warn(res);
        });
       // alert('Formulaire invalide');
      });
    }else{
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.warn(res);
      });
    }
  


  }


  
}
