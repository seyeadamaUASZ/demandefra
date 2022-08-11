import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ApplicationService } from '../../services/application.service';
import { FonctionnaliteService } from '../../services/fonctionnalite.service';
import { AjoutAppComponent } from '../ajout-app/ajout-app.component';

@Component({
  selector: 'app-edit-module',
  templateUrl: './edit-module.component.html',
  styleUrls: ['./edit-module.component.scss']
})
export class EditModuleComponent implements OnInit {

  application: [];
  fonctionnalite: any[];

  ModuleForm = this.formbuild.group({
    modNom: [this.module.modNom, Validators.required],
    modCode: [this.module.modCode, Validators.required],
    modDescription: [this.module.modDescription],
    fonModId: [''],
  });
  constructor(private formbuild: FormBuilder, private router: Router,
    private userService: UserService, private _snackBar: MatSnackBar,
    private fonctionnaliteService: FonctionnaliteService,
    private applicationService: ApplicationService,
    public dialogRef: MatDialogRef<AjoutAppComponent>, private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public module: any) {

  }

  ngOnInit() {
    this.applicationService.listeApplication().subscribe(res => {
      this.application = res.data;
      console.log(this.application)


    });
  }
  //get f() { return this.ModuleForm.controls; }


  onSubmit() {
    if(this.ModuleForm.valid){
      this.applicationService.addModule(this.ModuleForm.value).subscribe(data => {
        console.log(data);
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
  
          this.ModuleForm.reset();
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
  closeDialog() {
    this.dialogRef.close();
  }
}
