import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';

@Component({
  selector: 'app-addworkflow',
  templateUrl: './addworkflow.component.html',
  styleUrls: ['./addworkflow.component.scss']
})
export class AddworkflowComponent implements OnInit {
  loading:any;  
  workflow: any[];
  addForm = this.formbuild.group({
   name: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
    //groupId: [''],
    //version: [''], 
    description: ['', Validators.required], 
    wkfSecteur: ['', Validators.required],

    
  
  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddworkflowComponent>) {

  }

  ngOnInit() {
    this.WorkflowsListSecteur();
  }
  WorkflowsListSecteur(){
    this.workflowService.WorkflowsListSecteur().subscribe(data => {
      if (data.statut) {
        this.workflow = data.data; 
         
      } else {
        
      }

    })
  }
    
  get f() {   return this.addForm.controls;}
   
  onSubmit() { 
    if (this.addForm.valid) {
       
    this.workflowService.creatWorkflow(this.addForm.value,this.addForm.value).subscribe(data => {
      
      if (data) { 
       this.translate.get('workflow.creation').subscribe((res: string) => {           
        this.notification.success(res);
      });  
        this.addForm.reset();
        this.closeDialog();
      }
    }, error => {
      this.translate.get('workflow.creation').subscribe((res: string) => {           
        this.notification.success(res);
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
