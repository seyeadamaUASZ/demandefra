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

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {
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
  data2taskprocessinstid:any
  formulairesimple:any
  jbpmtaskentree: any[];
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
      private notification:NotificationService, private translate:TranslateService,
      public dialogRef: MatDialogRef<TaskComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { 
        this.data2 = this.data.tache.forms
        console.log("+++++++++++++++++++++verif exist ---------------- "+JSON.stringify(this.data2));
        this.data2taskid = this.data.tache.taskid
        this.data2taskcontainer = this.data.tache.containerid
        this.data2taskprocess = this.data.tache.processid
        this.data2taskprocessinstid = this.data.tache.processinstid
        this.data2taskuser = this.data.tache.usertask

      this.workflow.wkfConteneur = this.data2taskcontainer;
      this.workflow.wkfProcess_id = this.data2taskprocess;
      this.workflow.wkfTaskId = this.data2taskid;
      this.workflow.wkfProcess_inst_id = this.data2taskprocessinstid;
      this.workflow.taskactualowner = this.data2taskuser;
      this.formGen = new FormGroup({});
      this.formGendoc = new FormGroup({});
        
      //this.taskname = this.data2['task-name'];
      
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
      //console.log('++++++++++++++form output+++++++++++++++++++++++'+JSON.stringify(this.data2));
     this.form=this.data2
     
     this.extractname=this.data2
     this.formwithdoc=this.data2
    
     this.formGendoc = this.toFormGroupDoc(this.formwithdoc);
    
     this.formGen = this.toFormGroup(this.form);
     //alert(JSON.stringify(this.formGen.value));
     for (let i = 0; i < this.formwithdoc.length; i++) {  
       
       if(this.formwithdoc[i]["code"] == "Document"){
         this.nameDoc = this.formwithdoc[i]["name"]
          
       }
 
     } 
     console.log("+++++++++++++++++++++verif exist "+this.nameDoc)
     this.workflowService.listOfOutputTask(this.data2taskprocessinstid).subscribe(data => { 
       this.taskoutput = data['variable-instance'];  
       this.extractval = data['variable-instance']; 
        //console.log('++++++++++++++precedent output+++++++++++++++++++++++'+JSON.stringify(this.taskoutput)); 
        for (let i = 0; i < this.extractval.length; i++) {  
         if(this.extractval[i]["name"] == "fichier"){
           this.valDoc = this.extractval[i]["value"]
          // console.log("+++++++++++val docu contient # ++++++++++++++++"+this.extractval[i]["value"]);
           let n = 36
           this.iddoc = this.valDoc.slice(-36)
          // console.log("+++++++++++id docu ++++++++++++++++"+this.iddoc);
          
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
   // console.log(data);
     //console.log('++++++++++++++ +++++++++++++++++++++++'+JSON.stringify(data));
    
  })
}
  
toFormGroup(form) {
  let group: any = {}; 
  for (let i = 0; i < this.form.length; i++) {  
    
    if(this.form[i]["code"] != 'Document'){  
      //alert(this.form[i]["code"])
      group[this.form[i]["name"]] = new FormControl('', Validators.required); 
    }
  }         
   console.log('++++++++++++++ form simple+++++++++++++++++++++++'+JSON.stringify(group));
  return new FormGroup(group);
}

toFormGroupDoc(formwithdoc) {
  let groupdoc: any = {}; 
  for (let i = 0; i < this.formwithdoc.length; i++) { 
    if(this.formwithdoc[i]["code"] == 'Document'){
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
    let formData2 = new FormData();
    let formData3 = new FormData()
    formData.append("workflow", JSON.stringify(this.workflow)); 
    formData.append('doc', this.selectedFile,this.selectedFile.name); 
    formData.append('docname', this.nameDoc); 
   this.workflowService.startTaskWithDoc(formData).subscribe(data=>{
        this.notification.success('Document chargé');
       });

       formData2.append("workflow", JSON.stringify(this.workflow));
       formData2.append("form", JSON.stringify(this.formGen.value));
       this.workflowService.startTask(formData2).subscribe(data=>{ 
         //alert("Task Terminer ");        
          this.notification.success('Task terminé');     
         //console.log(data) 
         
         
         this.listOftask(this.username); 
         this.closeDialog();
   })
   formData3.append("workflow", JSON.stringify(this.workflow));
         formData3.append("formglobal", JSON.stringify(this.formGen.value));
         formData3.append('docname', this.nameDoc);
         this.workflowService.insertjbpmdonne(formData3).subscribe(data=>{ 
          console.log(data) 
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
