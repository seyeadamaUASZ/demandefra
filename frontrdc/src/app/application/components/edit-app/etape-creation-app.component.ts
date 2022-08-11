import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef, MatDialog, MatTableDataSource, MatPaginator } from '@angular/material';
import { Application } from 'src/app/application/models/application';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { WorkflowService } from '../../services/workflow.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { ApplicationService } from '../../services/application.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { FonctionnaliteService } from '../../services/fonctionnalite.service';
import { ChoixFoncComponent } from '../fonctionnalite/choix-fonc/choix-fonc.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-etape-creation-app',
  templateUrl: './etape-creation-app.component.html',
  styleUrls: ['./etape-creation-app.component.scss']
})
export class EtapeCreationAppComponent implements OnInit {
  // firstFormGroup: FormGroup;
  listModuleChecked;
  workflows;
  idApp;
  idModule;
  modules;
  formulaires;
  fichiers;
  formulaire = new FormControl();
  fichier = new FormControl();
  workflow = new FormControl();
  workflowSelected: [];
  formulaireSelected: [];
  fichierSelected: [];
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup
  ApplicationForm: any;
  toppingList: any;
  toppingList1: any;
  isOptional = false;
  module;
  id;
  application: Application = null;
  applicationForm = this._formBuilder.group({
    appId: ['', Validators.required],
    appNomEntreprise: ['', Validators.required],
    appSecteur: ['', Validators.required],
    appNom: ['', Validators.required],
    appNinea: [''],
    appAdresse: [''],
    appTelephoneFixe: [''],
    appTelephoneMobile: [''],
    appEmail: [''],
    appDateCreation: ['']

  });
  addAppFoncForm = this._formBuilder.group({
    appliFoncFonId: ['', Validators.required],
    appliFoncAppId: ['', Validators.required],
    applifonisActive: ['', Validators.required],

  });
  get f() { return this.applicationForm.controls; }

  fichierForm = this._formBuilder.group({
    idApp: [''],
    idFichier: [''],
  });
  workflowForm = this._formBuilder.group({
    idApp: [''],
    idWorkflow: ['']
  });
  formulaireForm = this._formBuilder.group({
    idApp: [''],
    idFormulaire: ['']
  });

