import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatDialogRef, MatPaginator, MatSnackBar, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { environment } from 'src/environments/environment';
import { Task } from '../../models/task';
import { TaskTimer } from '../../models/TaskTimer';
import { Workflowform } from '../../models/workflowform';
import { WorkflowService } from '../../services/workflow.service';
import { AddTransitionComponent } from '../add-transition/add-transition.component';
import { AddconfigTimerComponent } from '../addconfig-timer/addconfig-timer.component';
import { AddstatustaskComponent } from '../addstatustask/addstatustask.component';
import { AddtaskComponent } from '../addtask/addtask.component';
import { EditworkflowtaskComponent } from '../editworkflowtask/editworkflowtask.component'; 
import {convertDuration, convertToSecond, convertYouTubeDuration} from 'duration-iso-8601';
@Component({
  selector: 'app-simulertimer',
  templateUrl: './simulertimer.component.html',
  styleUrls: ['./simulertimer.component.scss']
})
export class SimulertimerComponent implements OnInit {
  wrktask:any 
  wrkprofil:any 
  idwrkf:any
  tskId:any
  tskName:any
  taskId:any
  containerId:any
  process_id:any 
  loading:boolean 
 donneeworkflow:any
 configTimerparid:any
 configTimer:any
 donneeconfigTimer:any
 tmId:any
 tsk_name:any
 tasknamesuivant:any
 recuptasksuivant:any
 tskduree:any
 titretimer:any
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSourceWrkfTask: MatTableDataSource<Task>;  
  displayedColumns: string[] = ['tskId', 'tskName', 'poOwner.proLibelle','action'];
  dataSourceWrkftimer: MatTableDataSource<TaskTimer>;   
  displayedColumnstimer: string[] = ['tmTitre', 'tmTimerType', 'tmTimerData','action'];
  constructor(private formbuild: FormBuilder, private router: Router, private workflowService: WorkflowService,
    private notification: NotificationService, private translate: TranslateService,
    public dialogRef: MatDialogRef<SimulertimerComponent>, @Inject(MAT_DIALOG_DATA) public elementform: any) {
    this.containerId = this.elementform.wkfConteneur
    this.process_id = this.elementform.wkfProcess_id
    this.idwrkf = this.elementform.wkfid
    this.taskId = this.elementform.tskId
    this.tmId = this.elementform.tmId
    

  }
  ngOnInit() {
    
    //this.listetaskparid(this.idwrkf) 
    //this.listeConfigTimerparid(this.taskId)
   // this.listeConfigTimer()
    this.recupTaskAndTimer(this.tmId)
  }

  applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSourceWrkfTask.filter = filterValue.trim().toLowerCase();
  }
 
  
   

  listetaskparid(idwrkf:any) {
    this.workflowService.listetaskparid(this.idwrkf).subscribe(data => {  

     
      if(data.statut){
        this.wrktask = data.data; 
        console.log(JSON.stringify(this.wrktask))
           
      }
      
    })
  }
  
  listeConfigTimerparid(tskId:any) {
    this.workflowService.listeConfigTimerparid(this.tskId).subscribe(data => {  
 
      if(data.statut){ 
        this.configTimerparid = data.data
  
      }
      
    })
  }
  listeConfigTimer() {
    this.workflowService.listeConfigTimer().subscribe(data => {  
 
      if(data.statut){ 
        this.configTimer = data.data
  
      }
      
    })
  }
  
  recupTaskAndTimer(tmId:any){
    this.workflowService.recupTaskAndTimer(this.tmId).subscribe(data => {  
 
      if(data.statut){ 
        this.donneeconfigTimer = data.data
        for (let i = 0; i < this.donneeconfigTimer.length; i++) {  
          this.titretimer = this.donneeconfigTimer[i].tm_titre
          this.tsk_name = this.donneeconfigTimer[i]["tsk_name"]
          this.tskduree = JSON.stringify(convertDuration(this.donneeconfigTimer[i].tm_timerdata))
          this.recuptasksuivant = this.donneeconfigTimer[i].tm_tasksuiv_id 
          console.log("++++++++++++++++++++++++++++++++++++++++++++++++++++"+JSON.stringify(this.recuptasksuivant)+"++++++++++"+this.tskduree) 
          this.workflowService.recupParTaskId(this.recuptasksuivant).subscribe(data => {   
            if(data.statut){
              this.tasknamesuivant = data.data.tskName; 
              console.log("+++++++++++++++++++tasknamesuivant+++++++++++++++++++++++++++++++++"+JSON.stringify(this.tasknamesuivant)) 
            } 
          })
        }
      }
    })
  
    
    

  }

  showActionForTimer(){

  }
  showDiv = {
    actiontimer : false, 
  }

  closeDialog() {
    this.dialogRef.close();
  }

  
}
