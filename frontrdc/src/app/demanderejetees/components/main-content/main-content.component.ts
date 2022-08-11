import { AfterViewInit, ChangeDetectorRef, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { AddDemandeAutFraComponent } from 'src/app/demandeAutFra/components/add-demandeAutFra/add-demandeAutFra.component';
import { DemanderejeteesService } from '../../service/demanderejetees.service';
import { ViewDemandeAutFraComponent } from 'src/app/demandeAutFra/components/view-demandeAutFra/view-demandeAutFra.component';
import { MotifrejetourenvoiService } from 'src/app/demandeAutFra/service/motifrejetourenvoi.service';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-main-content',
	templateUrl: './main-content.component.html',
	styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit, AfterViewInit {
	form;
	result: any;
	dataSource: MatTableDataSource<any>;
	profilLibelle = localStorage.getItem('profileLibelle');
	owner = localStorage.getItem('id');

	readonly DataStateEnum = DataStateEnum;
	rejet$: Observable<AppDataState<any[]>> | null = null;

	donnee: any;
	task: any = []
	status: any
	payLoad = '';
	isPagination: boolean = false

	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;

	columnDefinitions = ['numero', 'dateSoumission', 'prenomResponsable', 'nomResponsable', 'raisonSociale', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}
	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	  }
	constructor(private demanderejeteesService: DemanderejeteesService,
		private dialogRef: MatDialog,
		private motifService: MotifrejetourenvoiService,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private fichierService: FichierService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private ref: ChangeDetectorRef,
		private router: Router) { }

	ngOnInit() {
		this.listeDemandesRejetees();
	}

	ngAfterViewInit() {
		this.dataSource.paginator = this.paginator;
		this.dataSource.sort = this.sort;
	}

	listeDemandesRejetees() {
		this.rejet$ = this.demanderejeteesService.getDemandeRejetees(this.owner)
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
		/*this.demanderejeteesService.getDemandeRejetees(this.owner).subscribe(
			(data: any) => {
				this.dataSource = new MatTableDataSource<any>(data.data);
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
				this.isPagination = true
			},
			err => this.isPagination = false
		);*/
	}

	openDialogVisualiser(data): void {
		const dialog1 = this.dialogRef.open(ViewDemandeAutFraComponent, {
			width: '1500px',
			height: '800px',
			data: { data: data, openDialog: true }
		  }).afterClosed().subscribe(result => {
		  });
	}
}
