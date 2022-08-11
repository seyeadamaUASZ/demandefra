import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder4',
  templateUrl: './statfolder4.component.html',
  styleUrls: ['./statfolder4.component.scss']
})
export class Statfolder4Component implements OnInit {
  status: any;
  totaldemandesoumises:any = 0;
  totaldemandeencours:any = 0;
  totaldemandeacorriger:any = 0;
  totaldemanderejetees:any = 0;
  totaldemandedelivrees:any = 0;

  constructor(private widgetS:WidgetService) { }

  ngOnInit() {
    this.nombreDemandeSoumises();
    this.nombreDemandeEnCours();
    this.nombreDemandeACorriger();
    this.nombreDemandeRejetees();
    this.nombreDemandeDelivrees();
  }

  nombreDemandeSoumises(){
    this.widgetS.statistiqueChefAntenne(localStorage.getItem('id')).subscribe((data: any) => {
      this.totaldemandesoumises = data.data[0][1];
    })
  }

  nombreDemandeEnCours(){
    this.widgetS.statistiqueChefAntenne(localStorage.getItem('id')).subscribe((data: any) => {
      this.totaldemandeencours = data.data[0][2];
    })
  }

  nombreDemandeACorriger(){
    this.widgetS.statistiqueChefAntenne(localStorage.getItem('id')).subscribe((data: any) => {
      this.totaldemandeacorriger = data.data[0][3];
    })
  }

  nombreDemandeRejetees(){
    this.widgetS.statistiqueChefAntenne(localStorage.getItem('id')).subscribe((data: any) => {
      this.totaldemanderejetees = data.data[0][4];
    })
  }

  nombreDemandeDelivrees(){
    this.widgetS.statistiqueChefAntenne(localStorage.getItem('id')).subscribe((data: any) => {
      this.totaldemandedelivrees = data.data[0][5];
    })
  }
}
