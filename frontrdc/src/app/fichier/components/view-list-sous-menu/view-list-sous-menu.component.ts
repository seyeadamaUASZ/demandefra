import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatSnackBar, SimpleSnackBar, MatSnackBarRef,MatDialogConfig, } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FichierService } from '../../services/fichier.service';
import { AjoutSousMenuComponent } from '../ajout-sous-menu/ajout-sous-menu.component';
import { Menu } from 'src/app/utilisateur/models/menu';
import { EditSousMenuComponent } from '../edit-sous-menu/edit-sous-menu.component';

@Component({
  selector: 'app-view-list-sous-menu',
  templateUrl: './view-list-sous-menu.component.html',
  styleUrls: ['./view-list-sous-menu.component.scss']
})
export class ViewListSousMenuComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  sousMenu: any;
  loading:any;
  dataSource: MatTableDataSource<any>;
  constructor( private dialogRef: MatDialog, private formbuild: FormBuilder, private _snackBar: MatSnackBar, private translate: TranslateService, private fichierService:FichierService, private dialog:MatDialog, private router: Router) {}
  displayedColumns: string[] = ['menNom', 'action'];

  ngOnInit() {
      this.getSousMenu();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  getSousMenu(){
    this.fichierService.ListeSousMenuRapport().subscribe(data =>{
    this.sousMenu=data.data;
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

  openDialogEditSousMenu(sousMenu): void {
    const dialog1 = this.dialog.open(EditSousMenuComponent, {
      width: '700px',
      disableClose: true,
      data:sousMenu
    }).afterClosed().subscribe(data => {
      this.getSousMenu();
    });
  }

  openDialogAddSousMenu(): void {
    const dialog1 = this.dialog.open(AjoutSousMenuComponent, {
      width: '400px',
      disableClose: true
    }).afterClosed().subscribe(data => {
      this.getSousMenu();
    });
  }

}
