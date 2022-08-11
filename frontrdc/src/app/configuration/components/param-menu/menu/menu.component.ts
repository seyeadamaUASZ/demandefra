import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';


import { ViewMenuComponent } from '../view-menu/view-menu.component';
import { EditMenuComponent } from '../edit-menu/edit-menu.component';
import { AddMenuComponent } from '../add-menu/add-menu.component';
import { Menu } from 'src/app/parametrage/models/menu';
import { MenuService } from 'src/app/parametrage/services/menu.service';
import { Subject, BehaviorSubject } from 'rxjs';
import { Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/shared/services/notification.service';






/** Flat to-do item node with expandable and level information */

/********************************************************* */
/** Flat node with expandable and level information */
interface MenuFlatNode {
  expandable: boolean;
  hasActions: boolean;
  name: string;
  level: number;
  menu: Menu;
}

/********************************************************* */

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  dataChange = new BehaviorSubject<Menu[]>([]);
  get data(): Menu[] { return this.dataChange.value; }
  /********************************************************* */

  search: any = {
    menNom: ''
  }

  message: string;
  treeData: any[];
  private _transformermenu = (node: Menu, level: number) => {
    return {
      expandable: !!node.sousMenus && node.sousMenus.length > 0,
      hasActions: !!node.actions && node.actions.length > 0,
      name: node.menNom,
      menu: node,
      level: level,
    };
  }

  treeControl = new FlatTreeControl<MenuFlatNode>(
    node => node.level, node => node.expandable);


  treeFlattenerMenu = new MatTreeFlattener(
    this._transformermenu, node => node.level, node => node.expandable, node => node.sousMenus);

  dataSourceMenu = new MatTreeFlatDataSource(this.treeControl, this.treeFlattenerMenu);

  hasChild = (_: number, node: MenuFlatNode) => node.expandable;
  hasActions = (_: number, node: MenuFlatNode) => node.hasActions;
  /********************************************************* */

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  menuDataSource: MatTableDataSource<Menu>;
  displayedColumnsRoles: string[] = ['menNom', 'menPath', 'menIcone', 'action'];

  constructor(private readonly translate: TranslateService, private formbuild: FormBuilder, private menuService: MenuService,
    private dialogRef: MatDialog, private router: Router, private route: ActivatedRoute, private notification: NotificationService) {

  }
  menuForm = this.formbuild.group({
    menNom: ['', Validators.required],

  });

  filterChanged(filterText: string) {
    this.filter(filterText);
    if (filterText) {
      this.treeControl.expandAll();
    } else {
      this.treeControl.collapseAll();

    }
  }



  ngOnInit() {
  }

  ngAfterViewInit() {
    this.listMenus();
  }

  listMenus() {
    this.menuService.listeMenuHierarchique().subscribe(data => {
      if (data.statut) {
        this.dataSourceMenu.data = data.data;
        this.treeData = data.data;
        const donnee = this.buildFileTree(data.data, '0');
        this.dataChange.next(donnee);

      } else {
        window.alert("------------Description----------" + data.description);
      }
    })
  }

  openDialogAdd(menu): void {
    const dialog = this.dialogRef.open(AddMenuComponent, {
      width: '700px',
      data: menu
    }).afterClosed().subscribe(result => {
      this.listMenus();
    });
  }

  openDialogEdit(menu): void {
    const dialog = this.dialogRef.open(EditMenuComponent, {
      width: '700px',
      data: menu
    }).afterClosed().subscribe(result => {
      this.listMenus();
    });
  }

  openDialogView(menu): void {
    const dialog1 = this.dialogRef.open(ViewMenuComponent, {
      width: '700px',
      data: menu
    }).afterClosed().subscribe(result => {
      //location.reload();
      this.listMenus();
    });
  }

  confirmationSuppression(profile): void {
    const message = "Alert.confirm-action";
    /* const dialogData = new ConfirmDialogModel("role.alert-delete", message);
     const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
       maxWidth: "400px",
       data: dialogData
     });
     dialogRef.afterClosed().subscribe(dialogResult => {
   if(dialogResult === true){
      this.deleteRole(profile);
    
       //this.result = dialogResult;
     });   
      }*/
  }

  addChildMenu(node: MenuFlatNode) {
    /* let menAdd = new Menu();
     menAdd.menIdParent = node.menu.menIdParent;*/
    this.openDialogAdd(node);
  }

  addAction(node: MenuFlatNode) {
    this.menuService.changemenu(node.menu);
    this.router.navigate(['/parametrage/action']);
  }

  buildFileTree(obj: any[], level: string): Menu[] {
    return obj.filter(o =>
      (<string>o.menNom).startsWith(level + '.')
      && (o.menNom.match(/\./g) || []).length === (level.match(/\./g) || []).length + 1
    ).map(o => {
      const node = new Menu();
      node.menNom = o.menNom;
      node.menIcone = o.menIcone;
      node.menIconeColor = o.menIconeColor;
      node.menIdParent = o.menIdParent;
      node.menPath = o.menPath;
      node.menId = o.menId;
      node.sousMenus = o.sousMenus
      const children = obj.filter(so => (<string>so.sousMenus).startsWith(level + '.'));
      if (children && children.length > 0) {
        node.sousMenus = this.buildFileTree(children, o.menNom);
      }
      return node;
    });
  }
  public filter(filterText: string) {
    let filteredTreeData;
    if (filterText) {
      filteredTreeData = this.treeData.filter(d => d.menNom.toLocaleLowerCase().indexOf(filterText.toLocaleLowerCase()) > -1);
      Object.assign([], filteredTreeData).forEach(ftd => {
        let str = (<string>ftd.menNom);
        if (filteredTreeData.find(t => t.menNom === str)) {
          const obj = this.treeData.find(d => d.menNom === str);
          if (obj) {
            filteredTreeData.push(obj);
          }
        }
      });
    } else {

      filteredTreeData = this.treeData;
    }
 
    const data = this.buildFileTree(filteredTreeData, '0');

    this.dataChange.next(data);
  }



  onSubmit() {

    if (this.menuForm.valid) {
      this.menuService.saveMenu(this.menuForm.value).subscribe(data => {
        if (data.statut) {
          //let successEdit;
          this.translate.get('menu.success-edit').subscribe((res: string) => {
            this.notification.success(res);
          });

        } else {
          //let errorEdit;
          this.translate.get('menu.error-edit').subscribe((res: string) => {
            this.notification.error(res);
          });
        }
      });
    } else {
      let invalidForm;
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.error(res);
      });
    }
  }

  searchMenu() {
    let searchval = 1994;
    if (this.search.menNom == "") {
      searchval = null;
    }
    if (searchval != null) {
      this.menuService.search(this.search.menNom).subscribe(data => {
        if (data.statut) {
          this.dataSourceMenu.data = data.data;
          this.treeData = data.data;
          const donnee = this.buildFileTree(data.data, '0');
          this.dataChange.next(donnee);

        }
      }

      );
    } else {
      this.listMenus();
    }
  }


}

