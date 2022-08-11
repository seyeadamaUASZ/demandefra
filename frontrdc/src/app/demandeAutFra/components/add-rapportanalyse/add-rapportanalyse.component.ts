import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { ProduitsService } from '../../service/produits.service';
import { RapportanalyseService } from '../../service/rapportanalyse.service';
import { SignerDocumentComponent } from '../signer-document/signer-document.component';

@Component({
	selector: 'app-add-rapportanalyse',
	templateUrl: './add-rapportanalyse.component.html',
	styleUrls: ['./add-rapportanalyse.component.scss']
})
export class AddRapportanalyseComponent implements OnInit {

	status: any;
	donnee: any;
	donnee1: any;
	situation: any;
	profilLibelle: any;

	constructor(private rapportService: RapportanalyseService,
		private produitsService: ProduitsService,
		private router: Router, private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private dialogRef: MatDialog,
		public dialog: MatDialogRef<AddRapportanalyseComponent>) { }

	EditForm = this.formBuilder.group({
		referentiel: ['', Validators.required],
		commentaire: [''],
		analyse: [''],
		produits: ['']
	});

	ngOnInit() {
		this.profilLibelle = localStorage.getItem('profileLibelle');
		if (this.data) {
			this.donnee = this.data.data;
			this.status = this.donnee.data.status;
			this.EditForm.setValue(this.donnee);
		}
	}

	onSubmit() {
		this.EditForm.value.produits = this.data.produit;
		if (!this.analyse) {
			this.translate.get("rapportanalyse.selectAllFilesNotif").subscribe((res: string) => {
				this.notification.warn(res);
			});
			return;
		} else if (!this.EditForm.value.referentiel) {
			this.translate.get('rapportanalyse.remplirTousLesChampsNotif').subscribe((res: string) => {
				this.notification.error(res);
			});
			return;
		} else {
			this.produitsService.updateTaskProduits(this.data.produit.id, 14).subscribe(data => { });
			this.rapportService.createRapportanalyse(this.EditForm.value, this.analyse).subscribe((data: any) => {
				this.translate.get('rapportanalyse.confirmRapport').subscribe((res: string) => {
					this.notification.success(res);
				});
			});
			this.dialog.close();
		}
	}

	closeDialog() {
		this.dialog.close({ status: false });
	}

	@ViewChild('fileAnalyse') fileAnalyse;

	analyse: File;

	addFileAnalyse() {
		this.fileAnalyse.nativeElement.click();
	}

	onFileAnalyse() {
		this.analyse = this.fileAnalyse.nativeElement.files[0];
		const extension = this.analyse.name.split('.')[1].toLowerCase();
		if ('pdf' !== extension) {
			this.translate.get('rapportanalyse.fileExtenxionNotif').subscribe((res: string) => {
				this.notification.warn(res);
			});
			this.analyse = null;
			return;
		}
		if (this.analyse.size > 3000000) {
			this.translate.get('rapportanalyse.fileSizeNotif').subscribe((res: string) => {
				this.notification.warn(res);
			});
			this.analyse = null;
			return;
		}
	}

	autorisationFra() {
		const dialog1 = this.dialogRef.open(SignerDocumentComponent, {
			width: '800px',
			data: { data: this.data.data, produit: this.data.produit }
		}).afterClosed().subscribe(result => {
		});
	}	
}
