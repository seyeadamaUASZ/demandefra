import { Component, OnInit, Inject } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatDialog, MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { Menu } from 'src/app/utilisateur/models/menu';
import { FichierService } from '../../services/fichier.service';
import { Rapport } from '../../models/rapport';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
  selector: 'app-edit-sous-menu',
  templateUrl: './edit-sous-menu.component.html',
  styleUrls: ['./edit-sous-menu.component.scss']
})
export class EditSousMenuComponent implements OnInit {
  sousMenu: Menu;
  menuForm = this.formbuild.group({
    menNom: ['', Validators.required]
  });
  
  constructor(
    private formbuild: FormBuilder,
    private router: Router,
    private fichierService: FichierService,
    private readonly translate: TranslateService,
    private notification: NotificationService,
    public dialogRef: MatDialog,
    @Inject(MAT_DIALOG_DATA) public donnee: any
  ) {
  }

  ngOnInit() {
    this.detail();
  }

  detail() {
    this.sousMenu = this.donnee;
    this.menuForm.setValue({
      menNom: this.sousMenu.menNom
    });

  }

  onSubmit() {
    if(!this.menuForm.valid){
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.error(res);
       });
    }else{
      this.sousMenu.menNom = this.menuForm.value.menNom;
      this.fichierService.createSousMenu(this.sousMenu).subscribe(data => {
        if (data.statut) {
          this.translate.get('fichier.successSaveSousMenu').subscribe((res: string) => {
            this.notification.success(res);
        });
          this.menuForm.reset();
          this.closeDialog();

        }
      }, error => {
       this.translate.get('fichier.errorSaveSousMenu').subscribe((res: string) => {
          this.notification.error(res);
         });
      });
    }
      
  }
  closeDialog() {
    this.dialogRef.closeAll();
  }

}
