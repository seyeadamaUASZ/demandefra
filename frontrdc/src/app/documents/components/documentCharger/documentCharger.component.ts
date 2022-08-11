import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatPaginator, MatSnackBar, MatSort, MatTableDataSource } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { DocumentService } from '../../services/document.service';
import { PrivilegeSignerService } from '../../services/privilegeSigner.service';
import * as fileSaver from 'file-saver';
import { AjoutDocumentComponent } from '../ajout-document/ajout-document.component';
import { EditDocumentComponent } from '../edit-document/edit-document.component';
import { SignatureComponent } from '../signature/signature.component';
import { Observable,merge } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-documentCharger',
  templateUrl: './documentCharger.component.html',
  styleUrls: ['./documentCharger.component.scss']
})
export class DocumentChargerComponent implements OnInit {

  @ViewChild('pdfViewer') pdfViewer: ElementRef;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
  documents;
  documentSigne;

  dataSource: MatTableDataSource<Document>;
  dataSourceSigner: MatTableDataSource<Document>;
  dataSourceCertifier: MatTableDataSource<Document>;
  dataSourceConsulter: MatTableDataSource<Document>;

  dctId
  fileURL
  blob
  type

  constructor(private router: Router, private documentService:DocumentService,  private dialog: MatDialog,
    private notification: NotificationService,
    private dialogRef: MatDialog,
    private route: ActivatedRoute,
    private privilegeDocumentService:PrivilegeSignerService,

    private snackBar: MatSnackBar, private translate: TranslateService) {

     }
     displayedColumns: string[] = [ 'dctTitre', 'dctAuteur', 'dctType','dctDate', 'action'];

  ngOnInit() {
  this.getListDocument();
  this.getListSigner();
  this.getListCertifier();
  this. getListConsulter();


  }

  form1: FormGroup = new FormGroup({
		dctTitre: new FormControl(false),
		dctAuteur: new FormControl(false),
    dctType: new FormControl(false),
    dctDate: new FormControl(false),
		action: new FormControl(false)

	});

  form2: FormGroup = new FormGroup({
    dctTitre: new FormControl(false),
		dctAuteur: new FormControl(false),
    dctType: new FormControl(false),
    dctDate: new FormControl(false),
		action: new FormControl(false)

	});

  form3: FormGroup = new FormGroup({
    dctTitre: new FormControl(false),
		dctAuteur: new FormControl(false),
    dctType: new FormControl(false),
    dctDate: new FormControl(false),
		action: new FormControl(false)
	});

  dctTitre1 = this.form1.get('dctTitre');
	dctAuteur1 = this.form1.get('dctAuteur');
  dctType1 = this.form1.get('dctType');
  dctDate1 = this.form1.get('dctDate');
	action1 = this.form1.get('action');


  dctTitre2 = this.form2.get('dctTitre');
	dctAuteur2 = this.form2.get('dctAuteur');
  dctType2 = this.form2.get('dctType');
  dctDate2 = this.form2.get('dctDate');
	action2 = this.form2.get('action');

	dctTitre3 = this.form3.get('dctTitre');
	dctAuteur3 = this.form3.get('dctAuteur');
  dctType3 = this.form3.get('dctType');
  dctDate3 = this.form3.get('dctDate');
	action3 = this.form3.get('action');

  

