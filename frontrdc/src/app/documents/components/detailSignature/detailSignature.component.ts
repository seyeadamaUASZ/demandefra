import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { User } from 'src/app/utilisateur/models/user';
import { SignatureDocumentService } from '../../services/signatureDocument.service';

@Component({
  selector: 'app-detailSignature',
  templateUrl: './detailSignature.component.html',
  styleUrls: ['./detailSignature.component.scss']
})
export class DetailSignatureComponent implements OnInit {
 detail:User
  constructor(public dialogRef: MatDialogRef<DetailSignatureComponent>,private signatureDocumentService:SignatureDocumentService,
     @Inject(MAT_DIALOG_DATA) public donnee: any) { }

  ngOnInit() {
    this.detailSignature()
  }
   detailSignature(){
     this.signatureDocumentService.DetailDocumentSignerByIdDoc(this.donnee.dctId).subscribe(res=>{
        this.detail=res.data
     })
   }
   closeDialog() {
    this.dialogRef.close();
  }
}
