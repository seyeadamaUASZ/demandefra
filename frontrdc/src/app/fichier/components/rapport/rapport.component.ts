import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatSnackBar, SimpleSnackBar, MatSnackBarRef,MatDialogConfig, } from '@angular/material';
import { User } from 'src/app/utilisateur/models/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FichierService } from '../../services/fichier.service';
import { Fichier } from '../../models/fichier';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AjouterFichierComponent } from '../ajouter-fichier/ajouter-fichier.component';
import { EditFormComponent } from '../edit-form/edit-form.component';
import { EditFichierComponent } from '../edit-fichier/edit-fichier.component';
import { EditRapportFileComponent } from '../edit-rapport-file/edit-rapport-file.component';
import { Rapport } from '../../models/rapport';
import { Menu } from 'src/app/utilisateur/models/menu';

@Component({
  selector: 'app-rapport',
  templateUrl: './rapport.component.html',
  styleUrls: ['./rapport.component.scss']
})
export class RapportComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  fichier;
  menu: any;
  rapportNotGenere: any;
  rapportGeneres: any;
  loading:any;
  dataSource: MatTableDataSource<Fichier>;
  constructor( 
    private dialogRef: MatDialog,
    private formbuild: FormBuilder,
    private _snackBar: MatSnackBar,
    private translate: TranslateService,
    private fichierService:FichierService,
    private dialog:MatDialog,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    if(this.router.getCurrentNavigation()&&this.router.getCurrentNavigation().extras&&this.router.getCurrentNavigation().extras.state){   
      this.menu  = this.router.getCurrentNavigation().extras.state['menu'];    
      sessionStorage.setItem('menu', JSON.stringify(this.menu));  
    } else {
      this.menu = JSON.parse(sessionStorage.getItem("menu"));
    }
  }
  displayedColumns: string[] = ['fhrNom', 'action'];

  ngOnInit() { 
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
      this.getFichier();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  getFichier(){
    this.fichierService.listeRapportParMenu(this.menu).subscribe(data =>{
    this.fichier=data.data;
    this.dataSource = new MatTableDataSource<any>(data.data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    })
  }

  listeRapportNotGenerer(){
    this.fichierService.listeRapportNotgenerer().subscribe(res=>{
      this.rapportNotGenere=res.data;
    })
  }

  listeRapportGenerer(){
    this.fichierService.listeRapportGenerer().subscribe(res=>{
      this.rapportGeneres=res.data;
    })
  }

  openDialogAddFichier(): void {
    const dialog1 = this.dialog.open(AjouterFichierComponent, {
      width: '700px'
  
    }).afterClosed().subscribe(data => {
      this.listeRapportNotGenerer();
    });
  }

  openDialogEditFichier(rapport): void {
    const dialog = this.dialogRef.open(EditFichierComponent, {
      width: '700px',
      data: rapport
    }).afterClosed().subscribe(result => {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    });
  
  }

  openDialogEditRapportFile(rapport): void {
    const dialog = this.dialogRef.open(EditRapportFileComponent, {
      width: '700px',
      data: rapport
    }).afterClosed().subscribe(result => {
      this.listeRapportNotGenerer();
      this.listeRapportGenerer();
    });
  
  }

  openDialogEditRapport(idRapport): void {
    const dialog = this.dialogRef.open(EditFormComponent, {
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
        previousUrl: this.router.url
      } 
    });    
  }

}
