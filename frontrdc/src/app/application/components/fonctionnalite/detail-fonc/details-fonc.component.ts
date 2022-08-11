import { Component, OnInit, Input, ViewChild, Inject } from '@angular/core';
import { MatSnackBarRef, SimpleSnackBar, MatDialogConfig, MatDialog, MatSnackBar, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Fonctionnalite } from 'src/app/application/models/fonctionnalite';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { AppFonc } from 'src/app/application/models/appFonc';
import { FonctionnaliteService } from 'src/app/application/services/fonctionnalite.service';
import { NotificationService } from 'src/app/shared/services/notification.service';


@Component({
  selector: 'app-details-fonc',
  templateUrl: './details-fonc.component.html',
  styleUrls: ['./details-fonc.component.scss']
})
export class DetailsFoncComponent implements OnInit {
  displayedColumns: string[] = ['fonNom', 'action'];
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	dataSource1: MatTableDataSource<AppFonc>;
	fonctionnalite: any;
	appFonc: any;
	dataSource2:[];
  id;
  fon;
  idApp;
  constructor(private router: Router,
		private route: ActivatedRoute,
		private dialog: MatDialog,
		private _snackBar: MatSnackBar,
		@Inject(MAT_DIALOG_DATA) public donne: any,
		private snackBar: MatSnackBar, private fonctionnaliteService: FonctionnaliteService,
		public dialogRef: MatDialogRef<DetailsFoncComponent>,
		private notification: NotificationService,
		private translate: TranslateService) {this.id = this.route.snapshot.paramMap.get('id'); }

  ngOnInit() {
	this. listFonctionnalite();
	this.idApp = this.donne.idApp;
	//alert("----"+this.donne.idApp)

  }

  listFonctionnalite() {
		this.fonctionnaliteService.listFonctionnaliteAppFonc(this.donne.idModule,this.donne.idApp).subscribe(data => {
			if (data.statut) {
				this.appFonc = data.data;
				this.dataSource1 = new MatTableDataSource<AppFonc>(data.data);
				this.dataSource1.paginator = this.paginator;
				this.dataSource1.sort = this.sort;		
			} else {
			}

		})
		
  }
  closeDialog() {
	this.dialogRef.close();
}
 
	}






