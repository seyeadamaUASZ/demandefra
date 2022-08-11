import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { WorkflowService } from 'src/app/workflow/services/workflow.service';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-addformulairev2',
  templateUrl: './addformulairev2.component.html',
  styleUrls: ['./addformulairev2.component.scss']
})
export class Addformulairev2Component implements OnInit {
  loading:any;  
  selectedFile: File;
  containerId:any
  idwrkf:any 
  idform:any
  marked = false;
  theCheckbox = false; 
  addWrkForm = this.formbuild.group({
    nomform: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9_]*$')]],  
    idform: ['', Validators.required],
    positionform: ['', Validators.required],
   

  });
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<Addformulairev2Component>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
      this.containerId = this.elementform.wkfConteneur,
      this.idwrkf = this.elementform.idwrkf
  }

  ngOnInit() {
    this.listeFormulaireNonGenerer()
  }

  listeFormulaireNonGenerer(){
    this.workflowService.getList().subscribe((data:any)=>{
      this.idform =  data.data;
      console.log("++++++++++++"+JSON.stringify(this.idform))
       
    })
  }
  get f() {  return this.addWrkForm.controls; }
   
  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  onSubmit(){ 
    if (this.addWrkForm.valid) {
      let formData = new FormData(); 
      formData.append("workflowform",JSON.stringify(this.addWrkForm.value));  
      formData.append("idwrkf",this.idwrkf); 
      
     this.workflowService.chargerjbpmform2(formData).subscribe(data=>{
          this.notification.success('formulaire chargé');
          this.closeDialog();
         });
        }
  }
  closeDialog() {
    this.dialogRef.close();
  }


}
