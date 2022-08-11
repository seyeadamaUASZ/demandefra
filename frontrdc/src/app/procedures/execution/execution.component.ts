import { Component, OnInit,  Input, ViewChild, Inject } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material'; 
import { Router, Params, ActivatedRoute } from '@angular/router';
import { Observable,forkJoin , from } from 'rxjs';
import {Workflow} from '../models/workflow';
import {Task} from '../models/task';
import {ProcessInfo} from '../models/processinfo';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../shared/services/notification.service';
import {WorkflowService} from '../services/workflow.service';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import {TaskComponent } from 'src/app/procedures/task/task.component';
import {ProcessComponent } from 'src/app/procedures/process/process.component';
import {BpmComponent } from '../../workflow/component/bpm/bpm.component';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/utilisateur/models/user';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { TaskTableComponent } from '../tasktable/tasktable.component';
import { ViewdetailtaskComponent } from '../viewdetailtask/viewdetailtask.component'; 
import { JbpmTaskEntree } from '../models/jbpmTaskEntree';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-execution',
  templateUrl: './execution.component.html',
  styleUrls: ['./execution.component.scss']
})
export class ExecutionComponent implements OnInit {
   displayedColumnsMenu: string[] = ['description'];
   displayedColumns: string[]  =["taskid","taskname","taskactualowner","taskstatus","taskcreatedon","action"]
  //displayedColumns=[]  
  dataSource:MatTableDataSource<Task>;   
  loadedCharacter: {}; 
  /*displayedColumns = [];
  displayedActionCol = [];
  jbpmColumns = [];
  dataSource;
  dataSourceJbpm:MatTableDataSource<any>;  
  displayedColumnstaskjbpm:string[]*/
  @Input() dataMessageResults: any[];
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort; 

  workflow:any;
  
  task:any;
  processinfo:any;
  breakpoint:any;
  donnee1:any
  processId:any
  donnee:any={containerId:'',processId:''}
  workflowtask:any={wkfConteneur:"",wkfProcess_id:"",username:""}; 
  donnee2:any; 
  donnee3:any;
  alltaks:any;
  nomdemarer:any=0;
  complete:any=0;
  encoucours:any=0;
  terminer:any=0;
  donneetask:any; 
  username:any=[localStorage.getItem('username')];
  jbpmdiagrame = environment.jbpm;
  descriptionWkrfl:any;
  container:any; 
  dtask:any;
  datafortask:any;
  generatetable:any
  fetchprocessId:any
  fetchprocessInstId:any
  elementbutton:any
  elementbpm:any
  elementexecute:any
  elementexec:any
  workflowname:any
  nomwrkfl:any
  nomcallto: any
  nomwdgtlab : any
  displayColumns: string[];
  displayjbpmColumns: any=[];
  form:any
  taskoutput:any
  group:any
  containerId: any
  taskId:any
  elementforexec:any
  extractname:any
  formwithdoc:any
  dt:any
  dt2:any
  /*dtkeys:{} []*/
  /*dtkeys:any=[];*/
  @Input() dtkeys: any[]; 
  tdkeys1:any
  displayedjbpmColumns:any
  dataSourcejbpm:any
  datatest:any
  columnNames:any ={}
  filledArray:any[]
  finalData:any
  dtTaskId:any[]
  jbpmtaskid:any
  extractIds:any
  extractArrNames:any[]
  extractNamesVal:any[] 
  jbpmurl = environment.apii;
  EpOutput:any[]
  tableExtention:any
  constructor(    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification:NotificationService,
    private snackBar: MatSnackBar,private workflowService:WorkflowService,private usersService: UserService, private translate: TranslateService,private http: HttpClient) { 
      
    }
    
  ngOnInit() { 
    //this.recupGroupe(this.username); 
     this.listOftask(this.username);  
    this.countwidget(this.username);  
    this.route.paramMap.subscribe(params => {
      this.container = params.get('idname');
     // console.log('++++++++++++++++++++++++++++++++++++++++'+this.container); 
        
        this.workflowService.listContainerIdByProcess(this.container).subscribe(data => {
            this.processId =data['data'];
             
           // console.log('+++++++++++++++++++++++++++++++'+data['data'])
        })  
    });  
    
   this.nomDuWorkflows(this.container); 
   //this.listjbpmdonne();
   //this.listOfAlltask();
  
  }

