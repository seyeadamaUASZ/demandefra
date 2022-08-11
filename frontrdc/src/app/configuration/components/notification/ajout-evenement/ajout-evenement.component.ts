import { Component, OnInit, Inject } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatDialog, MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Action } from 'src/app/utilisateur/models/action';
import { NotificationService } from '../../../../shared/services/notification.service';
import { NotificationMessage } from 'src/app/parametrage/models/notification';
import { Menu } from 'src/app/parametrage/models/menu';
import { UserService } from 'src/app/parametrage/services/user.service';
import { NotificationServiceMessage } from 'src/app/parametrage/services/notification.service';

@Component({
  selector: 'app-ajout-evenement',
  templateUrl: './ajout-evenement.component.html',
  styleUrls: ['./ajout-evenement.component.scss']
})
export class AjoutEvenementComponent implements OnInit {
  public notification: NotificationMessage;
  panelOpenState = false;
  menu: Menu;
  listMenu = [];
  evenements = [];
  addedEvenements = [];
  removedEvenements = [];
  dataSource = new MatTableDataSource<Menu>();
  selectFormControl = new FormControl('', Validators.required);
  notifications: NotificationMessage[];
  constructor(private formbuild: FormBuilder, private router: Router,
    private userService: UserService,
    private notificationService: NotificationServiceMessage,
    private notificationAlert: NotificationService,
    private ref: MatDialog,
    private readonly translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public donnee,
    public dialogRef: MatDialogRef<AjoutEvenementComponent>
  ) {
  }

  ngOnInit() {
    this.userService.listItem().subscribe(data => {
      this.menu = data;
      this.listMenu = data.data;
    });
    this.notification = this.donnee;
    this.addedEvenements = [];
    this.removedEvenements = [];
    this.getEvenements();
  }

  selection = new SelectionModel<Menu>(true, []);
  selectionAction = new SelectionModel<Action>(true, []);
  displayedColumns: string[] = ['select', 'actCode', 'actNom', 'actDescription'];

  getEvenements() {
      this.notificationService.listeEvenements(this.notification).subscribe(data => {
        this.evenements = data.data;
      });
  }

  // /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected(actions) {
    const numSelected = this.selectionAction.selected.length;
    const numRows = actions ? actions.length : 0;
    return numSelected === numRows;
  }

  // /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle(actions) {
    this.isAllSelected(actions) ?
      this.selectionAction.clear() :
      actions.forEach(row => this.selectionAction.select(row));
  }

  // /** The label for the checkbox on the passed row */
  checkboxLabel(actions: Action[], row?: Action): string {
    if (!row) {
      return `${this.isAllSelected(actions) ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionAction.isSelected(row) ? 'deselect' : 'select'} row ${row.actCode + 1}`;
  }

  getDatasource(actions) {
    return new MatTableDataSource<Action>(actions);
  }

  selectionHasValue(selection: SelectionModel<Action>) {
    return selection.hasValue();
  }

  selectionIsSelected(row?: Action) {
    return this.isActionInEvenements(row);
  }

  selectionToggle(selection: SelectionModel<Action>, row?: Action) {
    return selection.toggle(row);
  }

  isActionInEvenements(row: Action) {
    let filteredElements = this.evenements ? this.evenements.filter(element => element.actId == row.actId) : null;
    return (filteredElements && filteredElements.length) > 0 ? true : false;
  }

  addActionInEvenement(row: Action) {
    this.addedEvenements.push(row);
    this.removedEvenements = this.removedEvenements.filter(element => element.actId !== row.actId);
  }

  removeActionFromEvenement(row: Action) {
    this.removedEvenements.push(row);
    this.addedEvenements = this.addedEvenements.filter(element => element.actId !== row.actId);
  }

  selectAction(event, row: Action) {
    if (event.checked) {
      this.addActionInEvenement(row);
    } else {
      this.removeActionFromEvenement(row);
    }
    this.selectionAction.toggle(row);
  }

  validateAllocation() {
    let removedEvenements = this.intersect(this.removedEvenements, this.evenements);
    let addedEvenements = this.difference(this.addedEvenements, this.evenements);
    this.notificationService.allocateEvenements(this.notification, removedEvenements, addedEvenements).subscribe(data => {
      this.evenements = data.data;        
      if (data.statut) {
        this.translate.get('role.accessrights.success-update').subscribe((res: string) => {
            this.notificationAlert.success(res);         
          });
        this.closeDialog();
      }
    });
  }

  intersect(evenements1: any[], evenements2: any[]) {
    let result = [];
    evenements1.map(function (item1) {
      evenements2.map(function (item2) {
        if (item1.actId === item2.actId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  difference(evenements1: any[], evenements2: any[]) {
    let result = [];
    if (evenements1 && evenements2)
      result = evenements1.filter(item1 => !evenements2.some(item2 => (item2.actId === item1.actId)));
    return result;
  }

closeDialog() {
  this.dialogRef.close();
}

}
