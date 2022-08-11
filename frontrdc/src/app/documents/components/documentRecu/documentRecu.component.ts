import { Component, ElementRef, OnInit, ViewChild, ViewChildren, QueryList } from '@angular/core';
import { MatDialog, MatPaginator, MatSnackBar, MatSort, MatTableDataSource } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { DocumentService } from '../../services/document.service';
import { PrivilegeSignerService } from '../../services/privilegeSigner.service';
import * as fileSaver from 'file-saver';
import { SignatureDocumentService } from '../../services/signatureDocument.service';
import { DetailSignatureComponent } from '../detailSignature/detailSignature.component';
import { AjoutDocumentComponent } from '../ajout-document/ajout-document.component';
import { EditDocumentComponent } from '../edit-document/edit-document.component';
import { SignatureComponent } from '../signature/signature.component';
import { FormGroup, FormControl } from '@angular/forms';
import { Observable, merge } from 'rxjs';
import { ApprobationComponent } from '../approbation/approbation.component';

@Component({
  selector: 'app-documentRecu',
  templateUrl: './documentRecu.component.html',
  styleUrls: ['./documentRecu.component.scss']
})
export class DocumentRecuComponent implements OnInit {

  @ViewChild('pdfViewer') pdfViewer: ElementRef;
  @ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();
  @ViewChild(MatSort) sort: MatSort;
  documents;
  documentSigne;

  dataSource: MatTableDataSource<Document>;
  dataSourceSigner: MatTableDataSource<Document>;
  dataSourceCertifier: MatTableDataSource<Document>;
  dataSourceConsulter: MatTableDataSource<Document>;
  dataSourceDejaSigner: MatTableDataSource<Document>;
  dataSourceApprouver: MatTableDataSource<Document>;
  dataSourceDejaCertifies: MatTableDataSource<Document>;
  dataSourceDejaApprouves: MatTableDataSource<Document>;

  dctId
  fileURL
  blob
  type

  constructor(private router: Router, private documentService: DocumentService, private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private route: ActivatedRoute,
    private privilegeDocumentService: PrivilegeSignerService,
    private signatureDocumentService: SignatureDocumentService,
    private snackBar: MatSnackBar, private translate: TranslateService) {

  }
  displayedColumns: string[] = ['dctTitre', 'dctAuteur', 'dctType', 'dctDate', 'action'];

  form: FormGroup = new FormGroup({
    dctTitre: new FormControl(false),
    dctAuteur: new FormControl(false),
    dctType: new FormControl(false),
    dctDate: new FormControl(false),
    action: new FormControl(false),
  });

  dctTitre = this.form.get('dctTitre');
  dctAuteur = this.form.get('dctAuteur');
  dctType = this.form.get('dctType');
  dctDate = this.form.get('dctDate');
  action = this.form.get('action');

