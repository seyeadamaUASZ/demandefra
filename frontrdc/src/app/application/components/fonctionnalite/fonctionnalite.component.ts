import { Component, OnInit, Input, ViewChild, Inject } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import {Fonctionnalite} from 'src/app/application/models/fonctionnalite';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { AjoutFoncComponent } from './ajout-fonc/ajout-fonc.component';
import{EditFoncComponent} from './edit-fonc/edit-fonc.component';
import { FonctionnaliteService } from '../../services/fonctionnalite.service';

@Component({
  selector: 'app-fonctionnalite',
  templateUrl: './fonctionnalite.component.html',
  styleUrls: ['./fonctionnalite.component.scss']
})
export class FonctionnaliteComponent implements OnInit {
  displayedColumns: string[] = [ 'fonNom', 'fonDescription', 'action'];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<Fonctionnalite>;
  fonctionnalite:any;
  id;

  constructor( private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar, 
    private translate: TranslateService,
    private snackBar: MatSnackBar, private fonctionnaliteService:FonctionnaliteService) {
      this.id = this.route.snapshot.paramMap.get('id');
     }

  ngOnInit() {

    this.listFonctionnalite();
  }

  listFonctionnalite() {
    this.fonctionnaliteService.listFonctionnalite(this.id).subscribe(data => {
      if (data.statut) {
        this.fonctionnalite = data.data;
        console.log('------------------------------');
        console.log(this.fonctionnalite);
        this.dataSource = new MatTableDataSource<Fonctionnalite>(data.data);
        //console.log(JSON.stringify(data.data));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        console.log(data.description);
      }

    })
  }
  openDialogAddFonctionnalite(): void {
    //this.dialog.open(AjoutFoncComponent, { disableClose: true });

	this.dialog.open(AjoutFoncComponent, {
    disableClose: true,
		width: '700px',
		data:this.id
	}).afterClosed().subscribe(data => {
	  this.listFonctionnalite();
	
	});
  }
  fermer(){
    this.router.navigate(['/application'], {queryParams: {index: 1}});
  }
  openDialogDeleteFonctionnalite(fonctionnalite) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel('fonctionnalite.alert-suppression', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.delete(fonctionnalite);
      }
    });
  }
  delete(fonctionnalite) {
    let messageSuccess;
    let messageError;
    this.translate.get('fonctionnalite.confirm-suppression').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('fonctionnalite.erreur-suppression').subscribe((res: string) => {
      messageError = res;
    });
    this.fonctionnaliteService.deleteFonctionnalite(fonctionnalite).subscribe(data => {
      if (data.statut) {
        this.snackBar.open(messageSuccess, 'Verification', {
          duration: 2000,
        });
      } else {
        this.snackBar.open(messageError, 'Verification', {
          duration: 2000,
        });
      }
	  this.listFonctionnalite();
    });
  }
  

  openDialogEdit(data): void {
		const dialog = this.dialog.open(EditFoncComponent, {
      disableClose:true,
			width: '700px',
      data:data
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listFonctionnalite();
		});

	}

}
