import { Component, Inject, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA,MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { version } from 'punycode';
import { Workflow } from 'src/app/procedures/models/workflow';
import { WorkflowService } from 'src/app/procedures/services/workflow.service';
import { PaiementService } from '../../service/paiement.service';

@Component({
  selector: 'app-detail-paiement',
  templateUrl: './detail-paiement.component.html',
  styleUrls: ['./detail-paiement.component.scss']
})
export class DetailPaiementComponent implements OnInit {

 element:any;
 paiement;
 
  constructor( private paiementService: PaiementService,
    public dialogRef: MatDialogRef<DetailPaiementComponent>, @Inject(MAT_DIALOG_DATA) public reference) { }

  ngOnInit() {
    this.detailPaiement()
  }
  
  detailPaiement() {   
    this.paiementService.getPaiementByReferencePaiement(this.reference).subscribe(data=>{
      this.paiement=data.data;
    })
  }  

  closeDialog() {
    this.dialogRef.close();
  }
}

