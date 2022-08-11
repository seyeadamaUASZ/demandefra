import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { WorkflowService } from '../../services/workflow.service';
import { UserService } from '../../../utilisateur/services/user.service';
@Component({
  selector: 'app-addstatustask',
  templateUrl: './addstatustask.component.html',
  styleUrls: ['./addstatustask.component.scss']
})
export class AddstatustaskComponent implements OnInit {
loading:any;  
  workflowform: any[];
  selectedFile: File;
  containerId:any
  process_id:any
  processdef:any
  idwrkf:any 
  marked = false;
  theCheckbox = false;
  profiles: any[];
  addTskStsForm = this.formbuild.group({
    taskstatusname:  ['', Validators.required],  

  });
  constructor(private formbuild: FormBuilder, private router: Router,private userService: UserService, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<AddstatustaskComponent> ) {
      
  }
  ngOnInit() {
    
  }
 
  onSubmit(){ 
   
    let formData = new FormData(); 
    formData.append("taskstatusform",JSON.stringify(this.addTskStsForm.value));   
   this.workflowService.createtaskstatus(formData).subscribe(data=>{
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
