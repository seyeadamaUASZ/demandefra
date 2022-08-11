import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable,merge } from 'rxjs';
import { EditCriterePwdComponent } from '../edit-critere-pwd/edit-critere-pwd.component';
// import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { CriterePwdService } from '../../services/critere-pwd.service';
import { CriterePwd } from '../../models/critere-pwd';
import { NotificationService } from '../../../shared/services/notification.service';
import { ParametreService } from '../../services/parametre.service';
import { Secteur } from '../../models/secteur';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AjoutQrcodeComponent } from '../../../qrcode/components/ajout-qrcode/ajout-qrcode.component';
import * as fileSaver from 'file-saver';
import { AddwidgetComponent } from '../addwidget/addwidget.component';
import { Widget } from '../../models/widget';
import { WidgetService } from '../../services/widget.service';
import { EditwidgetComponent } from '../editwidget/editwidget.component';
import { AddtemplatewidgetComponent } from '../addtemplatewidget/addtemplatewidget.component';
import { TemplateWidget } from '../../models/templatewidget';
import { AttributiontempwidgetComponent } from '../attributiontempwidget/attributiontempwidget.component';
import { EditattrtempwidgetComponent } from '../editattrtempwidget/editattrtempwidget.component';
import { FormControl, FormGroup } from '@angular/forms';
import { EdittemplatewidgetComponent } from '../edittemplatewidget/edittemplatewidget.component';




