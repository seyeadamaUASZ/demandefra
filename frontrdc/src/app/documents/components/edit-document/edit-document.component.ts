import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DocumentService } from '../../services/document.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TranslateService } from '@ngx-translate/core';
import { TypeDocuments } from '../../model/typeDocuments';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.scss']
})
export class EditDocumentComponent implements OnInit {
  typeDocument
  public documentFile: Array<File>;
  isFileValid: boolean;
  accept:any;
  FormulaireForm= this.formbuild.group({
    dctId: ['', Validators.required],
    dctTitre: ['', Validators.required],
    dctAuteur: ['', Validators.required],
    typeDocuments: ['', Validators.required],
    dctBlob: ['', Validators.required],
  });
  typedocumentset: TypeDocuments = null;
  constructor( public dialogRef: MatDialogRef<EditDocumentComponent>,  @Inject(MAT_DIALOG_DATA) public document: any, private documentService: DocumentService,
    private formbuild: FormBuilder, private router: Router,
    private notification: NotificationService, private translate:TranslateService) { }

  ngOnInit() {
    this.initView();
    this.getTypeDocument();


  }
  getTypeDocument(){
    this.documentService.getTypeDocuments().subscribe((resp:any)=>{
      this.typeDocument=resp.data
    })
  }

  initView() {
    this.FormulaireForm.setValue({
      dctId: this.document.dctId,
      dctTitre: this.document.dctTitre ? this.document.dctTitre : null,
      dctAuteur: this.document.dctAuteur ? this.document.dctAuteur : null,
      typeDocuments: this.document.typeDocuments ? this.document.typeDocuments : null,
      dctBlob: this.document.dctBlob ? this.document.dctBlob : null,

    });
  }
  compareDocument(d1, d2): boolean {
    return d1 && d2 ? d1.tydId === d2.tydId : false;
  }
  onSubmit() {
    this.documentService.updateDocument(this.documentFile[0], this.FormulaireForm.value).subscribe(data => {
      if (data.statut) {
        let ReportSaveSuccess;
        this.translate.get('document.confirmEnr').subscribe((res: string) => {
          ReportSaveSuccess = res;
        });
        this.translate.get(ReportSaveSuccess).subscribe((res: string) => {
          this.notification.success(res);
        });

        this.FormulaireForm.reset();
        this.closeDialog();
      }
    }, error => {
      let DocumentSaveError;
      this.translate.get('document.error-edit').subscribe((res: string) => {
        DocumentSaveError = res;
      });
      this.translate.get(DocumentSaveError).subscribe((res: string) => {
        this.notification.error(res);
      });

    });
  }

  uploadDocument(event: any) {
    if (event.target.files[0]) {
      const extension = event.target.files[0].name.split('.')[1].toLowerCase();
      if ( "pdf" === extension || "docx"===extension) {
        this.isFileValid = true;
      }
      this.documentFile = event.target.files;
      if (this.documentFile[0].size >3000000){
        this.translate.get("Verifier la taille du document!!").subscribe((res: string) => {
          this.notification.warn(res);
        });
        return;

      }
    }
  }




  closeDialog() {
    this.dialogRef.close();
  }


}
