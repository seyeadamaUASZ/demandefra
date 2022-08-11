import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatPaginator, MatSnackBar, MatSort, MatTableDataSource } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { environment } from 'src/environments/environment';
import { Paramattr } from '../../models/paramattr';
import { Paramwidget } from '../../models/Paramwidget';
import { Task } from '../../models/task';
import { Workflowform } from '../../models/workflowform';
import { WorkflowService } from '../../services/workflow.service';
import { AddTransitionComponent } from '../add-transition/add-transition.component';
import { AddconfigWidgetComponent } from '../addconfig-widget/addconfig-widget.component';
import { AddconfigattributComponent } from '../addconfigattribut/addconfigattribut.component';
import { AddstatustaskComponent } from '../addstatustask/addstatustask.component';
import { AddtaskComponent } from '../addtask/addtask.component';
import { EditconfigattributComponent } from '../editconfigattribut/editconfigattribut.component';
import { EditworkflowtaskComponent } from '../editworkflowtask/editworkflowtask.component';

@Component({
  selector: 'app-gestion-widget',
  templateUrl: './gestion-widget.component.html',
  styleUrls: ['./gestion-widget.component.scss']
})
export class GestionWidgetComponent implements OnInit {
  paramwidget:any 
  wrkprofil:any 
  idwrkf:any 
  containerId:any
  process_id:any
  elementform:any
  loading:boolean 
  confwidget:any
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSourceParamWidget: MatTableDataSource<Paramwidget>;   
  displayedColumns: string[] = ['nomparam','wkfId.name','wdgId.wdgNom','poOwner.proLibelle','action'];
  dataSourceConfigWidget: MatTableDataSource<Paramattr>;   
  displayedConfigWidgetColumns: string[] = ['wtId.wtNom','attributabscisse','variableordonnee','action'];
  constructor(
    private route: ActivatedRoute,private _snackBar: MatSnackBar, 
    private dialog: MatDialog,private dialogRef: MatDialog,private workflowService:WorkflowService,private notification: NotificationService, private translate: TranslateService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.idwrkf = params.get("idwrkf")
      this.process_id = params.get("processid")
      this.containerId = params.get("containerid") 
    })
     this.listeParamWidget(this.idwrkf)
    this.listeConfigWidget()
  }

  applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSourceParamWidget.filter = filterValue.trim().toLowerCase();
  }
  
  listeConfigWidget(){
    this.workflowService.listeConfigWidget().subscribe(data => {
      if(data.statut){ 
    //  console.log(JSON.stringify(data.data))
          this.dataSourceConfigWidget = new MatTableDataSource<Paramattr>(data.data.reverse());
          this.dataSourceConfigWidget.paginator = this.paginator;
          this.dataSourceConfigWidget.sort = this.sort;
  
      }
      
    })
  }
  listeParamWidget(idwrkf) {
    this.workflowService.listeParamwidget(this.idwrkf).subscribe(data => {
      if(data.statut){ 
        //console.log(JSON.stringify(this.wrktask))
          this.dataSourceParamWidget = new MatTableDataSource<Paramwidget>(data.data.reverse());
          this.dataSourceParamWidget.paginator = this.paginator;
          this.dataSourceParamWidget.sort = this.sort;
  
      }
      
    })
  }
  openDialogAddConfWidget(): void {
    this.elementform= {
      wkfConteneur: this.containerId,
      wkfProcess_id: this.process_id,
      wkfid: this.idwrkf
    }; 
    const dialog1 = this.dialog.open(AddconfigWidgetComponent , {
      disableClose: true,
      data : this.elementform,
      width: '700px',
    }).afterClosed().subscribe(result => {
       this.listeConfigWidget()

    });
  }

  openDialogConfigAttr(element): void {
    
    const dialog1 = this.dialog.open(AddconfigattributComponent , {
      disableClose: true,
      data : element,
      width: '960px',
    }).afterClosed().subscribe(result => {
      this.listeConfigWidget()

    });
  }

  openDialogEditConfigAttr(element): void {
    
    const dialog1 = this.dialog.open(EditconfigattributComponent , {
      disableClose: true,
      data : element,
      width: '960px',
    }).afterClosed().subscribe(result => {
      this.listeConfigWidget()

    });
  }

  supprimerConfig(element) {
    let alertSupp;
    this.translate.get('formulaire.confirm-supp').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("formulaire.alert-suppression", message);
    const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        let formData = new FormData();
      formData.append("configform", JSON.stringify(element));
        this.deleteAttributionConfigWidget(formData);
        
      }
      this.listeConfigWidget()
    });
  }
   
  deleteAttributionConfigWidget(element){
     this.workflowService.deleteAttributionConfigWidget(element).subscribe(data => {
       
      if(data.statut){ 
        this.listeConfigWidget()
          
      }
      
    }) 
  }
}
