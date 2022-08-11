import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { WorkflowService } from '../../services/workflow.service';


@Component({
  selector: 'app-editworkflowtask',
  templateUrl: './editworkflowtask.component.html',
  styleUrls: ['./editworkflowtask.component.scss']
})
export class EditworkflowtaskComponent implements OnInit {
  loading:any;  
  workflowform: any[];
  selectedFile: File;
  containerId:any
  process_id:any
  processdef:any
  idwrktsk:any 
  idwrkf:any
  idtsk:any
  marked = false;
  theCheckbox = false;
  profiles: any[];
  tasknfos: any[];

  status: any[];

  addTskForm = this.formbuild.group({
    ownername:  ['', Validators.required],  
    taskname:['', Validators.required],   
    positionform: ['', Validators.required], 
    taskdescription: ['', Validators.required], 
    
    Tasknamesuivant: [null],  
    statusId: ['', Validators.required], 
  });
  constructor(private formbuild: FormBuilder, private router: Router,private userService: UserService, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<EditworkflowtaskComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) { 
      this.idtsk  = this.elementform.idtsk
      this.containerId = this.elementform.wkfConteneur
      this.process_id = this.elementform.wkfProcess_id
      this.idwrktsk  = this.elementform.wkfid
  }
  ngOnInit() {
    this.extractProcessDefUser(this.containerId ,this.process_id)
    this.extractProfilId()
    this.listetaskparid(this.idwrktsk)

    this.extractStatusId()
  }
   
  extractStatusId(){
    this.workflowService.taskStatusAll().subscribe(res => {
      this.status =  res.data; 
  
  });
  }

  listetaskparid(idwrktsk:any) {
    this.workflowService.listetaskparid(this.idwrktsk).subscribe(data => {  
     
      if(data.statut){
        this.tasknfos = data.data;
      }
      
    })
  }
  extractProfilId(){
    this.userService.listprofil().subscribe(res => {
      this.profiles = res.data; 
  
  });
  }
  extractProcessDefUser(containerId:any,processId:any) {
    this.workflowService.extractProcessDefUser(this.containerId,this.process_id).subscribe(data => {  
      this.processdef = data.task
      for (var i = 0; i <  this.processdef.length; i++){   
      } 
      
    })
  }
  onSubmit(){ 
   
    let formData = new FormData(); 
    formData.append("taskgenform",JSON.stringify(this.addTskForm.value));   
    formData.append("idtsk",this.idtsk); 
   this.workflowService.updateworkflowtask(formData).subscribe(data=>{
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
