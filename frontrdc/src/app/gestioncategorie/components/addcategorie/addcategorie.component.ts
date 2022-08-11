import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { CategorieService } from '../../service/categorie.service';

@Component({
  selector: 'app-addcategorie',
  templateUrl: './addcategorie.component.html',
  styleUrls: ['./addcategorie.component.scss']
})
export class AddcategorieComponent implements OnInit {

  constructor(private categorieS:CategorieService ,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<AddcategorieComponent> ) { }

  ngOnInit() {
  }

  EditForm = this.formBuilder.group({
    nom:['',Validators.required],
    prix:['',Validators.required],
    });


    onSubmit(){
  		if(this.EditForm.invalid){
      this.translate.get('categorie.remplirTousLesChampsNotif').subscribe(data=>{
        this.notification.warn(data);
      })
      return;
    }
      this.categorieS.addCategorie(this.EditForm.value ).subscribe((data:any) => {
        if (data.statut) {
          this.translate.get('categorie.confirmEnr').subscribe((res: string) => {
            this.notification.success(res);
          });

          this.EditForm.reset();
          this.dialog.close({status:false});
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('categorie.erreurEnr').subscribe((res: string) => {
          this.notification.error(res);
        });
      });

  	}

  	closeDialog(){
  		this.dialog.close({status:false});
      //this.router.navigate(['gestiondesservices'])
  	}

}
