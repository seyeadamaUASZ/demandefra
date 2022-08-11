import { Component, OnInit, Inject,Input, ViewChild  } from '@angular/core';
import {FormGroup, FormControl,Validators, FormBuilder  } from '@angular/forms';
import { Router } from '@angular/router';
import {  MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatPaginator, MatSort, MatTableDataSource , MAT_DIALOG_DATA,MatDialogRef} from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../shared/services/notification.service';
import { WorkflowService } from '../services/workflow.service';
import { ChampsTask} from '../models/champstask';
import { Task} from '../models/task';
import {Workflow} from '../models/workflow';
import { version } from 'punycode';
import { environment } from 'src/environments/environment';
import * as fileSaver from 'file-saver';
@Component({
  selector: 'app-task',
  templateUrl: './tasktable.component.html',
  styleUrls: ['./tasktable.component.scss']
})
export class TaskTableComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  form:any
  formwithdoc:any
name:any
workflow:any={wkfConteneur:"",wkfProcess_id:""}; 
  formGen:FormGroup; 
  formGendoc:FormGroup; 
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
  datasouceTask:MatTableDataSource<any>;
  taskname:any
  taskoutput:any
  choix=false;
  selectedFile: File;
  docprocess:any
  File: any = {};
   nameDoc: any='';
  valDoc:any
  iddoc:any
  jbpmdoc = environment.jbpmdoc;
  data2:any
  data2taskid:any
  data2taskcontainer:any
  data2taskprocess:any
  data2taskuser:any
  taskid:any
  formFields:any
  typedoc:any
  
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
      private notification:NotificationService, private translate:TranslateService,
      public dialogRef: MatDialogRef<TaskTableComponent>, @Inject(MAT_DIALOG_DATA) public element: any) { 
        
        this.workflow.wkfConteneur = this.element['task-container-id'];
        this.workflow.wkfTaskId = this.element['task-id'];
        this.workflow.wkfProcess_id = this.element['task-proc-def-id']
        this.workflow.wkfProcess_inst_id = this.element['task-proc-inst-id'];
        this.workflow.taskactualowner = this.element['task-actual-owner']
      this.workflow.taskactualowner = this.username;
      this.formGen = new FormGroup({});
      this.formGendoc = new FormGroup({});
        
      this.taskname = this.element['task-name'];
      
    }


  ngOnInit() { 
    this.listOftask(this.username);  
    this.extractionChamps(); 
   
  }
 

   
  extractionChamps(){
    //alert(JSON.stringify(this.element['task-container-id']))
    /*console.log('-----------------id-----------'+JSON.stringify(this.data2taskid)
    +JSON.stringify(this.data2taskcontainer)
    +JSON.stringify(this.data2taskprocess)
      +JSON.stringify(this.data2taskuser));*/
 
        this.workflowService.listChampsTask(this.workflow.wkfConteneur,this.workflow.wkfTaskId).subscribe(data1 => {  
          console.log("+++++++++++ +++++++++++++++"+data1)
          for (let i = 0; i < data1.length; i++) {  
     
            this.formFields = data1[i]["fields"]
            console.log("+++++++++++++++++++++++++++fields+++++++++++++++"+JSON.stringify(this.formFields)) 
              
          }
          this.form=this.formFields
          this.extractname=this.formFields
          this.formwithdoc=this.formFields
          this.formGendoc = this.toFormGroup(this.formwithdoc);
          this.formGen = this.toFormGroup(this.form);
           
          for (let i = 0; i < this.formwithdoc.length; i++) {  
            
            if(this.formwithdoc[i]["code"] == "Document"){
              this.nameDoc = this.formwithdoc[i]["name"] 
              //console.log("+++++++++++name docu++++++++++++++++"+this.nameDoc)
             // console.log("+++++++++++name docu++++++++++++++++"+JSON.stringify(this.extractname[i]["name"]))
               
              
            }
      
          } 
          
        })
       
     
     console.log("+++++++++++++++++++++verif exist "+this.nameDoc)
     this.workflowService.listOfOutputTask(this.element['task-proc-inst-id']).subscribe(data => { 
       this.taskoutput = data['variable-instance'];  
       this.extractval = data['variable-instance']; 
        console.log('++++++++++++++precedent output+++++++++++++++++++++++'+JSON.stringify(this.taskoutput)); 
        for (let i = 0; i < this.extractval.length; i++) {  
         if(this.extractval[i]["name"] == "fichier"){
           this.valDoc = this.extractval[i]["value"]
           console.log("+++++++++++val docu contient # ++++++++++++++++"+this.extractval[i]["value"]);
           let n = 36
           this.iddoc = this.valDoc.slice(-36)
           console.log("+++++++++++id docu ++++++++++++++++"+this.iddoc);
          
         }
       }
        
        
       
     })
    
 
  }

  

 onNavigate(){
  // your logic here.... like set the url 
  const url = this.jbpmdoc+'business-central/jbpm/documents?templateid=sample-server&docid='+this.iddoc;
  window.open(url, '_blank');
}

listOftask(username:any) { 
  this.workflowService.listOftask(this.username,this.workflow.wkfConteneur).subscribe(data => { 
    this.donnee2 = data['task-summary']; 
    this.datasouceTask = new MatTableDataSource<any>(this.donnee2);
    this.datasouceTask.paginator = this.paginator;
    this.datasouceTask.sort = this.sort;
     
    
  })
}
 
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

toFormGroup(form) {
  let group: any = {}; 
  for (let i = 0; i < this.form.length; i++) {  
    if(group[this.form[i]["code"]] != "Document"){  
      group[this.form[i]["name"]] = new FormControl('', Validators.required); 
    }
  }         
  // console.log('++++++++++++++ +++++++++++++++++++++++'+JSON.stringify(group));
  return new FormGroup(group);
}

toFormGroupDoc(formwithdoc) {
  let groupdoc: any = {}; 
  for (let i = 0; i < this.formwithdoc.length; i++) { 
    if(groupdoc[this.formwithdoc[i]["code"]] == 'Document'){
      groupdoc[this.formwithdoc[i]["name"]] = new FormControl('', Validators.required); 
    }   
   
  }         
  return new FormGroup(groupdoc);
}

handleUpload(event) {
  this.selectedFile = event.target.files[0];
  
}  
 
onSubmit(){ 
  if(this.selectedFile!= null){
    let formData = new FormData(); 
    formData.append("workflow", JSON.stringify(this.workflow)); 
    formData.append('doc', this.selectedFile,this.selectedFile.name); 
    formData.append('docname', this.nameDoc); 
    this.workflowService.startTaskWithDoc(formData).subscribe(data=>{ 
        this.notification.warn('Document chargé');
        formData.append("workflow", JSON.stringify(this.workflow));
        formData.append("form", JSON.stringify(this.formGen.value));
        this.workflowService.startTask(formData).subscribe(data=>{ 
          //alert("Task Terminer ");        
           this.notification.warn('Task terminé');     
          //console.log(data)   
          this.listOftask(this.username); 
          this.closeDialog();
    })      
              
           
        })
      }else{
        let formData = new FormData();
        formData.append("workflow", JSON.stringify(this.workflow));
        formData.append("form", JSON.stringify(this.formGen.value)); 
       
        this.workflowService.startTask(formData).subscribe(data=>{ 
          //alert("Task Terminer ");        
           this.notification.warn('Task terminé');     
          //console.log(data)   
          this.listOftask(this.username); 
          this.closeDialog();
}) 
  }
   
    
}
 

closeDialog() {
  this.dialogRef.close();
}

}
