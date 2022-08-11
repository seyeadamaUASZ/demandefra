import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { merge, Observable } from 'rxjs';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { PaiementService } from '../../service/paiement.service';

import { DetailPaiementComponent } from '../detail-paiement/detail-paiement.component';
import { InfosPayeurComponent } from '../infos-payeur/infos-payeur.component';
@Component({
  selector: 'app-main-content-paiement',
  templateUrl: './main-content-paiement.component.html',
  styleUrls: ['./main-content-paiement.component.scss']
})
export class MainContentPaiementComponent implements OnInit {
  displayedColumns: string[] = ['reference_paiement', 'date_paiement', 'reference_client', 'montant', 'status', 'action'];


  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<any>;
  dataSource1: MatTableDataSource<any>;

  Paiement;
  notifications;
  operateurs;
  not;
  filledArray;
  obj;
  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private translate: TranslateService,
    private paiementService: PaiementService,
    private operateurpaiementService: PaiementService,@Inject(MAT_DIALOG_DATA) public crypt: any,
  ) { }
  form: FormGroup = new FormGroup({
    reference_paiement: new FormControl(false),
    date_paiement: new FormControl(false),
    reference_client: new FormControl(false),
    montant: new FormControl(false),
    status: new FormControl(false),
    action: new FormControl(false),

  });
  reference_paiement = this.form.get('reference_paiement');
  date_paiement = this.form.get('date_paiement');
  reference_client = this.form.get('reference_client');
  montant = this.form.get('montant');
  status = this.form.get('status');
  action = this.form.get('action');


  cbValues;

  columnDefinitions = [
    { def: 'reference_paiement', label: 'Id', hide: false },
    { def: 'date_paiement', label: 'Date', hide: false },
    { def: 'reference_client', label: 'Ref client', hide: false },
    { def: 'montant', label: 'Montant', hide: false },
    { def: 'status', label: 'Statut', hide: false },
    { def: 'action', label: 'Action', hide: false }
  ]
  getDisplayedColumns(): string[] {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }
  ngAfterViewInit() {
    let o1: Observable<boolean> = this.reference_paiement.valueChanges;
    let o2: Observable<boolean> = this.date_paiement.valueChanges;
    let o3: Observable<boolean> = this.reference_client.valueChanges;
    let o4: Observable<boolean> = this.montant.valueChanges;
    let o5: Observable<boolean> = this.status.valueChanges;
    let o6: Observable<boolean> = this.action.valueChanges;

    merge(o1, o2, o3, o4, o4, o5,o6).subscribe(v => {
      this.columnDefinitions[0].hide = this.reference_paiement.value;
      this.columnDefinitions[1].hide = this.date_paiement.value;
      this.columnDefinitions[2].hide = this.reference_client.value;
      this.columnDefinitions[3].hide = this.montant.value;
      this.columnDefinitions[4].hide = this.status.value;
      this.columnDefinitions[5].hide = this.action.value;
    });
  }

  ngOnInit() { 
    this.listPaiement();
    this.listOperateur();
  }
  //Affiche la liste opérateurs de paiement
  listOperateur() {
    this.operateurpaiementService.getOperateurPaiement().subscribe(data => {
      this.operateurs = data.data;
    })
  }
   //popup de création des transactions 

  openAjoutnfos() {
    const dialog1 = this.dialog.open(InfosPayeurComponent, {
      disableClose: true,
      width: '500px'
    }).afterClosed().subscribe(result => {
    });

  }
  //fonction de recherche rapide
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  applyFilter1(event: Event) {
    const filterValue1 = (event.target as HTMLInputElement).value;
    this.dataSource1.filter = filterValue1.trim().toLowerCase();
  }
  
  //Affiche la liste des paiements
  listPaiement() {
    this.paiementService.getListPaiement().subscribe(data => {
      this.Paiement = data.data;
      this.dataSource = new MatTableDataSource<any>(data.data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  //Affiche le détail des paiements
  openDialogView(reference) {
    console.log(reference);
    const dialog1 = this.dialog.open(DetailPaiementComponent, {
      disableClose: true,
      data: reference,
      width: '600px'
    }).afterClosed().subscribe(result => {
    });
  }
}
