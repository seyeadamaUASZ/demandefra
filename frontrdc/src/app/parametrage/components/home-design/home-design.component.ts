import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { field, value} from 'src/app/global.model';
import { Widget } from '../../models/widget';
import { WidgetService } from '../../services/widget.service';
import { Profil } from '../../models/profil';
import { ProfilService } from '../../services/profil.service';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { FormControl, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ParametreService } from '../../services/parametre.service';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-home-design',
  templateUrl: './home-design.component.html',
  styleUrls: ['./home-design.component.scss']
})
export class HomeDesignComponent implements OnInit {
  profilSelected:any;
  allWidgets: any;
  widgetByProfils: any;
  widgetByProfilNoAttr = [];
  widgetByProfilAttr = [];
  profils: Profil[];
  public profil: Profil; 
  profilControl = new FormControl('', Validators.required);
  value:value={
    label:"",
    value:""
  };
  success = false;
  loading: boolean;
  modelFields:Array<Widget>=[];
  model:any = {
    name:'Aperçu...',
    description:'App Description...',
    theme:{
      bgColor:"ffffff",
      textColor:"555555",
      bannerImage:""
    },
  
  };
  constructor(private widgetService:WidgetService,
    private profilService: ProfilService,
    private translate: TranslateService,
    private notification: NotificationService,
    private userService:UserService,
    public paramService: ParametreService,private userServices:UserService) {
      this.loading=false;
     }

  ngOnInit() {
    this.listeWidgets();
    this.profilService.listeProfils().subscribe(data => {
      this.profils = data.data;
      console.log(this.profils);
    });
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.nbrconnect();
    this.nbrintegrateur();
    this.nbrapplication();
    this.nbrmodule();
    this.nbrFichiers();
    this.nbrFormulaires();
    this.nbrWorkflows();

  }

  listeWidgets() {
    this.widgetService.listeWidget().subscribe(data=>{
      this.allWidgets = data.data;
      this.widgetByProfilNoAttr = data.data;
    });
  }
  nbrFichiers(){
    this.userService.nbrFichier().subscribe(res=>{
      this.nbrFichier = res.data;
    })
  }
  nbrFormulaires(){
    this.userService.nbrFormulaire().subscribe(res=>{
      this.nbrFormulaire = res.data;
    })
  }
  nbrWorkflows(){
    this.userService.nbrWorkflow().subscribe(res=>{
      this.nbrWorkflow = res.data;
    })
  }

  allWidgetByProfil(profil:any) {
    this.widgetService.allWidgetByProfil(profil).subscribe(data=>{
      this.widgetByProfils = data.data;
      this.widgetByProfilAttr = data.data; 
      this.widgetByProfilNoAttr = this.difference(this.allWidgets, this.widgetByProfilAttr);        
    });
  }

  ngAfterViewInit() {      
      this.profilControl.setValue(this.profil);     
  }


  profilSelection() {       
    this.profil = this.profilControl.value;
    if (this.profil) {
      this.widgetService.listeWidget().subscribe(data=>{
        this.allWidgets = data.data;
      });
      this.allWidgetByProfil(this.profil);
    } else {
      this.widgetByProfils = [];
      this.widgetByProfilAttr = [];
      // this.widgetByProfilNoAttr = this.allWidgets;
      this.listeWidgets();
    }

  }

compareProfil(p1:Profil,p2:Profil): boolean{       
    return p1 && p2 ? p1.proId === p2.proId : false;   
}

widgetByPro = [];

addAttr(event: CdkDragDrop<string[]>) {
  if (event.previousContainer === event.container) {
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    // this.done.push(event.container.data[event.previousIndex]);
    // alert("*** ADD " +JSON.stringify(this.widgetByProfilAttr)+ " ***");
  } else {
    transferArrayItem(event.previousContainer.data,
                      event.container.data,
                      event.previousIndex,
                      event.currentIndex);
                      // alert("*** ADD " +this.widgetByProfilAttr.length+ " ***");
  }
  this.orderWidgetByProfilAttr();
  // alert("*** ADD " +JSON.stringify(this.widgetByProfilAttr)+ " ***");
}

dropAttr(event: CdkDragDrop<string[]>) {
  if (event.previousContainer === event.container) {
    moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    // this.done.push(event.container.data[event.previousIndex]);
  } else {
    // console.log("*** " +JSON.stringify(event.container.data)+ " ***");
    transferArrayItem(event.previousContainer.data,
                      event.container.data,
                      event.previousIndex,
                      event.currentIndex);
  }
}


