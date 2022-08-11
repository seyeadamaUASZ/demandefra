import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { CategorieService } from '../../service/categorie.service';

@Component({
  selector: 'app-editcategorie',
  templateUrl: './editcategorie.component.html',
  styleUrls: ['./editcategorie.component.scss']
})
export class EditcategorieComponent implements OnInit {

  EditForm = this.formBuilder.group({
    nom: [this.donnee.nom, Validators.required],
    prix: [this.donnee.prix, Validators.required],
    });

  constructor(private categorieS: CategorieService,
    private router: Router, private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
    public dialog: MatDialogRef<EditcategorieComponent>) { }

  ngOnInit() {
  }
  
  onSubmit(){
    if(this.EditForm.invalid){
    this.translate.get('categorie.remplirTousLesChampsNotif').subscribe(data=>{
      this.notification.warn(data);
    })
    return;
  }
    this.categorieS.updateCategorie(this.donnee.id,this.EditForm.value).subscribe((data:any) => {
      if (data.statut) {
        this.translate.get('categorie.confirmEnr').subscribe((res: string) => {
          this.notification.success(res);

        });
        this.dialog.close({status:true});
        this.EditForm.reset();

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
