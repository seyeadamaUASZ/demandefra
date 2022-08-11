import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service'; 

@Component({
  selector: 'app-addconfig-timer',
  templateUrl: './addconfig-timer.component.html',
  styleUrls: ['./addconfig-timer.component.scss']
})
export class AddconfigTimerComponent implements OnInit {
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
  tasknfos: any[];
  selectedtypetimer:any 
  typetimercontent:any
  addTmForm = this.formbuild.group({ 
    titretimer: [''],
    tmTskActuel: [''],
    tmTskSuiv: [''],
    valuetimer:[''], 
    workflowid: ['', Validators.required],
  });
    selectedLink: any; 
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification: NotificationService, private translate: TranslateService,
    public dialogRef: MatDialogRef<AddconfigTimerComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
    this.containerId = this.elementform.wkfConteneur
    this.process_id = this.elementform.wkfProcess_id
    this.idwrkf = this.elementform.wkfid
    this.taskId = this.elementform.idwrktsk


  }
  ngOnInit() {  
    this.addTmForm.controls['workflowid'].setValue(this.idwrkf);
    this.addTmForm.controls['tmTskActuel'].setValue(this.taskId);  
    this.listetaskparid(this.idwrkf)
    
  }
  setradio(e: string): void {
    this.selectedLink = e;  
}  

isSelected(name: string): boolean {  

    if (!this.selectedLink) { // if no radio button is selected, always return false so every nothing is shown  
        return false;  
    }    

    return (this.selectedLink === name); // if current radio button is selected, return true, else return false  
}  
  listetaskparid(idwrktsk:any) {
    this.workflowService.listetaskparid(this.idwrkf).subscribe(data => {   
      if(data.statut){
        this.tasknfos = data.data;
      }
      
    })
  }
  //Créer une transition
  onSubmit() {
   let  selectedtypevar  = {
    typetimercontent : this.selectedtypetimer
   }
    let formData = new FormData(); 
    formData.append("configtmform",JSON.stringify(this.addTmForm.value));  
    formData.append("selectedtypevar",JSON.stringify(selectedtypevar)); 
    formData.append("idtask",JSON.stringify(selectedtypevar));  
   this.workflowService.createConfigTimer(formData).subscribe(data => {
      this.notification.success('Config créé');
      this.closeDialog();
    }); 
  }

  fieldsChange(values:any):void {
    this.selectedtypetimer = values.currentTarget.value
  }
 
  closeDialog() {
    this.dialogRef.close();
  }


}