  listjbpmdonne(){ 
    this.workflowService.listjbpmdonne().subscribe(data => {  
  
    this.dt =  data.data;   
      /*this.filledArray = new Array()
    for (let i = 0; i < this.dt.length; i++) {  
      this.jbpmtaskid = JSON.parse("{\"Action\" "+":"+"\""+this.dt[i]['taskId']+"\"}")
     this.dtkeys =  JSON.parse(this.dt[i]['taskformvalues']) ; 
      
      Object.assign(this.dtkeys, this.jbpmtaskid); 
    console.log('----------+++++++++dtkeys+++++++++++-------------'+ JSON.stringify(this.dtkeys)); 
      this.filledArray.push(this.dtkeys) 
       console.log('----------+++++++++filledArray+++++++++++-------------'+ JSON.stringify(this.filledArray)); 
    }*/ 
    //this.displayedColumns = Object.keys(this.filledArray[0]);  
    //this.displayedColumns  =["taskid","taskname","taskactualowner","taskstatus","taskcreatedon","action"]  
    //this.displayedColumns  =["taskid","taskname","taskactualowner","taskstatus","taskcreatedon","action","Annee_registre","Email","Num_registre"]
    
   //this.displayedColumns.push(this.filledArray[0])
   //console.log('----------+++++++++displayedColumns+++++++++++-------------'+ JSON.stringify(this.displayedColumns)); 
   // this.dataSource = new MatTableDataSource(this.filledArray);
    })
  }
  

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
/*
  recupGroupe(username:any){
    this.workflowService.recupGroupe(this.username).subscribe(data => { 
        this.group = data.data; 
    })
  }*/
   
  
  nomDuWorkflows(containerId:any){
    this.workflowService.nomDuWorkflows(this.container).subscribe(data => {
      if (data.statut) {
        this.workflowname = data.data;
        this.nomwrkfl = this.workflowname['name']
        this.nomcallto = this.workflowname['wkfCalltoaction']
        this.nomwdgtlab  = this.workflowname['wkfLabelwdgt']
         //console.log('++++++++++++++Nom du workflow++++++++++++++++++++++++++'+JSON.stringify(data.data));
        //console.log('------------------------------'); 
        
         
      } else {        
         this.notification.warn(data.description);            
      }

    })
  }
   
 
   
  listOftask(username:any) { 
    this.workflowService.listOftask(this.username,this.container).subscribe(data => {  
      this.donnee2 = data['task-summary']; 
      //console.log('+++++++++++++filtered json++++++++++++++++++++++++++++'+JSON.stringify(this.donnee2))
      this.datafortask = this.filterData(this.container);
      this.extractIds = this.filterData(this.container);
      
      console.log('+++++++++++++filtered json++++++++++++++++++++++++++++'+JSON.stringify(this.datafortask))
 /*this.extractArrNames = new Array()
      for (let i = 0; i < this.extractIds.length; i++) {   
      this.workflowService.listOfOutputTask(this.extractIds[i]["task-proc-inst-id"]).subscribe(data => {      
       this.taskoutput  =data['variable-instance'];
      //this.dataSourcejbpm = Object.assign(this.datafortask, this.taskoutput); 
       //this.extractArrNames =   Object.keys(this.taskoutput[i]); 
        
       console.log('++++++++++++++precedent output+++++++++++++++++++++++'+JSON.stringify(this.taskoutput[i]['name'])); 
       this.displayedColumns.push(this.taskoutput[i]['name'])
       console.log('++++++++++++++*********************++++++++++++++++++'+JSON.stringify(this.displayedColumns));
     })  
       }*/
       //this.displayedColumns  = ["taskid","taskname","taskactualowner","taskstatus","taskcreatedon","action"]  
       
      if((this.datafortask[0] != null)){

        this.dataSource = new MatTableDataSource<Task>(this.datafortask);
      
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort; 
         
        this.fetchprocessId = this.datafortask[0]['task-proc-def-id']
        this.fetchprocessInstId = this.datafortask[0]['task-proc-inst-id']
        this.taskId = this.datafortask[0]['task-id']
        this.elementexecute={
          utilisateur:this.username,
          taskId:this.taskId
  
        }
         
        this.elementbpm= { 
          wkfConteneur: this.container, 
          wkfProcess_inst_id: this.fetchprocessInstId
        }; 

      }
     
        
     // console.log('+++++++++++++get All Task++++++++++++++++++++++++++++'+ JSON.stringify(this.datafortask))
        //this.listOfOutputTask(this.fetchprocessInstId);

    })
  }
 
