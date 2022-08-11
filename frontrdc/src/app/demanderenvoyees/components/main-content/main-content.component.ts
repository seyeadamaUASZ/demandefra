import { Component, OnInit, ViewChild, AfterViewInit, QueryList, ViewChildren, Inject } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import * as fileSaver from 'file-saver';
import { merge, Observable, of } from 'rxjs';
import { DemanderenvoyeesService } from '../../service/demanderenvoyees.service';
import { ViewDemandeAutFraComponent } from 'src/app/demandeAutFra/components/view-demandeAutFra/view-demandeAutFra.component';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { catchError, map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-main-content',
	templateUrl: './main-content.component.html',
	styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit, AfterViewInit {
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	form;
	result: any
	dataSource: MatTableDataSource<any>;
	profilLibelle = localStorage.getItem('profileLibelle');
	poOwner = localStorage.getItem('profil');
	owner = localStorage.getItem('id');

	readonly DataStateEnum = DataStateEnum;
	renvoie$: Observable<AppDataState<any[]>> | null = null;

	donnee: any;
	task: any = []
	status: any

	columnDefinitions = ['numero', 'dateSoumission', 'prenomResponsable', 'nomResponsable', 'raisonSociale', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}
	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	  }
	constructor(private demanderenvoyeesService: DemanderenvoyeesService,
		private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private router: Router) {
	}

	ngOnInit() {		
		if(this.profilLibelle == "chef bureau"){
			this.listDemandeRenvoyees();
		} else {
			this.listDemandeRejetees();
		}	
	}

	ngAfterViewInit() {
		this.dataSource.paginator = this.paginator;
		this.dataSource.sort = this.sort;
	}
	
	listDemandeRejetees() {
		this.renvoie$ = this.demanderenvoyeesService.getDemandeRejetees()
		.pipe(
		  map((data: any) => {
			this.dataSource= new MatTableDataSource<any>(data.data);  
			this.dataSource.paginator = this.paginator;  
			this.dataSource.sort = this.sort;  
			return ({ dataState: this.DataStateEnum.LOADED, data: this.dataSource })
  
		  }),  
		  startWith({ dataState: this.DataStateEnum.LOADING }),  
		  catchError(err => of({ dataState: this.DataStateEnum.ERROR, errorMessage: err.message }))  
		)
		/*this.demanderenvoyeesService.getDemandeRejetees()
			.subscribe((data: any) => {
				if (data.statut) {
					this.dataSource = new MatTableDataSource<any>(data.data);
					this.dataSource.paginator = this.paginator.toArray()[0];
					this.dataSource.sort = this.sort;
				}
			})*/
	}

	listDemandeRenvoyees() {
		this.renvoie$ = this.demanderenvoyeesService.getDemandeRenvoyees()
		.pipe(
		  map((data: any) => {
			this.dataSource= new MatTableDataSource<any>(data.data);  
			this.dataSource.paginator = this.paginator;  
			this.dataSource.sort = this.sort;  
			return ({ dataState: this.DataStateEnum.LOADED, data: this.dataSource })
  
		  }),  
		  startWith({ dataState: this.DataStateEnum.LOADING }),  
		  catchError(err => of({ dataState: this.DataStateEnum.ERROR, errorMessage: err.message }))  
		)
		/*this.demanderenvoyeesService.getDemandeRenvoyees()
			.subscribe((data: any) => {
				if (data.statut) {
					this.dataSource = new MatTableDataSource<any>(data.data);
					this.dataSource.paginator = this.paginator.toArray()[0];
					this.dataSource.sort = this.sort;
				}
			})*/
	}

	openDialogVisualiser(data): void {
		const dialog = this.dialogRef.open(ViewDemandeAutFraComponent, {
			width: '1500px',
			data: { data: data, openDialog: true },
			disableClose: true
		}).afterClosed().subscribe(result => {
		});
	}
}
