import { Component, OnInit } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Menu } from '../../../models/menu';
import { Profil } from '../../../models/profil';
import { Action } from '../../../models/action';
import { UserService } from '../../../services/user.service';
import { RoleService } from '../../../services/role.service';
import { PrivilegeService } from '../../../services/privilege.service';
import { AjoutMenuComponent } from '../../ajout-menu/ajout-menu.component';
import { NotificationService } from '../../../../shared/services/notification.service';

@Component({
  selector: 'app-allocaterole',
  templateUrl: './allocaterole.component.html',
  styleUrls: ['./allocaterole.component.scss']
})
export class AllocateroleComponent implements OnInit {
  public profil: Profil;
  public profilSelected: Profil;
  panelOpenState = false;
  menu: Menu;
  listMenu = [];
  privileges = [];
  addedPrivileges = [];
  removedPrivileges = [];
  dataSource = new MatTableDataSource<Menu>();
  profilControl = new FormControl('', Validators.required);
  selectFormControl = new FormControl('', Validators.required);
  profils: Profil[];
  constructor(private formbuild: FormBuilder, private router: Router, private roleService: RoleService, private userService: UserService,
    private privilegeService: PrivilegeService, private ref: MatDialog,
    private readonly translate: TranslateService,
    private notification: NotificationService
  ) {
    if (this.router.getCurrentNavigation() && this.router.getCurrentNavigation().extras) {
      this.profil = this.router.getCurrentNavigation().extras.state["profil"];
    }
  }

  ngOnInit() {
    this.userService.listItem().subscribe(data => {
      this.menu = data;
      this.listMenu = data.data;
    });

    let p = new Profil();
    p.proId = this.profil.proId;
    this.privileges = [];
    this.addedPrivileges = [];
    this.removedPrivileges = [];
    this.roleService.listprofils().subscribe(data => {
      this.profils = data.data;
    });
    this.privilegeService.listPriveleges(p).subscribe(data => {
      this.privileges = data.data;
    });
  }

  ngAfterViewInit() {
    this.profilControl.setValue(this.profil);
  }

  profilSelection() {
    this.profil = this.profilControl.value;
    if (this.profil) {
      this.privilegeService.listPriveleges(this.profil).subscribe(data => {
        this.privileges = data.data;
      });
    } else {
      this.privileges = [];
    }
  }

  compareProfil(p1: Profil, p2: Profil): boolean {
    return p1 && p2 ? p1.proId === p2.proId : false;
  }

  selection = new SelectionModel<Menu>(true, []);
  selectionAction = new SelectionModel<Action>(true, []);
  displayedColumns: string[] = ['select', 'actCode', 'actNom', 'actDescription'];

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected(actions) {
    const numSelected = this.selectionAction.selected.length;
    const numRows = actions ? actions.length : 0;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle(actions) {
    this.isAllSelected(actions) ?
      this.selectionAction.clear() :
      actions.forEach(row => this.selectionAction.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(actions: Action[], row?: Action): string {
    if (!row) {
      return `${this.isAllSelected(actions) ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionAction.isSelected(row) ? 'deselect' : 'select'} row ${row.actCode + 1}`;
  }


  openDialogAdd() {
    const reff = this.ref.open(AjoutMenuComponent, {
      height: '500px',
      width: '600px',
    }).afterClosed().subscribe(result => {
      location.reload();
    });
  }

  getDatasource(actions) {
    return new MatTableDataSource<Action>(actions);
  }

  selectionHasValue(selection: SelectionModel<Action>) {
    return selection.hasValue();
  }

  selectionIsSelected(row?: Action) {
    return this.isActionInprivileges(row);
  }

  selectionToggle(selection: SelectionModel<Action>, row?: Action) {
    return selection.toggle(row);
  }

  isActionInprivileges(row: Action) {
    let filteredElements = this.privileges ? this.privileges.filter(element => element.actId == row.actId) : null;
    return (filteredElements && filteredElements.length) > 0 ? true : false;
  }

  addActionInPrivilege(row: Action) {
    this.addedPrivileges.push(row);
    this.removedPrivileges = this.removedPrivileges.filter(element => element.actId !== row.actId);
  }

  removeActionFromPrivilege(row: Action) {
    this.removedPrivileges.push(row);
    this.addedPrivileges = this.addedPrivileges.filter(element => element.actId !== row.actId);
  }

  selectAction(event, row: Action) {
    if (event.checked) {
      this.addActionInPrivilege(row);
    } else {
      this.removeActionFromPrivilege(row);
    }
    this.selectionAction.toggle(row);
  }

  validateAllocation() {
    let removedPrivileges = this.intersect(this.removedPrivileges, this.privileges);
    let addedPrivileges = this.difference(this.addedPrivileges, this.privileges);
    //alert("Removed : "+ JSON.stringify( removedPrivileges));
    //alert("Added :"+ JSON.stringify(addedPrivileges));       
    let p = new Profil();
    p.proId = this.profil.proId;
    this.privilegeService.allocatePriveleges(p, removedPrivileges, addedPrivileges).subscribe(data => {
      //this.privileges = data.data;        
      if (data.statut) {
        /*this._snackBar.open("Priviliges mis à jour avec succès !", 'Confirmation', {
          duration: 2000,
    });*/
        
        this.translate.get('role.accessrights.success-update').subscribe((res: string) => {
          this.notification.success(res);         
          });
        //alert("Priviliges mis à jour avec succès !");
        this.router.navigate(['/utilisateur'], {queryParams: {index: 1}});
      }

    });
  }
  

  intersect(privileges1: any[], privileges2: any[]) {
    let result = [];
    privileges1.map(function (item1) {
      privileges2.map(function (item2) {
        if (item1.actId === item2.actId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  difference(privileges1: any[], privileges2: any[]) {
    let result = [];
    if (privileges1 && privileges2)
      result = privileges1.filter(item1 => !privileges2.some(item2 => (item2.actId === item1.actId)));
    return result;
  }

  return() {
    this.router.navigate(['/utilisateur'], {queryParams: {index: 1}});
  }

}
