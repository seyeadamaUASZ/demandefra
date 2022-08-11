import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar, MatTable } from '@angular/material';
import { User } from '../../models/user';
import { Profile } from '../../models/profile';
import { DetailUtilisComponent } from '../detail-utilis/detail-utilis.component';
import { AjoutUtilisComponent } from '../ajout-utilis/ajout-utilis.component';
import { EditUtilisComponent } from '../edit-utilis/edit-utilis.component';
import { UserService } from '../../services/user.service';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { Observable, merge } from 'rxjs';
import { ToolbarComponent } from 'src/app/sharedcomponent/toolbar/toolbar.component';
import { NotificationService } from 'src/app/shared/services/notification.service';


@Component({
	selector: 'app-main-content-util',
	templateUrl: './main-content-util.component.html',
	styleUrls: ['./main-content-util.component.scss']
})
export class MainContentUtilComponent implements AfterViewInit {
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatTable) table: MatTable<any>;

	user;
	dataSource: MatTableDataSource<User>;
	dataSource1: MatTableDataSource<User>;

	tabIndex: any;
	langue;
	chartOptions = {
		responsive: true
	};
	chartData1 = [
		{ data: [0, 6, 2, 3, 6, 2, 7, 3, 6, 2, 1, 4], label: 'Applications' },
		{ data: [0, 14, 10, 4, 5, 10, 3, 3, 6, 6, 2, 9], label: 'Workflows' },
		{ data: [0, 26, 12, 21, 30, 11, 25, 10, 34, 15, 24, 19], label: 'Formulaires' }
	];
	chartLabels1 = ['January', 'February', 'Mars', 'April', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
	chartData2 = [
		{ data: [3, 16, 2, 13, 6, 8, 7, 13, 16, 12, 11, 14], label: 'Utilisateurs' },
		{ data: [2, 14, 10, 4, 5, 10, 3, 3, 6, 6, 2, 9], label: 'Connectés' },
	];
	chartLabels2 = ['January', 'February', 'Mars', 'April', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];



	constructor(private usersService: UserService, private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formbuild: FormBuilder,
		private _snackBar: MatSnackBar,
		private translate: TranslateService,
		private notification:NotificationService,
		private router: Router) {
	}
	displayedColumns: string[] = ['utiPrenom', 'utiNom', 'utiUsername', 'proLibelle', 'utiEmail', 'action'];
	userForm = this.formbuild.group({

		utiPrenom: ['', Validators.required],
		utiNom: ['', Validators.required],
		username: ['', Validators.required],
		password: ['', Validators.required],
		utiTelephone: ['', Validators.required],
		email: ['', Validators.required],
		//utiDateCreation: ['', Validators.required],
		//utiAdresse: ['', Validators.required],
		statut: ['', Validators.required],
		uti_pro_id: ['', Validators.required]
	});

	form: FormGroup = new FormGroup({
		utiPrenom: new FormControl(false),
		utiNom: new FormControl(false),
		utiUsername: new FormControl(false),
		proLibelle: new FormControl(false),
		utiEmail: new FormControl(false),
		action: new FormControl(false),
	});

	utiPrenom = this.form.get('utiPrenom');
	utiNom = this.form.get('utiNom');
	utiUsername = this.form.get('utiUsername');
	proLibelle = this.form.get('proLibelle');
	utiEmail = this.form.get('utiEmail');
	action = this.form.get('action');


	columnDefinitions = [
		{ def: 'utiPrenom', label: 'Prenom', hide: false },
		{ def: 'utiNom', label: 'Nom', hide: false },
		{ def: 'utiUsername', label: 'Username', hide: false },
		{ def: 'proLibelle', label: 'Profil', hide: false },
		{ def: 'utiEmail', label: 'Mail', hide: false },
		{ def: 'action', label: 'Action', hide: false }
	]
	getDisplayedColumns(): string[] {
		return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
	}

	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
		//filterValue = filterValue.trim(); // Remove whitespace
		//filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
		this.dataSource.filter = filterValue;
	}
	search:any ={utiPrenom:'', utiNom:'',utiUsername:'',proLibelle :'',  utiEmail :''};
	Filter() {
		let datat = { object: this.search };
		if (this.search.utiNom || this.search.utiPrenom || this.search.utiUsername || this.search.proLibelle || this.search.utiEmail) {
			this.usersService.advancedSearch(datat).subscribe((data: any) => {
				if (data.statut) {
					//this.afficher = false;
				}
				this.dataSource = new MatTableDataSource(data.data);

			});
		} else {
			this.translate.get('utilisateur.champsvides').subscribe(data => {
				this.notification.warn(data);
				this.listUsers();
				//this.afficher = true;
			})
		}

	}
	advancedSearch(){
		let searchval = 444;
		if(this.search.utiPrenom=="" && this.search.utiNom=="" && this.search.utiUsername=="" && this.search.utiEmail==""){
		  searchval =null;
		}
		if(searchval!=null){
		  let filter = {filter:this.search};
		  this.usersService.advancedSearch(filter).subscribe((data:any)=>{
			if(data.statut){
			  this.user = data.data;
			  this.dataSource1 = new MatTableDataSource<User>(data.data);
			  this.dataSource1.paginator = this.paginator;
			  this.dataSource1.sort = this.sort;
	
			}
		  })
		}else{
		  this.listUsers();
	
		}
	  }
	ngAfterViewInit() {
		this.listUsers();
		let o1: Observable<boolean> = this.utiPrenom.valueChanges;
		let o2: Observable<boolean> = this.utiNom.valueChanges;
		let o3: Observable<boolean> = this.utiUsername.valueChanges;
		let o4: Observable<boolean> = this.proLibelle.valueChanges;
		let o5: Observable<boolean> = this.utiEmail.valueChanges;
		let o6: Observable<boolean> = this.action.valueChanges;

		merge(o1, o2, o3, o4, o5, o6).subscribe(v => {
			this.columnDefinitions[0].hide = this.utiPrenom.value;
			this.columnDefinitions[1].hide = this.utiNom.value;
			this.columnDefinitions[2].hide = this.utiUsername.value;
			this.columnDefinitions[3].hide = this.proLibelle.value;
			this.columnDefinitions[4].hide = this.utiEmail.value;
			this.columnDefinitions[5].hide = this.action.value;
			console.log(this.columnDefinitions);
		});
	}
	ngOnInit() {
		this.dataSource.filterPredicate = function (data, filter: string): boolean {
			return data.utiPrenom.toLowerCase().includes(filter) || data.utiNom.toLowerCase().includes(filter) || data.utiUsername.toString() === filter || data.utiEmail.toString() === filter || data.uti_pro_id.proLibelle.toString() === filter;;
		};
		this.route.queryParams.subscribe(params => {
			this.tabIndex = params.index;
		});

	}

	listUsers() {
		this.usersService.listeUser().subscribe(data => {
			if (data.statut) {
				this.user = data.data.reverse();
				//console.log('------------------------------');
				console.log(this.user);
				this.dataSource = new MatTableDataSource<User>(data.data);
				//console.log(JSON.stringify(data.data));
				//this.paginator._intl.itemsPerPageLabel = 'Nombre de ligne';
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
			}

		})
	}


	openDialogAdd(): void {
		const dialog = this.dialogRef.open(AjoutUtilisComponent, {
			disableClose: true,
			width: '700px',

		}).afterClosed().subscribe(result => {
			//location.reload();
			//this.table.renderRows();
			this.listUsers();
		});

	}
	openDialogDroit() {
		const dialog1 = this.dialogRef.open(DetailUtilisComponent, {
			disableClose: true,
			width: '800px',

		}).afterClosed().subscribe(result => {
			//location.reload();
			//this.table.renderRows();
			//this.listUsers();
		});

	}
	openDialogUpdate(username) {
		//console.log(username);
		const dialog1 = this.dialogRef.open(EditUtilisComponent, {
			disableClose: true,
			width: '700px',
			data: username
		}).afterClosed().subscribe(result => {
			//location.reload();
			this.listUsers();
		});

	}
	openDialogDetail(username) {
		const dialog1 = this.dialogRef.open(DetailUtilisComponent, {
			disableClose: true,
			width: '700px',
			data: username
		}).afterClosed().subscribe(result => {

			this.table.renderRows();
		});

	}
	openDialogActive(utiId) {
		let messageActive
		this.translate.get('utilisateur.confirm-active').subscribe((res: string) => {
			messageActive = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("utilisateur.alert-active", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.actived(utiId, messageActive);
			}
		});
	}

	openDialogDesactive(utiId) {
		let messageDesactive;
		this.translate.get('utilisateur.confirm-desactive').subscribe((res: string) => {
			messageDesactive = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("utilisateur.alert-desactive", message);
		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.desactived(utiId, messageDesactive);
			}
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
		this.translate.get('utilisateur.confirm-suppression').subscribe((res: string) => {
			messageSuccess = res;
		});
		this.translate.get('utilisateur.erreur-suppression').subscribe((res: string) => {
			messageError = res;
		});
		this.usersService.deleteUser(username).subscribe(data => {
			if (data.statut) {
				this._snackBar.open(messageSuccess, 'Verification', {
					duration: 2000,
				});
			} else {
				this._snackBar.open(messageError, 'Verification', {
					duration: 2000,
				});
			}
			this.listUsers();
		});
	}
	statut(username) {

	}

	actived(utiId: any, message: any) {
		this.usersService.activer(utiId).subscribe(res => {
			this._snackBar.open(message, 'Verification', {
				duration: 2000,
			});
			this.listUsers();
		})
	}
	desactived(utiId: any, message: any) {
		this.usersService.desactiver(utiId).subscribe(res => {
			this._snackBar.open(message, 'Verification', {
				duration: 2000,
			});
			this.listUsers();
		})
	}



}
