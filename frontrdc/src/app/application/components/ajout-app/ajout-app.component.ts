import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialogRef, MatSnackBarRef, SimpleSnackBar, MatSnackBar, MatDialog, MatTableDataSource, MatPaginator } from '@angular/material';
import { WorkflowService } from '../../services/workflow.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { ApplicationService } from '../../services/application.service';
import { JsonpInterceptor } from '@angular/common/http';
import { stringify } from 'querystring';
import { Formulaire } from 'src/app/utilisateur/models/formulaire';
import { Fichier } from 'src/app/fichier/models/fichier';
import { Workflow } from 'src/app/utilisateur/models/workflow';
import { NotificationService } from '../../../shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';

import { FnParam } from '@angular/compiler/src/output/output_ast';
import { element, by } from 'protractor';
import { merge, Observable } from 'rxjs';
import { FonctionnaliteService } from '../../services/fonctionnalite.service';
import { ChoixFoncComponent } from '../fonctionnalite/choix-fonc/choix-fonc.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-ajout-app',
  templateUrl: './ajout-app.component.html',
  styleUrls: ['./ajout-app.component.scss']
})
export class AjoutAppComponent implements OnInit {
  // firstFormGroup: FormGroup;
  isChecked: false;
  selectedWorkflow = false;
  selectedWork = false;
  modules;
  idModulesSpecifiques;
  idApp;
  idAppliFonc;
  idModule;
  idWorkflow;
  module;
  workflows;
  workflow;
  formulaires;
  formulaire;
  fichiers;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  isOptional = false;
   id;

  fichierCheck: [];
  removedFichier: [];
  formulaireCheck: [];
  workflowChecked: [];

  profiles: any[];
  ApplicationForm = this.formbuild.group({
    appNomEntreprise: ['', Validators.required],
    appSecteur: ['', Validators.required],
    appNom: ['', Validators.required],
    appNinea: [''],
    appAdresse: [''],
    appTelephoneFixe: [''],
    appTelephoneMobile: [''],
    appEmail: [''],
    appDateCreation: [''],
  });
  fichierForm = this.formbuild.group({
    idApp: [''],
    idFichier: [''],
  });
  workflowForm = this.formbuild.group({
    idApp: [''],
    idWorkflow: ['']
  });
  formulaireForm = this.formbuild.group({
    idApp: [''],
    idFormulaire: ['']
  });
  etape2Form = this.formbuild.group({
    etape: [''],
    idApp: ['']
  });
  etape3Form = this.formbuild.group({
    etape: [''],
    idApp: ['']
  });
  // id = this.form.get('id');
  // description = this.form.get('description');
  
  

