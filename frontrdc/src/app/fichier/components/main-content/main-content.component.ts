import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatSnackBar, SimpleSnackBar, MatSnackBarRef,MatDialogConfig, } from '@angular/material';
import { User } from 'src/app/utilisateur/models/user';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FichierService } from '../../services/fichier.service';
import { Fichier } from '../../models/fichier';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AjouterFichierComponent } from '../ajouter-fichier/ajouter-fichier.component';
import { EditFormComponent } from '../edit-form/edit-form.component';
import { EditFichierComponent } from '../edit-fichier/edit-fichier.component';
import { EditRapportFileComponent } from '../edit-rapport-file/edit-rapport-file.component';
import { filter } from 'rxjs/operators';
import { Observable, merge } from 'rxjs';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;
  fichier;
  rapportNotGenere: any;
  rapportGeneres: any;
  loading:any;
  previousUrl: String;
  dataSource: MatTableDataSource<Fichier>;
  dataSourceRapportNoGeneres: MatTableDataSource<Fichier>;
  dataSourceRapportGeneres: MatTableDataSource<Fichier>;
  constructor( private dialogRef: MatDialog, private formbuild: FormBuilder, private _snackBar: MatSnackBar, private translate: TranslateService, private fichierService:FichierService, private dialog:MatDialog, private router: Router) {
    router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event: NavigationEnd) => {
      this.previousUrl = event.url;
    });
  }
  displayedColumns: string[] = ['fhrNom', 'action'];
  form:FormGroup = new FormGroup({
    rptNom: new FormControl(false),
    rptDescription: new FormControl(false),
    action: new FormControl(false),
  });

  rptNom = this.form.get('rptNom');
  rptDescription = this.form.get('rptDescription');
  action = this.form.get('action');

  
  columnDefinitions = [
    { def: 'rptNom', label: 'Nom', hide: false},
    { def: 'rptDescription', label: 'Description', hide: false},
    { def: 'action', label: 'Action', hide: false}
  ]
  getDisplayedColumns():string[] {
    return this.columnDefinitions.filter(cd=>!cd.hide).map(cd=>cd.def);
  }

  displayedColumnsRapportNoGeneres = ['rptNom','rptDescription','action'];
  displayedColumnsRapportGeneres = ['rptNom','rptDescription','action'];
  ngOnInit() {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
  }


  ngAfterViewInit() {
    let o1:Observable<boolean> =this.rptNom.valueChanges;
    let o2:Observable<boolean> = this.rptDescription.valueChanges;
    let o3:Observable<boolean> = this.action.valueChanges;

    merge(o1, o2,o3).subscribe( v=>{
    this.columnDefinitions[0].hide = this.rptNom.value;
    this.columnDefinitions[1].hide = this.rptDescription.value;
    this.columnDefinitions[2].hide = this.action.value;
       console.log(this.columnDefinitions);
     });
   }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  applyFilterRapportGeneres(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceRapportGeneres.filter = filterValue.trim().toLowerCase();
  }

  applyFilterRapportNoGeneres(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceRapportNoGeneres.filter = filterValue.trim().toLowerCase();
  }
  // getFichier(){
  //   this.fichierService.listFichier().subscribe(data =>{
  //   this.fichier=data.data;
  //   this.dataSource = new MatTableDataSource<Fichier>(data.data);
  //   this.dataSource.paginator = this.paginator.toArray()[2];
  //   this.dataSource.sort = this.sort;
  //   })
  // }

  listeRapportNotGenerer(){
    this.fichierService.listeRapportNotgenerer().subscribe(res=>{
      this.rapportNotGenere=res.data;
      this.dataSourceRapportNoGeneres = new MatTableDataSource(this.rapportNotGenere);
      this.dataSourceRapportNoGeneres.paginator = this.paginator.toArray()[0];
      
    })
  }

  listeRapportGenerer(){
    this.fichierService.listeRapportGenerer().subscribe(res=>{
      this.rapportGeneres=res.data;
      this.dataSourceRapportGeneres = new MatTableDataSource(this.rapportGeneres);
      this.dataSourceRapportGeneres.paginator = this.paginator.toArray()[1];
    })
  }

  openDialogAddFichier(): void {
    const dialog1 = this.dialog.open(AjouterFichierComponent, {
      disableClose: true,
      width: '400px'
  
    }).afterClosed().subscribe(data => {
      this.listeRapportNotGenerer();
    });
  }

  openDialogEditFichier(rapport): void {
    const dialog = this.dialogRef.open(EditFichierComponent, {
      disableClose: true,
      width: '400px',
      data: rapport
    }).afterClosed().subscribe(result => {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    });
  
  }

  openDialogEditRapportFile(rapport): void {
    const dialog = this.dialogRef.open(EditRapportFileComponent, {
      disableClose: true,
      width: '400px',
      height:'400px',
      data: rapport
    }).afterClosed().subscribe(result => {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    });
  
  }

  openDialogEditRapport(idRapport): void {
    const dialog = this.dialogRef.open(EditFormComponent, {
      disableClose: true,
      width: '1000px',
      data: idRapport
    }).afterClosed().subscribe(result => {
  
    });
  
  }

  validerRapport(data){
    this.fichierService.validerRapport(data).subscribe(res=>{
      this._snackBar.open(res.description, 'Verification', {
        duration: 2000,
      });
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    })
  }

  modeliserRapport(data){
    this.fichierService.modeliserRapport(data).subscribe(res=>{
      this._snackBar.open(res.description, 'Verification', {
        duration: 2000,
      });
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    })
  }

  openDialogDeleteFile(fichier) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("fichier suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
    maxWidth: "400px",
    data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if(dialogResult === true){
      this.delete(fichier);
    }
    });
  }
  delete(fichier) {
    let messageSuccess;
    let messageError;
    this.translate.get('fichier.confirm-suppression').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('fichier.erreur-suppression').subscribe((res: string) => {
      messageError = res;
    });
    this.fichierService.deleteFichier(fichier).subscribe(data => {
      if (data.statut) {
        this._snackBar.open(messageSuccess, 'Verification', {
          duration: 2000,
        });
      } else {
        this._snackBar.open(messageError, 'Verification', {
          duration: 2000,
        });
      }
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    });
  }

  openDialogDeleteRapport(rapport) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("fichier suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
    maxWidth: "400px",
    data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if(dialogResult === true){
      this.deleteRapport(rapport);
    }
    });
  }
  deleteRapport(rapport) {
    let messageSuccess;
    let messageError;
    this.translate.get('fichier.confirmSupp').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('fichier.erreurSupp').subscribe((res: string) => {
      messageError = res;
    });
    this.fichierService.supprimerRapport(rapport).subscribe(data => {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
      if (data.statut) {
        this._snackBar.open(messageSuccess, 'Verification', {
          duration: 2000,
        });
      } else {
        this._snackBar.open(messageError, 'Verification', {
          duration: 2000,
        });
      }
    });
  }

  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

   gotoExprotFile(rptId): void{   
    this.router.navigate(['/fichier/export'], { 
      state: { 
        data: rptId,
        previousUrl: this.previousUrl
      } 
    });    
  }

}
