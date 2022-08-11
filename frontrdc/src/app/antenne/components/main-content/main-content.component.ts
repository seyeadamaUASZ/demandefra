import { Component, OnInit, ViewChild, AfterViewInit, QueryList, ViewChildren, Inject } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { NotificationService } from '../../../shared/services/notification.service';
import { AntenneService } from '../../service/antenne.service';
import { AddantenneComponent } from '../addantenne/addantenne.component';
import { EditantenneComponent } from '../editantenne/editantenne.component';
@Component({
	selector: 'app-main-content',
	templateUrl: './main-content.component.html',
	styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit, AfterViewInit {	
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  antennes:any;
  code:any;

  readonly DataStateEnum = DataStateEnum;
	paiement$: Observable<AppDataState<any[]>> | null = null;

  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['code', 'nom', 'region', 'action'];
  selectedColumns = this.displayedColumns;
	getDisplayedColumns() {
		return this.selectedColumns;
	}

  constructor(private antenneService:AntenneService, private dialog: MatDialog,
    private translate: TranslateService,private notificationservice:NotificationService) { }

  ngOnInit() {
    this.allAntennes();
  }

  ngAfterViewInit() {
	this.dataSource.paginator = this.paginator;
	this.dataSource.sort = this.sort;
	}

  openAddAntenne(){
    const dialog1 = this.dialog.open(AddantenneComponent, {
      disableClose: true,
      width:'500px'
    }).afterClosed().subscribe(data => {
      this.allAntennes();
    });
  }

  
  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  allAntennes(){
    this.paiement$ = this.antenneService.getAllAntenne()
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
    /*this.antenneService.getAllAntenne()
    .subscribe((data:any)=>{
       this.antennes = data.data
       this.dataSource = new MatTableDataSource<any>(this.antennes);
       this.dataSource.paginator = this.paginator.toArray()[0];
			this.dataSource.sort = this.sort;
    },err=>{
      console.log(err)
    })*/
  }

  openDialogEditAntenne(data){
		const dialog1 = this.dialog.open(EditantenneComponent, {
			width: '700px',
			data: data,
			disableClose: true
		}).afterClosed().subscribe(result => {
      this.allAntennes();
		});
  }

  supprimer(element) {
		let alertSupp;
		this.translate.get('antenne.confirm-suppression').subscribe((res: string) => {
			alertSupp = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("antenne.module.alert-suppression", message);
		const dialogRef = this.dialog.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.antenneService.deleteAntenne(element).subscribe((data: any) => {
					this.antennes()
					this.notificationservice.info(data.description);
				});
			}
		});
	}
}
