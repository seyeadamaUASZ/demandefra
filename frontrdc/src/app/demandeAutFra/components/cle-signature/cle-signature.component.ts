import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { ProduitsService } from '../../service/produits.service';
import { RapportanalyseService } from '../../service/rapportanalyse.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import { Signature } from '../../models/signature';
import { SignerDocumentComponent } from '../signer-document/signer-document.component';

@Component({
	selector: 'app-cle-signature',
	templateUrl: './cle-signature.component.html',
	styleUrls: ['./cle-signature.component.scss']
})
export class CleSignatureComponent implements OnInit {
	status: any;
	donnee: any;
	profilLibelle: any;
	rapportGeneres:any;
	payLoad:any;
	owner: any;
	signature:Signature;

	constructor(private rapportService: RapportanalyseService,
		private produitsService: ProduitsService,
		private fichierService: FichierService,
		private router: Router, private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public dialogRef: MatDialogRef<SignerDocumentComponent>,
		public dialog: MatDialogRef<CleSignatureComponent>) { }

	EditForm = this.formBuilder.group({
		codePin: [''],
		utilisateur: ['']
	});

	ngOnInit() {
		this.profilLibelle = localStorage.getItem('profileLibelle');
		this.owner = localStorage.getItem('id');
		this.listeRapportGenerer();
		if (this.data) {
			this.donnee = this.data.data;
			this.status = this.donnee.data.status;
			this.EditForm.setValue(this.donnee);
		}
	}

	listeRapportGenerer() {
		this.fichierService.listeRapportGenerer().subscribe((res: any) => {
		  this.rapportGeneres = res.data;		  
		})
	}
	
	onSubmit() {
		this.EditForm.value.utilisateur = localStorage.getItem("id");
		if (this.data.data.data.data.status == 6) {
			this.rapportService.verificationCodePin(this.EditForm.value).subscribe((data: any) => {
				if(data.statut == true) {
					this.signature=data.data;					
					this.payLoad = JSON.stringify({ "numeroproduit": this.data.data.produit.id });
					this.fichierService.signerFraDefinitive(this.rapportGeneres[1].rptId, this.payLoad, this.data.data.produit.id, this.signature.id).subscribe((response: any) => {
						this.closeDialog();
						this.translate.get('signature.success').subscribe((res: string) => {
							this.notification.info(res);
						});						
					});						
				} else {
					this.translate.get('signature.warm').subscribe((res: string) => {
						this.notification.warn(res);
					});
				}		
			});
		} else {
			this.rapportService.verificationCodePin(this.EditForm.value).subscribe((data: any) => {
				if(data.statut == true) {
					this.signature=data.data;
					this.payLoad = JSON.stringify({ "numerodemande": this.data.data.data.data.numdemande });

					this.fichierService.signature(this.rapportGeneres[0].rptId, this.payLoad, this.data.data.data.data.id,this.signature.id).subscribe((response: any) => {
						this.closeDialog();
						this.translate.get('signature.success').subscribe((res: string) => {
							this.notification.info(res);
						});	
					});	
					
				} else {
					this.translate.get('signature.warm').subscribe((res: string) => {
						this.notification.warn(res);
					});
				}		
			});		
		}	
	}

	closeDialog() {
		this.dialog.close();
	}
}
