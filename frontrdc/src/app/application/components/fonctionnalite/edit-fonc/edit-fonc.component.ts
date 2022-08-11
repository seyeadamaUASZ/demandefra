
import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { FonctionnaliteService } from 'src/app/application/services/fonctionnalite.service';


@Component({
  selector: 'app-edit-fonc',
  templateUrl: './edit-fonc.component.html',
  styleUrls: ['./edit-fonc.component.scss']
})
export class EditFoncComponent implements OnInit {
  form = this.formbuild.group({
    fonId: ['', Validators.required],
    fonNom: ['', Validators.required],
    fonDescription: ['', Validators.required],
    fonCode: ['', Validators.required],
    fonModId: ['', Validators.required],
  });
  
  constructor(private formbuild: FormBuilder, private router: Router, private fonctionnaliteService: FonctionnaliteService,

    public dialogRef: MatDialogRef<EditFoncComponent>, 
    @Inject(MAT_DIALOG_DATA) public fonctionnalite: any, private translate: TranslateService,
    private notification:NotificationService) { }

  ngOnInit() {
    this.initView();
  }

  initView() {
    this.form.setValue({
      fonId: this.fonctionnalite.fonId,
      fonNom: this.fonctionnalite.fonNom,
      fonDescription: this.fonctionnalite.fonDescription,
      fonCode: this.fonctionnalite.fonCode,
      fonModId: this.fonctionnalite.fonModId
    });
  }
  onSubmit() {
    if (this.form.valid){
    this.fonctionnaliteService.updateFonctionnalite(this.form.value).subscribe(data => {
      if (data.statut) {
        this.translate.get('fonctionnalite.success-edit').subscribe((res: string) => {           
          this.notification.success(res);
        });
        this.form.reset();
        this.closeDialog();
      }else {
          let errorEdit;
          this.translate.get('fonctionnalite.error-edit').subscribe((res: string) => {          
            this.notification.error(res);
          });          
        }
    });
  }
  else {
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
