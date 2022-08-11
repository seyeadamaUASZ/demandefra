import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { WorkflowService } from 'src/app/workflow/services/workflow.service';

import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-visualiserForm',
  templateUrl: './visualiserForm.component.html',
  styleUrls: ['./visualiserForm.component.scss']
})
export class VisualiserFormComponent implements OnInit {
  champsFormulaire:any=[]
  constructor(private router: Router,private workflowService:WorkflowService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public donnee: any) {

  }

  ngOnInit() {
    this.listFormulaire();
  }
 listFormulaire(){
   this.workflowService.listChampsFormulaire(this.donnee.jfrmId).subscribe(data=>{
    this.champsFormulaire=data.data
    console.log(JSON.stringify(this.champsFormulaire))
   })
 }
 fermer(){
  this.dialog.closeAll();
}
}
