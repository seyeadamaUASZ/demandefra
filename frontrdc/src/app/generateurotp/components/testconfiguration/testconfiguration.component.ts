import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { GenerateurSService } from '../../service/generateur-s.service';

@Component({
  selector: 'app-testconfiguration',
  templateUrl: './testconfiguration.component.html',
  styleUrls: ['./testconfiguration.component.scss']
})
export class TestconfigurationComponent implements OnInit {
   
  testForm = this.formbuild.group({
    numero: [''],
    code: [''],
    email:[''],
    codeemail:['']  
  });

  codeOtp:any;
  codeOtpen:any; 
  constructor(public dialogRef: MatDialogRef<TestconfigurationComponent>,
     private translate: TranslateService,
     private notification: NotificationService,private otpS:GenerateurSService,
     private formbuild: FormBuilder, private router: Router,
    @Inject(MAT_DIALOG_DATA) public element: any) { }
    type:any;

  ngOnInit() {
    this.type=this.element.typeOtp;
    //console.log('libelle '+this.element.libelle)
    //this.generateCodeOtp();
  }


  closeDialog() {
    this.dialogRef.close();
  }


  changeValue(){
    //console.log("value numero "+this.testForm.value.numero)
    this.envoyercodeNumero(this.testForm.value.numero);
   
  }
  

  onSubmit(){
     let code = localStorage.getItem('codeotp')
     //console.log("code envoye "+code);
     if(this.testForm.value.code===code){
      this.translate.get("Code valide !!!").subscribe((res: string) => {
        this.notification.success(res);
      });
     }else{
      this.translate.get("code non valide !!").subscribe((res: string) => {
        this.notification.error(res);
      });
     }

     this.closeDialog();
  }


  onSubmit1(){
    this.otpS.verifiercode(this.testForm.value.code)
      .subscribe(data=>{
        if(data.statut){
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
        }else{
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.error(res);
          });
        }
      })
  }

  generateCodeOtp(){
    this.otpS.generatecodeOtp(this.element.typeOtp)
    .subscribe(data=>{
      this.codeOtp=data.data;
      
    })
  }


  envoyer(){
    this.otpS.envoyercodeOtp(this.element.typeOtp,"778094925")
    .subscribe(data=>{
      if (data.statut) {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.success(res);
        });
      }
    },err=>{
      console.log("err");
    })
  }

  //envoyer autre méthode
  envoyercodeNumero(numero){
    this.otpS.envoyerCodeOtp1(this.element.libelle,numero)
    .subscribe(data=>{
      if(data.statut){
        this.codeOtpen=data.data
        //console.log("code otp envoye depuis sms "+this.codeOtpen)
        localStorage.setItem('codeotp',this.codeOtpen)
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.success(res);
        });    
      }
    },err=>{
      console.log(err)
    })
  }

}
