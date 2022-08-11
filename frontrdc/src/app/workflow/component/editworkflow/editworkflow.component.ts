import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';

@Component({
  selector: 'app-editworkflow',
  templateUrl: './editworkflow.component.html',
  styleUrls: ['./editworkflow.component.scss']
})
export class EditworkflowComponent implements OnInit {
  workflowsect: any[];
 editForm = this.formbuild.group({
    wkfId: ['', Validators.required],
    name: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
    description: ['', Validators.required] ,
    groupId: ['', Validators.required],
    version: ['', Validators.required], 
    wkfSecteur: ['', Validators.required],
  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<EditworkflowComponent>, @Inject(MAT_DIALOG_DATA) public workflow: any) { }

  ngOnInit() {
    this.initView()
    this.WorkflowsListSecteur()
  }

  initView() {   
    this.editForm.setValue({      
      wkfId: this.workflow.wkfId,
      name: this.workflow.name,
      description: this.workflow.description,
      groupId: this.workflow.groupId,
      version: this.workflow.version, 
      wkfSecteur: this.workflow.wkfSecteur
    });  
  }
  WorkflowsListSecteur(){
    this.workflowService.WorkflowsListSecteur().subscribe(data => {
      if (data.statut) {
        this.workflowsect = data.data;  
      } else { 
      }

    })
  }

  f(){
    return this.editForm.controls;
  }

  onSubmit() {
    if (this.editForm.valid) {
      
    this.workflowService.updateWorkflow(this.editForm.value).subscribe(data => {
      if (data.statut) {
        this.editForm.reset();
        this.closeDialog();
        this.notification.success(data.description);
      } else {
        this.notification.warn(data.description);
      }
    }, error => {
      this.translate.get('Error.internalservererror').subscribe((res: string) => {

         this.notification.warn(res);
      });
    })
  }else {
    this.translate.get('invalid-form').subscribe((res: string) => {
         this.notification.warn(res);
      });
  }
  }

  closeDialog() {
    this.dialogRef.close();
  }


}
