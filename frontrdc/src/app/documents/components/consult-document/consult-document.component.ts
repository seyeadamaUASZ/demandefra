import { Component, OnInit, ViewChild, ElementRef, Inject } from '@angular/core';
import { DocumentService } from '../../services/document.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-consult-document',
  templateUrl: './consult-document.component.html',
  styleUrls: ['./consult-document.component.scss']
})
export class ConsultDocumentComponent implements OnInit {
  @ViewChild('pdfViewer') pdfViewer: ElementRef;
  fileURL
  blob
  type

  constructor(public dialogRef: MatDialogRef<ConsultDocumentComponent>, private documentService: DocumentService, @Inject(MAT_DIALOG_DATA) public document: any) { }

  ngOnInit() {
    this.consulterDocument();

  }
   consulterDocument() {
     this.documentService.consulter(this.document.dctId).subscribe(data => {
       this.type = this.document.dctType
       this.blob = new Blob([data.body], { type: this.type });
       this.fileURL = window.URL.createObjectURL(this.blob);
       this.pdfViewer.nativeElement.data = this.fileURL;
     });

   }

  closeDialog() {
    this.dialogRef.close();
  }


 }
