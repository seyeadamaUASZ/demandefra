import { AfterViewInit, Component, Inject, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { ViewDemandeAutFraComponent } from 'src/app/demandeAutFra/components/view-demandeAutFra/view-demandeAutFra.component';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { DemandeService } from '../../service/demande.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, AfterViewInit {

	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	form;
	result: any
	dataSource: MatTableDataSource<any>;
	profilLibelle = localStorage.getItem('profileLibelle');
	poOwner = localStorage.getItem('profil');
  	owner  = localStorage.getItem('id');

	donnee: any;
	task: any = []
	status: any

	readonly DataStateEnum = DataStateEnum;
	paiement$: Observable<AppDataState<any[]>> | null = null;

	columnDefinitions = ['numero', 'dateSoumission', 'prenomResponsable', 'nomResponsable', 'raisonSociale', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}
	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	  }
	constructor(private demandeS: DemandeService,
		private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private router: Router) {
	}


  ngOnInit() {
    this.listDemandeValidee()
  }

  ngAfterViewInit() {
	this.dataSource.paginator = this.paginator;
	this.dataSource.sort = this.sort;
	}


  listDemandeValidee() {
	this.paiement$ = this.demandeS.listDemandeValidees(this.owner)
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
		/*this.demandeS.listDemandeValidees(this.owner)
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