@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {  
  displayedColsWidget: string[] = ['wdgNom','wdgCode', 'wdgDescription','action'];
  displayedColsTempWidget: string[] = ['wtId','wtNom', 'actionTempl'];
  displayedColumns: string[] = [ 'name', 'weight', 'action'];
  dataSource: MatTableDataSource<Secteur>;
  dataSourceWidget: MatTableDataSource<Widget>;
  dataSourceTempWidget: MatTableDataSource<TemplateWidget>;
  @ViewChild(MatPaginator, { static: true }) paginator1: MatPaginator;
  secteur: Secteur;
  breakpoint:any;
  criterePwd: CriterePwd;
  formulairenotgenere:any;
  formulairegenere:any;
  loading:boolean=false;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  tabIndex = 0;
  qrcQrcodeByte:any
  retreivedResponse:any;
  retreivedImage:any;
  widget:any
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private translate: TranslateService,
    private paramService: ParametreService,
    private criterePwdService: CriterePwdService,
    private widgetService: WidgetService,
    
    ) { }


    onResize(event) {
      this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 5;
    } 
    
    form: FormGroup = new FormGroup({
      wdgNom: new FormControl(false),
      wdgCode: new FormControl(false),
      wdgDescription: new FormControl(false), 
      action: new FormControl(false),
  
    });
    wdgNom = this.form.get('wdgNom');
    wdgCode = this.form.get('wdgCode');
    wdgDescription = this.form.get('wdgDescription'); 
    action = this.form.get('action');
   
    columnDefinitions = [
      { def: 'wdgNom', label: 'Nom du widget', hide: false },
      { def: 'wdgCode', label: 'Code du widget', hide: false },
      { def: 'wdgDescription', label: 'Description du widget', hide: false }, 
      { def: 'action', label: 'Action', hide: false }
    ]
    getDisplayedColumns(): string[] {
      return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
    formTempl: FormGroup = new FormGroup({
      wtId: new FormControl(false),
      wtNom: new FormControl(false), 
      actionTempl: new FormControl(false),
  
    }); 

    wtId = this.formTempl.get('wtId');
    wtNom = this.formTempl.get('wtNom');  
    actionTempl = this.formTempl.get('actionTempl');
   
    columnDefinitionsTempl = [
      { deftempl: 'wtId', labeltempl: 'Id du templatewidget', hidetempl: false },
      { deftempl: 'wtNom', labeltempl: 'Nom du template du widget', hidetempl: false }, 
      { deftempl: 'actionTempl', labeltempl: 'Action', hidetempl: false }
    ]
    getDisplayedColumnsTempl(): string[] {
      return this.columnDefinitionsTempl.filter(cdtempl => !cdtempl.hidetempl).map(cdtempl => cdtempl.deftempl);
    }
    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 

    ngAfterViewInit() {
      /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
      let o1: Observable<boolean> = this.wdgNom.valueChanges;
      let o2: Observable<boolean> = this.wdgCode.valueChanges;
      let o3: Observable<boolean> = this.wdgDescription.valueChanges; 
      let o4: Observable<boolean> = this.action.valueChanges;
      /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
      let t1: Observable<boolean> = this.wtId.valueChanges;
      let t2: Observable<boolean> = this.wtNom.valueChanges; 
      let t3: Observable<boolean> = this.actionTempl.valueChanges; 
  /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
  
      merge(o1, o2, o3,o4).subscribe(v => {
        /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
        this.columnDefinitions[0].hide = this.wdgNom.value;
        this.columnDefinitions[1].hide = this.wdgCode.value;
        this.columnDefinitions[2].hide = this.wdgDescription.value; 
        this.columnDefinitions[3].hide = this.action.value; 
      });
      merge(t1, t2, t3).subscribe(v => { 
        this.columnDefinitionsTempl[0].hidetempl = this.wtId.value;
        this.columnDefinitionsTempl[1].hidetempl = this.wtNom.value;  
        this.columnDefinitionsTempl[2].hidetempl = this.actionTempl.value;
         
      });
    }
  
  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.route.queryParams.subscribe(params => {
			this.tabIndex = params.index||2;
    });
    this.getSecteur();
    this.infosCriterePwd();
    this.listWidget()
    this.listTemplateWidget()
  }

  listWidget() {
    this.widgetService.listeWidget().subscribe(data => {
      if (data.statut) {
        this.widget = data.data;
        //console.log('------------------------------');
       // console.log(this.workflow);
        this.dataSourceWidget = new MatTableDataSource<Widget>(data.data);
        //console.log('+++++++++++++++++++++++++++++++++++++++++++++++'+JSON.stringify(data.data));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        //console.log(data.description);
      }

    })
  }

  listTemplateWidget() {
    this.widgetService.listeTemplateWidget().subscribe(data => {
      if (data.statut) {
        this.widget = data.data;
        //console.log('------------------------------');
       // console.log(this.workflow);
        this.dataSourceTempWidget = new MatTableDataSource<TemplateWidget>(data.data);
        //console.log('+++++++++++++++++++++++++++++++++++++++++++++++'+JSON.stringify(data.data));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        //console.log(data.description);
      }

    })
  }


  infosCriterePwd() {
    this.criterePwdService.infoCriterePwd().subscribe(data => {
      this.criterePwd = data.data[0];
      // alert("*** "+data.data.pwdDure+" ***");

    })
  }
  getSecteur() {
    this.paramService.getListSecteur().subscribe(data => {
      console.log("-------------" + data);
      this.secteur = data.data;
      this.dataSource = new MatTableDataSource<Secteur>(data.data);
      this.dataSource.paginator = this.paginator;


    });
  }

  openDialogAddWidget() {
    const dialog1 = this.dialog.open(AddwidgetComponent, {
      disableClose: true,
      width: '700px' 
    }).afterClosed().subscribe(result => {
      this.listWidget();
    });

  }
  

  openDialogAddAttrTemplateWidget(element) {
    const dialog1 = this.dialog.open(AttributiontempwidgetComponent, {
      disableClose: true,
      data: element,
      width: '700px' 
    }).afterClosed().subscribe(result => {
      this.listWidget()
    });

  }

  openDialogEditAttrTemplateWidget(element) {
    const dialog1 = this.dialog.open(EditattrtempwidgetComponent, {
      disableClose: true,
      data: element,
      width: '700px' 
    }).afterClosed().subscribe(result => {
      this.listWidget()
    });

  }

  openDialogAddTemplateWidget() {
    const dialog1 = this.dialog.open(AddtemplatewidgetComponent, {
      disableClose: true,
      width: '700px' 
    }).afterClosed().subscribe(result => {
      this.listTemplateWidget()
    });

  }

  openDialogEditWidget(element) { 
    const dialog1 = this.dialog.open(EditwidgetComponent, {
      disableClose: true,
      width: '700px',
      data: element
    }).afterClosed().subscribe(result => {
      this.listWidget();
    });

  }
  openDialogEditTempWidget(element) { 
    const dialog1 = this.dialog.open(EdittemplatewidgetComponent, {
      disableClose: true,
      width: '700px',
      data: element
    }).afterClosed().subscribe(result => {
      this.listTemplateWidget()
    });

  }
   

  openDialogUpdate(criterePwd) {
    console.log(criterePwd);
    const dialog1 = this.dialog.open(EditCriterePwdComponent, {
      disableClose: true,
      width: '700px',
      data: criterePwd
    }).afterClosed().subscribe(result => {
      this.infosCriterePwd();
    });

  }
  openDialogDeleteSecteur(username) {
		const message = "secteur.alert-suppression";
		const dialogData = new ConfirmDialogModel("secteur.delete", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
      disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
        this.deleteSecteur(username);
			}
		});
	}
  
  
  
  deleteSecteur(element) {
    this.paramService.deleteSecteur(element).subscribe(data => {
      // console.log("fffffffffff"+data.description);
      this.getSecteur();

    });

  }


  openDialogDeleteWidget(element) {
		const message = "widget.alert-suppression";
		const dialogData = new ConfirmDialogModel("widget.delete", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
      disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
        this.deleteWidget(element);
			}
		});
	}
   
  deleteWidget(element) {
    this.widgetService.deleteWidget(element).subscribe(data => {
      // console.log("fffffffffff"+data.description);
      this.listWidget();

    });

  }
   
   
  deleteTempWidget(element) {
    this.widgetService.deleteTemplateWidget(element).subscribe(data => {
      // console.log("fffffffffff"+data.description);
      this.listTemplateWidget()

    });

  }

  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  /*openSnackBar(message: string, action: string): MatSnackBarRef < SimpleSnackBar > {
    return this.snackBar.open(message, action, {
      duration: 2000,
    });
}*/




}



