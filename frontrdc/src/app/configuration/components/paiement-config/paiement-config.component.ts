import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatPaginator, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { OperateurPaiementService } from '../../services/operateur-paiement.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { merge, Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-paiement-config',
  templateUrl: './paiement-config.component.html',
  styleUrls: ['./paiement-config.component.scss']
})
export class PaiementConfigComponent implements OnInit {


  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  // @ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<any>;
  tabIndex: any;

  constructor(
    private dialog: MatDialog,
    private translate: TranslateService,
    private notification: NotificationService,
    private operateurpaiementService: OperateurPaiementService) { }
  loading = false;
  langues;
  operateurs;
  displayedColumns: string[] = ['operateurCode', 'operateurNom', 'operateurStatut', 'action'];
  form:FormGroup = new FormGroup({
    operateurCode: new FormControl(false),
    operateurNom: new FormControl(false),
    operateurStatut: new FormControl(false),
    action: new FormControl(false),

  });
  operateurCode = this.form.get('operateurCode');
  operateurNom = this.form.get('operateurNom');
  operateurStatut = this.form.get('operateurStatut');
  action = this.form.get('action');


  cbValues;
 
  columnDefinitions = [
    { def: 'operateurCode', label: 'code', hide: false},
    { def: 'operateurNom', label: 'Nom', hide: false},
    { def: 'operateurStatut', label: 'Statut', hide: false},
    { def: 'action', label: 'Action', hide: false}
  ]
  getDisplayedColumns():string[] {
    return this.columnDefinitions.filter(cd=>!cd.hide).map(cd=>cd.def);
  }
  ngAfterViewInit() {
    let o1:Observable<boolean> =this.operateurCode.valueChanges;
    let o2:Observable<boolean> = this.operateurNom.valueChanges;
    let o3:Observable<boolean> = this.operateurStatut.valueChanges;
    let o4:Observable<boolean> = this.action.valueChanges;
 
    merge(o1, o2,o3,o4).subscribe( v=>{
    this.columnDefinitions[0].hide = this.operateurCode.value;
    this.columnDefinitions[1].hide = this.operateurNom.value; 
    this.columnDefinitions[2].hide = this.operateurStatut.value; 
    this.columnDefinitions[3].hide = this.action.value;  
      // console.log(this.columnDefinitions);
     });
   }
  ngOnInit() {
    this.getOperateur();
  }
  getOperateur(){
    this.operateurpaiementService.getOperateurPaiement().subscribe(data => {
      // alert("-------------paiement--------- "+data)
      this.dataSource = new MatTableDataSource<any>(data.data);
      this.dataSource.paginator = this.paginator;
      this.operateurs = data.data;
    })
  }
  activerOperateur(id) {

    let alertSupp;
    this.translate.get('operateur.confirm-activation').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("operateur.alert-activation", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.operateurpaiementService.ActiverOperateur(id).subscribe(data => {
          if (data.statut) {
            this.loading = false;
            this.notification.info(alertSupp);
            this.getOperateur();
    
          }
        })
       
      }
    });
  }
  desactiverOperateur(id) {
    let alertSupp;
    this.translate.get('operateur.confirm-desactivation').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("operateur.alert-desactivation", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.operateurpaiementService.DesactiverOperateur(id).subscribe(data => {
          if (data.statut) {        
            this.loading = false;
            this.notification.info(alertSupp);
            this.getOperateur();
          }
        })
       
      }
    });

  }


  // listLangues() {
  //   this.langueService.getLangue().subscribe((data) => {
  //     if (data.statut) {
  //       this.dataSource = new MatTableDataSource<Langue[]>(data.data);
  //       this.dataSource.paginator = this.paginator;
  //       this.langues = data.data;
  //       // this.dataSource.sort = this.sort; 
  //     }
  //   })
  // }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }




  // supprimerLangue(langue) {
  //   let alertSupp;
  //   this.translate.get('formulaire.confirm-supp').subscribe((res: string) => {
  //     alertSupp = res;
  //   });
  //   const message = "Alert.confirm-action";
  //   // const dialogData = new ConfirmDialogModel("langue.alert-suppression", message);
  //   // const dialogRef = this.dialog.open(ConfirmDialogComponent, {
  //   //   disableClose: true,
  //   //   maxWidth: "400px",
  //   //   data: dialogData
  //   // });
  //   dialogRef.afterClosed().subscribe(dialogResult => {
  //     if (dialogResult === true) {
  //       this.loading = true;
  //       // this.operateurpaiementService.deleteLangue(langue).subscribe((data) => {
  //       //   this.loading = false;
  //       //   this.notification.info(alertSupp);
  //       //   this.listLangues()
  //       // });
  //     }
  //   });
  // }

}









