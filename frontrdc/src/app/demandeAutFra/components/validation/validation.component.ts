import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { MotifrejetourenvoiService } from '../../service/motifrejetourenvoi.service';

@Component({
	selector: 'app-validation',
	templateUrl: './validation.component.html',
	styleUrls: ['./validation.component.scss']
})
export class ValidationComponent implements OnInit {

	status: any;
	donnee: any;

	constructor(
		private demandeAutFraService: DemandeAutFraService,
		private motifrejetourenvoiService: MotifrejetourenvoiService,
		private router: Router, private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public dialog: MatDialogRef<ValidationComponent>) { }

	EditForm1 = this.formBuilder.group({
		motif: [''],
		dateCommentaire: [''],
		poOwner: [''],
		status: [''],
		owner: [''],
		idLink: ['']
	});

	situation: any

	ngOnInit() {
		this.donnee = this.data.data.data;
	}

	profilLibelle = localStorage.getItem('profileLibelle')

	onSubmit1() {
		if (this.donnee) {
			this.EditForm1.value.idLink = this.donnee.id
		} else {
			this.EditForm1.value.idLink = null
		}
		this.EditForm1.value.poOwner = localStorage.getItem("profile")
		this.EditForm1.value.owner = localStorage.getItem("id")
		this.EditForm1.value.status = 3;
		this.motifrejetourenvoiService.createMotifrejetourenvoi(this.EditForm1.value).subscribe((data: any) => {});
		this.demandeAutFraService.updateTaskDemandeAutFra(this.data.data.data.id, 3).subscribe(data => {
			this.translate.get('demandevalide.succes').subscribe((res: string) => {
				this.notification.success(res);
			});
		});
		this.EditForm1.reset();
		this.closeDialog();
		location.reload();
	}

	closeDialog() {
		this.dialog.close({ status: false });
	}
}
