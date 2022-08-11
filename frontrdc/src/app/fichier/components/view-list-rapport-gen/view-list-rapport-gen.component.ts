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
import { AjoutSousMenuComponent } from '../ajout-sous-menu/ajout-sous-menu.component';
import { AttriSousMenuComponent } from '../attri-sous-menu/attri-sous-menu.component';

@Component({
  selector: 'app-view-list-rapport-gen',
  templateUrl: './view-list-rapport-gen.component.html',
  styleUrls: ['./view-list-rapport-gen.component.scss']
})
export class ViewListRapportGenComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  fichier;
  rapportNotGenere: any;
  rapportGeneres: any;
  loading:any;
  dataSource: MatTableDataSource<Fichier>;
  constructor( private dialogRef: MatDialog, private formbuild: FormBuilder, private _snackBar: MatSnackBar, private translate: TranslateService, private fichierService:FichierService, private dialog:MatDialog, private router: Router) {}
  displayedColumns: string[] = ['fhrNom','rptDescription', 'action'];

  ngOnInit() {
      this.getFichier();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  getFichier(){
    this.fichierService.listeRapportGenerer().subscribe(data =>{
    this.fichier=data.data;
    this.dataSource = new MatTableDataSource<any>(data.data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    })
  }

  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  openDialogAttriSousMenu(rapport): void {
    const dialog1 = this.dialog.open(AttriSousMenuComponent, {
      width: '700px',
      disableClose: true,
      data:rapport
    }).afterClosed().subscribe(data => {
      this.getFichier();
    });
  }

}
