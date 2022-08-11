import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../../shared/services/notification.service';
import { WorkflowService } from '../../../../procedures/services/workflow.service';


@Component({
  selector: 'app-addsecteur',
  templateUrl: './addsecteur.component.html',
  styleUrls: ['./addsecteur.component.scss']
})
export class AddsecteurComponent implements OnInit {
  loading:any; 
  addForm = this.formbuild.group({
    wsSecteur: ['', Validators.required],  
    codeSecteur: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddsecteurComponent>) {

  }

  ngOnInit() {
    
  }

   
   
  onSubmit() { 
    if (this.addForm.valid) { 
    this.workflowService.createSecteur(this.addForm.value).subscribe(data => {
      
      if (data) {
        //console.log(data);
        this.addForm.reset();
        this.closeDialog();
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {
         this.notification.warn(res);
      });
          
    }) 
  }else {
    this.notification.warn('Formulaire invalide');    
  }
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