  columnDefinitions = [
    { def: 'dctTitre', label: 'Titre', hide: false },
    { def: 'dctAuteur', label: 'Auteur', hide: false },
    { def: 'dctType', label: 'Type', hide: false },
    { def: 'dctDate', label: 'Date', hide: false },
    { def: 'action', label: 'Action', hide: false }
  ]
  getDisplayedColumns(): string[] {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  search: any = { dctTitre: '', dctAuteur: '', dctDate: '', typeDocuments: '' };

  advancedSearch(statusDocument, status) {
    let searchval = 444;
    if (this.search.dctTitre == "" && this.search.dctAuteur == "" && this.search.dctDate == "" && this.search.typeDocuments == "") {
      searchval = null;
    }
    if (searchval != null) {
      let filter = { filter: this.search };
      this.documentService.advancedSearchRecu(filter, statusDocument, status).subscribe((data: any) => {
        if (data.statut) {
          // this.documents = data.data;
          // this.dataSource = new MatTableDataSource<Document>(data.data);
          // this.dataSource.paginator = this.paginator.toArray()[0];
          // this.dataSource.sort = this.sort;
          if (status==0) {
            switch (statusDocument) {
              case 0:
                this.dataSourceSigner = new MatTableDataSource<Document>(data.data);
                this.dataSourceSigner.paginator = this.paginator.toArray()[0];
                this.dataSourceSigner.sort = this.sort;
                break;
              case 1:
                this.dataSourceCertifier = new MatTableDataSource<Document>(data.data);
                this.dataSourceCertifier.paginator = this.paginator.toArray()[1];
                this.dataSourceCertifier.sort = this.sort;
                break;
              case 2:
                this.dataSourceConsulter = new MatTableDataSource<Document>(data.data);
                this.dataSourceConsulter.paginator = this.paginator.toArray()[2];
                this.dataSourceConsulter.sort = this.sort;
                break;
              case 3:
                this.dataSourceApprouver = new MatTableDataSource<Document>(data.data);
                this.dataSourceApprouver.paginator = this.paginator.toArray()[3];
                this.dataSourceApprouver.sort = this.sort;
                break;
            }
          } else {
            switch (statusDocument) {
              case 0:
                this.dataSourceDejaSigner = new MatTableDataSource<Document>(data.data);
                this.dataSourceDejaSigner.paginator = this.paginator.toArray()[4];
                this.dataSourceDejaSigner.sort = this.sort;
                break;
              case 1:
                this.dataSourceDejaCertifies = new MatTableDataSource<Document>(data.data);
                this.dataSourceDejaCertifies.paginator = this.paginator.toArray()[5];
                this.dataSourceDejaCertifies.sort = this.sort;
                break;
              case 3:
                this.dataSourceDejaApprouves = new MatTableDataSource<Document>(data.data);
                this.dataSourceDejaApprouves.paginator = this.paginator.toArray()[6];
                this.dataSourceDejaApprouves.sort = this.sort;
                break;
            }
          }

        }
      })
    } else {
      this.getListDocument();

    }
  }

  ngOnInit() {
    this.getListDocument();
    this.getListSigner();
    this.getListCertifier();
    this.getListConsulter();
    this.getListApprouver();
    this.getDocumentDejaSigner();
    this.getDocumentDejaApprouves();
    this.getDocumentDejaCertifies();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceSigner.filter = filterValue.trim().toLowerCase();
  }
  applyFilterCertifier(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceCertifier.filter = filterValue.trim().toLowerCase();
  }
  applyFilterConsulter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceConsulter.filter = filterValue.trim().toLowerCase();
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
        this.dataSourceCertifier.paginator = this.paginator.toArray()[1];
        this.dataSourceCertifier.sort = this.sort;

      }
    })

  }
  getListConsulter() {
    this.privilegeDocumentService.listDocumentConsulterByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceConsulter = new MatTableDataSource<Document>(data.data);
        this.dataSourceConsulter.paginator = this.paginator.toArray()[2];
        this.dataSourceConsulter.sort = this.sort;

      }
    })

  }

  getListApprouver() {
    this.privilegeDocumentService.listDocumentApprouverByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceApprouver = new MatTableDataSource<Document>(data.data);
        this.dataSourceApprouver.paginator = this.paginator.toArray()[3];
        this.dataSourceApprouver.sort = this.sort;

      }
    })

  }
  getListDocument() {
    this.documentService.getDocumentByUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.documents = data.data;
        this.dataSource = new MatTableDataSource<Document>(data.data);
        // this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

      }
    })

  }

  openDialogDeleteDocument(document) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel('document.alert-suppression', message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.delete(document);
      }
    });

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
      console.log(this.columnDefinitions);
    });
  }

  delete(document) {
    let messageSuccess;
    let messageError;
    this.translate.get('document.confirm-suppression').subscribe((res: string) => {
      messageSuccess = res;
    });
    this.translate.get('document.erreur-suppression').subscribe((res: string) => {
      messageError = res;
    });
    this.documentService.deleteDocument(document).subscribe(data => {
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



  openDialogAdd(): void {
    //this.router.navigate(['/document/creation']);
    this.dialog.open(AjoutDocumentComponent, {
      disableClose: true,
      width: '700px',
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
      this.getListConsulter();
      this.getDocumentDejaSigner();
    });

  }


  consulterDocument(document) {
    this.documentService.consulter(document.dctId).subscribe(resp => {
      this.saveFile(resp.body, "document: " + document.dctTitre);

    });
    this.type = document.dctType
  }
  saveFile(data: any, filename?: string) {
    const blob = new Blob([data], { type: this.type });
    fileSaver.saveAs(blob, filename);
  }

  approuverDocument(document) {
    this.dialog.open(ApprobationComponent, {
      disableClose: true,
      width: '600px',
      data: document
    }).afterClosed().subscribe(data => {
      this.getListApprouver();
    });

  }

  getDocumentDejaSigner() {
    this.signatureDocumentService.listeDocumentSignerByIdUser(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceDejaSigner = new MatTableDataSource<Document>(data.data);
        this.dataSourceDejaSigner.paginator = this.paginator.toArray()[4];
        this.dataSourceDejaSigner.sort = this.sort;

      }
    })
  }

  getDocumentDejaCertifies() {
    this.documentService.getDocumentByUserDejaCertifies(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceDejaCertifies = new MatTableDataSource<Document>(data.data);
        this.dataSourceDejaCertifies.paginator = this.paginator.toArray()[5];
        this.dataSourceDejaCertifies.sort = this.sort;

      }
    })
  }

  getDocumentDejaApprouves() {
    this.documentService.getDocumentByUserDejaApprouves(localStorage.getItem("id")).subscribe(data => {
      if (data.statut) {
        this.dataSourceDejaApprouves = new MatTableDataSource<Document>(data.data);
        this.dataSourceDejaApprouves.paginator = this.paginator.toArray()[6];
        this.dataSourceDejaApprouves.sort = this.sort;

      }
    })
  }
  consulterDetailSignature(document): void {
    this.dialog.open(DetailSignatureComponent, {
      disableClose: true,
      width: '700px',
      data: document
    }).afterClosed().subscribe(data => {

    });

  }

}
