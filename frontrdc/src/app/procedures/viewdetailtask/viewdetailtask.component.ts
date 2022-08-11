import { Component, OnInit, Inject } from '@angular/core'; 
import { Router } from '@angular/router';
import {  MAT_DIALOG_DATA,MatDialogRef} from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../shared/services/notification.service';
import { WorkflowService } from '../services/workflow.service'; 
import { environment } from 'src/environments/environment';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-viewworkflow',
  templateUrl: './viewdetailtask.component.html',
  styleUrls: ['./viewdetailtask.component.scss']
})
export class ViewdetailtaskComponent implements OnInit {
  form:any
  formwithdoc:any
name:any
workflow:any={wkfConteneur:"",wkfProcess_id:""};  
  task:any;
  extractname:any
  extractval:any
  processinfo:any;
  breakpoint:any;
  donnee1:any
  processId:any
  donnee:any={containerId:'',processId:''}
  donnee2:any; 
  username:any=[localStorage.getItem('username')]; 
  taskname:any
  taskoutput:any
  choix=false;
  selectedFile: File;
  docprocess:any
  File: any = {};
   nameDoc: any='';
   formFields: any = {};
  valDoc:any
  iddoc:any
  jbpmdoc = environment.jbpmdoc;
  data2:any
  data2taskid:any
  data2taskcontainer:any
  data2taskprocess:any
  data2taskuser:any
  taskid:any
  typedoc:any
  constructor(private router: Router, private workflowService: WorkflowService,
    private notification:NotificationService, private translate:TranslateService,
    public dialogRef: MatDialogRef<ViewdetailtaskComponent>, @Inject(MAT_DIALOG_DATA) public element: any) { 


      this.workflow.wkfConteneur = this.element['task-container-id'];
      this.workflow.wkfTaskId = this.element['task-id'];
      this.workflow.wkfProcess_id = this.element['task-proc-def-id']
      this.workflow.wkfProcess_inst_id = this.element['task-proc-inst-id'];
      this.workflow.taskactualowner = this.element['task-actual-owner']
    this.workflow.taskactualowner = this.username;
     
    this.taskname = this.element['task-name'];
    
  }


ngOnInit() {   
  this.extractionChamps(); 
 
}

 
extractionChamps(){
 //alert(JSON.stringify(this.element['task-container-id']))
this.workflowService.listChampsTask(this.element['task-container-id'],this.element['task-id']).subscribe(data=>{
  this.extractname=data
  this.formwithdoc=data
 
  for (let i = 0; i < this.formwithdoc.length; i++) {  
     
      this.formFields = this.formwithdoc[i]["fields"]
      console.log("+++++++++++++++++++++++++++fields+++++++++++++++"+JSON.stringify(this.formFields)) 
        
    }

  for (let i = 0; i < this.formFields.length; i++) {  
    
    if(this.formFields[i]["code"] == "Document"){
      this.nameDoc = this.formFields[i]["name"]
      console.log("++++++++++++++++++++++++++++name docu++++++++++++++++"+this.nameDoc) 
        
    }

  } 
 console.log("++++++++++++++++++++++++++++++++++++++++++++++++++verif exist "+this.nameDoc)
  this.workflowService.listOfOutputTask(this.element['task-proc-inst-id']).subscribe(data => { 
    this.taskoutput = data['variable-instance'];  
    this.extractval = data['variable-instance']; 
     
    for (let i = 0; i < this.extractval.length; i++) {  
      if(this.extractval[i]["name"] == this.nameDoc){
        this.valDoc = this.extractval[i]["value"] 
        let n = 36
        this.iddoc = this.valDoc.slice(-36) 
       
      }
    } 
  })   
  })
} 

onNavigate(){
// your logic here.... like set the url 
const url = this.jbpmdoc+'business-central/jbpm/documents?templateid=sample-server&docid='+this.iddoc;
window.open(url, '_blank');
}

/*recuJbpm(){
  this.workflowService.recupDocJbpm(this.iddoc).subscribe(resp=>{
    alert(resp.body)
     this.telechargerDocJbpm(resp.body, resp.filename);
     console.log("+++++++++++response download ++++++++++++++++"+resp);
    }); 
  }*/

 /* telechargerDocJbpm(data: any) {
    const blob = new Blob([data]);
    fileSaver.saveAs(blob);
   }*/
   recuJbpm() {  
    this.workflowService.downloadFile(this.iddoc).subscribe(response => {
     
      this.typedoc = response.body.type
      console.log(response.body.type)
      console.log(response.body)
     var contentDisposition = response.headers.get('content-disposition');
            var jbpmfilename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
            console.log(jbpmfilename);
    this.saveFile(response.body, jbpmfilename);
    })
  }
  saveFile(data: any, filename?: string) {
 
    const blob = new Blob([data], {type: 'application/octet-stream'+this.typedoc});
    fileSaver.saveAs(blob, filename);
  }
   
  
closeDialog() {
  this.dialogRef.close();
}

}
