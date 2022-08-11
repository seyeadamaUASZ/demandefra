import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { WorkflowService } from 'src/app/workflow/services/workflow.service';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-addformulaire',
  templateUrl: './addformulaire.component.html',
  styleUrls: ['./addformulaire.component.scss']
})
export class AddformulaireComponent implements OnInit {
  loading:any;  
  selectedFile: File;
  containerId:any
  idwrkf:any 
  marked = false;
  theCheckbox = false; 
  addWrkForm = this.formbuild.group({
    nomform: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
    fichierform: ['', Validators.required],
    positionform: ['', Validators.required],
   

  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddformulaireComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
      this.containerId = this.elementform.wkfConteneur,
      this.idwrkf = this.elementform.idwrkf
  }

  ngOnInit() {

  }

   
  get f() {  return this.addWrkForm.controls; }
  handleUpload(event) {
    this.selectedFile = event.target.files[0];
    
  }  
  
  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  onSubmit(){ 
    if (this.addWrkForm.valid) {
      let formData = new FormData(); 
      formData.append("workflowform",JSON.stringify(this.addWrkForm.value)); 
      formData.append("containerId",this.containerId);
      formData.append("idwrkf",this.idwrkf);
      formData.append('fichierform', this.selectedFile,this.selectedFile.name); 
      
     this.workflowService.chargerjbpmform(formData).subscribe(data=>{
          this.notification.success('Document chargé');
          this.closeDialog();
         });
        }
  }
  closeDialog() {
    this.dialogRef.close();
  }


}
