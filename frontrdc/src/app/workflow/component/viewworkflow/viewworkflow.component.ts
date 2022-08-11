import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';
import { Workflow} from '../../models/workflow';
import { version } from 'punycode';

@Component({
  selector: 'app-viewworkflow',
  templateUrl: './viewworkflow.component.html',
  styleUrls: ['./viewworkflow.component.scss']
})
export class ViewworkflowComponent implements OnInit {
  element:any;
  workflowview: Workflow = null;
 form = this.formbuild.group({
    wkfId: ['', Validators.required],
    name: ['', Validators.required],
    description: ['', Validators.required], 
    version: ['', Validators.required],
    groupId: ['', Validators.required],
    containerId: ['', Validators.required],
    processId: ['', Validators.required]
  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<ViewworkflowComponent>, @Inject(MAT_DIALOG_DATA) public workflow: any) { }

  ngOnInit() {
    this.initView()

  }

  initView() {   
    this.workflowview = this.workflow;
    this.form.setValue({      
      wkfId: this.workflow.wkfId,
      name: this.workflow.name,
      description: this.workflow.description,
      version: this.workflow.version,
      groupId: this.workflow.groupId,
      containerId: this.workflow.containerId,
      processId: this.workflow.processId
    });  
  }
  

  closeDialog() {
    this.dialogRef.close();
  }


}