  constructor(private formbuild: FormBuilder, private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private workflowServis: WorkflowService,
    private fichierService: FichierService,
    private appliService: ApplicationService,
    private dialog: MatDialog,
    private fonctionnaliteService: FonctionnaliteService,
    private notification: NotificationService,
    private translate: TranslateService

    // public dialogRef: MatDialogRef<AjoutAppComponent>
  ) {
  }
  get f() { return this.ApplicationForm.controls; }
  addAppFoncForm = this.formbuild.group({
    appliFoncFonId: ['', Validators.required],
    appliFoncAppId: ['', Validators.required],
    applifonisActive: ['', Validators.required],

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
 
  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  ngOnInit() {
    this.listeModule();
    this.listWorkFlw();
    this.listFormulaire();
    this.listFichier();

    this.ApplicationForm = this.formbuild.group({
      appNomEntreprise: ['', Validators.required],
      appSecteur: ['', Validators.required],
      appNom: ['', Validators.required],
      appNinea: [''],
      appAdresse: [''],
      appTelephoneFixe: [''],
      appTelephoneMobile: [''],
      appEmail: [''],
      appEtapeCreation: [],
      appDateCreation: [],
    });
    this.fichierForm = this.formbuild.group({
      fichier: [''],
    });
    this.secondFormGroup = this.formbuild.group({
      secondCtrl: 'ok',
      choixModule: '',
    });
    this.thirdFormGroup = this.formbuild.group({
      thirdCtrl: ''
    });
    this.fourthFormGroup = this.formbuild.group({
      fourthCtrl: ''
    });
    
  }
  // selectionToggle(selection: SelectionModel<Action>, row?: Action) {
  //   return selection.toggle(row);
  // }

  // addActionInPrivilege(row: Fichier) {
  //   this.fichiers.push(row);
  //   this.removedFichier = this.removedFichier.filter(element => element.fichier !== row.id);
  // }

  // removeActionFromPrivilege(row: Fichier) {
  //   this.removedFichier.push(row);
  //   this.fichierCheck = this.fichierCheck.filter(element => element.actId !== row.actId);
  // }



  // selectActionFichier(event, row: Fichier) {
  //   if (event.checked) {
  //     this.addActionInPrivilege(row);
  //   } else {
  //     this.removeActionFromPrivilege(row);
  //   }
  //   this.selectionAction.toggle(row);
  // }
  alertModule() {
    if (this.isChecked) {
      console.log("ok");

    } else {
      console.log("non");

    }
  }


  clickedOptionWorkflow(event, idWorkf) {

    this.workflowForm.value.idApp = this.idApp;
    this.workflowForm.value.idWorkflow = idWorkf;

    if (event) {
      this.appliService.LierWorkflowEtApp(this.workflowForm.value).subscribe(data => {
      })
    } else {
      this.appliService.EnleverliaisonWorkflowEtApp(this.workflowForm.value.idWorkflow).subscribe(data => {
        //alert(data.statut);
      })
    }

  }
  //formulaire
  clickedOptionFormulaire(event, idForm) {

    this.formulaireForm.value.idApp = this.idApp;
    this.formulaireForm.value.idFormulaire = idForm;

    if (event) {
      this.appliService.LierFormulaireEtApp(this.formulaireForm.value).subscribe(data => {
      })
    } else {
      this.appliService.EnleverliaisonFormulaireEtApp(this.formulaireForm.value.idFormulaire).subscribe(data => {
        //alert(data.statut);
      })
    }

  }
  //fichier
  clickedOptionFichier(event, idFichier) {

    this.fichierForm.value.idApp = this.idApp;
    this.fichierForm.value.idFichier = idFichier;

    if (event) {
      this.appliService.LierFichierEtApp(this.fichierForm.value).subscribe(data => {
      })
    } else {
      this.appliService.EnleverliaisonFichierEtApp(this.fichierForm.value.idFichier).subscribe(data => {
        // alert(data.statut);
      })
    }

  }



  listeModule() {
    this.appliService.listeModule().subscribe(res => {
      this.module = res.data;
      //alert("okkk"+ this.module);
      this.dataSource = new MatTableDataSource(this.module);
        this.dataSource.paginator = this.paginator;
    })
  }
  listFormulaire() {
    // this.userService.listeFormulaire().subscribe(data => {
    this.appliService.getFormulaireLibre().subscribe(data => {
      this.formulaires = data.data;
      // console.log(this.formulaires);
    })
  }
  listWorkFlw() {
    //this.workflowServis.listWorkflows().subscribe(data => {
    this.appliService.getWorkflowLibre().subscribe(data => {
      this.workflows = data.data;
      // console.log(this.workflows);

    })
  }
  listFichier() {
    // this.fichierService.listFichier().subscribe(data => {
    this.appliService.getFichierLibre().subscribe(data => {
      this.fichiers = data.data;
    })
  }
  onSubmit() {
    if (this.ApplicationForm.value.appNom &&
      this.ApplicationForm.value.appNomEntreprise &&
      this.ApplicationForm.value.appSecteur) {
      this.ApplicationForm.value.appEtapeCreation = 1;
      this.appliService.addApplication(this.ApplicationForm.value).subscribe(data => {
        // this.ajoutAppFonc();
        // console.log(data);
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
          this.idApp = data.data.appId;
          //alert( this.idApp);
          // this.ApplicationForm.reset();
          // this.closeDialog();
        } else {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.success(res);
          });
        }
      }, error => {
        //this.openSnackBar(error, "confirmation");
        this.notification.error(error);

      });
    } else {
      let invalidForm;
      this.translate.get('application.invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
    }

  }
  //Choix des modules
  etapeUpdate2() {
    //this.ApplicationForm.reset();
    this.etape2Form.value.etape = 2;
    this.etape2Form.value.idApp = this.idApp;
    this.appliService.updateEtapeCreationApp(this.etape2Form.value).subscribe(data => {
      //  console.log(data);
      if (data.statut) {
        this.translate.get('application.etape2').subscribe((res: string) => {
          this.notification.success(res);
        });

      }
      else {
        let errorEdit;
        this.translate.get('application.error').subscribe((res: string) => {
          this.notification.error(res);
        });
      }
    }, error => {
      this.openSnackBar(error, "confirmation");
    });

  }
  ajoutAppFonc() {

    //this.addAppFoncForm.value.appliFoncFonId;
    this.addAppFoncForm.value.appliFoncAppId = this.idApp;
    //alert(""+this.idApp);
    this.fonctionnaliteService.creatAppFonc(this.addAppFoncForm.value).subscribe(data => {
      // alert(data.description);
      this.idAppliFonc = data.data?.idAppliFonc ? data.data?.idAppliFonc : undefined;
    })
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
  listeFonctionnaliteByModuleSansAjout() {
    this.fonctionnaliteService.listFonctionnalite(this.idModule).subscribe(data => {
      this.modules = data.data;
     // console.log(this.modules);
      // (this.modules);
      // for (let index = 0; index < this.modules.length; index++) {
      //   //this.idModulesSpecifiques[index] = this.modules[index].fonId;
      //   this.addAppFoncForm.value.appliFoncFonId = this.modules[index].fonId;
      //   //console.log("top" +this.idModulesSpecifiques);
      //   //this.ajoutAppFonc();
      // }
    });
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
  //Choix des composants
  etapeUpdate3() {
    //  this.ApplicationForm.reset();
    this.etape3Form.value.etape = 3;
    this.etape3Form.value.idApp = this.idApp;

    this.appliService.updateEtapeCreationApp(this.etape3Form.value).subscribe(data => {
      // alert(data);
      //alert("le fichier" + this.thirdFormGroup.value.str)
      if (data.statut) {
        this.translate.get('etape 3').subscribe((res: string) => {
          this.notification.success(res);
        });

      }
    }, error => {
      this.openSnackBar(error, "confirmation");
    });

  }

  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  closeDialog() {
    // this.dialogRef.close();
    this.router.navigate(['application']);
  }

  save() { }

  openDialogChoixFonctionnalite(donne): void {
    this.idModule = donne;
    this.listeFonctionnaliteByModuleSansAjout();
    const dialog1 = this.dialog.open(ChoixFoncComponent, {
      disableClose: true,
      width: '900px',
      data: {
        idModule: donne,
        idApp: this.idApp,
        idAppliFonc: this.idAppliFonc
      }
    });
  }
  lierModule(e, idModuleCheck) {
    //console.log("-----------"+eventt); 
    this.idModule = idModuleCheck;
    //console.log("--------" + event.checked);
    if (e.checked) {
      console.log("++++++++true+++++++++");
      this.listeFonctionnaliteByModule();
    } else if(!e.checked) {
      console.log("++++++++false+++++++++");
      this.DesactiverListeFonctionnaliteByModule();
      
    }

  }

  enleverUneFonctionnalite() {
    this.fonctionnaliteService.desactiverAppFon(this.idApp,this.id).subscribe(data => {

    })
  }



}