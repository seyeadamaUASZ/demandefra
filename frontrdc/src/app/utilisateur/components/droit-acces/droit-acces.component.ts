import { Component, OnInit } from '@angular/core';
import { Menu } from '../../models/menu';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { AjoutMenuComponent } from '../ajout-menu/ajout-menu.component';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-droit-acces',
  templateUrl: './droit-acces.component.html',
  styleUrls: ['./droit-acces.component.scss']
})
export class DroitAccesComponent implements OnInit {
  menu: Menu;
  dataSource= new MatTableDataSource<Menu>();
  constructor(private formbuild: FormBuilder, private router: Router, private userService: UserService,
    private ref: MatDialog) { }
  ngOnInit() {
    this.userService.listItem().subscribe(data => {
      this.menu = data;
      //console.log(this.menu);
      this.dataSource = new MatTableDataSource<Menu>(data.data);
     // this.paginator._intl.itemsPerPageLabel = 'Nombre de ligne';
     // this.dataSource.paginator = this.paginator;
    });
  }
  selection = new SelectionModel<Menu>(true, []);
  displayedColumns: string[] = ['select', 'code', 'libelle', 'icon','path'];

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Menu): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.code + 1}`;
  }
  openDialogAdd() {
    const reff = this.ref.open(AjoutMenuComponent, {
      height: '500px',
      width: '600px',
    }).afterClosed().subscribe(result => {
      location.reload();
    });

  }

  applyFilter(event: Event) {
		
	}

}
