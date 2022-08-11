import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { GenerateurSService } from '../../service/generateur-s.service';

@Component({
  selector: 'app-ajout-parametre',
  templateUrl: './ajout-parametre.component.html',
  styleUrls: ['./ajout-parametre.component.scss']
})
export class AjoutParametreComponent implements OnInit {
  addForm = this.formbuild.group({
    libelle: ['', Validators.required],  
    evenement: ['', Validators.required],  
    typeOtp:['', Validators.required],
    caractere:['',Validators.required],
    dureee:['',Validators.required],
    duree:['',Validators.required]  
   // wdgType:['', Validators.required],  
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private translate:TranslateService,private notification:NotificationService,private otpS:GenerateurSService,
    public dialogRef: MatDialogRef<AjoutParametreComponent>) { }

  ngOnInit() {
  }

  closeDialog() {
    this.dialogRef.close();
    //this.router.navigate(['generateurotp']);
  }

  onSubmit(){
     if(this.addForm.valid){
       this.otpS.addParametre(this.addForm.value)
       .subscribe(data=>{
        console.log(data);
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
  
          this.addForm.reset();
          this.closeDialog();
        }
       }, error => {
        this.translate.get(error).subscribe((res: string) => {
          this.notification.error(res);
        });
      });
    }else{
      let invalidForm;
      this.translate.get('application.invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
    }
  
     }
  }

