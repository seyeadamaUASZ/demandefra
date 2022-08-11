import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from '../../../../shared/services/notification.service';

import { Action } from 'src/app/parametrage/models/Action';
import { ActionService } from 'src/app/parametrage/services/action.service';

@Component({
  selector: 'app-add-action',
  templateUrl: './add-action.component.html',
  styleUrls: ['./add-action.component.scss']
})
export class AddActionComponent implements OnInit {
   action:Action;  
   actionForm = this.formbuild.group({
    actMenId: [''],   
    actCode: ['', Validators.required],
    actNom: ['', Validators.required],    
    actDescription: ['', Validators.required] 
  });
  constructor(private formbuild: FormBuilder, private router: Router,   
    private actionService: ActionService,
    private route: ActivatedRoute, public dialogRef: MatDialogRef<AddActionComponent>,
    @Inject(MAT_DIALOG_DATA) public donnee: any, private translate: TranslateService, 
    private notification:NotificationService) { }

  ngOnInit() {   
  }

  get f() { return this.actionForm.controls; }
  onSubmit() {              
    if (this.actionForm.valid) {
     // let action = new Action();
      let action  = {
        "actCode": this.actionForm.value.actCode,
        "actDescription": this.actionForm.value.actDescription,
        "actNom": this.actionForm.value.actNom,
        "actMenId": {"menId": this.donnee.menId}
      };
      let menu = {
        "menId": this.donnee.menId
      };
    
      /*menu.menId = this.donnee.menId
      action.actCode = this.actionForm.value.actCode;
      action.actDescription = this.actionForm.value.actDescription;
      action.actNom = this.actionForm.value.actNom;
      action.setActMenId(menu.menId);*/
      this.actionService.saveAction(action, menu).subscribe(data => {       
        if (data.statut) {
          //let successEdit;
          this.translate.get('Action.success-edit').subscribe((res: string) => {
            this.notification.success(res);
          });          
          this.closeDialog(action);
        } else {
            //let errorEdit;
            this.translate.get('Action.error-edit').subscribe((res: string) => {           
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

  closeDialog(action) {
    this.dialogRef.close(action);
  }

}
