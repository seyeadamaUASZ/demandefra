
import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { FonctionnaliteService } from 'src/app/application/services/fonctionnalite.service';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-ajout-fonc',
  templateUrl: './ajout-fonc.component.html',
  styleUrls: ['./ajout-fonc.component.scss']
})
export class AjoutFoncComponent implements OnInit {

  loading: any;
  idf;
  fontionnalite: any;
  addForm = this.formbuild.group({
    fonNom: ['', Validators.required],
    fonDescription: ['', Validators.required],
    fonCode: [''],
    fonModId: [''],

  });
  addAppFonc = this.formbuild.group({
    appliFoncFonId: ['', Validators.required],
    appliFoncAppId: ['', Validators.required],
    applifonisActive: ['', Validators.required],

  });


  constructor(private formbuild: FormBuilder, private router: Router, private fonctionnaliteService: FonctionnaliteService,

    public dialogRef: MatDialogRef<AjoutFoncComponent>, private _snackBar: MatSnackBar,
    private route: ActivatedRoute, @Inject(MAT_DIALOG_DATA) public id: any, private notification: NotificationService, private translate: TranslateService, ) { }

  ngOnInit() {
  }

  onSubmit() {

      this.addForm.value.fonModId = this.id;
      if(this.addForm.invalid){
        this.translate.get('invalid-form').subscribe((res: string) => {
          this.notification.info(res);
        });
      }else{
        this.fonctionnaliteService.creatFonctioonalite(this.addForm.value).subscribe(data => {
          if (data.statut) {
            this.translate.get(data.description).subscribe((res: string) => {
              this.notification.success(res);
            });
            console.log(data);
            this.addForm.reset();
            this.closeDialog();
  
          }else{
            this.translate.get('fonctionnalite.erreure').subscribe((res: string) => {
              this.notification.warn(res);
            });
          }
        }, error => {
          this.translate.get('Error.internalservererror').subscribe((res: string) => {
            this.notification.error(res);
          });
        });
      }
     
  }
  closeDialog() {
    this.dialogRef.close();
  }

}
