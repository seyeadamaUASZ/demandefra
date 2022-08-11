import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';
import { UserService } from '../../../utilisateur/services/user.service';
@Component({
  selector: 'app-addtask',
  templateUrl: './addtask.component.html',
  styleUrls: ['./addtask.component.scss']
})
export class AddtaskComponent implements OnInit {
  loading:any;  
  workflowform: any[];
  selectedFile: File;
  containerId:any
  process_id:any
  processdef:any
  idwrkf:any 
  marked = false;
  theCheckbox = false;
  theCheckbox2 = false;
  profiles: any[];

  status: any[];
  tasknfos: any[];
  idwrktsk:any
  wrkform:any
  idform:any
  addTskForm = this.formbuild.group({
    ownername:  ['', Validators.required],  
    taskname:['', Validators.required],   
    tasknamesuivant:[''],  
    positionform: ['', Validators.required], 
    endtask: ['', Validators.required],
    taskdescription: ['', Validators.required],  
    statusId: ['', Validators.required],



  });
  constructor(private formbuild: FormBuilder, private router: Router,private userService: UserService, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddtaskComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
      this.containerId = this.elementform.wkfConteneur
      this.process_id = this.elementform.wkfProcess_id
      this.idwrkf  = this.elementform.wkfid
  }
  ngOnInit() {
    
    this.extractProfilId()

    this.extractStatusId()
    this.listFormulaireworkflow(this.containerId)
    this.listeFormulaireNonGenerer()
  }
  listeFormulaireNonGenerer(){
    this.workflowService.getList().subscribe((data:any)=>{
      this.idform =  data.data;
      console.log("++++++++++++"+JSON.stringify(this.idform))
       
    })
  }
  extractProfilId(){
  this.userService.listprofil().subscribe(res => {
    this.profiles = res.data;
 
});
}
listFormulaireworkflow(containerId) {
  this.workflowService.listFormulaireworkflow(containerId).subscribe(data => {
  if(data.statut){
    this.wrkform = data.data;
   
  }

  })
}
extractStatusId(){
  this.workflowService.taskStatusAll().subscribe(res => {
    this.status =  res.data;
    console.log("+++++++++++++"+JSON.stringify(this.status));


});
}
   
  onSubmit(){ 
   
    let formData = new FormData(); 
    formData.append("taskgenform",JSON.stringify(this.addTskForm.value));  
    formData.append("idwrkf",this.idwrkf); 
   this.workflowService.createtask(formData).subscribe(data=>{
        this.notification.success('Document chargé');
        this.closeDialog();
       }); 
  }

  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  closeDialog() {
    this.dialogRef.close();
  }


}
