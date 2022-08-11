import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material';
import { RoleService } from '../../../services/role.service';
import { NotificationService } from '../../../../shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-addrole',
  templateUrl: './addrole.component.html',
  styleUrls: ['./addrole.component.scss']
})
export class AddroleComponent implements OnInit {

  addForm = this.formbuild.group({
    proLibelle: ['', Validators.required],
    proDescription: ['', Validators.required],
    nouvelleInscri:['']  
  });
  constructor(private formbuild: FormBuilder, private router: Router, private roleService: RoleService, 
     private notification:NotificationService,
    private translate:TranslateService,
     public dialogRef: MatDialogRef<AddroleComponent>) {

  }

  ngOnInit() {
  }

  onSubmit() {   
    if(this.addForm.valid){
    this.roleService.createProfil(this.addForm.value).subscribe(data => {
      if (data.statut) {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.success(res);         
          });
        this.addForm.reset();
        this.closeDialog();
      } else {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.warn(res);         
          });
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