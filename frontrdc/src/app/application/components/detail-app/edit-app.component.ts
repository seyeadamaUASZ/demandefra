import { Component, OnInit, ViewChild, Inject, Pipe } from '@angular/core';
import { Application } from 'src/app/application/models/application';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatDialog } from '@angular/material';
import { FormBuilder, Validators, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { DetailUtilisComponent } from 'src/app/utilisateur/components/detail-utilis/detail-utilis.component';
import { WorkflowService } from '../../services/workflow.service';
import { DatePipe } from '@angular/common';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { ApplicationService } from '../../services/application.service';

import { FonctionnaliteService } from '../../services/fonctionnalite.service';
import { DetailsFoncComponent } from '../fonctionnalite/detail-fonc/details-fonc.component';
import { FormulaireServiceService } from 'src/app/formulaire/components/service/formulaireService.service';

@Component({
  selector: 'app-edit-app',
  templateUrl: './edit-app.component.html',
  styleUrls: ['./edit-app.component.scss']
})
export class EditAppComponent implements OnInit {
  toppings = new FormControl();
  datePipeString: string;
  modules;
  workflows;
  fichiers;
  idModule;
  formulaires;
  ApplicationForm: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  isOptional: any;
  profiles: [];
  module;
  id;
  idApp
  idAppliFonc
  application: Application = null;
  // dataSource: MatTableDataSource<Application>;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<any>;
  displayedColumns = ['modNom','modDescription','action'];


  constructor(private formbuild: FormBuilder, private router: Router, private userService: UserService,
    private route: ActivatedRoute,
    //public dialogRef: MatDialogRef<DetailUtilisComponent>,
    private workflowServis: WorkflowService,
    //@Inject(MAT_DIALOG_DATA) public donnee: any,
    private _snackBar: MatSnackBar, private usersService: UserService,
    private datePipe: DatePipe,
    private fichierService: FichierService,
    private appService: ApplicationService,
    private formService: FormulaireServiceService,
    private fonctionnaliteService: FonctionnaliteService,
    private dialog: MatDialog,


  ) {
    this.id = this.route.snapshot.paramMap.get('id');
   // alert("mon alert"+ this.id);
  }

  ngOnInit() {
    this.detail();
    this.listeModule();
    this.listFormulaire();
    this.listWorkFlw();
    this.listFichier();
    this.secondFormGroup = this.formbuild.group({
      secondCtrl: 'ok'
    });
    this.thirdFormGroup = this.formbuild.group({
      thirdCtrl: ''
    });
    this.fourthFormGroup = this.formbuild.group({
      fourthCtrl: ''
    });
    this.appService.listeModuleByApp(this.id).subscribe((data:any)=>{
      if(data.statut){
        this.dataSource = new MatTableDataSource(data.data);
        this.dataSource.paginator = this.paginator;
      }
    },error=>{},()=>{
      
    })
  }
  listWorkFlw() {
    this.workflowServis.listWorkflowsByApp(this.id).subscribe(data => {
      this.workflows = data.data;
    })
  }
  listFormulaire() {
    this.appService.listFormulaireByApp(this.id).subscribe(data => {
      this.formulaires = data.data;
    })
  }
  listFichier() {
    this.fichierService.listFichierByApp(this.id).subscribe(data => {
      this.fichiers = data.data;
    })
  }
  detail() {
    // this.application = this.donnee;
    this.appService.getDetailApp(this.id).subscribe(data => {
      this.application = data.data;
      this.datePipeString = this.datePipe.transform(this.application.appDateCreation, 'dd-MM-yyyy');
    })



  }
  listeModule() {
    this.appService.listeModuleByApp(this.id).subscribe(res => {
      this.module = res.data;
      console.log("--------les modules--------"+this.module);
    })
  }
  openDialogFonctionnaliteApplication(module): void{
    //this.router.navigate(['/application/fonctionnalite/', id]);
    this.router.navigate(['/application/fonctionnalite/',module.modId]);
  
  }
  openDialogChoixFonctionnalite(donne): void {
  
    //this.idModule = donne;
    // this.listeFonctionnaliteByModule();
     const dialog1 = this.dialog.open(DetailsFoncComponent, {
      disableClose: true,
       width: '900px',
       //data: donne
       data: {
        idModule: donne,
        idApp: this.id,
      }
     });
  }
  listeFonctionnaliteByModule() {
    this.fonctionnaliteService.listFonctionnalite(this.idModule).subscribe(data => {
      this.modules = data.data;
      console.log(this.modules);
     // (this.modules);   
    });
  }

  //}
  // onSubmit() {
  //   this.userService.addApplication(this.applicationForm.value).subscribe(data => {
  //     // console.log(data);
  //     if (data.statut) {
  //       this._snackBar.open(data.description, 'Verification', {
  //         duration: 2000,
  //       });
  //       this.closeDialog();

  //     } else {
  //       window.alert('error');
  //     }

  //   });
  // }

  closeDialog() {
    // this.dialogRef.close();
    this.router.navigate(['/application']);

  }

}
