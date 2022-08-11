import { AfterViewInit, Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { CategorieService } from '../../service/categorie.service';
import { AddcategorieComponent } from '../addcategorie/addcategorie.component';
import { EditcategorieComponent } from '../editcategorie/editcategorie.component';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['nom', 'prix','action'];
  categories:any;
  loading: boolean;
  code:any;

  readonly DataStateEnum = DataStateEnum;
	paiement$: Observable<AppDataState<any[]>> | null = null;

  dataSource: MatTableDataSource<any>;
  constructor(private categorieS:CategorieService, private dialog: MatDialog,
    private translate: TranslateService,private notificationservice:NotificationService) { }

  ngOnInit() {
    this.allCategories();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    }
  

  openAddCategorie(){
    const dialog1 = this.dialog.open(AddcategorieComponent, {
      disableClose: true,
      width:'500px'
    }).afterClosed().subscribe(data => {
      this.allCategories();

    });
  }

  selectedColumns = this.displayedColumns;
	getDisplayedColumns() {
		return this.selectedColumns;
	}
  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  allCategories(){
    this.paiement$ = this.categorieS.allCategories()
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
    /*this.categorieS.allCategories()
    .subscribe((data:any)=>{
       this.categories = data.data
       this.dataSource = new MatTableDataSource<any>(this.categories);
       this.dataSource.paginator = this.paginator.toArray()[0];
			this.dataSource.sort = this.sort;
    },err=>{
      console.log(err)
    })*/
  }

  openDialogEditCategorie(data){
		const dialog1 = this.dialog.open(EditcategorieComponent, {
			width: '700px',
			data: data,
			disableClose: true
		}).afterClosed().subscribe(result => {
      this.allCategories();
		});
  }

  supprimer(element) {
		let alertSupp;
		this.translate.get('application.module.confirm-suppression').subscribe((res: string) => {
			alertSupp = res;
		});
		const message = "Alert.confirm-action";
		const dialogData = new ConfirmDialogModel("application.module.alert-suppression", message);
		const dialogRef = this.dialog.open(ConfirmDialogComponent, {
			disableClose: true,
			maxWidth: "400px",
			data: dialogData
		});
		dialogRef.afterClosed().subscribe(dialogResult => {
			if (dialogResult === true) {
				this.loading = true;
				this.categorieS.deleteCategorie(element).subscribe((data: any) => {
					this.allCategories()
					//this.loading = false;
					this.notificationservice.info(data.description);

				});
			}
		});
	}

}
