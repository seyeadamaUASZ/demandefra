import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-signature',
  templateUrl: './signature.component.html',
  styleUrls: ['./signature.component.scss']
})
export class SignatureComponent implements OnInit {

  @ViewChild('pdfViewer') pdfViewer: ElementRef;
  fileURL
  blob


  constructor(public dialogRef: MatDialogRef<SignatureComponent>,
    private documentService:DocumentService,
     @Inject(MAT_DIALOG_DATA) public donnee: any,private translate:TranslateService,private notification: NotificationService
     ) { }

  ngOnInit() {
    this.consulterDocument();
  }
 consulterDocument(){
     this.documentService.consulter(this.donnee.dctId).subscribe(data=>{
    this.blob = new Blob([data.body], {type: 'application/pdf'});
      this.fileURL = window.URL.createObjectURL(this.blob);
      this.pdfViewer.nativeElement.data =  this.fileURL;
      });

   }

  closeDialog() {
    this.dialogRef.close();
  }

  signerDocument(){
    this.documentService.signerDocument(localStorage.getItem('username'),this.donnee).subscribe(res=>{
        console.log(res.statut)
      let ReportSaveSuccess;
      this.translate.get('document.confirmSignature').subscribe((res: string) => {
        ReportSaveSuccess = res;
      });
      this.translate.get(ReportSaveSuccess).subscribe((res: string) => {
        this.notification.success(res);
      });
     this.closeDialog();

    }, error => {
      let ReportSaveError;
      this.translate.get('document.erreurserversignature').subscribe((res: string) => {
        ReportSaveError = res;
      });
      this.translate.get(ReportSaveError).subscribe((res: string) => {
        this.notification.error(res);
      });
    })

  }
}
