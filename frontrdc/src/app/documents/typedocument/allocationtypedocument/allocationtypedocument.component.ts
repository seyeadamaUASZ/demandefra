import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { RoleService } from 'src/app/utilisateur/services/role.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { TypeDocuments } from '../../model/typeDocuments';
import { PrivilegeDocumentService } from '../../services/privilegeDocument.service';
import { DocumentService } from '../../services/document.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Profil } from 'src/app/home/models/profil';

@Component({
  selector: 'app-allocationtypedocument',
  templateUrl: './allocationtypedocument.component.html',
  styleUrls: ['./allocationtypedocument.component.scss']
})
export class AllocationtypedocumentComponent implements OnInit {

  typeDocument: TypeDocuments
  typeDocuments = []
  privileges = [];
  profils = []
  addedPrivileges = []
  removedPrivileges = []
  public typeDocumentSelected: TypeDocuments;
  typeDocumentControl = new FormControl('', Validators.required);
  selectFormControl = new FormControl('', Validators.required);

  selectionAction = new SelectionModel<Profil>(true, []);
  displayedColumns: string[] = ['select', 'proLibelle', 'proDescription'];

  constructor(private formbuild: FormBuilder, private router: Router, private roleService: RoleService, private userService: UserService, private ref: MatDialog,
    private readonly translate: TranslateService,
    private notification: NotificationService,
    private privilegeServiceDocument: PrivilegeDocumentService,
    private documentService: DocumentService
  ) {
    if (this.router.getCurrentNavigation() && this.router.getCurrentNavigation().extras) {
      this.typeDocument = this.router.getCurrentNavigation().extras.state["typedocument"];
    }


  }

  ngOnInit() {
    this.roleService.listprofils().subscribe(data => {
      this.profils = data.data;

      let p = new TypeDocuments();
      p.tydId = this.typeDocument.tydId;
      this.privileges = [];
      this.addedPrivileges = [];
      this.removedPrivileges = [];
      this.documentService.getTypeDocuments().subscribe(data => {
        this.typeDocuments = data.data;
      });
      this.privilegeServiceDocument.listPriveleges(this.typeDocument).subscribe(data => {
        this.privileges = data.data;
      });
    });
  }
  compareTypeDocument(p1: TypeDocuments, p2: TypeDocuments): boolean {
    return p1 && p2 ? p1.tydId === p2.tydId : false;
  }
  ngAfterViewInit() {
    this.typeDocumentControl.setValue(this.typeDocument);
  }

  profilSelection($event) {
    this.typeDocument = this.typeDocumentControl.value;
    if (this.typeDocument) {
      this.privilegeServiceDocument.listPriveleges(this.typeDocument).subscribe(data => {
        this.privileges = data.data;
      });
    } else {
      this.privileges = [];
    }
  }
  getDatasource(profil) {
    return new MatTableDataSource<Profil>(profil);
  }
  isAllSelected(profil) {
    const numSelected = this.selectionAction.selected.length;
    const numRows = profil ? profil.length : 0;
    return numSelected === numRows;
  }
  masterToggle(profil) {
    this.isAllSelected(profil) ?
      this.selectionAction.clear() :
      profil.forEach(row => this.selectionAction.select(row));
  }

  checkboxLabel(profils: Profil[], row?: Profil): string {
    if (!row) {
      return `${this.isAllSelected(profils) ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionAction.isSelected(row) ? 'deselect' : 'select'} row ${row.proLibelle + 1}`;
  }

  selectionIsSelected(row?: Profil) {
    return this.isActionInprivileges(row);
  }

  isActionInprivileges(row: Profil) {
    let filteredElements = this.privileges ? this.privileges.filter(element => element.proId == row.proId) : null;
    return (filteredElements && filteredElements.length) > 0 ? true : false;
  }

  selectAction(event, row: Profil) {
    if (event.checked) {
      this.addActionInPrivilege(row);
    } else {
      this.removeActionFromPrivilege(row);
    }
    this.selectionAction.toggle(row);
  }

  addActionInPrivilege(row: Profil) {
    this.addedPrivileges.push(row);
    this.removedPrivileges = this.removedPrivileges.filter(element => element.proId !== row.proId);
  }

  removeActionFromPrivilege(row: Profil) {
    this.removedPrivileges.push(row);
    this.addedPrivileges = this.addedPrivileges.filter(element => element.proId !== row.proId);
  }

  validateAllocation() {
    let removedPrivileges = this.intersect(this.removedPrivileges, this.privileges);
    let p = new TypeDocuments();
    p.tydId = this.typeDocument.tydId;
    this.privilegeServiceDocument.allocatePriveleges(p, removedPrivileges, this.addedPrivileges).subscribe(data => {
      if (data.statut) {
        this.translate.get('role.accessrights.success-update').subscribe((res: string) => {
          this.notification.success(res);
        });
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
  
  differenceee(privileges1: any[], privileges2: any[]) {
    let result = [];
    if (privileges1 && privileges2)
      result = privileges1.filter(item1 => !privileges2.some(item2 => (item2.actId === item1.actId)));
    return result;
  }

  return() {
    this.router.navigate(['/document/typedocument'], { queryParams: { index: 1 } });
  }
}
