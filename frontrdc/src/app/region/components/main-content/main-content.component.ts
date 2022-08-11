import { AfterViewInit, Component, Inject, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import * as fileSaver from 'file-saver';
import { ViewDemandeAutFraComponent } from 'src/app/demandeAutFra/components/view-demandeAutFra/view-demandeAutFra.component';
import { RegionService } from '../../service/region.service';
import { AddregionComponent } from '../addregion/addregion.component';
import { EditregionComponent } from '../editregion/editregion.component';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { Observable, of } from 'rxjs';
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
  	regions:any;

	donnee: any;
	task: any = []
	status: any
	payLoad = '';

	readonly DataStateEnum = DataStateEnum;
	paiement$: Observable<AppDataState<any[]>> | null = null;

	columnDefinitions = ['code', 'nom', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}

	constructor(private regionService: RegionService,
		private dialog: MatDialog,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private fichierService: FichierService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private router: Router) { }

	ngOnInit() {
		this.listeRegions();
	}

	ngAfterViewInit() {
		this.dataSource.paginator = this.paginator;
		this.dataSource.sort = this.sort;
		}
	

  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

	listeRegions(){
		this.paiement$ = this.regionService.getAllRegion()
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
    /*this.regionService.getAllRegion()
    .subscribe((data:any)=>{
       this.regions = data.data
       this.dataSource = new MatTableDataSource<any>(this.regions);
       this.dataSource.paginator = this.paginator.toArray()[0];
			this.dataSource.sort = this.sort;
    },err=>{
      console.log(err)
    })*/
  }

	openAddRegion(): void{
    const dialog1 = this.dialog.open(AddregionComponent, {
      disableClose: true,
      width:'500px'
    }).afterClosed().subscribe(data => {
      this.listeRegions();
    });
  }

  openDialogEditRegion(data){
		const dialog1 = this.dialog.open(EditregionComponent, {
			width: '700px',
			data: data,
			disableClose: true
		}).afterClosed().subscribe(result => {
      this.listeRegions();
		});
  }

  supprimer(element) {
		let alertSupp;
		this.translate.get('region.confirm-suppression').subscribe((res: string) => {
			alertSupp = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("region.module.alert-suppression", message);
		const dialogRef = this.dialog.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.regionService.deleteRegion(element).subscribe((data: any) => {
					this.regions()
					this.notification.info(data.description);
				});
			}
		});
	}
}
