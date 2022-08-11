import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';
import { AddtaskComponent } from '../addtask/addtask.component';
import { GestionTaskComponent } from '../gestion-task/gestion-task.component';

@Component({
  selector: 'app-add-transition',
  templateUrl: './add-transition.component.html',
  styleUrls: ['./add-transition.component.scss']
})
export class AddTransitionComponent implements OnInit {
  loading: any;
  workflowform: any[];
  selectedFile: File;
  containerId: any
  process_id: any
  processdef: any
  idwrkf: any
  taskId: any
  marked = false;
  theCheckbox = false;
  wrkform: any
  idtaskform:any
  addTrnForm = this.formbuild.group({
    trnId: [],
    trnAction: [''],
    trnTskActuel: [''],
    trnTskSuiv: [''],
    trnWkfId: [''],
  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification: NotificationService, private translate: TranslateService,
    public dialogRef: MatDialogRef<AddtaskComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
    this.containerId = this.elementform.wkfConteneur
    this.process_id = this.elementform.wkfProcess_id
    this.idwrkf = this.elementform.wkfid
    this.taskId = this.elementform.tskId

  }
  ngOnInit() {
    this.addTrnForm.setValue({
      trnId: "",
      trnAction: [''],
      trnTskActuel: this.elementform.idwrktsk,
      trnTskSuiv: [],
      trnWkfId: this.idwrkf,
    })
    this.listFormulaireworkflow(this.containerId)
    this.listetaskparid(this.idwrkf)
  }
  
  listetaskparid(idwrkf:any) {
    this.workflowService.listetaskparid(this.idwrkf).subscribe(data => {   
      if(data.statut){
        this.idtaskform =  data.data;
        console.log("++++++++++++"+JSON.stringify(this.idtaskform)) 
      } 
    })
  }
   

  //lier formulaire à un workflow par le containerId
  listFormulaireworkflow(containerId) {
    this.workflowService.listFormulaireworkflow(containerId).subscribe(data => {
      if (data.statut) {
        this.wrkform = data.data;
      }
    })
  }
  //Créer une transition
  onSubmit() {
    let formData = new FormData(); 
    formData.append("transitionform",JSON.stringify(this.addTrnForm.value));   
    this.workflowService.createTransition(formData).subscribe(data => {
      this.notification.success('transition créé');
      this.closeDialog();
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }


}
