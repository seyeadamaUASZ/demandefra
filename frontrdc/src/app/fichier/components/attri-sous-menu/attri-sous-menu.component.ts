import { Component, OnInit, Inject } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatDialog, MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { Menu } from 'src/app/utilisateur/models/menu';
import { FichierService } from '../../services/fichier.service';
import { Rapport } from '../../models/rapport';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
  selector: 'app-attri-sous-menu',
  templateUrl: './attri-sous-menu.component.html',
  styleUrls: ['./attri-sous-menu.component.scss']
})
export class AttriSousMenuComponent implements OnInit {
  public rapport: any;
  panelOpenState = false;
  menu: Menu;
  listMenu = [];
  menuRapports = [];
  addedMenuRapports = [];
  removedMenuRapports = [];
  dataSource = new MatTableDataSource<Menu>();
  selectFormControl = new FormControl('', Validators.required);
  rapports: Rapport[];
  constructor(private formbuild: FormBuilder, private router: Router,
    private userService: UserService,
    private fichierService: FichierService,
    private notificationAlert: NotificationService,
    private ref: MatDialog,
    private readonly translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public donnee,
    public dialogRef: MatDialogRef<AttriSousMenuComponent>
  ) {
  }

  ngOnInit() {
    this.fichierService.ListeSousMenuRapport().subscribe(data => {
      this.menu = data;
      this.listMenu = data.data;
    });
    this.rapport = this.donnee;
    this.addedMenuRapports = [];
    this.removedMenuRapports = [];
    this.getMenuRapports();
  }

  selection = new SelectionModel<Menu>(true, []);
  selectionMenu = new SelectionModel<Menu>(true, []);
  displayedColumns: string[] = ['select', 'menNom'];

  getMenuRapports() {
      this.fichierService.listeMenuRapports(this.rapport).subscribe(data => {
        this.menuRapports = data.data;
      });
  }

  // /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected(menus) {
    const numSelected = this.selectionMenu.selected.length;
    const numRows = menus ? menus.length : 0;
    return numSelected === numRows;
  }

  // /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle(menus) {
    this.isAllSelected(menus) ?
      this.selectionMenu.clear() :
      menus.forEach(row => this.selectionMenu.select(row));
  }

  // /** The label for the checkbox on the passed row */
  checkboxLabel(menus: Menu[], row?: Menu): string {
    if (!row) {
      return `${this.isAllSelected(menus) ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionMenu.isSelected(row) ? 'deselect' : 'select'} row ${row.menNom + 1}`;
  }

  getDatasource(menus) {
    return new MatTableDataSource<Menu>(menus);
  }

  selectionHasValue(selection: SelectionModel<Menu>) {
    return selection.hasValue();
  }

  selectionIsSelected(row?: Menu) {
    return this.isMenuInMenuRapports(row);
  }


  isMenuInMenuRapports(row: Menu) {
    let filteredElements = this.menuRapports ? this.menuRapports.filter(element => element.menId == row.menId) : null;
    return (filteredElements && filteredElements.length) > 0 ? true : false;
  }

  addMenuInMenuRapports(row: Menu) {
    this.addedMenuRapports.push(row);
    this.removedMenuRapports = this.removedMenuRapports.filter(element => element.menId !== row.menId);
  }

  removeMenuFromMenuRapports(row: Menu) {
    this.removedMenuRapports.push(row);
    this.addedMenuRapports = this.addedMenuRapports.filter(element => element.menId !== row.menId);
  }

  selectMenu(event, row: Menu) {
    if (event.checked) {
      this.addMenuInMenuRapports(row);
    } else {
      this.removeMenuFromMenuRapports(row);
    }
    this.selectionMenu.toggle(row);
  }

  validateAllocation() {
    let removedMenuRapports = this.intersect(this.removedMenuRapports, this.menuRapports);
    let addedMenuRapports = this.difference(this.addedMenuRapports, this.menuRapports);
    this.fichierService.allocateMenuRapports(this.rapport, removedMenuRapports, addedMenuRapports).subscribe(data => {
      this.menuRapports = data.data;        
      if (data.statut) {
        this.translate.get('role.accessrights.success-update').subscribe((res: string) => {
            this.notificationAlert.success(res);         
          });
        this.closeDialog();
      }
    });
  }

  intersect(menuRapports1: any[], menuRapports2: any[]) {
    let result = [];
    menuRapports1.map(function (item1) {
      menuRapports2.map(function (item2) {
        if (item1.menId === item2.menId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  difference(menuRapports1: any[], menuRapports2: any[]) {
    let result = [];
    if (menuRapports1 && menuRapports2)
      result = menuRapports1.filter(item1 => !menuRapports2.some(item2 => (item2.menId === item1.menId)));
    return result;
  }

closeDialog() {
  this.dialogRef.close();
}

}
