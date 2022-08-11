import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { ParametreService } from 'src/app/utilisateur/services/parametre.service';
import { WidgetService } from '../services/widget.service';
import { ApplicationService } from 'src/app/application/services/application.service';
import * as Highcharts from 'highcharts';
import { Options } from "highcharts";
import HC_exporting from 'highcharts/modules/exporting';
import { MatDialog, MatOption, MatSelect } from '@angular/material';
import { FormGroup } from '@angular/forms';
import { VisualiserboxComponent } from './widgets/visualiserbox/visualiserbox.component';
import { StyleManagerService } from 'src/app/shared/style-manager.service';

@Component({
  selector: 'home-content',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  profilId:any = localStorage.getItem('profil');
  profile:any;
  box:any='box'
  chartOptions = {
    responsive: true
  };
  MeschartLine = [];
  MeschartBar = [];
  Meschart = [];

  a;
  b;
  c;
  l;
  count;
  breakpoint: any;
  nbrCommerciaux: any;
  nbrAdmin: any;
  nbrAppli: any;
  nbrModule: any;
  nbrIntegrateur: any;
  nbrConnect: any;
  nbrUser: any;
  @Input() profil: any;
  widgets: any
  isRow: true;
  widgetTemplateAttr:any;
  Langues;
  Themes;
  n;

  selecteddiagrammes:any=[];
  selecteddiagramme :any;
  widgetgroup:any=[];

  stats:any;
  isShowUti = false;
  isShowprof = false;
   
  chartData1 = [
    { data: [0, 6, 2, 3, 6, 2, 7, 3, 6, 2, 1, 4], label: this.translate.instant('dashboard.connectes') },
    { data: [0, 14, 10, 4, 5, 10, 3, 3, 6, 6, 2, 9], label: 'Workflows' },
    { data: [0, 26, 12, 21, 30, 11, 25, 10, 34, 15, 24, 19], label: 'Formulaires' }
  ];
  chartLabels1 = ['January', 'February', 'Mars', 'April', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
  chartData2 = [
    { data: [3, 16, 2, 13, 6, 8, 7, 13, 16, 12, 11, 14], label: 'Utilisateurs' },
    { data: [2, 14, 10, 4, 5, 10, 3, 3, 6, 6, 2, 9], label: 'Connectés' },
  ];
  chartLabels2 = ['January', 'February', 'Mars', 'April', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
  
  @ViewChild('select') select: MatSelect;

  allSelected=false;
   foods: any[] = [
    {value: 'Diagramme en baton', viewValue: 'Diagramme en baton'},
    {value: 'Diagramme circulaire', viewValue: 'Diagramme circulaire'},
    {value: 'courbe', viewValue: 'courbe'}
  ];
  toggleAllSelection() {
    if (this.allSelected) {
      this.select.options.forEach((item: MatOption) => item.select());
    } else {
      this.select.options.forEach((item: MatOption) => item.deselect());
    }
  }

  allWidget:any=[];
  toggleDisplay1() {
    this.isShowUti = !this.isShowUti;
  }
  toggleDisplay2() {
    this.isShowprof = !this.isShowprof;
  }
  changeWebsite(){
    console.log("selection diagramme : "+ this.selecteddiagrammes.widgetTemplate.wtNom);
  }

  optionClick() {
    let newStatus = true;
    this.select.options.forEach((item: MatOption) => {
      if (!item.selected) {
        newStatus = false;
        this.allWidget.pop(item)
      }else{
        this.allWidget.push({
          'nom':item.viewValue
        })
      }
    });
    this.allSelected = newStatus;
  }

  toppings: FormGroup;
  constructor(private router: Router, 
    private appService: ApplicationService,
    private userService: UserService,
    public paramService: ParametreService,
    private widgetService: WidgetService,
    private usersService: UserService,
    private translate: TranslateService,
    private dialog: MatDialog) {
  }

  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 5;
  }
  
  ngOnInit() { 
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.profil = localStorage.getItem('profil');
    this.getProfile();
    this.listWidgetAttrs(this.profil);
    this.getState();
    this.nbrUsers();
  }
  
  getState(){
    this.widgetService.listeStatProfil().subscribe(res=>{
      this.stats = res.data;      
    })
  }

  lesChart() {
    for (let index = 0; index < this.widgets.length; index++) {
      if (this.widgets[index].wdgCode == 'lne_app_wkf_fml') {
        this.MeschartLine[index] = this.widgets[index];
        this.l = true;
      } else if (this.widgets[index].wdgCode == 'bar_uti_con') {
        this.MeschartBar[index] = this.widgets[index];
        this.b = true;

      }
    }
  }

  nbrmodule() {
    this.appService.nbrModule().subscribe(res => {
      this.nbrModule = res.data;
    })
  }

  nbrapplication() {
    this.appService.nbrApplication().subscribe(res => {
      this.nbrAppli = res.data;
    })
  }
 
  nbrconnect() {
    this.userService.nbrUserConnect().subscribe(res => {
      this.nbrConnect = res.data;
    })
  }

  nbrUsers() {
    this.userService.nbrUtilisateur().subscribe(res => {
      this.nbrUser = res.data;
    })
  }
 
  listLangue() {
    this.paramService.getLangue().subscribe(data => {
      this.Langues = data.data;
    });
  }

  listTheme() {
    this.paramService.getTheme().subscribe(data => {
      this.Themes = data.data;
    });
  }

  onChartClick($event) {
  }

  listWidgetAttrs(id){
    this.widgetService.checkWidgetTemplateAttr(id)
    .subscribe(resp=>{
      this.widgets=resp.data;
    },err=>{
      console.log(err);
    })
  }

  getProfile(){
    this.profile = localStorage.getItem("profileLibelle");
  }

  openNav() {
    document.getElementById("mySidepanel").style.width = "100%";
  }
  
  closeNav() {
    document.getElementById("mySidepanel").style.width = "0";
  }

  visualiserBox(parambox): void {
		const dialog = this.dialog.open(VisualiserboxComponent, {
			disableClose: true,
			width: '900px',
      data: parambox
		}).afterClosed().subscribe(result => { 
      this.getProfile();
		});
	}
}
