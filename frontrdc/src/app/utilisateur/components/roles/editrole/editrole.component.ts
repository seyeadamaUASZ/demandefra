import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { RoleService } from '../../../services/role.service';
import { NotificationService } from '../../../../shared/services/notification.service';

@Component({
  selector: 'app-editrole',
  templateUrl: './editrole.component.html',
  styleUrls: ['./editrole.component.scss']
})
export class EditroleComponent implements OnInit {
  action:string;
  local_data:any;
 form = this.formbuild.group({
    proId: ['', Validators.required],
    proLibelle: ['', Validators.required],
    proDescription: ['', Validators.required]  
  });
  constructor(private formbuild: FormBuilder, private router: Router, private roleService: RoleService,
  private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<EditroleComponent>, @Inject(MAT_DIALOG_DATA) public role: any) { }

  ngOnInit() {
    this.initView()
  }

  initView() {   
    this.form.setValue({      
      proId: this.role.proId,
      proLibelle: this.role.proLibelle,
      proDescription: this.role.proDescription
    });  
  }

  onSubmit() {
    if(this.form.valid){
    this.roleService.updateProfil(this.form.value).subscribe(data => {
      if (data.statut) {
        this.translate.get('role.success-update').subscribe((res: string) => {
          this.notification.success(res);         
          });
        this.form.reset();
        this.closeDialog();
      }
    }, error => {
      this.translate.get('Formulaire invalide').subscribe((res: string) => {
          this.notification.error(res);         
          });     
    });
    } else {     
      this.translate.get('utilisateur.invalid-form').subscribe((res: string) => {       
         this.notification.error(res);
      });
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }

}