	columnDefinitions1 = [
		{ def: 'dctTitre', label: 'document.titre', hide: false },
		{ def: 'dctAuteur', label: 'document.auteur', hide: false },
    { def: 'dctType', label: 'document.type', hide: false },
    { def: 'dctDate', label: 'document.date_chargement', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]

  columnDefinitions2 = [
		{ def: 'dctTitre', label: 'document.titre', hide: false },
		{ def: 'dctAuteur', label: 'document.auteur', hide: false },
    { def: 'dctType', label: 'document.type', hide: false },
    { def: 'dctDate', label: 'document.date_chargement', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]

  columnDefinitions3 = [
		{ def: 'dctTitre', label: 'document.titre', hide: false },
		{ def: 'dctAuteur', label: 'document.auteur', hide: false },
    { def: 'dctType', label: 'document.type', hide: false },
    { def: 'dctDate', label: 'document.date_chargement', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]


  
	getDisplayedColumns1(): string[] {
		return this.columnDefinitions1.filter(cd => !cd.hide).map(cd => cd.def);
	}

  getDisplayedColumns2(): string[] {
		return this.columnDefinitions2.filter(cd => !cd.hide).map(cd => cd.def);
	}
  getDisplayedColumns3(): string[] {
		return this.columnDefinitions3.filter(cd => !cd.hide).map(cd => cd.def);
	}

 


	
	ngAfterViewInit() {
    
		let o1: Observable<boolean> = this.dctTitre1.valueChanges;
		let o2: Observable<boolean> = this.dctAuteur1.valueChanges;
		let o3: Observable<boolean> = this.dctType1.valueChanges;
    let o4: Observable<boolean> = this.dctDate1.valueChanges;
    let o5: Observable<boolean> = this.action1.valueChanges;

    let o12: Observable<boolean> = this.dctTitre2.valueChanges;
		let o22: Observable<boolean> = this.dctAuteur2.valueChanges;
		let o32: Observable<boolean> = this.dctType2.valueChanges;
    let o42: Observable<boolean> = this.dctDate2.valueChanges;
    let o52: Observable<boolean> = this.action2.valueChanges;

    let o13: Observable<boolean> = this.dctTitre3.valueChanges;
		let o23: Observable<boolean> = this.dctAuteur3.valueChanges;
		let o33: Observable<boolean> = this.dctType3.valueChanges;
    let o43: Observable<boolean> = this.dctDate3.valueChanges;
    let o53: Observable<boolean> = this.action3.valueChanges;

		merge(o1, o2, o3,o4,o5).subscribe(v => {
			this.columnDefinitions1[0].hide = this.dctTitre1.value;
			this.columnDefinitions1[1].hide = this.dctAuteur1.value;
      this.columnDefinitions1[2].hide = this.dctType1.value;
			this.columnDefinitions1[3].hide = this.dctDate1.value;
      this.columnDefinitions1[4].hide = this.action1.value;
			
		});

    merge(o12, o22, o32,o42,o52).subscribe(v => {
			this.columnDefinitions2[0].hide = this.dctTitre2.value;
			this.columnDefinitions2[1].hide = this.dctAuteur2.value;
      this.columnDefinitions2[2].hide = this.dctType2.value;
			this.columnDefinitions2[3].hide = this.dctDate2.value;
      this.columnDefinitions2[4].hide = this.action2.value;
			
		});

    merge(o13, o23, o33,o43,o53).subscribe(v => {
			this.columnDefinitions3[0].hide = this.dctTitre3.value;
			this.columnDefinitions3[1].hide = this.dctAuteur3.value;
      this.columnDefinitions3[2].hide = this.dctType3.value;
			this.columnDefinitions3[3].hide = this.dctDate3.value;
      this.columnDefinitions3[4].hide = this.action3.value;
			
		});


   

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

  getListSigner(){
    this.documentService.getDocumentByUserSigner(localStorage.getItem("id")).subscribe(data=>{
      if(data.statut){
        this.documentSigne = data.data;
          this.dataSourceSigner = new MatTableDataSource<Document>(data.data);
          this.dataSourceSigner.paginator = this.paginator;
          this.dataSourceSigner.sort = this.sort;

      }
    })

  }

  getListCertifier(){
    this.documentService.getDocumentByUserCertifier(localStorage.getItem("id")).subscribe(data=>{
      if(data.statut){
          this.dataSourceCertifier = new MatTableDataSource<Document>(data.data);
          this.dataSourceCertifier.paginator = this.paginator;
          this.dataSourceCertifier.sort = this.sort;

      }
    })

  }
  getListConsulter(){
    this.documentService.getDocumentByUserConsulter(localStorage.getItem("id")).subscribe(data=>{
      if(data.statut){
          this.dataSourceConsulter = new MatTableDataSource<Document>(data.data);
          this.dataSourceConsulter.paginator = this.paginator;
          this.dataSourceConsulter.sort = this.sort;

      }
    })

  }
getListDocument(){
  this.documentService.getDocumentByUser(localStorage.getItem("id")).subscribe(data=>{
    if(data.statut){
      this.documents = data.data;
				this.dataSource = new MatTableDataSource<Document>(data.data);
				this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;

    }
  })

}

openDialogDeleteDocument(document){
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



  openDialogAdd(): void{
    //this.router.navigate(['/document/creation']);
    this.dialog.open(AjoutDocumentComponent, {
      disableClose: true,
      width: '700px',
    }).afterClosed().subscribe(data => {
      this.getListDocument();
      this.getListSigner();
      this.getListCertifier();
      this. getListConsulter();


    });

  }

  openDialogUpdate(document): void{
    this.dialog.open(EditDocumentComponent, {
      disableClose: true,
      width: '700px',
      data:document
    }).afterClosed().subscribe(data => {
      this.getListDocument();

    });

  }

  openDialogSignerDocument(document): void{
    this.dialog.open(SignatureComponent, {
      disableClose: true,
      width: '700px',
      data:document
    }).afterClosed().subscribe(data => {

    });

  }


  consulterDocument(document){
    this.documentService.consulter(document.dctId).subscribe(resp=>{
       this.saveFile(resp.body, "document: "+document.dctTitre);

      });
      this.type = document.dctType
    }
    saveFile(data: any,  filename?: string) {
      const blob = new Blob([data], {type:this.type});
      fileSaver.saveAs(blob,  filename);
     }
}
