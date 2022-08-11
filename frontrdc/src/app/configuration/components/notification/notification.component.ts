import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar } from '@angular/material';
// import { DetailUtilisComponent } from '../detail-utilis/detail-utilis.component';
// import { AjoutNotificationComponent } from '../ajout-notification/ajout-notification.component';
// import { EditUtilisComponent } from '../edit-notification/edit-notification.component';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { AjoutEvenementComponent } from './ajout-evenement/ajout-evenement.component';
import { AjoutNotificationComponent } from './ajout-notification/ajout-notification.component';
import { EditNotificationComponent } from './edit-notification/edit-notification.component';
import { NotificationMessage } from 'src/app/parametrage/models/notification';
import { NotificationServiceMessage } from 'src/app/parametrage/services/notification.service';
import { TypeDeNotification } from '../../models/typeDeNotification';
import { AjoutTypeNotifComponent } from './ajout-type-notif/ajout-type-notif.component';
import { EditTypeNotifComponent } from './edit-type-notif/edit-type-notif.component';
import { ViewNotificationComponent } from './view-notification/view-notification.component';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
	selector: 'app-notification',
	templateUrl: './notification.component.html',
	styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements AfterViewInit {
	recherche: any = {
		tndType: ''
	}

	filter: any = {
		notification: ''
	}

	notifi: NotificationMessage[] = [];

	not: TypeDeNotification[] = [];

	displayedColumns: string[] = ['ntfObjet', 'ntfTntId', 'action'];
	displayedColumns1: string[] = ['tdnType', 'tdnContenu', 'action'];
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatPaginator, { static: false }) paginator1: MatPaginator;
	@ViewChild(MatSort) sort1: MatSort;
	@ViewChild(MatSort) sort: MatSort;
	notificationProfils;
	dataSource: MatTableDataSource<NotificationMessage>;
	dataSource1: MatTableDataSource<TypeDeNotification>;
	langue;
	message;
	filtreNotification
	constructor(
		private notificationService: NotificationServiceMessage, private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formbuild: FormBuilder,
		private _snackBar: MatSnackBar,
		private translate: TranslateService,
		private notification: NotificationService,
		private router: Router) {
	}
	applyFilter1(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	applyFilter2(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource1.filter = filterValue.trim().toLowerCase();
	}


	ngAfterViewInit() {
		this.listNotification();
		this.listTypeDeNotification();
	}
	ngOnInit() {
		//	this.loardChart();
	}

	listNotification() {
		this.notificationService.listeNotificationProfils().subscribe(data => {
			if (data) {
				this.notificationProfils = data.data;
				console.log(this.notificationProfils);
				this.dataSource = new MatTableDataSource<NotificationMessage>(this.notificationProfils);
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
			} else {
				this.translate.get("error").subscribe((res: string) => {
					this.notificationProfils.warn(res);
				});
			}
		});
	}

	sortingCaseInsentive() {
		return (data, sortHeaderId) => data[sortHeaderId].toLocaleLowerCase();
	}


	openDialogAdd(): void {
		const dialog = this.dialogRef.open(AjoutNotificationComponent, {
			width: '700px',
			disableClose: true
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listNotification();
		});
	}

	openDialogUpdate(notification) {
		const dialog1 = this.dialogRef.open(EditNotificationComponent, {
			disableClose: true,
			width: '700px',
			data: notification
		}).afterClosed().subscribe(result => {
			this.listNotification();
		});
	}



	openDialogEvenement(notification) {
		const dialog1 = this.dialogRef.open(AjoutEvenementComponent, {
			disableClose: true,
			data: notification
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listNotification();
		});
	}


	listTypeDeNotification() {
		this.notificationService.listeTypeDeNotification().subscribe(data => {
			if (data) {
				this.message = data.data;
				console.log(JSON.stringify(this.message))
				this.dataSource1 = new MatTableDataSource<TypeDeNotification>(this.message);
				this.dataSource1.paginator = this.paginator1;
				this.dataSource1.sort = this.sort1;
			} else {
				this.translate.get("error").subscribe((res: string) => {
					this.notificationProfils.warn(res);
				});
			}
		});
	}


	openDialogAddMessage(): void {
		const dialog = this.dialogRef.open(AjoutTypeNotifComponent, {
			width: '500px',
			disableClose: true
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listTypeDeNotification();
		});
	}

	openDialogDeleteUser(username) {
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("Alert.suppression", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.delete(username);
			}
		});
	}
	delete(username) {
		let messageSuccess;
		let messageError;
		this.translate.get('notification.confirm-suppression').subscribe((res: string) => {
			messageSuccess = res;
		});
		this.translate.get('notification.erreur-suppression').subscribe((res: string) => {
			messageError = res;
		});
		this.notificationService.deleteTypeDeNotif(username).subscribe(data => {
			if (data.statut) {
				this._snackBar.open(messageSuccess, 'Verification', {
					duration: 2000,
				});
			} else {
				this._snackBar.open(messageError, 'Verification', {
					duration: 2000,
				});
			}
			this.listTypeDeNotification();
		});
	}
	statut(username) {

	}

	oppenDialogUdateNotif(typeNotification) {
		const dialog = this.dialogRef.open(EditTypeNotifComponent, {
			width: '500px',
			disableClose: true,
			data: typeNotification
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listTypeDeNotification();
		});


	}


	openDialogDeleteNotification(username) {
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("Alert.suppression", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.deleteNotification(username);
			}
		});
	}
	deleteNotification(username) {
		let messageSuccess;
		let messageError;
		this.translate.get('notification.confirm-suppression').subscribe((res: string) => {
			messageSuccess = res;
		});
		this.translate.get('notification.erreur-suppression').subscribe((res: string) => {
			messageError = res;
		});
		this.notificationService.deleteNotification(username).subscribe(data => {
			if (data.statut) {
				this._snackBar.open(messageSuccess, 'Verification', {
					duration: 2000,
				});
			} else {
				this._snackBar.open(messageError, 'Verification', {
					duration: 2000,
				});
			}
			this.listNotification();
		});
	}
	// statut(username) {

	// }


	openDialogDetails(notification) {
		const dialog1 = this.dialogRef.open(ViewNotificationComponent, {
			disableClose: true,
			width: '700px',
			data: notification
		}).afterClosed().subscribe(result => {
			this.listNotification();
		});
	}
	search() {
		let searchval = 1994;
		if (this.recherche.tndType == "") {
			searchval = null;
		}
		if (searchval != null) {
			this.notificationService.search(this.recherche.tndType).subscribe(data => {
				if (data.statut) {
					this.filtreNotification = data.data
					this.dataSource1 = new MatTableDataSource<TypeDeNotification>(this.filtreNotification);
					this.dataSource1.paginator = this.paginator1;
					this.dataSource1.sort = this.sort1;

				}
			}

			);
		} else {
			this.listTypeDeNotification();
		}
	}


	searchNotification() {
		let searchval = 1994;
		if (this.filter.notification == "") {
			searchval = null;
		}
		if (searchval != null) {
			this.notificationService.searchNotification(this.filter.notification).subscribe(data => {
				if (data.statut) {
					this.notificationProfils = data.data;
					console.log(this.notificationProfils);
					this.dataSource = new MatTableDataSource<NotificationMessage>(this.notificationProfils);
					this.dataSource.paginator = this.paginator;
					this.dataSource.sort = this.sort;

				}
			}

			);
		} else {
			this.listNotification();
		}
	}


}
