import { AfterViewInit, ChangeDetectorRef, Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { merge, Observable } from 'rxjs';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { Formulairev2Service } from '../../services/formulairev2.service';
import { DetailComponent } from '../detail/detail.component';
import { EditFormComponent } from '../edit-form/edit-form.component';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit, AfterViewInit {
  dataSourceFormulaireNotGeneres: MatTableDataSource<any>;
  displayedColumnsFormulaireNotGeneres = ['frmNom','frmDescription','action'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  applyFilterNotGeneres(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceFormulaireNotGeneres.filter = filterValue.trim().toLowerCase();
  }

  form: FormGroup = new FormGroup({
		frmNom: new FormControl(false),
		frmDescription: new FormControl(false),
    frmStatus: new FormControl(false),
		action: new FormControl(false),

	});

  frmNom1 = this.form.get('frmNom');
	frmDescription1 = this.form.get('frmDescription');
  frmStatus1 = this.form.get('frmStatus');
	action1 = this.form.get('action');

  columnDefinitionsNoGeneres = [
		{ def: 'frmNom', label: 'formulaire.frmNom', hide: false },
		{ def: 'frmDescription', label: 'formulaire.frmDescription', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]

  getDisplayedColumnsNoGeneres(): string[] {
		return this.columnDefinitionsNoGeneres.filter(cd => !cd.hide).map(cd => cd.def);
	}
  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private notification: NotificationService, private translate: TranslateService,
    private formulaireV2Service: Formulairev2Service) { }
  
    ngAfterViewInit(): void {
    this.listeFormulaireNonGenerer();

  }

  ngOnInit() {
    let o11: Observable<boolean> = this.frmNom1.valueChanges;
		let o22: Observable<boolean> = this.frmDescription1.valueChanges;
    let o33: Observable<boolean> = this.action1.valueChanges;

		merge(o11, o22, o33).subscribe(v => {
			this.columnDefinitionsNoGeneres[0].hide = this.frmNom1.value;
			this.columnDefinitionsNoGeneres[1].hide = this.frmDescription1.value;
			this.columnDefinitionsNoGeneres[2].hide = this.action1.value;
			
		});
  }

  listeFormulaireNonGenerer(){
    this.formulaireV2Service.getList().subscribe((data:any)=>{
      this.dataSourceFormulaireNotGeneres = new MatTableDataSource(data.data);
      this.dataSourceFormulaireNotGeneres.paginator = this.paginator;
      this.dataSourceFormulaireNotGeneres.sort = this.sort;

    })
  }
  openDialogAddForm(): void {
    const dialog1 = this.dialog.open(EditFormComponent, {
      disableClose: true,
      // width: '700px',

    }).afterClosed().subscribe(result => {
			if(result.status){
        this.listeFormulaireNonGenerer();
      }
		});

  }
  openDialogEditForm(element){
    const dialog1 = this.dialog.open(EditFormComponent, {
      disableClose: true,
      data: element
      // width: '700px',

    }).afterClosed().subscribe(result => {
			if(result.status){
        this.listeFormulaireNonGenerer();
      }
		});
  }

  openDialogDetail(element){
    const dialog1 = this.dialog.open(DetailComponent, {
      disableClose: true,
      data: element,
      width: 'fit-content'

    }).afterClosed().subscribe(result => {
			
		});
  }

  supprimerFormulaire(element){

    let alertSupp;
    this.translate.get('configuration.confirm-suppression').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.formulaireV2Service.deleteForm(element).subscribe((data:any)=>{
          if(data.statut){
            this.translate.get("notif.confirmSuppression").subscribe((res: string)=>{
              this.notification.success(res);
            });
            this.listeFormulaireNonGenerer();
          }
        })
      }
    });

  }

}
