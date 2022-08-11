import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import {  merge, Observable } from 'rxjs';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { ParametreOtp } from '../../models/parametreotp';
import { GenerateurSService } from '../../service/generateur-s.service';
import { AjoutParametreComponent } from '../ajout-parametre/ajout-parametre.component';
import { EditParametreComponent } from '../edit-parametre/edit-parametre.component';
import { TestconfigurationComponent } from '../testconfiguration/testconfiguration.component';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
  displayedColumns: string[] = ['libelle', 'evenement', 'type', 'dureee','duree','caractere','action'];
  otps:any;
  loading: boolean;
  code:any;
  form: FormGroup = new FormGroup({
    libelle: new FormControl(false),
    evenement: new FormControl(false),
    typeOtp:new FormControl(false),
    dureee:new FormControl(false),
    duree:new FormControl(false),
    caractere:new FormControl(false),
    action: new FormControl(false),

  });
  libelle = this.form.get('libelle');
  evenement = this.form.get('evenement');
  dureee =this.form.get('dureee');
  duree =this.form.get('duree');
  typeOtp=this.form.get('typeOtp');
  caractere=this.form.get('caractere');
  action = this.form.get('action');


  cbValues;

  columnDefinitions = [
    { def: 'libelle', label: 'Libelle', hide: false },
    { def: 'evenement', label: 'Evenement', hide: false },
    { def: 'typeOtp', label: 'Evenement', hide: false },
    { def: 'dureee', label: 'format', hide: false },
    { def: 'duree', label: 'Duree', hide: false },
    { def: 'caractere', label: 'Caractere', hide: false },
    { def: 'action', label: 'Action', hide: false }
  ]
  getDisplayedColumns(): string[] {
    return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
  }

  openDialogAddModule(element){
     const dialog1 = this.dialog.open(TestconfigurationComponent, {
      disableClose: true,
      data: element

    }).afterClosed().subscribe(data => {
      this.getAllParametre();
    });
  }

  applyFilterModule(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  dataSource: MatTableDataSource<ParametreOtp>;
  constructor(private otpS:GenerateurSService,private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog, private translate:TranslateService, private notification: NotificationService) {
      this.loading=false;
    }

  ngAfterViewInit() {
    let o1: Observable<boolean> = this.libelle.valueChanges;
    let o2: Observable<boolean> = this.evenement.valueChanges;
    let o3: Observable<boolean> = this.dureee.valueChanges;
    let o4: Observable<boolean> = this.duree.valueChanges;
    let o5:Observable<boolean> = this.caractere.valueChanges;
    let o6:Observable<boolean> = this.typeOtp.valueChanges;
    let o7:Observable<boolean> = this.action.valueChanges;
    merge(o1, o2, o3,o4,o5,o6,o7).subscribe(v => {
      this.columnDefinitions[0].hide = this.libelle.value;
      this.columnDefinitions[1].hide = this.evenement.value;
      this.columnDefinitions[2].hide = this.dureee.value;
      this.columnDefinitions[3].hide = this.duree.value;
      this.columnDefinitions[4].hide = this.caractere.value;
      this.columnDefinitions[5].hide = this.typeOtp.value;
      this.columnDefinitions[6].hide = this.action.value;
      console.log(this.columnDefinitions);
    });

  }

  ngOnInit() {
    this.getAllParametre();
  }

  openDialogGenerateurotp(element){

  }

  openDialogEditModule(element): void {
    const dialog1 = this.dialog.open(EditParametreComponent, {
      disableClose: true,
      data: element,
      width:'500px'

    }).afterClosed().subscribe(data => {
      this.getAllParametre();
    });
  }

  supprimerModule(element){
    let alertSupp;
    this.translate.get('application.module.confirm-suppression').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("application.module.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;
        this.otpS.deleteParametre(element.idParametreOtp).subscribe((data:any) => {
          this.getAllParametre();
          this.loading = false;
          this.notification.info(data.description);

        });
      }
    });
  }

  openAddParametreOtp():void{
    const dialog1 = this.dialog.open(AjoutParametreComponent, {
      disableClose: true,
      width:'500px'
    }).afterClosed().subscribe(data => {
      this.getAllParametre();

    });
  }



  getAllParametre(){
    this.otpS.getAllGenerateurOtp()
    .subscribe(data=>{
      if(data.statut){
        this.otps=data.data

        this.dataSource = new MatTableDataSource<ParametreOtp>(this.otps);
        console.log(this.dataSource);
      }else{
        console.log(data.description);
      }

    })

  }




}
