import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../../shared/services/notification.service';
import { MenuService } from 'src/app/parametrage/services/menu.service';
import { Menu } from 'src/app/parametrage/models/menu';


@Component({
  selector: 'app-add-menu',
  templateUrl: './add-menu.component.html',
  styleUrls: ['./add-menu.component.scss']
})
export class AddMenuComponent implements OnInit {
  menu:Menu;  
  menuForm = this.formbuild.group({
    //menId: ['', Validators.required],
    menIdParent: [''],   
    menNom: ['', Validators.required],
    menPath: ['', Validators.required],    
    menIcone: ['', Validators.required],
    menIconeColor: ['', Validators.required],   
  });
  constructor(private formbuild: FormBuilder, private router: Router,   
    private menuService: MenuService,
    private route: ActivatedRoute, public dialogRef: MatDialogRef<AddMenuComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private translate: TranslateService, 
    private notification:NotificationService) { }

  ngOnInit() {
    this.initForm();
  }

  initForm(){  
    this.menu = new Menu();
    if(this.donnee)
      this.menu.menIdParent = this.donnee.menu.menId;        
  }
  get f() { return this.menuForm.controls; }
  onSubmit() {
    
    this.menuForm.value.menIdParent = this.menu.menIdParent;   
    if (this.menuForm.valid) {
      this.menuService.saveMenu(this.menuForm.value).subscribe(data => {       
        if (data.statut) {
          //let successEdit;
          this.translate.get('menu.success-edit').subscribe((res: string) => {
            this.notification.success(res);
          });          
          this.closeDialog();
        } else {
            //let errorEdit;
            this.translate.get('menu.error-edit').subscribe((res: string) => {           
            this.notification.error(res);
          });         
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
