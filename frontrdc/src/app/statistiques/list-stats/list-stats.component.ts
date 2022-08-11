import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { CategorieService } from 'src/app/gestioncategorie/service/categorie.service';
import { RegionService } from 'src/app/region/service/region.service';
import { AppDataState, DataStateEnum } from 'src/app/State';
import { FilterpopupComponent } from '../filterpopup/filterpopup.component';
import { StatisitquesService } from '../services/statisitques.service';

@Component({
  selector: 'app-list-stats',
  templateUrl: './list-stats.component.html',
  styleUrls: ['./list-stats.component.scss']
})
export class ListStatsComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	form;
	result: any
	dataSource: MatTableDataSource<any>;

  readonly DataStateEnum = DataStateEnum;
	paiement$: Observable<AppDataState<any[]>> | null = null;

  columnDefinitions = ['numeroFra', 'categorie', 'raisonSociale', 'region', 'antenneRegionaleDepartementale', 'statutJuridique','statusProduit'];
	selectedColumns = this.columnDefinitions;
  produits:any[];
	getDisplayedColumns() {
		return this.selectedColumns;
	}

  search = {
    categorie: undefined,
    raisonsociale: undefined,
    region:undefined,
    antenneRegionaleDepartementale:undefined,
    statutJuridique:undefined,
    status:undefined
  }

  filter:any;

  setFilter (){
    return {
      filter: {
        categorie: (this.search.categorie==undefined || this.search.categorie.length==0)?undefined: this.search.categorie?.map((m)=>m.id).join(),//produit
        raisonsociale: this.search.raisonsociale,//demande
        region:(this.search.region==undefined || this.search.region.length==0)?undefined: this.search.region?.map((m)=>m.id).join(),//demande
        antenneRegionaleDepartementale:(this.search.antenneRegionaleDepartementale==undefined || this.search.antenneRegionaleDepartementale.length==0)?undefined: this.search.antenneRegionaleDepartementale?.map((m)=>m.id).join(),//demande
        statutJuridique:(this.search.statutJuridique==undefined || this.search.statutJuridique.length==0)?undefined: this.search.statutJuridique?.map((m)=>m).join(),//demande
        status:(this.search.status==undefined || this.search.status.length==0)?undefined: this.search.status?.map((m)=>m.id).join()//produit
      }
    }
  }
  categories:any;
  raisonsociales:any;
  regions:any;
  statuts:any = [
    {
      id:6,
      nom:"Autorisé"
    },
    {
      id:10,
      nom:"Refusé"
    }
  ];

  constructor(private categorieService: CategorieService,private regionService:RegionService
    ,private dialog: MatDialog,
    private statisquesService: StatisitquesService) { }

  getCategorie(){
    this.categorieService.allCategories().subscribe((resp:any)=>{
      this.categories = resp.data;
    });
  }
  getRegion(){
    this.regionService.getAllRegion().subscribe((resp:any)=>{
      this.regions= resp.data;
    })
  }
  ngOnInit() {
    this.getCategorie();
    this.getRegion();
    this.listeDemandes();
  }

  filterPlus(){
    const dialogRef = this.dialog.open(FilterpopupComponent,{
      data:this.search
    });
    dialogRef.afterClosed().subscribe((result)=>{
      if(result.status){
        this.search = result.search;
        this.statisquesService.getSearch(this.setFilter()).subscribe((resp:any)=>{
          this.dataSource = new MatTableDataSource(resp.data);
          this.dataSource.paginator = this.paginator;
          this.produits = resp.data;
        });
      }
    })
  }

  listeDemandes() {
    this.paiement$ = this.statisquesService.getSearch(this.setFilter())
			.pipe(
			  map((data: any) => {
				this.dataSource= new MatTableDataSource<any>(data.data);  
				this.dataSource.paginator = this.paginator;  
        this.produits = data.data;
				this.dataSource.sort = this.sort;  
				return ({ dataState: this.DataStateEnum.LOADED, data: this.dataSource })
	  
			  }),  
			  startWith({ dataState: this.DataStateEnum.LOADING }),  
			  catchError(err => of({ dataState: this.DataStateEnum.ERROR, errorMessage: err.message }))  
			)
    /*this.statisquesService.getSearch(this.setFilter()).subscribe((resp:any)=>{
      this.dataSource = new MatTableDataSource(resp.data);
      this.dataSource.paginator = this.paginator;
      this.produits = resp.data;
    });*/
  }

  comparewith(a:any,b:any){
    return a.id == b.id;
  }

  exportExcel(){
    this.statisquesService.exportAsExcelFile(this.produits,"PRODUITS")
  }
}