  save() {let p = new Profil();
    p.proId = this.profil.proId;
    this.widgetService.allWidgetByProfil(this.profil).subscribe(data=>{
      let widgets = data.data;
      this.orderWidgetByProfilAttr();
      let removedWedget = this.intersect(widgets, this.widgetByProfilNoAttr );
      let widgetByProfilAttr = this.difference(this.widgetByProfilAttr, widgets);
      let widgetByProfilUpdate = this.difference(this.widgetByProfilAttr, widgetByProfilAttr);
      this.widgetService.attribuerWidget(this.profil, removedWedget, widgetByProfilAttr, widgetByProfilUpdate).subscribe(data => {        
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
					this.notification.success(res);
			  	});
          /*this._snackBar.open(data.description, 'Verification', {
            duration: 2000,
      });*/
  
  
        } else {
          this.translate.get(data.description).subscribe((res: string) => {
					this.notification.warn(res);
			  	});
          /*this._snackBar.open(data.description, 'Verification', {
            duration: 2000,
      });*/
        }
    
      });
      // alert("*** ADD " +JSON.stringify(widgetByProfilAttr)+ " ***");
      // alert("*** UPDATE " +JSON.stringify( widgetByProfilUpdate )+ " ***");
       
    });
  }

  intersect(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    wedgets1.map(function (item1) {
      wedgets2.map(function (item2) {
        if (item1.wdgId === item2.wdgId) {
          result.push(item1);
        }
      })
    });
    return result;
  }

  difference(wedgets1: any[], wedgets2: any[]) {
    let result = [];
    result = wedgets1.filter(item1 => !wedgets2.some(item2 => (item2.wdgId === item1.wdgId)));
    return result;
  }

  chartOptions = {
    responsive: true
  };
  breakpoint:any;
  nbrCommerciaux:any;
  nbrAdmin:any;
  nbrAppli:any;
  nbrModule:any;
  nbrIntegrateur:any;
  nbrConnect:any;
  widgets: any
  nbrUser;
  Langues;
  Themes;
  nbrFichier:any;
  nbrFormulaire:any;
  nbrWorkflow:any;
   chartData1 = [
    { data: [0, 6, 2, 3,6, 2, 7,3, 6, 2,1,4], label: 'Applications' },
    { data: [0, 14, 10, 4, 5, 10, 3,3, 6, 6,2,9], label: 'Workflows' },
    { data: [0, 26, 12, 21, 30, 11, 25, 10, 34, 15,24,19], label: 'Formulaires' }
  ];
  chartLabels1 = ['January', 'February', 'Mars', 'April','Mai','Juin','Juillet','Aout','Septembre','Octobre','Novembre','Décembre'];
  chartData2 = [
    { data: [3, 16, 2, 13,6, 8, 7,13, 16, 12,11,14], label: 'Utilisateurs' },
    { data: [2, 14, 10, 4, 5, 10, 3,3, 6, 6,2,9], label: 'Connectés' },
  ];
  chartLabels2 = ['January', 'February', 'Mars', 'April','Mai','Juin','Juillet','Aout','Septembre','Octobre','Novembre','Décembre'];
  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 5;
  }
 
  nbrmodule(){
    this.userService.nbrModule().subscribe(res=>{
      this.nbrModule = res.data;
    })
  }
  nbrapplication(){
    this.userService.nbrApplication().subscribe(res=>{
      this.nbrAppli = res.data;
    })
  }
  nbrintegrateur(){
    this.userService.nbrIntegrateur().subscribe(res=>{
      this.nbrIntegrateur = res.data;
    })
  }
  nbrconnect(){
    this.userService.nbrUserConnect().subscribe(res=>{
      this.nbrConnect = res.data;
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

  orderWidgetByProfilAttr() {
    let i = 1;
    this.widgetByProfilAttr.forEach(w => {
      w.wdgOrdre = i;
      i = i+1;
    });
  }

 
}
