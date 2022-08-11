import { Component, OnInit, ViewChild, ElementRef, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { DocumentService } from 'src/app/documents/services/document.service';
import { CleSignatureComponent } from '../cle-signature/cle-signature.component';

@Component({
  selector: 'app-signer-document',
  templateUrl: './signer-document.component.html',
  styleUrls: ['./signer-document.component.scss']
})
export class SignerDocumentComponent implements OnInit {
  payLoad = '';
  rapportGeneres: any
  profileLibelle;
  status;
  owner;
  @ViewChild('pdfViewer') pdfViewer: ElementRef;

  constructor(
    private fichierService: FichierService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private translate: TranslateService,
    private notification: NotificationService, private documentService: DocumentService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<SignerDocumentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.owner = localStorage.getItem('id');
    this.status = this.data.data.data.status;
    if (this.status == 6) {
      this.listeAutFraDefinitiveGenerer();
    } else {
      this.listeRapportGenerer();
    }
  }

  listeRapportGenerer() {
    this.fichierService.listeRapportGenerer().subscribe((res: any) => {
      this.rapportGeneres = res.data;
      this.payLoad = JSON.stringify({ "numerodemande": this.data.data.data.numdemande });
      let varr = this.fichierService.genererAutFraProPdf(this.rapportGeneres[0].rptId, this.payLoad).subscribe((response) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        this.pdfViewer.nativeElement.data = fileURL;
      });
    })
  }

  signer() {    
    const dialog1 = this.dialog.open(CleSignatureComponent, {
			width: '800px',
			data: { data: this.data }
		}).afterClosed().subscribe(result => {
      this.dialogRef.close();
		});
  }

  listeAutFraDefinitiveGenerer() {
    this.fichierService.listeRapportGenerer().subscribe((res: any) => {
      this.rapportGeneres = res.data;
      this.payLoad = JSON.stringify({ "numeroproduit": this.data.produit.id });
      let varr = this.fichierService.genererAutFraDefPdf(this.rapportGeneres[1].rptId, this.payLoad).subscribe((response) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        this.pdfViewer.nativeElement.data = fileURL;
      });
    })
  }

  signerAutDefinitive() {
    const dialog1 = this.dialog.open(CleSignatureComponent, {
			width: '800px',
			data: { data: this.data }
		}).afterClosed().subscribe(result => {
      this.dialogRef.close();
		});
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
