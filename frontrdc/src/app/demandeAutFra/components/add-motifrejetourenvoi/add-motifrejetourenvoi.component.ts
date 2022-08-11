import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { ProduitsService } from '../../service/produits.service';

@Component({
	selector: 'app-add-motifrejetourenvoi',
	templateUrl: './add-motifrejetourenvoi.component.html',
	styleUrls: ['./add-motifrejetourenvoi.component.scss']
})
export class AddMotifrejetourenvoiComponent implements OnInit {

	status: any;
	donnee: any;
	donnee1: any;
	situation: any;
	profilLibelle: any;

	constructor(private motifrejetourenvoiService: MotifrejetourenvoiService,
		private demandeAutFraService: DemandeAutFraService,
		private produitsService: ProduitsService,
		private router: Router, private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public dialog: MatDialogRef<AddMotifrejetourenvoiComponent>) { }

	EditForm = this.formBuilder.group({
		motif: [''],
		dateCommentaire: [null],
		poOwner: [''],
		status: [''],
		owner: [''],
		idLink: ['']
	});

	ngOnInit() {
		this.profilLibelle = localStorage.getItem('profileLibelle');
		if (this.data) {
			this.donnee = this.data.data;
			this.donnee1 = this.data.produit;
			this.status = this.donnee.data.status;
			this.EditForm.setValue(this.donnee);
		}
	}

	onSubmit() {
		this.EditForm.value.poOwner = localStorage.getItem("profile")
		this.EditForm.value.owner = localStorage.getItem("id")
		this.EditForm.value.status = this.status
		if (!this.EditForm.value.motif) {
			this.translate.get('motifrejetourenvoi.remplirTousLesChampsNotif').subscribe((res: string) => {
				this.notification.error(res);
			});
		} else {
			if (this.donnee) {
				this.EditForm.value.idLink = this.donnee.data.id
			} else {
				this.EditForm.value.idLink = null
			}
			if (this.data.data.data.status != 6) {
				if(this.profilLibelle == 'chef bureau') {
					this.motifrejetourenvoiService.createMotifrejetourenvoi(this.EditForm.value).subscribe((data: any) => {
						this.motifrejetourenvoiService.updateTaskMotifrejetourenvoi(data.data.id, 9).subscribe((data: any) => {});
					})
					this.demandeAutFraService.updateTaskDemandeAutFra(this.data.data.data.id, 10).subscribe(data => {
						this.translate.get('demanderejet.succes').subscribe((res: string) => {
							this.notification.success(res);
						});
					});
					this.EditForm.reset();
					this.closeDialog();
					location.reload();
				} else {
					this.motifrejetourenvoiService.createMotifrejetourenvoi(this.EditForm.value).subscribe((data: any) => {
						this.motifrejetourenvoiService.updateTaskMotifrejetourenvoi(data.data.id, 10).subscribe((data: any) => {});
					})
					this.demandeAutFraService.updateTaskDemandeAutFra(this.data.data.data.id, 10).subscribe(data => {
						this.translate.get('demanderejet.succes').subscribe((res: string) => {
							this.notification.success(res);
						});
					});
					this.EditForm.reset();
					this.closeDialog();
					location.reload();
				}				
			} else {
				if (this.donnee1) {
					this.EditForm.value.idLink = this.donnee1.id
				} else {
					this.EditForm.value.idLink = null
				}

				this.motifrejetourenvoiService.createRenvoiProduit(this.EditForm.value).subscribe((data: any) => {

					this.motifrejetourenvoiService.updateTaskMotifrejetourenvoi(data.data.id, 10).subscribe((data: any) => {});
				});
				this.produitsService.updateTaskProduits(this.data.produit.id, 10).subscribe((data: any) => {
					this.translate.get('produitrejet.succes').subscribe((res: string) => {
						this.notification.success(res);
					});
				});
				this.EditForm.reset();
				this.closeDialog();
			}
		}
	}

	onSubmit1() {
		if (this.donnee) {
			this.EditForm.value.idLink = this.data.data.data?.id
		} else {
			this.EditForm.value.idLink = null
		}
		this.EditForm.value.poOwner = localStorage.getItem("profile")
		this.EditForm.value.owner = localStorage.getItem("id")

		this.demandeAutFraService.renvoiDemandeAuFra(this.data.data.data, this.EditForm.value)
			.subscribe((data: any) => {
				if (data.statut) {
					this.translate.get('chefdebureau.confirmEnr').subscribe((res: string) => {
						this.notification.success(res);
					});

					this.EditForm.reset();
					this.dialog.close({ status: true });
					location.reload();
				} else {
					this.translate.get('error').subscribe((res: string) => {
						this.notification.error(res);
					});

					this.EditForm.reset();
					this.closeDialog();
					location.reload()
				}
			})
	}

	closeDialog() {
		this.dialog.close({ status: false });
	}
}
