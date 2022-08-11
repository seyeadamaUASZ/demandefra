import { Component, OnInit,  Input, ViewChild,PipeTransform, Pipe, } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable,merge, from } from 'rxjs'; 
import {Workflow} from '../../models/workflow'; 
import { TranslateService } from '@ngx-translate/core';
import {WorkflowService} from '../../services/workflow.service';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import {AddworkflowComponent } from 'src/app/workflow/component/addworkflow/addworkflow.component'; 
import {BpmComponent } from 'src/app/workflow/component/bpm/bpm.component';
import { ViewworkflowComponent} from 'src/app/workflow/component/viewworkflow/viewworkflow.component';
import { EditworkflowComponent} from 'src/app/workflow/component/editworkflow/editworkflow.component';
import { environment } from 'src/environments/environment'; 
import { UserService } from 'src/app/utilisateur/services/user.service';
import { DomSanitizer } from "@angular/platform-browser";
import { NotificationService } from '../../../shared/services/notification.service';
import { FormControl, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-main-content-workflow',
  templateUrl: './main-content-workflow.component.html',
  styleUrls: ['./main-content-workflow.component.scss']
})
//@Pipe({ name: 'safe' })
export class MainContentWorkflowComponent implements OnInit {
  displayedColumnsMenu: string[] = ['description'];
  displayedColumns: string[] = ['secteur','name', 'description', 'action'];
  displayedColumns1: string[] = ['taskid', 'taskname', 'taskactualowner', 'taskstatus', 'taskcreatedon', 'action'];
  
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<Workflow>;
  datasouce1:MatTableDataSource<any>;
  datasouceTask:MatTableDataSource<any>;
  workflow:any;
  titresecteur:any;
  task:any;
  processinfo:any;
  breakpoint:any;
  donnee1:any
  processId:any
  donnee:any={containerId:'',processId:''}
  donnee2:any; 
  username:any=[localStorage.getItem('username')];
  jbpmdiagrame = environment.jbpm+"kie-wb.jsp?standalone&perspective=LibraryPerspective&header=UberfireBreadcrumbsContainer";
 
  descriptionWkrfl:any;
  constructor(    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,private workflowService:WorkflowService,private usersService: UserService, private translate: TranslateService,
    private sanitizer: DomSanitizer,private notification:NotificationService) { }
    form: FormGroup = new FormGroup({
      secteur: new FormControl(false),
      name: new FormControl(false),
      description: new FormControl(false),
      action: new FormControl(false),
  
    });

    secteur = this.form.get('secteur');
    name = this.form.get('name'); 
    description = this.form.get('description');
    action = this.form.get('action');
   
    columnDefinitions = [
      { def: 'secteur', label: 'Secteur', hide: false },
      { def: 'name', label: 'Nom du workflow', hide: false },
      { def: 'description', label: 'Description', hide: false }, 
      { def: 'action', label: 'Action', hide: false }
    ]
    getDisplayedColumns(): string[] {
      return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
    }
    ngAfterViewInit() {
      let o1: Observable<boolean> = this.secteur.valueChanges;
      let o2: Observable<boolean> = this.name.valueChanges;
      let o3: Observable<boolean> = this.description.valueChanges; 
      let o5: Observable<boolean> = this.action.valueChanges;
  
      merge(o1, o2, o3,o5).subscribe(v => {
        this.columnDefinitions[0].hide = this.secteur.value;
        this.columnDefinitions[1].hide = this.name.value;
        this.columnDefinitions[2].hide = this.description.value; 
        this.columnDefinitions[4].hide = this.action.value; 
      });
    }
  ngOnInit() {
    this.listWorkflow();
     
  }

  urllistforms(workflowname){ 
    this.router.navigate(['/liste-formulaire/'+workflowname]);
};
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
 
  workflowParams(workflow){			
		this.workflowService.listWorkflows = workflow;	
		 this.router.navigate(['/workflow/execution'], { 
            state: {workflow:workflow} 
          });
	}
  listWorkflow() {
    this.workflowService.listWorkflows().subscribe(data => {
      if (data.statut) {
        this.workflow = data.data; 
        this.dataSource = new MatTableDataSource<Workflow>(data.data);  
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else { 
      }

    })
  }
  

  listOftask(username:any) { 
    this.workflowService.listOftask(this.username).subscribe(data => { 
      this.donnee2 = data['task-summary']; 
      this.datasouceTask = new MatTableDataSource<any>(this.donnee2);
      this.datasouceTask.paginator = this.paginator;
      this.datasouceTask.sort = this.sort; 
      
    })
  }
  listChampsProcess(data:any,data1:any) {
    this.workflowService.listChampsProcess(data,data1).subscribe(data => {   
      
    })
  }
  listChampsTask(data:any,data1:any) {
    this.workflowService.listChampsTask(data,data1).subscribe(data => {   
      
    })
  }
  listContainer() {
    this.workflowService.listOfContainer().subscribe(data => {
        this.donnee1 = data.result['kie-containers']['kie-container'];
        this.datasouce1 = new MatTableDataSource<any>(this.donnee1); 
        this.datasouce1.paginator = this.paginator;
        this.datasouce1.sort = this.sort; 
    })
  }
  
 executeProject(workflow,varprocess:any){ 
      this.workflowService.startProjet(workflow,varprocess).subscribe(data=>{ 
        alert('workflow demarer');
        this.listWorkflow();
        this.listOftask(this.username);
      })
 }

 modeliserWorkflow(workflow){
  this.workflowService.modeliser(workflow).subscribe(data=>{ 
    alert('Workflow compiler');
    this.listWorkflow();
  })

 }
  openDialogDeleteWorkflow(workflow) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("Supprimer ce workflow", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,

    maxWidth: "400px",
    data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if(dialogResult === true){
      this.delete(workflow);
    }
    });
  }
   
  delete(workflow) {
  
    this.workflowService.deleteWorkflow(workflow).subscribe(data => {
      if (data.statut) {
        this.translate.get('workflow.confirm-suppression').subscribe((res: string) => {           
          this.notification.success(res);
        });         
         
      } else {
        this.translate.get('workflow.erreur-suppression').subscribe((res: string) => {           
          this.notification.error(res);
        });   
        
      }
      this.listWorkflow();
    });
  }
 
  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 5;
  }
  
    __cardClick(emp) {
      console.log(emp);
      this.router.navigate(['workflow', emp.id]);
    }
    openDialogAddWorkflow(): void {
      const dialog1 = this.dialog.open(AddworkflowComponent , {
        disableClose: true,

        width: '700px',
      }).afterClosed().subscribe(result => {
        this.listWorkflow();
  
      });
    }
    openDialogEdit(workflow): void {
      const dialog = this.dialog.open(EditworkflowComponent, {
        disableClose: true,

        width: '700px',
        data: workflow
      }).afterClosed().subscribe(result => {
        this.listWorkflow();				
      });
    }
    openDialogView(workflow): void {
      const dialog = this.dialog.open(ViewworkflowComponent, {
        disableClose: true,
        
        width: '500px',
        data: workflow
      });
    }

     

    goToLink(url: string){ 
      window.open(url, "_blank");
  }

  openjbpmlink(jbpmdiagrame){
    return this.sanitizer.bypassSecurityTrustResourceUrl(jbpmdiagrame);
  }
}

 