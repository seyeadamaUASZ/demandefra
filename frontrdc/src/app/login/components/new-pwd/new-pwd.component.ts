import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ResetpwdService } from '../../services/resetpwd.service';

@Component({
  selector: 'app-new-pwd',
  templateUrl: './new-pwd.component.html',
  styleUrls: ['./new-pwd.component.scss']
})
export class NewPwdComponent implements OnInit {
  message:any;
  appLogo;
  EnvoiTokenForm = this.formbuild.group({
    token: ['', Validators.required],
  });
  constructor(private formbuild: FormBuilder,
		private translate: TranslateService,
     private router: Router,private resetpwd: ResetpwdService,
     private notification:NotificationService) {

  }

  appName;
  imageAccueil;
  ngOnInit() {
    this.imageAccueil=localStorage.getItem('imageConnexion');
    this.appName = localStorage.getItem("appName");
    this.appLogo = localStorage.getItem("logo");

  }
  onSubmit() {
    if(this.EnvoiTokenForm.valid){
      this.resetpwd.verifToken(this.EnvoiTokenForm.value.token).subscribe(data => {
        //console.log(data);
         if (data.statut == true) {
           this.translate.get(data.description).subscribe((res: string) => {
             this.notification.success(res);
           });
          // alert(data.description);
           this.EnvoiTokenForm.reset();
           this.router.navigate(['/login']);
         }else{
           this.translate.get(data.description).subscribe((res: string) => {
             this.notification.success(res);
           });
         //alert(data.description);
         }
       }, error => {
        // this.router.navigate(['login/newPwd']);
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
