import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource,  MatSnackBar, SimpleSnackBar, MatSnackBarRef } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { FichierService } from '../../services/fichier.service';
import { Fichier } from '../../models/fichier';

@Component({
  selector: 'app-rapport-sous-menu',
  templateUrl: './rapport-sous-menu.component.html',
  styleUrls: ['./rapport-sous-menu.component.scss']
})
export class RapportSousMenuComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  fichier;
  menu: any;
  rapportNotGenere: any;
  rapportGeneres: any;
  loading:any;
  param: any;
  dataSource: MatTableDataSource<Fichier>;
  constructor( 
    private _snackBar: MatSnackBar,
    private fichierService:FichierService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }
  displayedColumns: string[] = ['fhrNom', 'action'];

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.param = params['id'];
      this.menu = this.getMenuByPath (this.param);
    });
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  getFichier(menu){
    this.fichierService.listeRapportParMenu(menu).subscribe(data =>{
    this.fichier=data.data;
    this.dataSource = new MatTableDataSource<any>(data.data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    })
  }

  getMenuByPath (param) {
    this.fichierService.getMenuByPath('fichier/rapport/'+param).subscribe(data => {
      this.getFichier(data.data);
    });
  }

  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  gotoExprotFile(rptId): void{
    this.router.navigate(['/fichier/export'], { 
      state: { 
        data: rptId,
        previousUrl: this.router.url
      } 
    });    
  }

}
