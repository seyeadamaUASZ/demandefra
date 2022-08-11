import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatPaginator, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { httpTranslateLoader } from 'src/app/sharedcomponent/sharedcomponent.module';
import { Langue } from '../../models/langue';
import { Traduction } from '../../models/traduction';
import { LangueService } from '../../services/langue.service';
import { TraductionService } from '../../services/traduction.service';
import { EditLangueComponent } from '../edit-langue/edit-langue.component';
import { EditTraductionComponent } from '../edit-traduction/edit-traduction.component';

@Component({
  selector: 'app-langue',
  templateUrl: './langue.component.html',
  styleUrls: ['./langue.component.scss']
})
export class LangueComponent implements OnInit {

  // @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();
  @ViewChild(MatTableDataSource) tableTraduction: MatTableDataSource<any>;

	// @ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<Langue[]>;
  dataSourceTraduction: MatTableDataSource<Traduction[]>;

	tabIndex:any;
  selectedLangue;
  constructor(private langueService: LangueService,
    private dialog: MatDialog,
    private translate:TranslateService,
    private notification: NotificationService,
    private traductionService: TraductionService,
    private http: HttpClient
   ) { }
  loading = false;
  langues;
	displayedColumns: string[] = [ 'lngCode', 'lngLibelleComplet', 'lngLangue', 'lngIcone','action'];

  displayedColumnsTraductions: string[] = [ 'reference', 'defaultLangue', 'selectedLangue','action'];

  ngOnInit() {
    this.listLangues();
  }

  listLangues(){
    this.langueService.getLangue().subscribe((data)=>{
      if(data.statut){
        this.dataSource = new MatTableDataSource<Langue[]>(data.data);
        this.dataSource.paginator = this.paginator.toArray()[0];
        this.langues = data.data;
        if(this.langues.length>1){
          this.selectedLangue = this.langues[1];
        }else{
          this.selectedLangue = this.langues[0];
        }
        // this.dataSource.sort = this.sort; 
        this.listTraduction(this.selectedLangue);

      }
    })
  }
  listTraduction(langue){
    this.traductionService.getTraduction(langue).subscribe((data)=>{
      if(data.statut){
        this.dataSourceTraduction = new MatTableDataSource<Traduction[]>(data.data);
        this.dataSourceTraduction.paginator = this.paginator.toArray()[1];
      }
    })
  }
  setListLangues(){
    this.langueService.getLangue().subscribe((data)=>{
      if(data.statut){
        this.langues = data.data;
      }
    })
  }
  applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  applyFilterTraduction(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSourceTraduction.filter = filterValue.trim().toLowerCase();
  }
  
  
  openDialogEditLangue(langue): void {
    const dialog = this.dialog.open(EditLangueComponent, {
      disableClose: true,
      width: "30%",
      data: langue

    }).afterClosed().subscribe(result => {
			this.listLangues();
		});
  }

  openDialogEditLangue2(): void {
    const dialog = this.dialog.open(EditLangueComponent, {
      disableClose: true,
      width: "30%"

    }).afterClosed().subscribe(result => {
			this.listLangues();
		});
  }


  openDialogEditTraduction(): void {
    const dialog = this.dialog.open(EditTraductionComponent, {
      disableClose: true,
      width: "70%",
      data: this.selectedLangue,
      

    }).afterClosed().subscribe(result => {
      if(result?.statut){
        this.listTraduction(this.selectedLangue);
        let currentLang = this.translate.currentLang;
        this.translate.reloadLang(currentLang);
        
        
      }else{
        if(result?.update){
          this.openDialogUpdateTraduction(result.traduction);
        }
      }
    });

  }

  openDialogUpdateTraduction(traduction): void {
    const dialog = this.dialog.open(EditTraductionComponent, {
      disableClose: true,
      width: "70%",
      data: {traduction:traduction,update:true}

    }).afterClosed().subscribe(result => {
      if(result?.statut){
        this.listTraduction(this.selectedLangue);
        httpTranslateLoader(this.http);
       
      }
    });

  }

  supprimerLangue(langue) {
    let alertSupp;
    this.translate.get('configuration.confirm-suppression').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.langueService.deleteLangue(langue).subscribe((data)=>{
          this.loading = false;
          this.notification.info(alertSupp);
          this.listLangues()
        });
      }
    });
  }

  supprimerTraduction(traduction) {
    
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.traductionService.deleteTraduction(traduction).subscribe((data)=>{
          if(data.statut){
            this.loading = false;
            this.listTraduction(this.selectedLangue);
            httpTranslateLoader(this.http);
          }
          this.translate.get(data.description).subscribe((res: string)=>{
            this.notification.info(res);
          });
        });
      }
    });
  }

}