  countwidget(username:any) { 
    this.workflowService.listOftask(this.username,this.container).subscribe(data => {  
      
      this.donnee2 = data['task-summary']; 
       
     for (var i = 0; i < this.donnee2.length; i++){ 
        if(this.donnee2[i]['task-container-id']==this.container){
          if(this.donnee2[i]['task-status']=="Completed")this.complete++; 
          if(this.donnee2[i]['task-status']=="Ready")this.nomdemarer++; 
          if(this.donnee2[i]['task-status']=="InProgress") this.encoucours++;
        }
       
    } 
    })
  }

   
  listOfOutputTask(data:any) {
    this.workflowService.listOfOutputTask(this.fetchprocessInstId).subscribe(data => {      
      this.taskoutput = data['variable-instance'] ;
      //console.log('++++++++++++++precedent output+++++++++++++++++++++++'+JSON.stringify(this.taskoutput));
      
    })
  }
  /*listOfAlltask() { 
    this.workflowService.listOfAlltask().subscribe(data => {  
      
      this.alltaks = data['task-summary']; 
      for (var i = 0; i < this.alltaks.length; i++){
        var objTask = this.alltaks[i]['task-actual-owner'];
        console.log('+++++++++++++  ++++++++++++++++++++++++++++'+ objTask)
        for (var key in objTask){
            var attrName = key;
            var attrValue = objTask['task-status'];
            console.log('+++++++++++++  ++++++++++++++++++++++++++++'+ attrValue)
        }
    }
      console.log('+++++++++++++get All Task++++++++++++++++++++++++++++'+ JSON.stringify(this.alltaks))
    })
  }*/
  

   filterData(locationName) {
    return this.donnee2.filter(object => {
      return object['task-container-id'] == locationName;
    });
  }
  listChampsProcess(data:any,data1:any) {
    this.workflowService.listChampsProcess(data,data1).subscribe(data => {  
     // console.log('+++++++++++++get champs task++++++++++++++++++++++++++++'+JSON.stringify(data));
      
    })
  }
  listChampsTask(data:any,data1:any) {
    this.workflowService.listChampsTask(data,data1).subscribe(data => {  
      console.log(data);
      
    })
  }
   
  
  reclamerOneTask(containerId, TaskId,username){ 
       
   //alert(JSON.stringify(this.workflowtask))
  this.workflowService.reclamerTask(containerId,TaskId,username).subscribe(res=>{  
      this.listOftask(this.username);
})    
}

suspendreOneTask(containerId, TaskId,username){  
 
 this.workflowService.suspendreTask(containerId,TaskId,username).subscribe(res=>{  
     this.listOftask(this.username);
})    
}

 /*executeProject(elementbutton,varprocess:any,doc:File){ 
      this.workflowService.startProjet(elementbutton,varprocess,doc).subscribe(data=>{
        //console.log(data)
       
        this.notification.info('Workflow démarré');
    
        this.listOftask(this.username);
      })
 } */

 /*executeProject(elementbutton){ 
  this.elementforexec = { 
    wkfConteneur: this.container,
    wkfProcess_id: this.processId
  }; 
  
  let formData = new FormData();
  formData.append("workflow", JSON.stringify(this.elementforexec));
  
  this.workflowService.startProjet(formData).subscribe(data=>{
    //console.log(data)
   
    this.notification.info('Workflow démarré');

    this.listOftask(this.username);
  })
} */
 executeProject(elementbutton){ 
  this.elementbutton = { 
    wkfConteneur: this.container,
    wkfProcess_id: this.processId 
  };  
  let formData = new FormData();
  formData.append("workflow", JSON.stringify(this.elementbutton)); 
  formData.append("utilisateur", JSON.stringify(this.username)); 
   this.workflowService.startProjet(formData).subscribe(data=>{
    this.elementexec = JSON.stringify(data)
    this.notification.info('Processus démarré');
    //console.log('+++++++++++++ ++++++++++++++++++++++++++++'+JSON.stringify(this.elementexec));
    //console.log('----------------------------'+JSON.stringify(data.tache.forms));
    const dialog2 = this.dialog.open(TaskComponent , {
      width: '700px',  
      data: data
    }).afterClosed().subscribe(result => {
      this.listOftask(this.username);
      this.listjbpmdonne()
    });  
    this.listOftask(this.username);
  }) 
 
   
}
 
   
 openDialogBpm(containerId, procIntId): void {
    this.elementbpm= { 
      wkfConteneur: containerId, 
      wkfProcess_inst_id: procIntId
    }; 
  const dialog2 = this.dialog.open(BpmComponent , {
    width: '700px',  
    data :  this.elementbpm
  }) 
}
  openDialogAddProcess(element): void {
    const dialog2 = this.dialog.open(ProcessComponent , {
      width: '700px',  
      data : element
    }).afterClosed().subscribe(result => {
      this.listOftask(this.username);
    });  
  }
  openDialogAddTaskTable(element): void {
     
     const dialog2 = this.dialog.open(TaskTableComponent , {
      width: '700px', 
      height: '100%',
      data : element
    }).afterClosed().subscribe(result => {
      this.listOftask(this.username);
    });  
  }

  openDialogDetailTask(element): void {
     
    const dialog2 = this.dialog.open(ViewdetailtaskComponent , {
     width: '700px', 
     height: '100%',
     data : element
   }).afterClosed().subscribe(result => {
     
   });  
 }

   

    goToLink(url: string){ 
      window.open(url, "_blank");
  }
}
