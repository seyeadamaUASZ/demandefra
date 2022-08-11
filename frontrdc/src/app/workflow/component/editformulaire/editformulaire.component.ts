import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';

@Component({
  selector: 'app-editformulaire',
  templateUrl: './editformulaire.component.html',
  styleUrls: ['./editformulaire.component.scss']
})
export class EditformulaireComponent implements OnInit {

  loading:any;  
  workflowform: any[];
  selectedFile: File;
  containerId:any
  jfrmId:any 
  marked = false;
  theCheckbox = false;
  nomformedit:any
  addWrkForm: FormGroup;
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<EditformulaireComponent>, @Inject(MAT_DIALOG_DATA) public element: any) { 
      this.jfrmId = this.element.jfrmId
      this.nomformedit = this.element.jfrmFormulaire
  }


  ngOnInit() { 
    this.addWrkForm = this.formbuild.group({
      nomform:  ['', Validators.required], 
      fichierform: ['', Validators.required],
      positionform: ['', Validators.required], 
    }); 
    this.addWrkForm.setValue({
      nomform: this.nomformedit,  
    }); 
  }

   
  f(){
    return this.addWrkForm.controls;
  }
  handleUpload(event) {
    this.selectedFile = event.target.files[0];
    
  }  
  
  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  onSubmit(){ 
    
      let formData = new FormData(); 
      formData.append("workflowform",JSON.stringify(this.addWrkForm.value)); 
      formData.append("containerId",this.containerId);
      formData.append("idwrkf",this.jfrmId);
      formData.append('fichierform', this.selectedFile,this.selectedFile.name); 
      
     this.workflowService.chargerjbpmform(formData).subscribe(data=>{
          this.notification.success('Document chargé');
          this.closeDialog();
         });
   
  }
  closeDialog() {
    this.dialogRef.close();
  }

  
}
