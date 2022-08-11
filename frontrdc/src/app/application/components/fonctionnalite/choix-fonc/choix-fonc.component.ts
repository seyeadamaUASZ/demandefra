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
	selector: 'app-choix-fonc',
	templateUrl: './choix-fonc.component.html',
	styleUrls: ['./choix-fonc.component.scss']
})
export class ChoixFoncComponent implements OnInit {
	displayedColumns: string[] = ['fonNom', 'action'];
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	dataSource: MatTableDataSource<Fonctionnalite>;
	dataSource1: MatTableDataSource<AppFonc>;
	fonctionnalite: any;
	appFonc: any;
	dataSource2:[];
	id;
	idApp = this.donnee.idApp;
	idAppliFonc = this.donnee.idAppliFonc;
	fon;
	isActive;
	constructor(private router: Router,
		private route: ActivatedRoute,
		private dialog: MatDialog,
		private _snackBar: MatSnackBar,
		@Inject(MAT_DIALOG_DATA) public donnee: any,
		private snackBar: MatSnackBar, private fonctionnaliteService: FonctionnaliteService,
		public dialogRef: MatDialogRef<ChoixFoncComponent>,
		private notification: NotificationService,
		private translate: TranslateService) {
		this.id = this.route.snapshot.paramMap.get('id');
	}

	ngOnInit() {

		this.listFonctionnalite();

	}


	listFonctionnalite() {
		this.fonctionnaliteService.listFonctionnaliteAppFonc(this.donnee.idModule, this.idApp).subscribe(data => {
			if (data.statut) {
				this.appFonc = data.data;
				this.dataSource1 = new MatTableDataSource<AppFonc>(data.data);
				this.dataSource1.paginator = this.paginator;
				this.dataSource1.sort = this.sort;
				//this.appliFonIsActive = data.data.appliFonIsActive;
				
			} else {
			}
		
		})
		
	}

	openDialogActive(fonId) {
		let messageActive;
		this.fon= fonId;
		
		this.translate.get('fonctionnalite.confirm-active').subscribe((res: string) => {
			messageActive = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("fonctionnalite.alert-active", message);
		const dialogRef = this.dialog.open(ConfirmDialogComponent, {
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.actived(this.fon, messageActive);
			}
		});
		
	}



	openDialogDesactive(fonId) {
		let messageDesactive;
		this.fon= fonId;
		
		this.translate.get('fonctionnalite.confirm-desactive').subscribe((res: string) => {
			messageDesactive = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("fonctionnalite.alert-desactive", message);
		const dialogRef = this.dialog.open(ConfirmDialogComponent, {
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.desactived(this.fon, messageDesactive);
			}
		});
		
	}



	actived(fonId:any, message: any) {
		this.fonctionnaliteService.activerAppFon(this.idApp,fonId).subscribe(res => {
			this.translate.get(message).subscribe((res: string) => {
				this.notification.success(res);
			});
			this.listFonctionnalite();
		})
	}
	desactived(fonId:any, message: any) {
		this.fonctionnaliteService.desactiverAppFon(this.idApp,fonId).subscribe(res => {
			this.translate.get(message).subscribe((res: string) => {
				this.notification.success(res);
			});
			this.listFonctionnalite();

		})
	}

	closeDialog() {
		this.dialogRef.close();
	}

}
