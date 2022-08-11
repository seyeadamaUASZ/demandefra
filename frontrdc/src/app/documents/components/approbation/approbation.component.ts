import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { DocumentService } from '../../services/document.service';
import { SignatureComponent } from '../signature/signature.component';

@Component({
  selector: 'app-approbation',
  templateUrl: './approbation.component.html',
  styleUrls: ['./approbation.component.scss']
})
export class ApprobationComponent implements OnInit {
  renvoi:boolean = false;
  countrenvoie:number = 0;
  code;
  constructor(public dialogRef: MatDialogRef<SignatureComponent>,
    private documentService:DocumentService,
     @Inject(MAT_DIALOG_DATA) public donnee: any,private translate:TranslateService,private notification: NotificationService) { }

  ngOnInit() {
    this.renvoiControl()
    this.envoiCode()
  }

  envoiCode(){
    this.documentService.envoyerCode(localStorage.getItem('username'),this.donnee).subscribe((data:any)=>{
      this.translate.get('document.codeenvoye').subscribe((res)=>{
        this.notification.info(res)
      })
    })
  }

  verifyCode(){
    if(this.code=='' || this.code ==undefined){
      this.translate.get('document.approbationSaisirCodeEnvoye').subscribe((res)=>{
        this.notification.warn(res)
      })
      return;
    }
    this.documentService.verifyCode(localStorage.getItem('username'),this.code,this.donnee).subscribe((data:any)=>{
      if(data.statut){
        this.translate.get('document.approbationSuccess').subscribe((res)=>{
          this.notification.success(res)
        })
        this.closeDialog();
        
      }else{
        this.translate.get('document.approbationFailed').subscribe((res)=>{
          this.notification.warn(res)
        })
      }
    })
  }

  

  renvoiControl(){
    setTimeout(()=>{
      this.renvoi = true;
      this.countrenvoie += 30000;

    },this.countrenvoie)
  }
  renvoyer(){
    this.renvoi = false;
    this.envoiCode();
    this.renvoiControl()
  }

  closeDialog(){
    this.dialogRef.close()
  }
}
