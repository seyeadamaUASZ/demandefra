import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { AjoutParametreComponent } from '../ajout-parametre/ajout-parametre.component';
import { GenerateurSService } from '../../service/generateur-s.service';

@Component({
  selector: 'app-edit-parametre',
  templateUrl: './edit-parametre.component.html',
  styleUrls: ['./edit-parametre.component.scss']
})
export class EditParametreComponent implements OnInit {
  
  addForm = this.formbuild.group({
    libelle: [this.element.libelle, Validators.required],
    evenement: [this.element.evenement, Validators.required],
    dureee: [this.element.dureee],
    duree:[this.element.duree],
    caractere:[this.element.caractere],
    typeOtp:[this.element.typeOtp, Validators.required],
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EditParametreComponent>, private translate: TranslateService,
    private notification: NotificationService,private otpS:GenerateurSService,
    @Inject(MAT_DIALOG_DATA) public element: any) { 

    }

  ngOnInit() {
  }

  closeDialog() {
    this.dialogRef.close();
  }

  onSubmit(){
    //console.log("formulaire: "+JSON.stringify(this.addForm.value))
     if(this.addForm.valid){
       this.otpS.modifierParametre(this.element.idParametreOtp,this.addForm.value)
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
