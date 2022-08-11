import { Component, OnInit, ViewChild, ElementRef, ViewChildren, QueryList } from '@angular/core';
import { MatDialog, MatSnackBar, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { EditQrcodeComponent } from 'src/app/qrcode/components/edit-qrcode/edit-qrcode.component';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Document } from '../model/document';
import { DocumentService } from '../services/document.service';
import * as fileSaver from 'file-saver';
import { SignatureComponent } from './signature/signature.component';
import { PrivilegeSignerService } from '../services/privilegeSigner.service';
import { AjoutDocumentComponent } from './ajout-document/ajout-document.component';
import { EditDocumentComponent } from './edit-document/edit-document.component';
import { FormGroup, FormControl } from '@angular/forms';
import { Observable, merge } from 'rxjs';
// import { AjoutBlockchainComponent } from './ajout-blockchain/ajout-blockchain.component';
// import { VerifyBlockchainComponent } from './verify-blockchain/verify-blockchain.component';
// import { StockageblockchainService } from '../services/stockageblockchain.service';


@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {
  @ViewChild('pdfViewer') pdfViewer: ElementRef;
  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;
  documents;
  documentSigne;

  dataSource: MatTableDataSource<Document>;
  dataSourceBl: MatTableDataSource<any>;
  dataSourceSigner: MatTableDataSource<Document>;
  dataSourceCertifier: MatTableDataSource<Document>;
  dataSourceConsulter: MatTableDataSource<Document>;
  dctId;
  fileURL;
  blob;
  type;
  dataSourceApprouver: MatTableDataSource<Document>;

  constructor(private router: Router, private documentService: DocumentService, private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private route: ActivatedRoute,
    private privilegeDocumentService: PrivilegeSignerService,
    private snackBar: MatSnackBar, private usersService: UserService, private translate: TranslateService,
    // private stockageBlockchaineService: StockageblockchainService
    ) {
  }


  displayedColumns: string[] = ['dctTitre', 'dctAuteur', 'dctType', 'dctDate', 'action'];

  form: FormGroup = new FormGroup({
    dctTitre: new FormControl(false),
    dctAuteur: new FormControl(false),
    dctType: new FormControl(false),
    dctDate: new FormControl(false),
    action: new FormControl(false),
  });
  form2: FormGroup = new FormGroup({
    stblNom: new FormControl(false),
    stblHash: new FormControl(false),
    stblDescription: new FormControl(false),
    action: new FormControl(false),
  });

  dctTitre = this.form.get('dctTitre');
  dctAuteur = this.form.get('dctAuteur');
  dctType = this.form.get('dctType');
  dctDate = this.form.get('dctDate');
  action = this.form.get('action');

  stblNom = this.form2.get('stblNom');
  stblHash = this.form2.get('stblHash');
  stblDescription = this.form2.get('stblDescription');
  stblAction = this.form2.get('action');


  columnDefinitions = [
    { def: 'dctTitre', label: 'Titre', hide: false },
    { def: 'dctAuteur', label: 'Auteur', hide: false },
    { def: 'dctType', label: 'Type', hide: false },
    { def: 'dctDate', label: 'Date', hide: false },
    { def: 'action', label: 'Action', hide: false }
  ]

  columnDefinitions2 = [
    { def: 'stblNom', label: 'blockchain.fileName', hide: false },
    { def: 'stblHash', label: 'blockchain.fileHash', hide: false },
    { def: 'stblDescription', label: 'blockchain.fileDescription', hide: true },
    { def: 'action', label: 'register.action', hide: true }
  ]
  getDisplayedColumns(): string[] {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  getDisplayedColumns2(): string[] {
    return this.columnDefinitions2.filter(cd => !cd.hide).map(cd => cd.def);
  }

  ngAfterViewInit() {
    let o1: Observable<boolean> = this.dctTitre.valueChanges;
    let o2: Observable<boolean> = this.dctAuteur.valueChanges;
    let o3: Observable<boolean> = this.dctType.valueChanges;
    let o4: Observable<boolean> = this.dctDate.valueChanges;
    let o5: Observable<boolean> = this.action.valueChanges;

    merge(o1, o2, o3, o4, o5).subscribe(v => {
      this.columnDefinitions[0].hide = this.dctTitre.value;
      this.columnDefinitions[1].hide = this.dctAuteur.value;
      this.columnDefinitions[2].hide = this.dctType.value;
      this.columnDefinitions[3].hide = this.dctDate.value;
      this.columnDefinitions[4].hide = this.action.value;
    });

    let o11: Observable<boolean> = this.stblNom.valueChanges;
    let o22: Observable<boolean> = this.stblHash.valueChanges;
    let o33: Observable<boolean> = this.stblDescription.valueChanges;
    let o44: Observable<boolean> = this.stblAction.valueChanges;

    merge(o11, o22, o33, o44).subscribe(v => {
      this.columnDefinitions2[0].hide = this.stblNom.value;
      this.columnDefinitions2[1].hide = this.stblHash.value;
      this.columnDefinitions2[2].hide = this.stblDescription.value;
      this.columnDefinitions2[3].hide = this.stblAction.value;
    });
  }
  search:any ={dctTitre:'', dctAuteur:'', dctDate:'', typeDocuments :''};

  advancedSearch(){
    let searchval = 444;
    if(this.search.dctTitre=="" && this.search.dctAuteur=="" && this.search.dctDate=="" && this.search.typeDocuments==""){
      searchval =null;
    }
    if(searchval!=null){
      let filter = {filter:this.search};
      this.documentService.advancedSearch(filter).subscribe((data:any)=>{
        if(data.statut){
          this.documents = data.data;
          this.dataSource = new MatTableDataSource<Document>(data.data);
          this.dataSource.paginator = this.paginator.toArray()[0];
          this.dataSource.sort = this.sort;

        }
      })
    }else{
      this.getListDocument();

    }
  }

  ngOnInit() {
    this.getListDocument();
    this.getListSigner();
    this.getListCertifier();
    // this.getListDocBlockchain();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getListSigner() {
    this.privilegeDocumentService.listDocumentSignerByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.documentSigne = data.data;
        console.log(JSON.stringify(this.documentSigne))
        this.dataSourceSigner = new MatTableDataSource<Document>(data.data);
        this.dataSourceSigner.paginator = this.paginator.toArray()[0];
        this.dataSourceSigner.sort = this.sort;

      }
    })

  }
  getListCertifier() {
    this.privilegeDocumentService.listDocumentCertifierByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceCertifier = new MatTableDataSource<Document>(data.data);
        this.dataSourceCertifier.paginator = this.paginator.toArray[0]
        this.dataSourceCertifier.sort = this.sort;

      }
    })

  }
  getListConsulter() {
    this.privilegeDocumentService.listDocumentConsulterByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceConsulter = new MatTableDataSource<Document>(data.data);
        this.dataSourceConsulter.paginator = this.paginator.toArray()[0];
        this.dataSourceConsulter.sort = this.sort;

      }
    })

  }

  
  getListDocument() {
    this.documentService.getDocumentByUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.documents = data.data;
        this.dataSource = new MatTableDataSource<Document>(data.data);
        this.dataSource.paginator = this.paginator.toArray()[0];
        this.dataSource.sort = this.sort;

      }
    })

  }

  getListDocBlockchain(){
    // this.stockageBlockchaineService.getListDoc(localStorage.getItem('username')).subscribe(data=>{
    //   this.dataSourceBl = new MatTableDataSource<Document>(data.data);
    //   this.dataSourceBl.paginator = this.paginator.toArray()[0];
    // })
  }
  openDialogDeleteDocument(dctId) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel('document.alert-suppression', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.delete(dctId);
      }
    });

  }

  delete(dctId) {
    let messageSuccess;
    let messageError;
    this.translate.get('document.confirm-suppression').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('document.erreur-suppression').subscribe((res: string) => {
      messageError = res;
    });
    this.documentService.deleteDocument(dctId).subscribe(data => {
      if (data.statut) {
        this.snackBar.open(messageSuccess, 'Verification', {
          duration: 2000,
        });
      } else {
        this.snackBar.open(messageError, 'Verification', {
          duration: 2000,
        });
      }
      this.getListDocument();
    });
  }



  openDialogAdd(): void{
    this.dialog.open(AjoutDocumentComponent, {
      disableClose: true,
      // width: '700px',
    }).afterClosed().subscribe(data => {
      this.getListDocument();

    });

  }

  openDialogUpdate(document): void {
    this.dialog.open(EditDocumentComponent, {
      disableClose: true,
      width: '700px',
      data: document
    }).afterClosed().subscribe(data => {
      this.getListDocument();

    });

  }

  openDialogSignerDocument(document): void {
    this.dialog.open(SignatureComponent, {
      disableClose: true,
      width: '700px',
      data: document
    }).afterClosed().subscribe(data => {
      this.getListDocument();
      this.getListSigner();
      this.getListCertifier();

    });

  }

  openDialogConsultDocument(document) {
    this.documentService.consulter(document.dctId).subscribe(resp => {
      this.saveFile(resp.body, document.dctTitre);

    });
    this.type = document.dctType
  }
  saveFile(data: any, filename?: string) {
    const blob = new Blob([data], { type: this.type });
    fileSaver.saveAs(blob, filename);
  }

  openDialogAddBlockChain() {
    // this.dialog.open(AjoutBlockchainComponent, {
    //   disableClose: true,
    // }).afterClosed().subscribe(data => {


    // });
  }

  openDialogVerifyBlockChain() {
    // this.dialog.open(VerifyBlockchainComponent, {
    //   disableClose: true,
    // }).afterClosed().subscribe(data => {


    // });
  }
}
