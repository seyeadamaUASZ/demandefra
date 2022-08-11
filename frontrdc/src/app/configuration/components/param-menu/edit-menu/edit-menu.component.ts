import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../../shared/services/notification.service';
import { MenuService } from 'src/app/parametrage/services/menu.service';
import { Menu } from 'src/app/parametrage/models/menu';


@Component({
  selector: 'app-edit-menu',
  templateUrl: './edit-menu.component.html',
  styleUrls: ['./edit-menu.component.scss']
})
export class EditMenuComponent implements OnInit {
  menu:Menu;  
  menuForm = this.formbuild.group({
    menId: ['', Validators.required],
    menIdParent: [''],
    menNom: ['', Validators.required],
    menPath: ['', Validators.required],    
    menIcone: ['', Validators.required],
    menIconeColor: ['', Validators.required],   
  });
  constructor(private formbuild: FormBuilder, private router: Router,   
    private menuService: MenuService,
    private route: ActivatedRoute, public dialogRef: MatDialogRef<EditMenuComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private translate: TranslateService,
    private notification:NotificationService) { }

  ngOnInit() {
    this.initForm();
  }

  initForm(){
    //alert("--------------------------"+JSON.stringify(this.donnee.menu));
    this.menu = this.donnee.menu;
    this.menuForm.setValue({
      menId: this.menu.menId,
      menIdParent: this.menu.menIdParent,
      menNom: this.menu.menNom ? this.menu.menNom : null,
      menPath: this.menu.menPath ? this.menu.menPath : null,
      menIcone: this.menu.menIcone ? this.menu.menIcone : null,
      menIconeColor: this.menu.menIconeColor ? this.menu.menIconeColor : null,      
    });
  }
  get f() { return this.menuForm.controls; }
  onSubmit() {
    if (this.menuForm.valid) {
      this.menuService.updateMenu(this.menuForm.value).subscribe(data => {       
        if (data.statut) {
          let successEdit;
          this.translate.get('menu.success-edit').subscribe((res: string) => {
            this.notification.success(res);
          });
          /*this._snackBar.open(successEdit, 'Verification', {
            duration: 2000,
      });*/

          this.closeDialog();


        } else {
          let errorEdit;
          this.translate.get('menu.error-edit').subscribe((res: string) => {
            //errorEdit = res;
            this.notification.error(res);
          });
          /*this._snackBar.open(errorEdit, 'Verification', {
            duration: 2000,
      });*/
        }

      });
    } else {
      let invalidForm;
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
      /*this._snackBar.open(invalidForm, 'Confirmation', {
        duration: 2000,
      });*/
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