  dataSource: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator;
  displayedColumns = ['select','modNom','modDescription','action'];
  selection = new SelectionModel<any>(true, []);

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => {this.lierModule({checked:true},row);});
  }
  oldcheckMod(row){
    
    this.listModuleChecked.forEach(element => {
          if(row.modId == element.modId){
            this.selection.toggle(row);
          }
        }); 
  }
  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  constructor(private _formBuilder: FormBuilder,
    // @Inject(MAT_DIALOG_DATA) public donnee: any, 
    private usersService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    // public dialogRef: MatDialog,
    private workflowService: WorkflowService,
    private _snackBar: MatSnackBar,
    private fichierService: FichierService,
    private applicationService: ApplicationService,
    private fonctionnaliteService: FonctionnaliteService,
    private dialog: MatDialog,
    private notification: NotificationService,
    private translate: TranslateService


  ) {
    this.id = this.route.snapshot.paramMap.get('id');

  }


  listWorkFlw() {
    // this.workflowService.listWorkflows().subscribe(data => {
    this.applicationService.getWorkflowLibreOuSpecifique(this.id).subscribe(data => {
      this.workflows = data.data;
      // console.log("-------------"+this.workflows);
      this.workflowSelected = this.workflows.filter(i => i.wkfAppId != null);
      //alert(this.workflows);
    })
  }
  listFormulaire() {
    // this.usersService.listeFormulaire().subscribe(data => {
    // this.applicationService.getFormulaireLibre().subscribe(data => {
    this.applicationService.getFormulaireLibreOuSpecifique(this.id).subscribe(data => {
      this.formulaires = data.data;
      this.formulaireSelected = this.formulaires.filter(i => i.frmAppId != null);

      // alert(this.formulaires);
    })
  }

  listFichier() {
    // this.fichierService.listFichier().subscribe(data => {
    this.applicationService.getFichierLibreOuSpecifique(this.id).subscribe(data => {
      this.fichiers = data.data;
      this.fichierSelected = this.fichiers.filter(i => i.rptAppId != null);
      // alert(this.fichier);
    })
  }
  ngOnInit() {
    this.detail();
    //this.getEtape();
    this.listeModule();
    this.listWorkFlw();
    this.listFichier();
    this.listFormulaire();
    this.listFichier();
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ''
    });
    this.thirdFormGroup = this._formBuilder.group({
      // thirdCtrl: ''
    });
    this.fourthFormGroup = this._formBuilder.group({
      fourthCtrl: ''
    });
    this.applicationService.listeModuleByApp(this.id).subscribe((data:any)=>{
      if(data.statut){
        this.listModuleChecked = data.data;
      }
    },error=>{},()=>{
      this.dataSource.data.forEach(row=>{
        this.oldcheckMod(row);
      })
    })
  }
  getEtape() {
    this.secondFormGroup.value.secondCtrl = "ok";
  }
  listeModule() {
    this.applicationService.listeModule().subscribe(res => {
      this.module = res.data;
      //alert("okkk"+ this.module);
      this.dataSource = new MatTableDataSource(this.module);
      this.dataSource.paginator = this.paginator;
    })
  }


  detail() {
    this.applicationService.getDetailApp(this.id).subscribe(data => {
      this.application = data.data;
      this.applicationForm.setValue({
        appId: this.application.appId,
        appNomEntreprise: this.application.appNomEntreprise ? this.application.appNomEntreprise : null,
        appSecteur: this.application.appSecteur ? this.application.appSecteur : null,
        appNom: this.application.appNom ? this.application.appNom : null,
        appNinea: this.application.appNinea ? this.application.appNinea : null,
        appAdresse: this.application.appAdresse ? this.application.appAdresse : null,
        appTelephoneFixe: this.application.appTelephoneFixe ? this.application.appTelephoneFixe : null,
        appTelephoneMobile: this.application.appTelephoneMobile ? this.application.appTelephoneMobile : null,
        appEmail: this.application.appEmail ? this.application.appEmail : null,
        appDateCreation: this.application.appDateCreation ? this.application.appDateCreation : null,

      });
      //this.datePipeString = this.datePipe.transform(this.application.appDateCreation, 'dd-MM-yyyy');
    })



  }
  onSubmit() {
    if (this.applicationForm.value.appNom &&
      this.applicationForm.value.appNomEntreprise &&
      this.applicationForm.value.appSecteur) {
      this.applicationService.addApplication(this.applicationForm.value).subscribe(data => {
        // console.log(data);
        if (data.statut) {
          // this._snackBar.open(data.description, 'Verification', {
          //   duration: 2000,
          // });
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          //this.closeDialog();

        } 
        // else {
        //   //window.alert('error');
        //   this.translate.get(data.description).subscribe((res: string) => {
        //     this.notification.warn(res);
        //   });
        //   // this._snackBar.open(data.description, 'Verification', {
        //   //   duration: 2000,
        //   // });
        // }

      });
    } else {
      this.translate.get("application.invalid-form").subscribe((res: string) => {
        this.notification.warn(res);
      });
      // this._snackBar.open("formulaire invalide", 'Erreur', {
      //   duration: 2000,
      // });

    }

  }

  closeDialog() {
    this.router.navigate(['/application']);

    //this.dialogRef.closeAll();
  }
  clickedOptionWorkflow(event, idWorkf) {

    this.workflowForm.value.idApp = this.id;
    this.workflowForm.value.idWorkflow = idWorkf;

    if (event) {
      this.applicationService.LierWorkflowEtApp(this.workflowForm.value).subscribe(data => {
      })
    } else {
      this.applicationService.EnleverliaisonWorkflowEtApp(this.workflowForm.value.idWorkflow).subscribe(data => {
        //alert(data.statut);
      })
    }

  }
  //formulaire
  clickedOptionFormulaire(event, idForm) {
    this.formulaireForm.value.idApp = this.id;
    this.formulaireForm.value.idFormulaire = idForm;
    if (event) {
      this.applicationService.LierFormulaireEtApp(this.formulaireForm.value).subscribe(data => {
      })
    } else {
      this.applicationService.EnleverliaisonFormulaireEtApp(this.formulaireForm.value.idFormulaire).subscribe(data => {
        //alert(data.statut);
      })
    }

  }
 
  enregistrer() {
    this.translate.get("application.mis_a_jour").subscribe((res: string) => {
      this.notification.success(res);
    });
  }
  //fichier
  clickedOptionFichier(event, idFichier) {
    this.fichierForm.value.idApp = this.id;
    this.fichierForm.value.idFichier = idFichier;
    if (event) {
      this.applicationService.LierFichierEtApp(this.fichierForm.value).subscribe(data => {
      })
    } else {
      this.applicationService.EnleverliaisonFichierEtApp(this.fichierForm.value.idFichier).subscribe(data => {
        // alert(data.statut);
      })
    }

  }
  openDialogFonctionnaliteApplication(module): void {
    //this.router.navigate(['/application/fonctionnalite/', id]);
    this.router.navigate(['/application/fonctionnalite/', module.modId]);

  }
  ajoutAppFonc() {

    //this.addAppFoncForm.value.appliFoncFonId;
    this.addAppFoncForm.value.appliFoncAppId = this.application.appId;
    //alert(""+this.idApp);
    this.fonctionnaliteService.creatAppFonc(this.addAppFoncForm.value).subscribe(data => {
      // alert(data.description);

    })
  }
  openDialogChoixFonctionnalite(donne): void {
    this.idModule = donne;
    const dialogRef = this.dialog.open(ChoixFoncComponent, {
      disableClose: true,
      width: '900px',
      data: {
        idModule: donne,
        idApp: this.id
      }
    })
  }
  DesactiverListeFonctionnaliteByModule() {
    this.fonctionnaliteService.listFonctionnalite(this.idModule).subscribe(data => {
      this.modules = data.data;
      console.log(this.modules);
      // (this.modules);
      for (let index = 0; index < this.modules.length; index++) {
        //this.idModulesSpecifiques[index] = this.modules[index].fonId;
        //this.addAppFoncForm.value.appliFoncFonId = this.modules[index].fonId;
        this.id = this.modules[index].fonId;

        //console.log("top" +this.idModulesSpecifiques);
        //this.ajoutAppFonc();
        this.enleverUneFonctionnalite();
      }

    });
  }
  enleverUneFonctionnalite() {
    this.fonctionnaliteService.desactiverAppFon(this.application.appId,this.id).subscribe(data => {

    })
  }
  lierModule(e, row) {
    //console.log("-----------"+eventt); 
    this.idModule = row.modId;
    //console.log("--------" + event.checked);
   
      if (e.checked) {
        console.log("++++++++true+++++++++");
        this.listeFonctionnaliteByModule();
      } else if(!e.checked) {
        console.log("++++++++false+++++++++");
        this.DesactiverListeFonctionnaliteByModule();
      }
    
    this.selection.toggle(row);

  }
  listeFonctionnaliteByModule() {
    this.fonctionnaliteService.listFonctionnalite(this.idModule).subscribe(data => {
      this.modules = data.data;
      console.log(this.modules);
      // (this.modules);   
      for (let index = 0; index < this.modules.length; index++) {
        //this.idModulesSpecifiques[index] = this.modules[index].fonId;
        this.addAppFoncForm.value.appliFoncFonId = this.modules[index].fonId;
        //console.log("top" +this.idModulesSpecifiques);
        this.ajoutAppFonc();
      }
    });
  }


}