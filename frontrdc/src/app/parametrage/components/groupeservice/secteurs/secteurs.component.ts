import { Component, OnInit,  Input, ViewChild } from '@angular/core';
import { MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, from } from 'rxjs';
import {Task} from '../../../../procedures/models/task';
import {ProcessInfo} from '../../../../procedures/models/processinfo';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/utilisateur/models/user';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { Workflow } from 'src/app/procedures/models/workflow';
import { WorkflowService } from 'src/app/procedures/services/workflow.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-secteurs',
  templateUrl: './secteurs.component.html',
  styleUrls: ['./secteurs.component.scss']
})
export class SecteursComponent implements OnInit {
  displayedColumnsMenu: string[] = ['description'];
  displayedColumns: string[] = ['name', 'description', 'wkfEtat','Gerer', 'action'];
  displayedColumns1: string[] = ['taskid', 'taskname', 'taskactualowner', 'taskstatus', 'taskcreatedon', 'action'];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<Workflow>;
  datasouce1:MatTableDataSource<any>;
  datasouceTask:MatTableDataSource<any>;
  workflow:any;
  task:any;
  secteurparam:any;
  processinfo:any;
  breakpoint:any;
  donnee1:any
  processId:any
  donnee:any={containerId:'',processId:''}
  donnee2:any;
  username:any=[localStorage.getItem('username')];
  jbpmdiagrame = environment.jbpm;
  descriptionWkrfl:any;
  complete:any=0;
  container:any;
  nomdemarer:any=0;
  encoucours:any=0;
  constructor(    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private workflowService:WorkflowService,
    private usersService: UserService,
     private translate: TranslateService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.secteurparam = params.get("id")
    })
    //this.listWorkflow();
    this.listWorkflowsbysectors(this.secteurparam);
    //this.listContainer();
    //this.countwidget(this.username);
    //this.listProcessByContainer("evaluation_1.0.0-SNAPSHOT");

  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  workflowParams(workflow){
		this.workflowService.listWorkflows = workflow;
		 this.router.navigate(['/procedures/execution'], {
            state: {workflow:workflow}
          });
	}
  /*listWorkflow() {
    this.workflowService.listWorkflows().subscribe(data => {
      if (data.statut) {
        this.workflow = data.data;
        //console.log('------------------------------');
        console.log(this.workflow);
        this.dataSource = new MatTableDataSource<Workflow>(data.data);
        //console.log('+++++++++++++++++++++++++++++++++++++++++++++++'+JSON.stringify(data.data));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        console.log(data.description);
      }

    })
  }*/

  listWorkflowsbysectors(secteurparam){
    this.workflowService.listWorkflowsbysector(this.secteurparam).subscribe(data => {
      if (data.statut) {
        this.workflow = data.data;

      } else {
        //console.log(data.description);
      }

    })
  }

  /*countwidget(username:any) {
    this.workflowService.listOftask(this.username,this.workflow.wkfConteneur).subscribe(data => {

      this.donnee2 = data['task-summary'];
      console.log('+++++++++++++++++++++this.donnee2++++++++++++++++++++++++++++'+JSON.stringify(this.donnee1));
     for (var i = 0; i < this.donnee2.length; i++){
        if(this.donnee2[i]['task-container-id']==this.container){
          if(this.donnee2[i]['task-status']=="Completed")this.complete++;
          if(this.donnee2[i]['task-status']=="Ready")this.nomdemarer++;
          if(this.donnee2[i]['task-status']=="InProgress") this.encoucours++;
        }
    }
    })
  }*/
  listContainer() {
    this.workflowService.listOfContainer().subscribe(data => {
        this.donnee1 = data.result['kie-containers']['kie-container'];
     //console.log('+++++++++++++++++++++kie server++++++++++++++++++++++++++++'+JSON.stringify(this.donnee1));

    })
  }


}

