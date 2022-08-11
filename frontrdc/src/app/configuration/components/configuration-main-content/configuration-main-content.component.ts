import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { Langue } from '../../models/langue';
import { LangueService } from '../../services/langue.service';
import { EditLangueComponent } from '../edit-langue/edit-langue.component';
import { SelectionModel } from '@angular/cdk/collections';
import { RoleService } from 'src/app/utilisateur/services/role.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { stringify } from 'querystring';
import { Profil } from 'src/app/home/models/profil';

@Component({
  selector: 'app-configuration-main-content',
  templateUrl: './configuration-main-content.component.html',
  styleUrls: ['./configuration-main-content.component.scss']
})
export class ConfigurationMainContentComponent implements OnInit {
  form = this.formbuild.group({
    proId: ['', Validators.required],
    proLibelle: ['', Validators.required],
    proDescription: ['', Validators.required],
    nouvelleInscri:['', Validators.required]  
  });
  tabIndex:any;
  selected;
  displayedColumns: string[] = ['select', 'proLibelle', 'proDescription'];
  profils:any;
  ProId:any;
  dataSource: MatTableDataSource<Profil>;
  selectionAction = new SelectionModel<Profil>(true, []);
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  constructor(private roleService:RoleService,
    private formbuild: FormBuilder, private router: Router,
    private translate:TranslateService,
    private notification:NotificationService ) { }
 

  ngOnInit() {
    this.roleService.listprofils().subscribe(data => {
      this.profils = data.data;
      this.dataSource = new MatTableDataSource<Profil>(data.data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
    
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
  nouvelUtilisateur(e,ProIdChecked) {
    console.log(ProIdChecked)
    if (e.checked) {
      this.ProId = ProIdChecked
      this.form.setValue({      
        proId: this.ProId.proId,
        proLibelle: this.ProId.proLibelle,
        proDescription: this.ProId.proDescription,
        nouvelleInscri:this.ProId.nouvelleInscri
      }); 
      this.form.value.nouvelleInscri=true;
    } 
    else if(!e.checked) {
      this.ProId = ProIdChecked;
      this.form.setValue({      
        proId: this.ProId.proId,
        proLibelle: this.ProId.proLibelle,
        proDescription: this.ProId.proDescription,
        nouvelleInscri:this.ProId.nouvelleInscri
      });
      this.form.value.nouvelleInscri=false;
    }

  }
  selectionIsSelected(row?: Profil) {
    return this.isActionInprivileges(row);
  }
  isActionInprivileges(row) {
    let filteredElements = this.profils ? this.profils.filter(element => element.proId == row.proId && row.nouvelleInscri==true ) : null;
    return (filteredElements && filteredElements.length) > 0 ? true : false;
  }
  validateProfils(){
    this.roleService.updateProfil(this.form.value).subscribe(data => {
      if (data.statut) {
        this.translate.get("profil attribué aux nouveaux inscris").subscribe((res: string) => {
          this.notification.success(res);         
          });
        this.form.reset();
      } else {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.warn(res);         
          });
      }
    })
  }
  

}
