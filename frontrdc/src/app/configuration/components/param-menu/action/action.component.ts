import { Component, OnInit , ViewChild} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatTableDataSource, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';
import { AddActionComponent } from '../add-action/add-action.component';
import { Action } from 'src/app/parametrage/models/Action';
import { Menu } from 'src/app/parametrage/models/menu';
import { MenuService } from 'src/app/parametrage/services/menu.service';
import { ActionService } from 'src/app/parametrage/services/action.service';


@Component({
  selector: 'app-action',
  templateUrl: './action.component.html',
  styleUrls: ['./action.component.scss']
})
export class ActionComponent implements OnInit {
 actions:Action[];
 menu:Menu;
 dataSource: MatTableDataSource<Action>;
 displayedColumns: string[] = ['actCode', 'actNom', 'actDescription'];
 @ViewChild('table', { static: true }) table;
 @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  constructor(private router: Router, private readonly translate: TranslateService, private actionService: ActionService, 
  private menuService: MenuService, private dialogRef: MatDialog) {}

  ngOnInit() {    
    if(sessionStorage.getItem("menu")!=null){
      this.menu = JSON.parse(sessionStorage.getItem("menu"));
    } else {
       this.menuService.currentMenu.subscribe(menu=>this.menu = menu);
       sessionStorage.setItem("menu", JSON.stringify(this.menu));
    }        
    this.dataSource = new MatTableDataSource<Action>(this.menu.actions);  	
		
  }

   ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  public ngOnDestroy() {
    sessionStorage.removeItem("menu");
  }

  applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

  openDialogAdd(): void {
		const dialog = this.dialogRef.open(AddActionComponent, {
			width: '700px',
      data: this.menu
		}).afterClosed().subscribe(result => {          
      this.dataSource.data.push(result);     
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.table.renderRows()       
		});	  		
	}

   back(){        
     this.router.navigate(['/parametrage/menu']);
  }

}
