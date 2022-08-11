import { Component, OnInit, Input, ViewChild, AfterViewInit, ViewChildren, QueryList } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { merge, Observable } from 'rxjs';
import { AjoutAppComponent } from '../ajout-app/ajout-app.component';
import { AjoutUtilisComponent } from 'src/app/utilisateur/components/ajout-utilis/ajout-utilis.component';
import { AjoutModuleComponent } from '../ajout-module/ajout-module.component';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { Application } from 'src/app/application/models/application';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EditAppComponent } from '../detail-app/edit-app.component';
import { EtapeCreationAppComponent } from '../edit-app/etape-creation-app.component';
import { ApplicationService } from '../../services/application.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import * as fileSaver from 'file-saver';
import { HttpHeaders } from '@angular/common/http';
import { FormulaireServiceService } from 'src/app/formulaire/components/service/formulaireService.service';
import { FormPublicationComponent } from '../form-publication/form-publication.component';
import { EditModuleComponent } from '../edit-module/edit-module.component';
@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit, AfterViewInit {
  tabIndex = 0;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  isOptional = false;
  breakpoint: any;
  nbrCommerciaux: any;
  nbrAdmin: any;
  nbrAppli: any;
  nbrModule: any;
  nbrIntegrateur: any;
  nbrConnect: any;

  displayedColumns: string[] = ['appNom', 'appNomEntreprise', 'appStatus', 'action'];
  // displayedColumns: string[] = ['appId', 'appNom', 'appAdresse', 'appNinea', 'appNomEntreprise', 'appEmail', 'action'];
  form: FormGroup = new FormGroup({
    appNom: new FormControl(false),
    appNomEntreprise: new FormControl(false),
    action: new FormControl(false),

  });
  appNom = this.form.get('appNom');
  appNomEntreprise = this.form.get('appNomEntreprise');
  action = this.form.get('action');


  cbValues;

  columnDefinitions = [
    { def: 'appNom', label: 'Nom', hide: false },
    { def: 'appNomEntreprise', label: 'Entreprise', hide: false },
    { def: 'appStatus', label: 'Status', hide: false },
    { def: 'action', label: 'Action', hide: false }
  ]
  getDisplayedColumns(): string[] {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  displayedColumnsModule = ["modNom", "modDescription", "actionModule"];

  formModule: FormGroup = new FormGroup({
    modNom: new FormControl(false),
    modDescription: new FormControl(false),
    actionModule: new FormControl(false),

  });
  modNom = this.formModule.get('modNom');
  modDescription = this.formModule.get('modDescription');
  actionModule = this.formModule.get('actionModule');

  columnDefinitionsModule = [
    { defModule: 'modNom', labelModule: 'Nom', hideModule: false },
    { defModule: 'modDescription', labelModule: 'Description', hideModule: false },
    { defModule: 'actionModule', labelModule: 'Action', hideModule: false },
  ]
  getDisplayedColumnsModule(): string[] {
    return this.columnDefinitionsModule.filter(cdModule => !cdModule.hideModule).map(cdModule => cdModule.defModule);
  }
  ngAfterViewInit() {
    let o1: Observable<boolean> = this.appNom.valueChanges;
    let o2: Observable<boolean> = this.appNomEntreprise.valueChanges;
    let o3: Observable<boolean> = this.action.valueChanges;

    merge(o1, o2, o3).subscribe(v => {
      this.columnDefinitions[0].hide = this.appNom.value;
      this.columnDefinitions[1].hide = this.appNomEntreprise.value;
      this.columnDefinitions[2].hide = this.action.value;
      console.log(this.columnDefinitions);
    });


    let i1: Observable<boolean> = this.modNom.valueChanges;
    let i2: Observable<boolean> = this.modDescription.valueChanges;
    let i3: Observable<boolean> = this.actionModule.valueChanges;

    merge(i1, i2, i3).subscribe(v => {
      this.columnDefinitionsModule[0].hideModule = this.modNom.value;
      this.columnDefinitionsModule[1].hideModule = this.modDescription.value;
      this.columnDefinitionsModule[2].hideModule = this.actionModule.value;
    });

  }

  result;
  // @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>()
  @ViewChild(MatSort) sort: MatSort;
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceModule.filter = filterValue.trim().toLowerCase();
  }
  dataSource: MatTableDataSource<Application>;
  application: any;
  module: any;
  loading: boolean;

  dataSourceModule: MatTableDataSource<any[]>;




  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private _formBuilder: FormBuilder,
    // private dialogRef: MatDialogRef<AjoutAppComponent>,
    private snackBar: MatSnackBar,
    private notification: NotificationService,
    private translate: TranslateService, private userService: FormulaireServiceService,
    private appService: ApplicationService) {
    this.loading = false;
  }


  listApplication() {
    this.userService.listeApplication().subscribe(data => {
      if (data.statut) {
        this.application = data.data;
        //sort for most recent
        this.application = this.application.reverse();
        this.dataSource = new MatTableDataSource<Application>(this.application);
        this.dataSource.paginator = this.paginator.toArray()[0];
        this.dataSource.sort = this.sort;
      } else {
      }

    })
  }

  telecharger(id) {
    this.loading = true
    this.appService.zipper(id).subscribe(res => {
      this.result = res
      this.loading = false
      const message = this.result.data;
      const dialogData = new ConfirmDialogModel('Le lien d\'emplacement de votre application', message);
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: true,
        maxWidth: "400px",
        data: dialogData
      });
    })
  }
  openDialogDownloadApplication(data) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel('application.alert-telecharger', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "500px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.download(data);
      }
    });
  }
  // dialog for publication
  openDialogPublier(data) {
    const dialogRef = this.dialog.open(FormPublicationComponent, {
      data: data,
      disableClose: true,
      width: '650px'
    });
    dialogRef.afterClosed().subscribe(data => {
      this.listApplication();
    });
  }
  // depublier une app
  depublier(app) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.appService.depublierApp(app).subscribe((data) => {
          if (data.statut) {
            this.translate.get(data.description).subscribe(res => {
              this.notification.success(res);
              this.listApplication();
            });
          }
        });
      }
    });
  }
  download(data) {
    this.loading = true
    this.appService.downloadFile(data.appId).subscribe(response => {
      this.loading = false
      console.log(response)
      this.saveFile(response.body, data.appNom + ".zip");
    })
  }
  saveFile(data: any, filename?: string) {

    const blob = new Blob([data], { type: 'text/zip' });
    fileSaver.saveAs(blob, filename);
  }


  openDialogUpdate(application) {
    this.router.navigate(['/application/detail/', application.appId]);

    console.log(application);
    // const dialog1 = this.dialog.open(EditAppComponent, {
    //  // width: '800px',
    //   data: application
    // }).afterClosed().subscribe(data => {
    //   this.listApplication();
    // });

  }
  openDialogDupliquer(form) {
    let messageDesactive;
    this.translate.get('application.confirmegeneration').subscribe((res: string) => {
      messageDesactive = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("application.confirmegeneration", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.genererApplication(form);
      }
    });
  }

  genererApplication(id) {
    this.loading = true;
    this.userService.genererFormulaire("frontrdc", id).subscribe(data => {
      this.loading = false;
      // this.snackBar.open(data.description, 'Verification', {
      //   duration: 2000,

      // });
      this.translate.get(data.description).subscribe((res: string) => {
        this.notification.success(res);
      });

    })
  }
  openDialogDeleteApplication(username) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel('application.alert-suppression', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.delete(username);
      }
    });
  }


  delete(username) {
    let messageSuccess;
    let messageError;
    this.translate.get('utilisateur.confirm-suppression').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('utilisateur.erreur-suppression').subscribe((res: string) => {
      messageError = res;
    });
    this.userService.deleteApplication(username).subscribe(data => {
      if (data.statut) {
        let invalidForm;
        this.translate.get('application.application-supprime').subscribe((res: string) => {
          this.notification.success(res);
        });

      } else {
        this.translate.get('application.error').subscribe((res: string) => {
          this.notification.error(res);
        });
      }
      this.listApplication();
    });
  }
  
  statut(username) {

  }
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.tabIndex = params.index;
    });
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.listApplication();
    this.listeModule();
    this.nbrcomm();
    this.nbrconnect();
    this.nbrintegrateur();
    this.nbrapplication();
    this.nbrmodule();
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ''
    });
    this.thirdFormGroup = this._formBuilder.group({
      thirdCtrl: ''
    });

  }

  listeModule() {
    this.userService.listeModule().subscribe(res => {
      this.module = res.data;
      this.dataSourceModule = new MatTableDataSource<any[]>(this.module);
      this.dataSourceModule.paginator = this.paginator.toArray()[1];
    })
  }
  nbrmodule() {
    this.userService.nbrModule().subscribe(res => {
      this.nbrModule = res.data;
      console.log(this.module)
    })
  }
  nbrapplication() {
    this.userService.nbrApplication().subscribe(res => {
      this.nbrAppli = res.data;
    })
  }
  nbrintegrateur() {
    this.userService.nbrIntegrateur().subscribe(res => {
      this.nbrIntegrateur = res.data;
    })
  }
  nbrconnect() {
    this.userService.nbrUserConnect().subscribe(res => {
      this.nbrConnect = res.data;
    })
  }
  nbrcomm() {
    this.userService.nbrCommerciaux().subscribe(res => {
      this.nbrCommerciaux = res.data;
    })
  }


  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 5;
  }

  __cardClick(emp) {
    console.log(emp);
    this.router.navigate(['application', emp.id]);
  }
  openDialogAddApp(): void {
    this.router.navigate(['/application/creation']);
    // const dialog1 = this.dialog.open(AjoutAppComponent, {
    //   width: '1000px',

    // }).afterClosed().subscribe(data => {
    //   this.listApplication();
    // });
  }
  openDialogAddModule(): void {
    const dialog1 = this.dialog.open(AjoutModuleComponent, {
      disableClose: true

    }).afterClosed().subscribe(data => {
      this.listeModule();
      this.nbrModule();
    });
  }
  openDialogEditModule(module): void {
    const dialog1 = this.dialog.open(EditModuleComponent, {
      disableClose: true,
      data: module

    }).afterClosed().subscribe(data => {
      this.listeModule();
      this.nbrModule();
    });
  }

  supprimerModule(module) {
    let alertSupp;
    this.translate.get('application.module.confirm-suppression').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("application.module.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.appService.deleteModule(module).subscribe((data) => {
          this.loading = false;
          this.notification.info(alertSupp);
          this.listeModule();
        });
      }
    });
  }

  openDialogEtapeCreation(element) {
    this.router.navigate(['/application/edit/', element.appId]);

    // const dialog1 = this.dialog.open(EtapeCreationAppComponent, {
    // data:element
    // }).afterClosed().subscribe(data => {
    //   this.listApplication();
    //   this.nbrapplication();
    // });


  }


  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this.snackBar.open(message, action, {
      duration: 2000,
    });
  }



  openDialogFonctionnaliteApplication(module): void {
    //this.router.navigate(['/application/fonctionnalite/', id]);
    this.router.navigate(['/application/fonctionnalite/', module.modId]);

  }
}
