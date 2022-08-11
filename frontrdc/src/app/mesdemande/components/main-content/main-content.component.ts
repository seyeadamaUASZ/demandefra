import { Component, Inject, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { FichierService } from 'src/app/fichier/services/fichier.service';
import * as fileSaver from 'file-saver';
import { ViewDemandeAutFraComponent } from 'src/app/demandeAutFra/components/view-demandeAutFra/view-demandeAutFra.component';
import { MesdemandeService } from '../../service/mesdemande.service';
import { UserService } from 'src/app/utilisateur/services/user.service';

@Component({
	selector: 'app-main-content',
	templateUrl: './main-content.component.html',
	styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
	@ViewChildren(MatPaginator) paginator: QueryList<MatPaginator> = new QueryList();
	@ViewChild(MatSort) sort: MatSort;
	form;
	result: any
	dataSource: MatTableDataSource<any>;
	profilLibelle = localStorage.getItem('profileLibelle');
	poOwner = localStorage.getItem('profil');

	donnee: any;
	task: any = []
	status: any
	payLoad = '';

	columnDefinitions = ['numero', 'dateSoumission', 'prenomResponsable', 'nomResponsable', 'raisonSociale', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}

	constructor(private mesdemandeService: MesdemandeService,
		private userService: UserService,
		private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private fichierService: FichierService,
		private notification: NotificationService,
		@Inject(MAT_DIALOG_DATA) public data: any,
		private router: Router) { }

	ngOnInit() {
		this.listeMesDemandes();
	}

	listeMesDemandes() {
		this.userService.getUserByUsername(localStorage.getItem('username')).subscribe((data: any) => {
			this.mesdemandeService.getMesDemandes(data.data.utiEmail).subscribe((data: any) => {
				if (data.statut) {
					this.dataSource = new MatTableDataSource<any>(data.data);
					this.dataSource.paginator = this.paginator.toArray()[0];
					this.dataSource.sort = this.sort;
				}
			})
		});		
	}

	openDialogVisualiser(data): void {
		const dialog = this.dialogRef.open(ViewDemandeAutFraComponent, {
			width: '1500px',
			data: { data: data, openDialog: true }, 
			disableClose: true
		}).afterClosed().subscribe(result => {
		});
	}
}
