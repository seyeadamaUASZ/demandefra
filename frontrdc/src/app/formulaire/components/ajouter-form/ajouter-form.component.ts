import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { Router } from '@angular/router';
import { MatDialogRef, MatSnackBar } from '@angular/material';
import { NotificationService } from '../../../shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { ApplicationService } from 'src/app/application/services/application.service';
import { FormulaireServiceService } from '../service/formulaireService.service';

@Component({
  selector: 'app-ajouter-form',
  templateUrl: './ajouter-form.component.html',
  styleUrls: ['./ajouter-form.component.scss']
})
export class AjouterFormComponent implements OnInit {

  FormulaireForm = this.formbuild.group({
    frmDescription: ['', Validators.required],
    frmNom: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private formService: FormulaireServiceService,
    private notification: NotificationService,
    private translate:TranslateService,
    public dialogRef: MatDialogRef<AjouterFormComponent>) {

  }

  ngOnInit() {

  }

get f(){
    return this.FormulaireForm.controls;
  }

  onSubmit() {
    if (this.FormulaireForm.valid) {
    this.formService.addFormulaire(this.FormulaireForm.value).subscribe(data => {
      console.log(data);
      if (data.statut) {
        this.notification.success(data.description);

        this.FormulaireForm.reset();
        this.closeDialog();
      }else{
        this.notification.warn(data.description);
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
       this.notification.error(res);     
      });
    });
    } else {
      this.translate.get('invalid-form').subscribe((res: string) => {
       this.notification.warn(res);     
      });
  }
  }


  closeDialog() {
    this.dialogRef.close();
  }
}
