import { Component, OnInit, ViewChild, AfterViewInit, Inject } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar, MatTable, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
  
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { Observable, merge } from 'rxjs';
import { ToolbarComponent } from 'src/app/sharedcomponent/toolbar/toolbar.component';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { User } from 'src/app/utilisateur/models/user';

@Component({
  selector: 'app-visualiserbox',
  templateUrl: './visualiserbox.component.html',
  styleUrls: ['./visualiserbox.component.scss']
})
export class VisualiserboxComponent implements OnInit {
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild(MatTable) table: MatTable<any>;

	user;
  userconnect;
	dataSource: MatTableDataSource<User>;
  dataSourceConnet: MatTableDataSource<User>;
	tabIndex: any;
	langue; 

	constructor(private usersService: UserService,
		private route: ActivatedRoute,
		private formbuild: FormBuilder,
		private _snackBar: MatSnackBar,
		private translate: TranslateService,
		private router: Router,@Inject(MAT_DIALOG_DATA) public parambox: any,public dialogRef: MatDialogRef<VisualiserboxComponent>) {
	}
	displayedColumns: string[] = ['utiPrenom', 'utiNom', 'utiUsername', 'proLibelle', 'utiEmail' ];
  displayedColumnsConnect: string[] = ['utiPrenom', 'utiNom', 'utiUsername', 'proLibelle', 'utiEmail' ];
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
	});

	utiPrenom = this.form.get('utiPrenom');
	utiNom = this.form.get('utiNom');
	utiUsername = this.form.get('utiUsername');
	proLibelle = this.form.get('proLibelle');
	utiEmail = this.form.get('utiEmail'); 


	columnDefinitions = [
		{ def: 'utiPrenom', label: 'Prenom', hide: false },
		{ def: 'utiNom', label: 'Nom', hide: false },
		{ def: 'utiUsername', label: 'Username', hide: false },
		{ def: 'proLibelle', label: 'Profil', hide: false },
		{ def: 'utiEmail', label: 'Mail', hide: false } 
	]
	getDisplayedColumns(): string[] {
		return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
	}

	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	ngAfterViewInit() {
		this.listUsers();
    this.listUsersConnect() 
		let o1: Observable<boolean> = this.utiPrenom.valueChanges;
		let o2: Observable<boolean> = this.utiNom.valueChanges;
		let o3: Observable<boolean> = this.utiUsername.valueChanges;
		let o4: Observable<boolean> = this.proLibelle.valueChanges;
		let o5: Observable<boolean> = this.utiEmail.valueChanges; 

		merge(o1, o2, o3, o4, o5).subscribe(v => {
			this.columnDefinitions[0].hide = this.utiPrenom.value;
			this.columnDefinitions[1].hide = this.utiNom.value;
			this.columnDefinitions[2].hide = this.utiUsername.value;
			this.columnDefinitions[3].hide = this.proLibelle.value;
			this.columnDefinitions[4].hide = this.utiEmail.value; 
			console.log(this.columnDefinitions);
		});
	}
	ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.tabIndex = params.index;
		});

		}

	listUsers() {
		this.usersService.listeUser().subscribe(data => {
			if (data.statut) {
				this.user = data.data.reverse();
       // console.log('------------------------------'+JSON.stringify(	this.user));
				console.log(this.user);
				this.dataSource = new MatTableDataSource<User>(data.data);
				//console.log(JSON.stringify(data.data));
				//this.paginator._intl.itemsPerPageLabel = 'Nombre de ligne';
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
			} 

		})
	}

  listUsersConnect() {
		this.usersService.getListUsersConnect().subscribe(data => {
			if (data.statut) {
				this.userconnect = data.data.reverse();
				 console.log('------------------------------'+JSON.stringify(	this.userconnect));
				console.log(this.userconnect);
				this.dataSourceConnet = new MatTableDataSource<User>(data.data);
				this.dataSourceConnet.paginator = this.paginator;
				this.dataSourceConnet.sort = this.sort;
			} 

		})
	}
  
	
  closeDialog() {
    this.dialogRef.close();
  }


}
