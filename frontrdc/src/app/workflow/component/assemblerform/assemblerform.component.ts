import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core'; 
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { WorkflowService } from 'src/app/workflow/services/workflow.service';
@Component({
  selector: 'app-assemblerform',
  templateUrl: './assemblerform.component.html',
  styleUrls: ['./assemblerform.component.scss']
})
export class AssemblerformComponent implements OnInit {
  timePeriods = [
    '1',
    '2',
    '3',
    '4',
    '5'
  ];

  
  wrkform:any
  wrkformGenere:any
  idwrkf:any
containerId:any 
nomform:any
  constructor(private formbuild: FormBuilder, private router: Router,  
      private translate:TranslateService,private workflowService:WorkflowService,
    public dialogRef: MatDialogRef<AssemblerformComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any ) {
      this.containerId = this.elementform.wkfConteneur,
      this.idwrkf = this.elementform.idwrkf
  }

  ngOnInit() {
   this.listFormulaireworkflowGenerer()
  }
  listFormulaireworkflowGenerer() {
    this.workflowService.listFormulaireGenerer(this.containerId).subscribe(data => {
    if(data.statut){
      this.wrkformGenere = data.data; 
      for (var i = 0; i < this.wrkformGenere.length; i++){      
          this.nomform = this.wrkformGenere[i]['jfrmFormulaire']; 
          console.log("++++++++"+JSON.stringify(this.nomform));
         
      } 

    }

    })

  }
  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.wrkformGenere, event.previousIndex, event.currentIndex);
    console.log("++++++++"+JSON.stringify(this.nomform));
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
