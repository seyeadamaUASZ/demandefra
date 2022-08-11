import { Component, OnInit, ViewChild, AfterViewInit, QueryList, ViewChildren } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar } from '@angular/material';
import { AddDemandeAutFraComponent } from '../add-demandeAutFra/add-demandeAutFra.component';
import { ViewDemandeAutFraComponent } from '../view-demandeAutFra/view-demandeAutFra.component';
import { DemandeAutFraService } from '../../service/demandeAutFra.service';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import * as fileSaver from 'file-saver';
import { merge, Observable } from 'rxjs';
import { AddProduitsComponent } from '../add-produits/add-produits.component';
import { AddMotifrejetourenvoiComponent } from '../add-motifrejetourenvoi/add-motifrejetourenvoi.component';
import { AddSuivredemandeComponent } from '../add-suivredemande/add-suivredemande.component';


@Component({
	selector: 'app-main-content',
	templateUrl: './main-content.component.html',
	styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements AfterViewInit {
	@ViewChildren(MatPaginator) paginator: QueryList<MatPaginator> = new QueryList();
	@ViewChild(MatSort) sort: MatSort;
	form;
	result: any
	dataSource: MatTableDataSource<any>;
	dataSource2: MatTableDataSource<any>;
	dataSourceTraites: MatTableDataSource<any>;
	profilLibelle = localStorage.getItem('profileLibelle');

	task: any = []
	status: any

	columnDefinitions = ['raisonsociale', 'adresseEntreprise', 'emailEntreprise', 'ninea', 'registrecommerce', 'statutjuridique', 'telephoneEntreprise', 'prenomResponsable', 'nomResponsable', 'civiliteResponsable', 'adresseResponsable', 'telephoneResponsable', 'emailResponsable', 'certificatanalysesproduit', 'registrecommerceetcreditmobilier', 'juridique', 'processusfabrication', 'copieninea', 'cnipasseport', 'action'];
	selectedColumns = this.columnDefinitions;
	getDisplayedColumns() {
		return this.selectedColumns;
	}
	constructor(private demandeAutFraService: DemandeAutFraService,
		private dialogRef: MatDialog,
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private translate: TranslateService,
		private notification: NotificationService,
		private router: Router) {
	}
	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.dataSource.filter = filterValue.trim().toLowerCase();
	  }
	ngAfterViewInit() {
		this.listDemandeAutFra();
		this.verifyDemandeAutFra();
		if (!this.demandeAutFra) {
			this.listTask(localStorage.getItem('profil'))
		}

		this.verifyProduits();
		this.verifyMotifrejetourenvoi();
		this.verifySuivredemande();

	}
	ngOnInit() {
	}
	demandeAutFra = false
	verifyDemandeAutFra() {
		this.demandeAutFraService.getAllTask().subscribe(data => {
			this.task = data
			console.log(this.task.data)
			for (let i = 0; i < this.task.data.length; i++) {
				if (this.task.data[i].poOwner.proId == localStorage.getItem("profil") && this.task.data[i].tskFormName == 'DemandeAutFra') {
					this.demandeAutFra = true
					this.listDemandeAutFra();
					this.demandeAutFraService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {
						this.status = data.data
					})
					break
				}
			}
		})
	}
	listDemandeAutFra() {
		this.demandeAutFraService.getDemandeAutFraAll(localStorage.getItem('id')).subscribe(data => {
			this.form = data
			if (this.form.statut) {
				this.dataSource = new MatTableDataSource<any>(this.form.data);
				this.dataSource.paginator = this.paginator.toArray()[0];
				this.dataSource.sort = this.sort;
			} else {

			}
		})
	}
	listTask(poowner) {
		this.demandeAutFraService.getTask(poowner).subscribe(data => {
			this.form = data
			if (this.form.statut) {
				this.dataSource2 = new MatTableDataSource<any>(this.form.data);
				this.dataSource2.paginator = this.paginator.toArray()[0];
				this.dataSource2.sort = this.sort;
				this.getTaskTraite();
			} else {
			}
		})
	}
	getTaskTraite() {
		let traitant = null;
		if (this.produits) {
			traitant = 't1';
		}
		if (this.motifrejetourenvoi) {
			traitant = 't2';
		}
		if (this.suivredemande) {
			traitant = 't3';
		}
		this.demandeAutFraService.getTaskTraite(localStorage.getItem('profil'), traitant).subscribe((data: any) => {
			if (data.statut) {
				this.dataSourceTraites = new MatTableDataSource<any>(data.data);
				this.dataSourceTraites.paginator = this.paginator.toArray()[1];
				this.dataSourceTraites.sort = this.sort;
			}
		});
	}
	openDialogAdd(): void {
		const dialog = this.dialogRef.open(AddDemandeAutFraComponent, {
			width: '1500px',
			data: this.status,
			disableClose: true
		}).afterClosed().subscribe(result => {
			location.reload();
			this.listDemandeAutFra();
		});

	}
	openDialogEdit(data) {
		const dialog1 = this.dialogRef.open(AddDemandeAutFraComponent, {
			width: '700px',
			data: { data: data, status: this.status }
			, disableClose: true
		}).afterClosed().subscribe(result => {
		});
	}
	openDialogDetailDemandeAutFra(data) {
		const dialog1 = this.dialogRef.open(ViewDemandeAutFraComponent, {
			width: '700px',
			data: { data: data, status: this.status }
		}).afterClosed().subscribe(result => {
		});
	}

	produits = false
	verifyProduits() {
		this.demandeAutFraService.getAllTask().subscribe(data => {
			this.task = data
			console.log(this.task.data)
			for (let i = 0; i < this.task.data.length; i++) {
				if (this.task.data[i].poOwner.proId == localStorage.getItem("profil") && this.task.data[i].tskFormName == 'Produits') {
					this.produits = true
					this.demandeAutFraService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {
						this.status = data.data
					})
					break
				}
			}
		})
	}

	openDialogAddProduits(data) {
		const dialog1 = this.dialogRef.open(AddProduitsComponent, {
			width: '700px',
			data: { data: data, status: this.status }
		}).afterClosed().subscribe(result => {
			location.reload();
			this.listDemandeAutFra();
		});
	}

	motifrejetourenvoi = false
	verifyMotifrejetourenvoi() {
		this.demandeAutFraService.getAllTask().subscribe(data => {
			this.task = data
			console.log(this.task.data)
			for (let i = 0; i < this.task.data.length; i++) {
				if (this.task.data[i].poOwner.proId == localStorage.getItem("profil") && this.task.data[i].tskFormName == 'Motifrejetourenvoi') {
					this.motifrejetourenvoi = true
					this.demandeAutFraService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {
						this.status = data.data
					})
					break
				}
			}
		})
	}

	openDialogAddMotifrejetourenvoi(data) {
		const dialog1 = this.dialogRef.open(AddMotifrejetourenvoiComponent, {
			width: '700px',
			data: { data: data, status: this.status }
		}).afterClosed().subscribe(result => {
			location.reload();
			this.listDemandeAutFra();
		});
	}

	suivredemande = false
	verifySuivredemande() {
		this.demandeAutFraService.getAllTask().subscribe(data => {
			this.task = data
			console.log(this.task.data)
			for (let i = 0; i < this.task.data.length; i++) {
				if (this.task.data[i].poOwner.proId == localStorage.getItem("profil") && this.task.data[i].tskFormName == 'Suivredemande') {
					this.suivredemande = true
					this.demandeAutFraService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {
						this.status = data.data
					})
					break
				}
			}
		})
	}

	openDialogAddSuivredemande(data) {
		const dialog1 = this.dialogRef.open(AddSuivredemandeComponent, {
			width: '700px',
			data: { data: data, status: this.status }
		}).afterClosed().subscribe(result => {
			location.reload();
			this.listDemandeAutFra();
		});
	}
}
