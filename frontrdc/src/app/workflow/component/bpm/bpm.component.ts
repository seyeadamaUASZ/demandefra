import { Component, OnInit, Inject,Input, ViewChild } from '@angular/core';
import { FormGroup, FormControl,Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import {  MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatPaginator, MatSort, MatTableDataSource , MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { WorkflowService } from '../../services/workflow.service';
import {Workflow} from '../../models/workflow';
import { version } from 'punycode';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
@Component({
  selector: 'app-bpm',
  templateUrl: './bpm.component.html',
  styleUrls: ['./bpm.component.scss'],
   
})

export class BpmComponent implements OnInit {
  imgbpm:any
  workflow:any={wkfConteneur:"",wkfProcess_inst_id:""}; 
  balisebpm:string;
  logoHtml: SafeHtml;
  constructor( private router: Router, private workflowService: WorkflowService,
    public dialogRef: MatDialogRef<BpmComponent>, @Inject(MAT_DIALOG_DATA) public element: any,private sanitizer: DomSanitizer) { 
      this.workflow.wkfConteneur = this.element['task-container-id'];
      this.workflow.wkfProcess_inst_id = this.element['task-proc-inst-id'];
      
     
    }
    
  ngOnInit() { 
    this.initView();
}


 
initView() { 
  this.workflowService.displayBpm(this.element.wkfConteneur,this.element.wkfProcess_inst_id).subscribe(data=>{ 
    this.imgbpm =data.data  
    this.logoHtml = this.sanitizer.bypassSecurityTrustHtml(this.imgbpm);
    console.log(this.imgbpm, this.logoHtml);
 // console.log('++++++++++++++++++++++++++++++'+this.imgbpm) ;
})
}

}
